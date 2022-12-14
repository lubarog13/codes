#include <iostream>
#include <math.h>

int main() {
    float a=1, b=5, x0, x, e=0.01, n=0, f1, f2;
    int option;
    do {
        std::cout<<"Программа вычисляет точку пересечения графика функции с осью абсцисс. Выберите опцию: "<<std::endl<<"\n1, чтобы ввести начало и конец интервала\n2, чтобы ввести погрешность\n3, чтобы начать вычисления\n0, чтобы выйти\n";
        std::cin>>option;
        switch (option) {
            case 1:
                std::cout<<"Введите начало интервала: ";
                std::cin>>a;
                std::cout<<"Введите конец интервала: ";
                std::cin>>b;
                break;
            case 2:
                do {
                    std::cout<<"Введите погрешность: ";
                    std::cin>>e;
                    if (e<=0) {
                        std::cout<<"Погрешность должна быть больше 0\n";
                    }
                } while(e<=0);
                break;
            case 3:
                f1=pow(exp(b), (-0.5))-pow((b-0.8), 2) + 0.1;
                f2=(1-8 * exp(0.5*b))/(4*exp(0.5*b));
                if(f1*f2>0) x=b;
                else x=a;
                do{
                    x0=x;
                    f1=pow(exp(x0), (-0.5))-pow((x0-0.8), 2) + 0.1;
                    f2=exp(-0.5*x0)*(0.5)-2*x0+1.6;
                    x=x0-f1/f2;
                    n++;
                    std::cout<<"На шаге "<<n<<" значение x= "<<x<<". Погрешность = "<<abs(x-x0)<<". Значение функции ="<<f1<<". Значение производной функции = "<<f2<<"\n";
                } while (abs(x-x0)>e);
                std::cout<<"\n\n\nТочка пересечения графика с осью абсцисс = ("<<x<<",0), достигнуто за "<<n<<" шагов\n\n";
                break;
            case 0:
                break;
            default:
                std::cout<<"Не вверно введена опция меню\n";
                break;
        }
    } while (option!=0);
    return 0;
}
