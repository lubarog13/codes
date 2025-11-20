#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>

void var1() {
    // Бесконечный цикл для непрерывного выполнения команд
    while(1) {
        // Выделение памяти для команды
        char *command = malloc(100);
        // Чтение команды с клавиатуры
        scanf("%s", command);
        // Выполнение команды
        system(command);
        // Освобождение памяти
        free(command);
    }
}

void var2() {
    // Выделение памяти для команды
    char *command = malloc(100);
    // Чтение команды с клавиатуры
        scanf("%s", command);
    // Создание нового процесса
    int id = fork();
    // Получение ID текущего процесса
        int pid = getpid();
        // Получение ID родительского процесса
        int ppid = getppid();
        // Вывод ID текущего процесса и родительского процесса
        printf("pid: %d\n", pid);
        printf("ppid: %d\n", ppid);
        // Если ID текущего процесса равен 0, то это дочерний процесс
        if (id == 0) {
            // Засыпание процесса на 40 секунд
            sleep(40);
        } else {
            // Ожидание завершения дочернего процесса
            waitpid(id, NULL, 0);
        }
        // Освобождение памяти
        free(command);
}

void var3() {
    // Выделение памяти для команды
    char *command = malloc(100);
    // Чтение команды с клавиатуры
    int i, *p = &i;
    i = 1;
    // Чтение команды с клавиатуры
    scanf("%s", command);
    // Создание нового процесса
    int id = fork();
    // Получение ID текущего и родительского процесса
    int pid = getpid();
    int ppid = getppid();
    // Если ID текущего процесса равен 0, то это дочерний процесс
    if (id == 0) {
        // Изменение значения i
        i = 2;
    }
    // Вывод ID текущего процесса, родительского процесса, значения i, указателя на i и значения по указателю на i
    printf("pid: %d, ppid: %d, i: %d, p: %p, *i: %d \n", pid, ppid, i, p, *p);
    // Ожидание завершения дочернего процесса
    waitpid(id, NULL, 0);
    // Вывод ID текущего процесса, родительского процесса, значения i, указателя на i и значения по указателю на i
    printf("pid: %d, ppid: %d, i: %d, p: %p, *i: %d \n", pid, ppid, i, p, *p);
    // Освобождение памяти
    free(command);
}

void makeProcessTree(int depth, int pid) {
    // Если оставшаяся глубина равна 0, то выход из функции
    if (depth == 0) {
        return;
    }
    // Создание нового процесса
    int id = fork();
    // Если ID текущего процесса равен 0, то это дочерний процесс
    if (id == 0) {
        // Рекурсивный вызов функции для создания нового процесса
        makeProcessTree(depth - 1, pid+1);
    } else {
        // Получение статуса завершения дочернего процесса
        int status;
        // Ожидание завершения дочернего процесса
        waitpid(id, &status, 0);
        // Вывод ID текущего процесса, ID родительского процесса и статуса завершения
        printf("pid: %d, id: %d, status: %d\n", getpid(), pid, status);
    }
}

int main(int argc, char **argv, char **env) {
    //var1();
    //var2();
    //var3();
    int count = atoi(argv[1]);
    makeProcessTree(count, 0);
    return 0;

}