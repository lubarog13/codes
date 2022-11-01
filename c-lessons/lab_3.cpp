#include <iostream>
#include <math.h>

int main() {
    double x, y, r, a, b;
    std::cout<<"Введите x: ";
    std::cin>>x;
    std::cout<<"Введите y: ";
    std::cin>>y;
    std::cout<<"Введите радиус окружности: ";
    std::cin>>r;
    std::cout<<"Введите коэффицент a: ";
    std::cin>>a;
    std::cout<<"Введите коэффицент b: ";
    std::cin>>b;
    if(x > 0) {
        std::cout<<"Точка не входит в область";
        return 0;
    } else if (a*x + b > y ) {
        std::cout<<"Точка не входит в область";
        return 0;
    } else if (sqrt(pow(x, 2) + pow(y, 2)) >= r) {
        std::cout<<"Точка не входит в область";
        return 0;
    }
    std::cout<<"Точка входит в область\n";
    return 0;
}
