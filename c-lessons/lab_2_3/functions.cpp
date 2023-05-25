#include "functions.h"

#include <cstddef>
#include <iostream>
#include <string>

int inputInt(string message, int min)
{
    int n;
    cout << message << endl;
    while ((!(cin >> n)) || (n < min))
    {
        cout << "Неправильно введено число." << endl;
        cin.clear();
        cin.ignore(numeric_limits<streamsize>::max(), '\n');;
        cout << message;
    }
    // cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');;

    return n;
}

char inputChar(string message) {
    char ch;
    cout << message << endl;
    while (!(cin >> ch))
    {
        cout << "Неправильно введен символ" << endl;
        cin.clear();
        cin.ignore(numeric_limits<streamsize>::max(), '\n');
        cout << message << endl;
    }
    cin.ignore(numeric_limits<streamsize>::max(), '\n');
    return ch;
}

string inputString(string message) {
    string str = "";
    cout << message << endl;
    getline(cin, str, '\n');
    return str;
}

void addSymbols(string &input, string substring, char symbol, int count) {
    size_t i = input.find(substring);
    if (i==string::npos) {
        return;
    }
    input.insert(i, count, symbol);
    return;
}

void outputString(string input) {
    cout << input << endl;
}
