/*Напишите программу, которая ищет среди целых чисел, принадлежащих числовому отрезку [120115; 120200],
 число, имеющее максимальное количество различных натуральных делителей, если таких чисел несколько — найдите 
 максимальное из них. Выведите на экран количество делителей такого числа и само число.*/
 #include <iostream>
#include <fstream>
using namespace std;

int main()
{
    int count=0, maxcount=0, max=0;
    for(int i=120115;i<=120200; i++){
        count=0;
        for(int j=1; j<=i; j++){
            if(i%j==0) count++;
        }
        if(count>=maxcount){
            maxcount = count;
            max = i;
        }
    }
    cout<<maxcount<<" "<<max;
    return 0;
}