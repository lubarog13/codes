#include "functions2.h"
int main ()
{
    int* int_mass[N];
    int mass_length=0;
    int rows=0, columns=0;
    int count=0;
    setlocale(LC_ALL, "Russian");
    //SetConsoleOutputCP(CP_UTF8);
    readFile(int_mass, rows, columns);
    cout << " \n";
    cout << "Исходный текст: \n";
    outContent (int_mass, mass_length);
    cout << " \n";
    ChangeSub(int_mass, mass_length);
    cout << "Преобразованный массив: \n";
    outContent (int_mass, mass_length);
    cout << " \n";
    SaveFile(int_mass, mass_length);
    cout << " \n";
    return 0;
}
