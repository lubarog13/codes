#include "functions.h"

int main() {
    string full_string, substring, adding_string, new_string;
    full_string = inputString("Введите строку:");
    substring = inputString("Введите подстроку:");
    adding_string = inputString("Введите строку, которую будете добавлять:");
    new_string = addSubstring(full_string, substring, adding_string);
    cout << "Новая строка: "<<endl << new_string << endl;
    return 0;
}