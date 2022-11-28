#include <iostream>

using namespace std;

int main()
{
    /*Назовём натуральное число подходящим, если у него ровно 3 различных простых делителя. 
    Например, число 180 подходящее (его простые делители — 2, 3 и 5), а число 12 — нет (у него только два различных простых делителя). 
    Определите количество подходящих чисел, принадлежащих отрезку [10 001; 50 000], а также наименьшее из таких чисел. 
    В ответе запишите два целых числа: сначала количество, затем наименьшее число.*/
    int count=0, min=0, n, a=0, b=100000, isSimple=true, menu=0;
    do {
    cout<<"Выберите опцию: 1 - ввести начало диапазона, 2 - ввести конец диапазона, 3 - начать вычисления, 0 - выйти из программы\n";
    cin>>menu;
    switch(menu) {
        case 1:
        do {
            cout<<"Введите начало диапазона: ";
            cin>>a;
            if (a >= b) {
                cout<<"Начало диапазона больше конца, конец сейчас "<<b<<"\n";
            }
            if (a<=0) {
                cout<<"Число должно быть больше либо равным 0\n";
            }
        } while (b<=a || a<=0);
        break;
        case 2:
        do {
            cout<<"Введите конец диапазона: ";
            cin>>b;
            if (a >= b) {
                cout<<"Конец диапазона больше начала, начало сейчас "<<a<<"\n";
            }
            if (b<=0) {
                cout<<"Число должно быть больше либо равным 0\n";
            }
        } while (b<=a || b<=0);
        break;
        case 3:
            for(int i=a; i<=b; i++){
                n=0;
                for(int j=2; j<i; j++){
                    isSimple = true;
                    for(int k=2; k<j; k++){
                        if(j%k==0) {
                            isSimple = false;
                            break;
                        }
                    }
                    if(i%j==0 && isSimple) n++;
                }
                if(n==3){
                    count++;
                    if(min==0) min=i;
                }
            }

            cout<<"Количество чисел с тремя различными простыми делителями в диапазоне ["<<a<<";"<<b<<"] = "<<count<<". Минимальное из них: "<<min<<"\n";
            break;
        case 0:
            continue;
        default:
            cout<<"Неверно введен номер команды \n";
            break;
    }
    } while (menu!=0);
    return 0;
}
