#include <cerrno>
#include <csignal>
#include <cstdio>
#include <cstdlib>
#include <cstring>

#include <sys/wait.h>
#include <unistd.h>


int main(int argc, char *argv[])
{
	if (argc != 4) {
		printf("Использование: %s входной_файл выходной_нечетные выходной_четные\n",
		       argv[0]);
		return 1;
	}

	FILE *fp = fopen(argv[1], "r");
	if (fp == nullptr) {
		perror(argv[1]);
		return 1;
	}

	int fildes[2]; // канал для передачи данных
	if (pipe(fildes) == -1) {
		perror("pipe");
		return 1;
	}

	char fd_str[16]; // строка для передачи дескриптора канала
	snprintf(fd_str, sizeof(fd_str), "%d", fildes[0]);

	// блокировка синхронизирующих сигналов (наследуются потомками)
	sigset_t set;
	sigemptyset(&set);
	sigaddset(&set, SIGQUIT);
	sigaddset(&set, SIGUSR1);
	sigaddset(&set, SIGUSR2);
	if (sigprocmask(SIG_BLOCK, &set, nullptr) == -1) {
		perror("sigprocmask");
		return 1;
	}

	pid_t pid_1 = fork(); // создание первого потомка
	if (pid_1 == -1) {
		perror("fork");
		return 1;
	}
	if (pid_1 == 0) {
		close(fildes[1]); // закрытие канала для записи
		if (setpgid(0, 0) == -1) { // установка группы процессов
			perror("setpgid");
			_exit(1);
		}
		execl("./lab7_odd", "lab7_odd", argv[2], fd_str, nullptr); // запуск программы для нечетных строк
		perror("execl lab7_odd");
		_exit(1);
	} 

	pid_t pid_2 = fork(); // создание второго потомка
	if (pid_2 == -1) {
		perror("fork");
		return 1;
	}
	if (pid_2 == 0) {
		close(fildes[1]);
		if (setpgid(0, pid_1) == -1) { // установка группы процессов
			perror("setpgid");
			_exit(1);
		}
		execl("./lab7_even", "lab7_even", argv[3], fd_str, nullptr); // запуск программы для четных строк
		perror("execl lab7_even");
		_exit(1);
	}

	close(fildes[0]); // закрытие канала для чтения
	// установка группы процессов для первого и второго потомков
	if (setpgid(pid_1, pid_1) == -1 && errno != EACCES) {
		perror("setpgid 1");
		return 1;
	}
	if (setpgid(pid_2, pid_1) == -1 && errno != EACCES) {
		perror("setpgid 2");
		return 1;
	}

	// построчная запись всего файла в канал
	char ch[512];
	while (fgets(ch, sizeof(ch), fp) != nullptr) {
		size_t len = strlen(ch);
		size_t written = 0;
		while (written < len) {
			ssize_t n = write(fildes[1], ch + written, len - written); // запись строки в канал
			if (n < 0) {
				if (errno == EINTR) {
					continue;
				}
				perror("write");
				fclose(fp);
				return 1;
			}
			written += static_cast<size_t>(n);
		}
	}
	fclose(fp);

	// оповещение об окончании записи
	kill(pid_1, SIGQUIT);
	kill(pid_2, SIGQUIT);

	// ожидание завершения первого и второго потомков
	waitpid(pid_1, nullptr, 0);
	waitpid(pid_2, nullptr, 0);

	close(fildes[1]);
	return 0;
}
