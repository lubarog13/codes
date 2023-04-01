#include "functions.h"

int main() {
    // размерность массива
    int n;
    //Заданное число
    int k = 3;
    getSize(&n);
    //массив чисел
    int a[n][n];
    cout<<"Введите элементы массива\n";
    assign(a[0], n);
    cout<<"Введенный массив:\n";
    output(a[0], n);
    cout << "Введите число для вычитания: ";
    getNumber(&k);
    subArray(a[0], n, k);
    cout<<"Результат выполнения:\n";
    output(a[0], n);
    return 0;
}