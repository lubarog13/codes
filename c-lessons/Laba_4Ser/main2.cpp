#include "functions2.h"
int main ()
{
    string text_mass[N];
    int mass_length=0;
    int count=0;
    setlocale(LC_ALL, "Russian");
    //SetConsoleOutputCP(CP_UTF8);
    readFile(text_mass, mass_length);
    cout << " \n";
    cout << "Исходный текст: \n";
    outContent (text_mass, mass_length);
    cout << " \n";
    ChangeSub(text_mass, mass_length);
    cout << "Преобразованный текст: \n";
    outContent (text_mass, mass_length);
    cout << " \n";
    SaveFile(text_mass, mass_length);
    cout << " \n";
    return 0;
}
