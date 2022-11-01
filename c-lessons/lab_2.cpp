#include <iostream>
#include <math.h>

using namespace std;

int main()
{
    double x=3.961, y=-1.625;
    double g;
    std::cout<<"Введите x: ";
    std::cin>>x;
    std::cout<<"Введите y: ";
    std::cin>>y;
    g = pow(2, -1*x) * sqrt(x+y);
    std::cout<<"Результат вычисления выражения 2^-x*sqrt(x+y), при x="<<x<<"y="<<y<<": "<<g<<"\n";
    return 0;
}
