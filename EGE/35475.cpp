#include <iostream>
bool isSimple(int a){
    for(int i=2; i<a; i++){
        if(a%i==0) return false;
    }
    return true;
}

int main()
{
    /*Назовём натуральное число подходящим, если у него ровно 3 различных простых делителя. 
    Например, число 180 подходящее (его простые делители — 2, 3 и 5), а число 12 — нет (у него только два различных простых делителя). 
    Определите количество подходящих чисел, принадлежащих отрезку [10 001; 50 000], а также наименьшее из таких чисел. 
    В ответе запишите два целых числа: сначала количество, затем наименьшее число.*/
    int count=0, min=0, n;
    for(int i=10001; i<=50000; i++){
        n=0;
        for(int j=2; j<i; j++){
            if(i%j==0 && isSimple(j)) n++;
        }
        if(n==3){
            count++;
            if(min==0) min=i;
        }
    }
    std::cout<<count<<min;
    return 0;
}