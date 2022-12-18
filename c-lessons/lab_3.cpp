#include <iostream>
#include <math.h>

using namespace std;

int main()
{
    setlocale(LC_ALL, "Russian");
    double x, y, r, a, b, eps;

    cout << "Введите радиус окружности: ";
    cin >> r;

    if (r <= 0) {
        cout << "Ошибка! Введите положительный радиус\n";
        return 1;
    }

    cout << "Введите коэффицент a для прямой: ";
    cin >> a;
    cout << "Введите коэффицент b для прямой: ";
    cin >> b;
    if ((pow(2 * b * a, 2) - 4 * (1 + a * a) * (pow(b, 2) - pow(r, 2))) < 0)
    //ошибается при а=2,3 и т.д. https://www.mathway.com/ru/Graph - проверка.
    {
        cout << "Прямая не пересекает окружность\n";
        return 2;
    }

    cout << "Введите погрешность: ";
    cin >> eps;
    if ((eps <= 0) || (eps >= 1)) {
        cout << "Ошибка! Неверно введена погрешность, требуется от 0 до 1\n";
        return 3;
    }


    cout << "Введите x точки: ";
    cin >> x;
    cout << "Введите y точки: ";
    cin >> y;
    if (((pow(x, 2) + pow(y, 2)) < pow(r, 2)) && (a * x + b > y) && (y < 0))
    {
        cout <<"Точка входит в область\n";
    }
    else
    {
        if ((abs((pow(x, 2) + pow(y, 2)) - (pow(r, 2))) <= eps) || ((abs((abs(a * x + b) - abs(y))) <= eps) || (abs(y) <= eps)))
        {
            cout << "Точка лежит на границе области\n";
        }
        else
        {
            cout << "Точка не входит в область\n";
        }

    }
    return 0;
}
