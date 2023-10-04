#include "functions2.h"
int main ()
{
    int* int_mass[N];
    int sizes[N];
    int arr_len=0;
    setlocale(LC_ALL, "Russian");
    //SetConsoleOutputCP(CP_UTF8);
    for (int i=0; i<2; i++){
    readFile(int_mass, sizes, arr_len);
    cout << " \n";
    outContent (int_mass, sizes, arr_len);
    cout << " \n";
    }
    changeArr(int_mass, sizes, arr_len);
    outContent(int_mass, sizes, arr_len);
    /* ChangeSub(int_mass, mass_length);
    cout << "Преобразованный массив: \n";
    outContent (int_mass, mass_length);
    cout << " \n";
    */
    for (int i = 0; i<arr_len; i++) {
        SaveFile(int_mass, i, sizes[i]);
    }
    cout << " \n"; 
    memoryFree(int_mass, arr_len);
    return 0;
}
