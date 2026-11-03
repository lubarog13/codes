#include "lab_7.h"

#include <cstdio>
#include <cstdlib>

// Чётные символы: сначала ждёт SIGUSR2, шлёт SIGUSR1
int main(int argc, char *argv[])
{
	if (argc != 3) {
		printf("Использование: %s выходной_файл fd_канала\n", argv[0]);
		return 1;
	}

	int pipe_fd = atoi(argv[2]);
	setup_quit_handler();
	child_work(pipe_fd, argv[1], SIGUSR2, SIGUSR1);
	return 0;
}
