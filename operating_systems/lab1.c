#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int find_option(char **env) {
    for (int i = 0; env[i] != NULL; i++) {
        if (strncmp(env[i], "MAIN_OUTPUT_OPTION=", 19) == 0) {
            return atoi(env[i] + 19);
        }
    }
    return 0;
}

int main(int argc, char **argv, char **env) {
    int option = find_option(env);
    if (option == 0) {
    for (int i = 0; i < argc; i++) {
        printf("argv[%d] = %s\n", i, argv[i]);
    }
    }
    else {
    for (int i = 0; env[i] != NULL; i++) {
        printf("env[%d] = %s\n", i, env[i]);
    }
}
 return argc;

}