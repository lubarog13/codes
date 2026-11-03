#include "lab_7.h"

#include <cerrno>
#include <cstdio>
#include <cstdlib>

#include <fcntl.h>
#include <unistd.h>

volatile sig_atomic_t pipe_write_is_finished = 0; // флаг окончания записи в канал

void quit_handler(int)
{
	pipe_write_is_finished = 1;
}

// установка обработчика сигнала SIGQUIT
void setup_quit_handler()
{
	struct sigaction sigact{};
	sigact.sa_handler = quit_handler;
	sigemptyset(&sigact.sa_mask);
	sigact.sa_flags = 0;
	if (sigaction(SIGQUIT, &sigact, nullptr) == -1) {
		perror("sigaction");
		_exit(1);
	}

		// разблокировка SIGQUIT (остальные сигналы остаются заблокированными)
	sigset_t quit_set;
	sigemptyset(&quit_set);
	sigaddset(&quit_set, SIGQUIT);
	if (sigprocmask(SIG_UNBLOCK, &quit_set, nullptr) == -1) {
		perror("sigprocmask");
		_exit(1);
	}
}

// принимает канал, имя выходного файла, сигнал ожидания и сигнал отправки
void child_work(int pipe_fd, const char *out_name, int wait_sig, int send_sig)
{
	FILE *out = fopen(out_name, "w");
	if (out == nullptr) {
		perror(out_name);
		_exit(1);
	}

	// чтение без блокировки
	int flags = fcntl(pipe_fd, F_GETFL);
	fcntl(pipe_fd, F_SETFL, flags | O_NONBLOCK);

	sigset_t wait_set;
	sigemptyset(&wait_set);
	sigaddset(&wait_set, wait_sig);

	// чётный потомок сначала ждёт сигнал от нечётного
	if (wait_sig == SIGUSR2) {
		int sig = 0;
		sigwait(&wait_set, &sig);
	}

	char ch = 0;
	int n = read(pipe_fd, &ch, 1); // чтение символа из канала
	if (n < 0 && (errno == EAGAIN || errno == EWOULDBLOCK || errno == EINTR)) {
		n = 0;
	}

	// пока не закончится запись в канал или канал не пуст
	while (!pipe_write_is_finished || n > 0) {
		if (n > 0) {
			fputc(ch, out);
			fflush(out);
			// передача сигнала всей группе процессов
			if (kill(0, send_sig) == -1) {
				perror("kill");
				_exit(1);
			}
			int sig = 0; // ожидание сигнала
			sigwait(&wait_set, &sig);
		}

		n = read(pipe_fd, &ch, 1); // чтение символа из канала
		if (n < 0 && (errno == EAGAIN || errno == EWOULDBLOCK || errno == EINTR)) {
			n = 0;
		} else if (n < 0) {
			perror("read");
			fclose(out);
			_exit(1);
		}
	}

	// передача сигнала всей группе процессов перед выходом
	kill(0, send_sig);

	fclose(out);
	close(pipe_fd);
	_exit(0);
}
