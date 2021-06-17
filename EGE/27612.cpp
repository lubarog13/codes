#include <iostream>

int main()
{
    /*Рассматривается множество целых чисел, принадлежащих числовому отрезку [2050; 9166], 
    которые делятся на 7 и не делятся на 13, 14, 19, 22. Найдите количество таких чисел и максимальное из них.
     В ответе запишите два целых числа без пробелов и других дополнительных символов: сначала количество, затем максимальное число.*/
     int count=0, max=0;
     for(int i=2050; i<=9166; i++){
         if(i%7==0 && i%13!=0 && i%14!=0 && i%19!=0 && i%22!=0){
             count++;
             max=i;
         }
     }
     std::cout<<count<<max;
    return 0;
}