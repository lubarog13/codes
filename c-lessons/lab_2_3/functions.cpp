#include "functions.h"
#include <cstddef>
#include <iostream>
#include <string>

string inputString(string message) {
    string str = "";
    while (str.empty()) {
        cout << message << endl;
        getline(cin, str);
    }
    return str;
}

string addSubstring(string input, string substring, string adding) {
    size_t i = input.find(substring);
    if (i==string::npos) {
        cout << "Подстрока не найдена" << endl;
        return input;
    }
    i = input.find(substring, i+1);
    if (i==string::npos) {
        cout << "Подстрока найдена только один раз" << endl;
        return input;
    }
    input.insert(i + substring.length(), adding);
    return input;
}