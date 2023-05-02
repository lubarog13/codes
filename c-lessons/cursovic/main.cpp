#include "functions.h"

int main() {
    string full_string, substring, adding_string, new_string;
    full_string = inputString("Введите строку:");
    substring = inputString("Введите подстроку:");
    new_string = deleteSymbols(full_string, substring,  3);
    cout << "Новая строка: "<<endl << new_string << endl;
    return 0;
}