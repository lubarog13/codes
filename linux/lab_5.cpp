#include <cstdio>
#include <cstdlib>
#include <csignal> // Для signal, SIGFPE, SIGSEGV
#include <unistd.h> // Для _exit

void handle_error(int sig)
{
    if (sig == SIGFPE) { // Деление на ноль
        printf("Ошибка: деление на ноль\n");
        fflush(stdout);
        _exit(1);
    }

    printf("Ошибка: нарушение защиты памяти\n"); // Неверное использование указателя
    fflush(stdout);
    _exit(2);
}

int main(int argc, char *argv[])
{
    if (argc != 2) {
        printf("Использование: %s тип_ошибки (1 — деление на ноль, 2 — неверный указатель)\n",
               argv[0]);
        return 1;
    }

    int error_type = atoi(argv[1]);
    if (error_type != 1 && error_type != 2) {
        printf("Неверный тип ошибки: укажите 1 или 2\n");
        return 1;
    }

    struct sigaction act; // Структура для установки обработчика сигналов
    act.sa_handler = handle_error;
    sigemptyset(&act.sa_mask);
    act.sa_flags = 0;
    if (sigaction(SIGFPE, &act, NULL) == -1) {
        perror("Не удалось установить обработчик ошибки деления на ноль");
        return 1;
    }
    if (sigaction(SIGSEGV, &act, NULL) == -1) {
        perror("Не удалось установить обработчик ошибки нарушения защиты памяти");
        return 1;
    }
    
    if (error_type == 1) {
        int a = 1;
        int b = 0;
        int c = a / b; // Деление на ноль
        (void)c;
    } else {
        int *p = NULL;
        *p = 1; // Нарушение защиты памяти
    }

    return 0;
}
