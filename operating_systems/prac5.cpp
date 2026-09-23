#include <signal.h>
#include <iostream>
#include <unistd.h>

#include <sys/mman.h> // for shared memory created
#include <sys/stat.h> // for mode constants
#include <fcntl.h> // for O_* constant
#include <string.h> // for strcpy
#include <stdlib.h>

#define SHARED_OBJ_NAME "/shared_obj"

/*
Требуется создать процесс, который запускает два дочерних процесса.

Один из дочерних процессов периодически посылает текстовые сообщения второму.

Оба процесса информируют процесс-родитель о посылке и получении сообщения,

а процесс родитель печатает эту информацию в консоли. При завершеии родительского процесса он завершает дочерние процессы.
*/

struct message {
    int pid;
    char msg[100];
    int reciever_pid;
};

void send_message(int pid, int reciever_pid, std::string msg) {
    int shmFd = shm_open(SHARED_OBJ_NAME, O_CREAT | O_RDWR, S_IRUSR | S_IWUSR);
    ftruncate(shmFd, sizeof(message));
    message *msg_ptr = (message*)mmap(NULL, sizeof(message), PROT_READ | PROT_WRITE, MAP_SHARED, shmFd, 0);
    msg_ptr->pid = pid;
    strcpy(msg_ptr->msg, msg.c_str());
    msg_ptr->reciever_pid = reciever_pid;
    munmap(msg_ptr, sizeof(message));
    close(shmFd);
    std::cout << "Message sent from " << getpid() << " to " << pid << ": " << msg << std::endl;
}

bool receive_message(int pid, char* curr_msg, int* reciever_pid) {
    int shmFd = shm_open(SHARED_OBJ_NAME, O_RDWR, S_IRUSR | S_IWUSR);
    ftruncate(shmFd, sizeof(message));
    message *msg_ptr = (message*)mmap(NULL, sizeof(message), PROT_READ | PROT_WRITE, MAP_SHARED, shmFd, 0);
    if (getpid() != msg_ptr->pid)
    {
        return false;
    }
    else
    {
        std::cout << "Process " << getpid() << ": Receive " << msg_ptr->msg << " from PID " << msg_ptr->reciever_pid << std::endl;
        strcpy(curr_msg, msg_ptr->msg);
        *reciever_pid = msg_ptr->reciever_pid;
        munmap(msg_ptr, sizeof(message));
    }

    close(shmFd);
    return true;
}

int main() {
    printf("Init the initial value.\n");
    pid_t pid1 = fork();
    pid_t pid2 = fork();
    
    printf("pid1: %d, pid2: %d\n", pid1, pid2);
    if (pid1 == 0 && pid2 != 0) {
        printf("Child process 1 started\n");
        send_message(getppid(), getpid(), "Some message from child process " + std::to_string(getpid()));
    }
    while (true) {
        char curr_msg[100];
        int reciever_pid;
        if (pid1 == 0 && pid2 != 0) {
            if (receive_message(getppid(), curr_msg, &reciever_pid))  {
                printf("receive message from pid1\n");
                sleep(1);
                send_message(getppid(), getpid(), "Some message from child process " + std::to_string(getpid()));
                continue;
            };
        }
        else if (pid2 == 0 && pid1 != 0) {
            if (receive_message(getppid(), curr_msg, &reciever_pid))  {
                printf("receive message from pid2\n");
                sleep(2);
                send_message(getppid(), getpid(), "Some message from child process " + std::to_string(getpid()));
                continue;
            };
        } 
        else if (pid1 != 0 && pid2 != 0) {
            if (receive_message(pid1, curr_msg, &reciever_pid))  {
                if (reciever_pid == pid2) {
                    printf("send message to pid1\n");
                    send_message(pid1, pid2, curr_msg);
                }
                else {
                    printf("send message to pid2\n");
                    send_message(pid2, pid1, curr_msg);
                }
            }
            if (receive_message(pid2, curr_msg, &reciever_pid))  {
                if (reciever_pid == pid1) {
                    printf("send message to pid2\n");
                    send_message(pid2, pid1, curr_msg);
                }
                else {
                    printf("send message to pid1\n");
                    send_message(pid1, pid2, curr_msg);
                }
            }
        }
        sleep(2);
    }
    // shm_unlink(SHARED_OBJ_NAME);

    return 0;

}