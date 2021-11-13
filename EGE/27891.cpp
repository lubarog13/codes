#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;

int main()
{
    /*Последовательность натуральных чисел характеризуется числом Х — наибольшим числом, 
    кратным 14 и являющимся произведением двух элементов последовательности с различными номерами. 
    Гарантируется, что хотя бы одно такое произведение в последовательности есть.*/
    ifstream in;
    in.open("27891-B.txt");
    int a, max2=0, max7=0, max=0, max14=0;
    in>>a;
    while(!in.eof()){
        in>>a;
        if(a%14==0 && a>max14) max14=a;
        else if(a%2==0 && a>max2) max2=a;
        else if(a%7==0 && a>max7) max7=a;
        if(a%14!=0 && a>max) max=a;
    }
    if(max*max14>max2*max7) cout<<max*max14;
    else cout<<max7*max2;
    return 0;
}