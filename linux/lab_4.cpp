#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <fcntl.h> // Для работы с файлами
#include <pthread.h> // Для потоков
#include <unistd.h> // Для close

struct thread_arg {
    int file;
    const char *line;
};

void *write_line(void *arg)
{
    thread_arg *data = (thread_arg *)arg;
    dprintf(data->file, "%s", data->line); // Запись полученной строки в свой файл
    return NULL;
}

int main(int argc, char *argv[])
{
    if (argc != 4) {
        printf("Использование: %s входной_файл выходной_нечетные выходной_четные\n",
               argv[0]);
        return 1;
    }

    FILE *input = fopen(argv[1], "r"); // Входной файл
    if (input == NULL) {
        perror(argv[1]);
        return 1;
    }

    int odd_file = open(argv[2], O_WRONLY | O_CREAT | O_TRUNC, 0644); // Нечетные строки
    int even_file = open(argv[3], O_WRONLY | O_CREAT | O_TRUNC, 0644); // Четные строки
    if (odd_file < 0 || even_file < 0) {
        perror("open");
        return 1;
    }

    char *odd_line = NULL;
    char *even_line = NULL;
    size_t odd_cap = 0;
    size_t even_cap = 0;

    while (getline(&odd_line, &odd_cap, input) != -1) { // Чтение нечетной строки
        pthread_t odd_thread, even_thread;
        thread_arg odd_arg = {odd_file, odd_line};
        int odd_result = pthread_create(&odd_thread, NULL, write_line, &odd_arg); // Поток для нечетной строки
        if (odd_result != 0) {
            printf("Ошибка при создании потока для нечетной строки: %s\n", strerror(odd_result));
            return 1;
        }

        int has_even = getline(&even_line, &even_cap, input) != -1; // Чтение четной строки
        thread_arg even_arg = {even_file, even_line};
        if (has_even) {
            int even_result = pthread_create(&even_thread, NULL, write_line, &even_arg); // Поток для четной строки
            if (even_result != 0) {
                printf("Ошибка при создании потока для четной строки: %s\n", strerror(even_result));
                return 1;
            }
        }

        pthread_join(odd_thread, NULL); // Ожидание завершения потоков
        if (has_even) {
            pthread_join(even_thread, NULL);
        }
    }

    free(odd_line);
    free(even_line);
    fclose(input);
    close(odd_file);
    close(even_file);
    return 0;
}
