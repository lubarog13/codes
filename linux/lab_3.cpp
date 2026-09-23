#include <cstdio>
#include <cstdlib>
#include <fcntl.h> // Для работы с файлами
#include <sys/wait.h> // Для wait
#include <unistd.h> // Для fork, vfork, execl, getpid, getppid, getuid, getgid, usleep

void write_info(const char *file_name, const char *name, int delay)
{
    usleep(delay * 1000); // Задержка в миллисекундах
    int file = open(file_name, O_WRONLY | O_APPEND | O_CREAT, 0644);
    dprintf(file, "%s: PID=%d, PPID=%d, UID=%d, GID=%d\n",
            name, getpid(), getppid(), getuid(), getgid()); // Запись информации о процессе в файл
    close(file);
}

int main(int argc, char *argv[])
{
    if (argc == 4 && argv[1][0] == '-') {
        write_info(argv[2], "Потомок2", atoi(argv[3]));
        return 0;
    }

    if (argc != 5) {
        printf("Использование: %s файл задержка_предка "
               "задержка_потомка1 задержка_потомка2\n", argv[0]);
        return 1;
    }

    int file = open(argv[1], O_WRONLY | O_CREAT | O_TRUNC, 0644);
    dprintf(file,
            "Задержки (мс): предок=%s, потомок1=%s, потомок2=%s\n\n",
            argv[2], argv[3], argv[4]);
    close(file);

    if (fork() == 0) { // Создание первого потомка
        write_info(argv[1], "Потомок1", atoi(argv[3]));
        return 0;
    }

    if (vfork() == 0) { // Создание второго потомка с использованием vfork
        execl(argv[0], argv[0], "-child2", argv[1], argv[4], NULL); // Замена процесса потомка на новый процесс
        _exit(1); // Если execl не удается, завершаем процесс с ошибкой
    }

    write_info(argv[1], "Предок", atoi(argv[2]));
    wait(NULL); // Ожидание завершения потомков
    wait(NULL);
    return 0;
}
