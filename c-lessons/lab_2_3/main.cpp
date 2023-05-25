#include "functions.h"

int main() {
    string full_string, substring;
    char input_char;
    int count;
    full_string = inputString("Введите строку:");
    substring = inputString("\nВведите контекст:");
    input_char = inputChar("\nВведите символ, который вы собираетесь добавить:");
    count = inputInt("\nВведите количество добавляемых символов:", 0);
    addSymbols(full_string, substring, input_char, count);
    outputString("\nНовая строка: \n" + full_string);
    return 0;
}