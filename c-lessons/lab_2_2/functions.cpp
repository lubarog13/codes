#include "functions.h"

void getSize(int *n) {
    cout << "Введите размер двумерного массива\n";
    do {
    getNumber(n);       
    if (*n <= 0) {
        cout << "Размер должен быть больше или равным 0"<<endl;
    } 
    } while (*n <= 0);
}

void getNumber(int *k) {
    while (!(cin >> *k)) {
                cin.clear();
                cin.ignore();
                cout << "Введите число\n";
    } 
}

void assign(int *arr, int n)
{
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++) {
            cout << "Введите элемент ["<<i<<"]["<<j<<"]"<<endl;
            getNumber(arr+ i*n + j);
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