#include <iostream>

int main()
{
    /*Пусть M — сумма минимального и максимального натуральных делителей целого числа, не считая единицы и
     самого числа. Если таких делителей у числа нет, то считаем значение M равным нулю.

    Напишите программу, которая перебирает целые числа, большие 452 021, в порядке возрастания и ищет 
    среди них такие, для которых значение M при делении на 7 даёт в остатке 3. Вывести первые 5 найденных чисел и
    соответствующие им значения M.

    Формат вывода: для каждого из 5 таких найденных чисел в отдельной строке сначала выводится само число,
    затем — значение М. Строки выводятся в порядке возрастания найденных чисел.*/
    int count=0, min=0, max, s=452021;
    while(count!=5){
        s++;
        min=0;
        max=0;
        for(int i=2; i<s; i++){
            if(s%i==0){
                if(min==0) min=i;
                max=i;
            }
        }
        if((min+max)%7==3){
            std::cout<<s<<" "<<min+max<<std::endl;
            count++;
        }
    }
    return 0;
}