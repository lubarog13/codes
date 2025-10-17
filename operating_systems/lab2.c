#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
//написать программу, которая создает дерево указанного разммера (в ширну легче, в глубину сложнее)

void var1() {
    while(1) {
        char *command = malloc(100);
        scanf("%s", command);
        system(command);
        free(command);
    }
}

void var2() {
        char *command = malloc(100);
        scanf("%s", command);
        int id = fork();
        int pid = getpid();
        int ppid = getppid();
        printf("pid: %d\n", pid);
        printf("ppid: %d\n", ppid);
        if (id == 0) {
            sleep(40);
        } else {
            waitpid(id, NULL, 0);
        }
        free(command);
}

void var3() {
        char *command = malloc(100);
        int i, *p = &i;
        i = 1;
        scanf("%s", command);
        int id = fork();
        int pid = getpid();
        int ppid = getppid();
        if (id == 0) {
            i = 2;
        }
        printf("pid: %d, ppid: %d, i: %d, p: %p, *i: %d \n", pid, ppid, i, p, *p);
        waitpid(id, NULL, 0);
        printf("pid: %d, ppid: %d, i: %d, p: %p, *i: %d \n", pid, ppid, i, p, *p);
        free(command);
}


void makeProcessTree(int depth) {
    if (depth == 0) {
        return;
    }
    int id = fork();
    if (id == 0) {
        printf("pid: %d, depth: %d\n", getpid(), depth);
        makeProcessTree(depth - 1);
    } else {
        waitpid(id, NULL, 0);
    }
}

int main(int argc, char **argv, char **env) {
    //var1();
    //var2();
    //var3();
    int count = atoi(argv[1]);
    makeProcessTree(count);
    return 0;

}