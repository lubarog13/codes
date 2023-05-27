#include <iostream>
#include <iomanip>

using namespace std;

const int N = 10;

int main()
{
    setlocale(LC_ALL, "Russian");

    int A[N][N];
    int n;
    int c;

    cout << "Введите размерность матрицы: ";
    cin >> n;
    while ((n <= 0) || (n > N))
    {
        cout << "Ошибка! Размер квадратной матрица должен быть больше нуля\n";
        cin >> n;
    }
    cout << endl;

    cout << "Введите элементы массива: \n";
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++)
        {
            cin >> A[i][j];
        }
    }
    cout << endl;

    cout << "Исходный массив: \n";
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++)
        {
            cout << setw(7) << right << A[i][j];
        }
        cout << endl;
    }
    cout << endl;

    cout << "Введите число, которое нужно вычесть: ";
    cin >> c;

    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n-1-i; j++)
        {
            A[i][j] -= c;
        }
        cout << endl;
    }

    cout << "Преобразованный массив: \n";
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++)
        {
            cout << setw(7) << right << A[i][j];
        }
        cout << endl;
    }
    cout << endl;

    return 0;
}


