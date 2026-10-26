#include <cerrno>
#include <csignal>
#include <cstdio>
#include <cstdlib>
#include <cstring>

#include <fcntl.h>
#include <sys/wait.h>
#include <unistd.h>

#define TURN_SIG SIGUSR1 // один сигнал для передачи очереди

volatile sig_atomic_t got_turn = 0; // разрешение на чтение символа

void turn_handler(int)
{
	got_turn = 1;
}

// пустой обработчик для SIGQUIT
void quit_handler(int) {}

// установка обработчиков сигналов
void setup_signals()
{
	struct sigaction turn_action{};
	turn_action.sa_handler = turn_handler;
	sigemptyset(&turn_action.sa_mask);
	turn_action.sa_flags = 0;
	if (sigaction(TURN_SIG, &turn_action, nullptr) == -1) {
		perror("sigaction");
		_exit(1);
	}

	struct sigaction quit_action{};
	quit_action.sa_handler = quit_handler;
	sigemptyset(&quit_action.sa_mask);
	quit_action.sa_flags = 0;
	if (sigaction(SIGQUIT, &quit_action, nullptr) == -1) {
		perror("sigaction");
		_exit(1);
	}
}

// ожидание своей очереди
void wait_for_turn()
{
	while (!got_turn) {
		sigset_t wait_mask;
		sigemptyset(&wait_mask);
		sigsuspend(&wait_mask);
	}
	got_turn = 0;
}

// передать очередь брату через группу (один сигнал)
void pass_turn()
{
	sigset_t turn_set, old_mask;
	sigemptyset(&turn_set);
	sigaddset(&turn_set, TURN_SIG);

	// блокировка сигнала TURN_SIG
	if (sigprocmask(SIG_BLOCK, &turn_set, &old_mask) == -1) {
		perror("sigprocmask");
		_exit(1);
	}

	// передача очереди через группу
	if (kill(-getpgrp(), TURN_SIG) == -1 && errno != ESRCH) {
		perror("kill");
		_exit(1);
	}

	// ожидание сигнала TURN_SIG
	timespec zero{};
	while (sigtimedwait(&turn_set, nullptr, &zero) == TURN_SIG) {
	}

	if (sigprocmask(SIG_SETMASK, &old_mask, nullptr) == -1) {
		perror("sigprocmask");
		_exit(1);
	}
}

void child_work(int pipe_fd, const char *out_name)
{
	int out_file = open(out_name, O_WRONLY | O_CREAT | O_TRUNC, 0644);
	if (out_file < 0) {
		perror(out_name);
		_exit(1);
	}

	while (true) {
		wait_for_turn();

		char c = 0;
		int n = 0;
		do {
			n = read(pipe_fd, &c, 1);
		} while (n < 0 && errno == EINTR); // SIGQUIT / TURN_SIG не обрывают чтение

		if (n < 0) {
			perror("read");
			close(out_file);
			_exit(1);
		}
		// EOF
		if (n == 0) {
			pass_turn(); // передать очередь
			break;
		}

		if (write(out_file, &c, 1) != 1) {
			perror("write");
			close(out_file);
			_exit(1);
		}

		pass_turn();
	}

	close(out_file);
	close(pipe_fd);
	_exit(0);
}

int main(int argc, char *argv[])
{
	if (argc != 4) {
		printf("Использование: %s входной_файл выходной_нечетные выходной_четные\n",
		       argv[0]);
		return 1;
	}

	int data_pipe[2];
	if (pipe(data_pipe) == -1) {
		perror("pipe");
		return 1;
	}

	// блокировка сигналов до установки обработчиков у потомков
	sigset_t block_set, old_set;
	sigemptyset(&block_set);
	sigaddset(&block_set, TURN_SIG);
	sigaddset(&block_set, SIGQUIT);
	if (sigprocmask(SIG_BLOCK, &block_set, &old_set) == -1) {
		perror("sigprocmask");
		return 1;
	}

	pid_t odd_pid = fork();
	if (odd_pid == -1) {
		perror("fork");
		return 1;
	}
	if (odd_pid == 0) {
		close(data_pipe[1]); // закрытие канала для записи в дочернем процессе
		setup_signals();
		if (setpgid(0, 0) == -1) { // установка группы для дочернего процесса
			perror("setpgid");
			_exit(1);
		}
		sigprocmask(SIG_SETMASK, &old_set, nullptr); // снятие блокировки сигналов
		child_work(data_pipe[0], argv[2]);
	}

	pid_t even_pid = fork();
	if (even_pid == -1) {
		perror("fork");
		return 1;
	}
	if (even_pid == 0) {
		close(data_pipe[1]); // закрытие канала для записи в дочернем процессе
		setup_signals();
		if (setpgid(0, odd_pid) == -1) { // установка группы для дочернего процесса
			perror("setpgid");
			_exit(1);
		}
		sigprocmask(SIG_SETMASK, &old_set, nullptr);
		child_work(data_pipe[0], argv[3]);
	}

	close(data_pipe[0]);
	// родителю SIGQUIT не нужен
	if (std::signal(SIGQUIT, SIG_IGN) == SIG_ERR) {
		perror("signal");
		return 1;
	}
	sigprocmask(SIG_SETMASK, &old_set, nullptr);

	if (setpgid(odd_pid, odd_pid) == -1 && errno != EACCES) {
		perror("setpgid odd");
		return 1;
	}
	if (setpgid(even_pid, odd_pid) == -1 && errno != EACCES) {
		perror("setpgid even");
		return 1;
	}

	FILE *input = fopen(argv[1], "r");
	if (input == nullptr) {
		perror(argv[1]);
		return 1;
	}

	// старт первого потомка
	kill(odd_pid, TURN_SIG);

	char *line = nullptr;
	size_t cap = 0;
	while (getline(&line, &cap, input) != -1) {
		int len = strlen(line);
		int written = 0;
		while (written < len) {
			int n = write(data_pipe[1], line + written, len - written);
			if (n < 0) {
				if (errno == EINTR) {
					continue;
				}
				perror("write");
				free(line);
				fclose(input);
				return 1;
			}
			written += static_cast<int>(n);
		}
	}
	free(line);
	fclose(input);

	// оповещение группы об окончании записи
	kill(-odd_pid, SIGQUIT);
	close(data_pipe[1]); // закрытие канала для записи в родительском процессе

	int status = 0;
	waitpid(odd_pid, &status, 0);
	waitpid(even_pid, &status, 0);

	return 0;
}
