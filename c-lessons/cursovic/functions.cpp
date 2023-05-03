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

char inputChar(string message) {
    char ch;
    cout << message << endl;
   while (!(cin >> ch))
      {
         cout << "Неправильно введен символ" << endl;
          cin.clear();
          cin.ignore(numeric_limits<streamsize>::max(), '\n');;
          cout << message;  
      } 
    return ch;
}

int inputInt(string message)
{
    int n;
      cout << message << endl;    
      while (!(cin >> n))
      {
         cout << "Неправильно введено число" << endl;
          cin.clear();
          cin.ignore(numeric_limits<streamsize>::max(), '\n');;
          cout << message;  
      } 
    // cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');;
    
    return n;
}

string addSymbols(string input, string substring, char symbol, int count) {
    size_t i = input.find(substring);
    if (i==string::npos) {
        cout << "Подстрока не найдена" << endl;
        return input;
    }
    input.insert(i, count, symbol);
    return input;
}


string replaceSymbols(string input, string substring, string replacement, int count) {
    size_t i = input.find(substring);
    if (i==string::npos) {
        cout << "Подстрока не найдена" << endl;
        return input;
    }
    if (i < count) {
        count = i;
    }
    i -= count;
    input.replace(i, count, replacement);
    return input;
}

string deleteSymbols(string input, string substring, int count) {
    size_t i = input.find(substring);
    if (i==string::npos) {
        cout << "Подстрока не найдена" << endl;
        return input;
    }
    if (i < count) {
        count = i;
    }
    i -= count;
    input.erase(i, count);
    return input;
}