#include <iostream>
#include <math.h>

int main() {
    float a, b, x0, x, e, n=0, f1, f2;
    int option;
    do {
        std::cout<<"Программа вычисляет точку пересечения графика функции с осью абсцисс на заданном интервале"<<std::endl<<"Введите 1, чтобы ввести данные и 2, чтобы выйти\n";
        std::cin>>option;
        if(option!=2) {
            std::cout<<"Введите начало интервала: ";
            std::cin>>a;
            std::cout<<"Введите конец интервала: ";
            std::cin>>b;
            std::cout<<"Введите погрешность: ";
            std::cin>>e;
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
                std::cout<<"На шаге "<<n<<" искомое значение = "<<x<<". Погрешность = "<<abs(x-x0)<<"\n";
            } while (abs(x-x0)>e);
            std::cout<<"\n\n\nТочка пересечения графика с осью абсцисс = ("<<x<<",0), достигнуто за "<<n<<" шагов\n\n";
        }
    } while (option!=2);
    return 0;
}
