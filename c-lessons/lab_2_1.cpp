#include <iostream>
using namespace std;

void assign(int *arr, int n)
{
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++) {
            cin >>  *(arr+ i*n + j);
        }
    }
}

void output(int *arr, int n)
{
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++) {
            cout << *(arr+ i*n + j) << " ";
        }
        cout << endl;
    }
}

void subArray(int *arr, int n, int k) {
     for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++) {
            if(j > n - i - 2) break;
             *(arr+ i*n + j) -= k;
        }
        cout << endl;
    }
}

int main() {
    // размерность массива
    int n;
    //Заданное число
    int k = 3;
    cout << "Введите размер двумерного массива\n";
    cin >> n;
    //массив чисел
    int a[n][n];
    cout<<"Введите элементы массива\n";
    assign(a[0], n);
    cout<<"Введенный массив:\n";
    output(a[0], n);
    cout << "Введите число для вычитания: ";
    cin >> k;
    subArray(a[0], n, k);
    cout<<"Результат выполнения:\n";
    output(a[0], n);
    return 0;
}