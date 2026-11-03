#include "lab_7.h"

#include <cstdio>
#include <cstdlib>

// Нечётные символы: после символа шлёт SIGUSR2, ждёт SIGUSR1
int main(int argc, char *argv[])
{
	if (argc != 3) {
		printf("Использование: %s выходной_файл fd_канала\n", argv[0]);
		return 1;
	}

	int pipe_fd = atoi(argv[2]);
	setup_quit_handler();
	child_work(pipe_fd, argv[1], SIGUSR1, SIGUSR2);
	return 0;
}
