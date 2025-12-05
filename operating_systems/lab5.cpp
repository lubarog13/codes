#include <signal.h>
#include <iostream>
#include <unistd.h>

void sig_reaction(int sig_num) {
    std::cout << "Signal " << sig_num << " received" << std::endl;
    std::cout << "Waiting for signal..." << std::endl;
}

int main() {
    struct sigaction sa;
    sa.sa_handler = sig_reaction;
    sa.sa_flags = 0;
    sigaction(SIGTERM, &sa, NULL);
    std::cout << "Waiting for signal..." << std::endl;
    while (true) {
        sleep(1);
    }
    return 0;
}