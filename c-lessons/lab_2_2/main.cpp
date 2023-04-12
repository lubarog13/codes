#include "functions.h"

int main() {
    // размерность массива
    int n;
    //Заданное число
    int k = 3;
    n = inputInt("Введите размер двумерного массива\n", 0, N);
    //массив чисел
    int a[n][n];
    cout<<"Введите элементы массива\n";
    assign(a[0], n);
    cout<<"Введенный массив:\n";
    output(a[0], n);
    k = inputInt("Введите число для вычитания: ");
    subArray(a[0], n, k);
    cout<<"Результат выполнения:\n";
    output(a[0], n);
    return 0;
}