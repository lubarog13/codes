#include "functions.h"
#include <cstddef>
#include <iostream>
#include <string>

string checkOpenInputFile(string message) {
    string filename;
    bool is_open = false;
    while (!is_open) {
        filename = inputString(message);
        cout<<filename;
        ifstream fs(filename, ios::in);
        cout<<filename;
        if (!fs) // fs.is_open
        {
            cout << "Ошибка. Имя или путь к файлу неправильный.\n"; //Если ошибка открытия файла, то завершаем программу
            is_open = false;
        }
        else {
            cout<<filename;
            fs.close();
            is_open = true;
        }
    }
    return filename;
}

string checkOpenOutputFile(string message) {
    string filename;
    bool is_open = false;
    while (!is_open) {
        filename = inputString(message);
        ofstream fs(filename, ios::out);

        if (!fs) // fs.is_open
        {
            cout << "Ошибка. Имя или путь к файлу неправильный.\n"; //Если ошибка открытия файла, то завершаем программу
            is_open = false;
        }
        else {
            fs.close();
            is_open = true;
        }
    }
    return filename;
}



void readFile (string message, string* mass, int& count) {
    string filename;
    filename = checkOpenInputFile("Введите название файла для чтения:");
    ifstream fin(filename, ios::in);

    count = 0;
    while((!fin.eof()) && (count < N))
    {
        getline(fin, mass[count]); //Считываем строки в массив
        count++;
    }
    fin.close(); 
}

void outContent (string* mass, int start, int count) {
    for (int i=start; i<count; i++) {
        cout << "Строка номер " << i + 1 << " = " << *(mass + i) << endl;
    }
}

int switchLine(int oldline_index, int count, bool under) {
    int count2=0;
    if(under) {
        count2 = inputInt("Введите смещение вниз: ", 0, (count - oldline_index - 1));
        return oldline_index + count2;
    }
    else {
        count2 = inputInt("Введите смещение вверх: ", 0, oldline_index);
        return oldline_index - count2;
    }
    
}       

string inputString(string message) {
    string str = "";
    while (str.empty()) {
        cout << message << endl;
        getline(cin, str, '\n');
        cout<<"ok";
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
        cin.ignore(numeric_limits<streamsize>::max(), '\n');
        cout << message;
    }
    cin.ignore(numeric_limits<streamsize>::max(), '\n');
    return ch;
}

int inputInt(string message, int min, int max)
{
    int n;
    do
    {
      cout << message << endl;    
      if ((!(cin >> n)) || (n < min) || (n > max))
      {
          cout << "Размер должен быть больше " << min << " и меньше " << max << endl;
          cin.clear();
          cin.ignore(numeric_limits<streamsize>::max(), '\n');
      } 
    }
    while (n <= min || n >= max);
    // cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');;
    
    return n;
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

void SafeFile(string* mass, int count)
{
    string filename;
        filename = checkOpenOutputFile("Введите название файла для сохранения:");
        ofstream fout(filename, ios::out);  
        for (int i=0; i<count; i++) {
            fout << mass[i] << endl;
        }
        fout.close();
        cout << "Файл был сохранен"  << endl;
   
}