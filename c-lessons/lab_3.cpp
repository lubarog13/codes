#include <iostream>
#include <math.h>

int main() {
    double x, y, r, a, b, eps;
    std::cout<<"Введите x: ";
    std::cin>>x;
    std::cout<<"Введите y: ";
    std::cin>>y;
    std::cout<<"Введите радиус окружности: ";
    std::cin>>r;
    if(r<=0) {
        std::cout<<"Введите положительный радиус\n";
        return 0;
    }
    std::cout<<"Введите коэффицент a: ";
    std::cin>>a;
    std::cout<<"Введите коэффицент b: ";
    std::cin>>b;
    std::cout<<"Введите погрешность: ";
    std::cin>>eps;
    if(eps<=0 || eps>=1) {
        std::cout<<"Неверно введена погрешность, требуется от 0 до 1\n";
    }
    else if (y > 0 + eps) {
        std::cout<<"Точка не входит в область\n";
    } else if (a*x + b < y - eps ) {
        std::cout<<"Точка не входит в область\n";
    } else if (sqrt(pow(x, 2) + pow(y, 2)) > r+eps) {
        std::cout<<"Точка не входит в область\n";
    }
    else {
        std::cout<<"Точка входит в область\n";
    }
    return 0;
}
