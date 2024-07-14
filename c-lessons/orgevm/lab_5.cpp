#include <stdlib.h>
#include <stdio.h>
#include <conio.h>
#include <dos.h>

volatile int stopFlag = 0;
unsigned long ticks;

void interrupt (*oldTimerISR)(...);

void interrupt timerISR(...) {
    ticks--;
    delay(1000);
    if (ticks==0)
    {stopFlag=1;}
    setvect(0x1c,oldTimerISR);
}

int main() {
    clrscr();
    randomize();
    int maxSeconds;
    printf("Enter Time: ");
    scanf("%d", &maxSeconds);
    int randomSeconds = random(maxSeconds) + 1;
    printf("Random number: %d\n", randomSeconds);
    oldTimerISR = getvect(0x1c);
    ticks = randomSeconds;
    while (stopFlag==0) {
        setvect(0x1c, timerISR);
    }
    printf("End of the programm");
    getch();
    return 0;
}