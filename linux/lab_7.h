#ifndef LAB_7_H
#define LAB_7_H

#include <csignal>

// true — родитель прислал SIGQUIT (запись в канал окончена)
extern volatile sig_atomic_t pipe_write_is_finished;

void quit_handler(int);
void setup_quit_handler();
void child_work(int pipe_fd, const char *out_name, int wait_sig, int send_sig);

#endif // LAB_7_H
