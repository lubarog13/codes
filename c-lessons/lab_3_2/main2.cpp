#include "functions2.h"
int main ()
{
    int* int_mass[N];
    int rows=0, columns=0;
    int count=0;
    setlocale(LC_ALL, "Russian");
    //SetConsoleOutputCP(CP_UTF8);
    readFile(int_mass, rows, columns);
    cout << " \n";
    cout << "Исходный массив: \n";
    outContent (int_mass, rows, columns);
    cout << " \n";
    /* ChangeSub(int_mass, mass_length);
    cout << "Преобразованный массив: \n";
    outContent (int_mass, mass_length);
    cout << " \n";
    */
    SaveFile(int_mass, rows, columns);
    cout << " \n"; 
    return 0;
}
