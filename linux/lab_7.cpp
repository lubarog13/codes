#include <cerrno>
#include <csignal>
#include <cstdio>
#include <cstdlib>
#include <cstring>

#include <fcntl.h>
#include <sys/wait.h>
#include <unistd.h>

volatile sig_atomic_t got_turn = 0; // разрешение на чтение символа

// разбудить потомка после окончания записи в канал
void turn_handler(int)
{
	got_turn = 1;
}

// SIGQUIT: родитель закончил запись в канал
void quit_handler(int) {}

// настройка сигналов
void setup_signals()
{
    // настройка сигнала SIGUSR1
	struct sigaction turn_action{};
	turn_action.sa_handler = turn_handler;
	sigemptyset(&turn_action.sa_mask);
	turn_action.sa_flags = 0;
	if (sigaction(SIGUSR1, &turn_action, nullptr) == -1 ||
	    sigaction(SIGUSR2, &turn_action, nullptr) == -1) {
		perror("sigaction");
		_exit(1);
	}

    // настройка сигнала SIGQUIT
	struct sigaction quit_action{};
	quit_action.sa_handler = quit_handler;
	sigemptyset(&quit_action.sa_mask);
	quit_action.sa_flags = 0;
	if (sigaction(SIGQUIT, &quit_action, nullptr) == -1) {
		perror("sigaction");
		_exit(1);
	}
}

// ожидание сигнала SIGUSR1 или SIGUSR2
void wait_for_turn()
{
	while (!got_turn) {
		sigset_t wait_mask;
		sigemptyset(&wait_mask);
		sigsuspend(&wait_mask);
	}
    // сброс флага разрешения на чтение символа
	got_turn = 0;
}

// работа потомка
void child_work(int pipe_fd, const char *out_name, pid_t sibling, int send_sig)
{
	int out_file = open(out_name, O_WRONLY | O_CREAT | O_TRUNC, 0644);
	if (out_file < 0) {
		perror(out_name);
		_exit(1);
	}

	while (true) {
        // ожидание сигнала SIGUSR1 или SIGUSR2
		wait_for_turn();

		char c = 0;
		int n = read(pipe_fd, &c, 1); // чтение одного символа из канала
		if (n <= 0) { // если нечего читать
			kill(sibling, send_sig); // разбудить второго потомка для выхода
			break;
		}

		if (write(out_file, &c, 1) != 1) {
			perror("write");
			close(out_file);
			_exit(1);
		}

		kill(sibling, send_sig); // передать очередь другому потомку
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
	int info_pipe[2];
	int ready_pipe[2];
	if (pipe(data_pipe) == -1 || pipe(info_pipe) == -1 || pipe(ready_pipe) == -1) {
		perror("pipe");
		return 1;
	}

    // блокировка сигналов SIGUSR1, SIGUSR2, SIGQUIT
	sigset_t block_set, old_set;
	sigemptyset(&block_set);
	sigaddset(&block_set, SIGUSR1);
	sigaddset(&block_set, SIGUSR2);
	sigaddset(&block_set, SIGQUIT);
	if (sigprocmask(SIG_BLOCK, &block_set, &old_set) == -1) {
		perror("sigprocmask");
		return 1;
	}

	pid_t odd_pid = fork(); // первый потомок — нечетные символы
	if (odd_pid == -1) {
		perror("fork");
		return 1;
	}
	if (odd_pid == 0) { 
        // закрытие канала записи данных, канала для передачи PID и канала для ожидания готовности
		close(data_pipe[1]);
		close(info_pipe[1]);
		close(ready_pipe[0]);

		setup_signals();
        // сброс блокировки сигналов
		sigprocmask(SIG_SETMASK, &old_set, nullptr); 

		pid_t even_pid = 0;
		if (read(info_pipe[0], &even_pid, sizeof(even_pid)) !=
		    static_cast<int>(sizeof(even_pid))) {
			perror("read even_pid");
			_exit(1);
		}
		close(info_pipe[0]);

		char ready = 1;
		if (write(ready_pipe[1], &ready, 1) != 1) {
			perror("write ready");
			_exit(1);
		}
		close(ready_pipe[1]);

		child_work(data_pipe[0], argv[2], even_pid, SIGUSR2);
	}

	pid_t even_pid = fork(); // второй потомок — четные символы
	if (even_pid == -1) {
		perror("fork");
		return 1;
	}
	if (even_pid == 0) {
		close(data_pipe[1]);
		close(info_pipe[0]);
		close(info_pipe[1]);
		close(ready_pipe[0]);

		setup_signals();
		sigprocmask(SIG_SETMASK, &old_set, nullptr);

		char ready = 1;
		if (write(ready_pipe[1], &ready, 1) != 1) {
			perror("write ready");
			_exit(1);
		}
		close(ready_pipe[1]);

		child_work(data_pipe[0], argv[3], odd_pid, SIGUSR1);
	}

	close(data_pipe[0]);
	close(info_pipe[0]);
	close(ready_pipe[1]);
	sigprocmask(SIG_SETMASK, &old_set, nullptr);

	// передать первому потомку PID второго
	if (write(info_pipe[1], &even_pid, sizeof(even_pid)) !=
	    static_cast<int>(sizeof(even_pid))) {
		perror("write");
		return 1;
	}
	close(info_pipe[1]);

	char ready_buf[2];
	int ready_got = 0;
	while (ready_got < 2) {
		int n = read(ready_pipe[0], ready_buf + ready_got, 2 - ready_got);
		if (n <= 0) {
			perror("read ready");
			return 1;
		}
		ready_got += n;
	}
	close(ready_pipe[0]);

	FILE *input = fopen(argv[1], "r");
	if (input == nullptr) {
		perror(argv[1]);
		return 1;
	}

	kill(odd_pid, SIGUSR1); // запуск поочередного чтения с нечетного символа

	char *line = nullptr;
	size_t cap = 0;
	while (getline(&line, &cap, input) != -1) { // Построчное чтение входного файла
		int len = strlen(line);
		int written = 0;
		while (written < len) {
			int n = write(data_pipe[1], line + written, len - written);
			if (n < 0) {
				if (errno == EINTR) { // если сигнал прервал запись
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

	kill(odd_pid, SIGQUIT); // оповещение об окончании записи в канал
	kill(even_pid, SIGQUIT);
	close(data_pipe[1]); // закрытие канала — потомки получают EOF

	int status = 0;
	waitpid(odd_pid, &status, 0); // ожидание завершения потомков
	waitpid(even_pid, &status, 0);

	return 0;
}
