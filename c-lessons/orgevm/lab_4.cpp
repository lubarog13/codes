#include <dos.h>
#include <conio.h>
#include <bios.h>

int x1 = 10, y1 = 10, x2 = 70, y2 = 20;

void main()
{
    int ch, x = 5, y = 5, j;
    textbackground(BLACK);
    textcolor(BLUE);
    clrscr();
    gotoxy(x, y);
    cprintf("Press Left to go ledt, Right to go right");
    window(x1, y1, x2, y2);
    textbackground(BLUE);
    textcolor(BLACK);
    clrscr();
    gotoxy(x, y);
    cprintf("*");
    while(bioskey(1)!=0);
    ch = bioskey(0);
    do
    {
        switch(ch)
        {
            case 0x4d00:
            {
                while(ch==0x4d00)
                {
                    delay(100);
                    x++;
                    clrscr();
                    gotoxy(x, y);
                    cprintf("*");
                    if(bioskey(1)!=0)
                    {
                        ch=bioskey(0);
                    }
                    if(x==60)
                    {
                        x=1;
                    }
                }
                break;
            }
            case 0x4b00:
            {
                while(ch==0x4b00)
                {
                    delay(100);
                    x--;
                    clrscr();
                    gotoxy(x, y);
                    cprintf("*");
                    if(bioskey(1)!=0)
                    {
                        ch=bioskey(0);
                    }
                    if(x==1)
                    {
                        x=60;
                    }
                }
                break;
            }
            default:
            {
                j=0;
                break;
            }
        }
    } while(j);
}