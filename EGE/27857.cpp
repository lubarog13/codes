#include <iostream>

int main()
{
    /*Напишите программу, которая ищет среди целых чисел, принадлежащих числовому отрезку [84052; 84130], 
    число, имеющее максимальное количество различных натуральных делителей, если таких чисел несколько — найдите минимальное 
    из них. Выведите на экран количество делителей такого числа и само число.
    */
   int count, maxcount=0, max=0;
   for(int i=84052; i<=84130; i++){
       count=0;
       for(int j=1; j<=i; j++){
           if(i%j==0) count++;
       }
       if (count>maxcount){
           maxcount=count;
           max=i;
       }
   }
   std::cout<<maxcount<<" "<<max;
    return 0;
}