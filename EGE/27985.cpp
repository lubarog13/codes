#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;

int main()
{
    /*По каналу связи передавалась последовательность положительных целых чисел,
     все числа не превышают 1000. Количество чисел известно. Затем передаётся контрольное 
     значение последовательности — наибольшее число R, удовлетворяющее следующим условиям:

    1) R — произведение двух различных переданных элементов последовательности («различные» означает, 
    что не рассматриваются квадраты переданных чисел, произведения различных элементов последовательности,
     равных по величине, допускаются);

    2) R делится на 14.*/
      ifstream in;
      in.open("27985_B.txt");
      int a, max=0, max7=0, max2=0, max14=0;
      in>>a;
      while(!in.eof()){
          in>>a;
          if(a%14==0 && a>max14) max14=a;
          else if(a%7==0 && a>max7) max7=a;
          else if(a%2==0 && a>max2) max2=a;
          if(a>max && a%14!=0) max=a;
      }
      if(max*max14>max2*max7) cout<<max*max14;
      else cout<<max2*max7;
      in.close();
    return 0;
}