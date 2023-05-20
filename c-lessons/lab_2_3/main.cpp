#include "functions.h"

int main() {
    string full_string, substring;
    char input_char;
    int count;
    full_string = inputString("Введите строку:");
    substring = inputString("Введите контекст:");
    input_char = inputChar("Введите символ, который вы собираетесь добавить:");
    count = inputInt("Введите количество добавляемых символов:");
    addSymbols(full_string, substring, input_char, count);
    cout << "Новая строка: "<<endl << full_string << endl;
    return 0;
}