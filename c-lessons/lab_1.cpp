#include <iostream>
using namespace std;

int main()
{
    setlocale(LC_ALL, "Russian");
    int a, b, r;
    cout << "Введите первое целое число: ";
    cin >> a;
    cout << "Введите второе целое число: ";
    cin >> b;
    r = a - b;
    cout << "Разность между первым и вторым числом: " << r << "\n";
    return 0;

}
