#include <stdio.h>
#include <math.h>
#include <conio.h>
#include <dos.h>
#include <graphics.h>
#include <stdlib.h>
#define pi 3.14159265358979323846
#define dx 100
#define dy 100
#define x0 10
#define y0 10
#define MaxX 5
#define MinX 0.5


int main(void)
{
    clrscr();
    int graph_driver,
    graph_mode,
    graph_error_code,
    midx,
    midy;
    char str[3];
    graph_driver = DETECT;
    initgraph(&graph_driver, &graph_mode, "C:\\YOGISOFT\\TC\\BGI");
    graph_error_code = graphresult();
    if (graph_error_code != grOk)
    {
        printf("error%s\n ",
        grapherrormsg(graph_error_code));
        getch();
        return 0;
    }
    midx = getmaxx();
    midy = getmaxy() / 2;
    setviewport(0,0,midx, midy, 1);
    line(x0, midy, x0, 0);
    line(x0, midy/1.7, midx, midy/1.7);
    int k=0;
    for(int i=dx; k<=MaxX; i+=dx)
    {
        k++;
        sprintf(str,"%dpi", k);
        outtextxy(i-15, midy-90, str);
        line(i, midy-100, i, midy-105);
    };
    k=1;
    for(int j=y0+28; k>=-1; j+=dy)
    {
        sprintf(str,"%d", k);
        outtextxy(12, j-7, str);
        line(x0, j, x0+5, j);
        k--;
    };
    double x1;
    double y1;
    double mmax=0;
    double mashtabx=dx/pi;
    double x,y;
    setviewport(0,0,midx,midy,0);
    x1=MinX*pi;
    do
    {
        y1=sin(x1)*sin(x1)+cos(x1)*cos(x1)*cos(x1);
        x=x1*mashtabx;
        y=y1*dy;
        putpixel(x,midy-y-100,3);
        if(y1>mmax)
        mmax=y1;
        x1+=0.0001;
    } while(x<=MaxX*dx);
    char str2[16];
    sprintf(str2, "Max value is %f", mmax);
    outtextxy(0, midy+30, str2);
    getchar();
    closegraph();
    return 0;
}