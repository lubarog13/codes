#include "functions.h"

// Операции ввода с консоли ...................................................................................

int InputInt(string message, int min)
{
    int n;
    cout << message;
    while ((!(cin >> n)) || (n < min))
    {
        cout << "Ошибка! Неправильно введено число. Оно должно быть больше " << min << "." << endl;
        cin.clear();
        cin.ignore(numeric_limits<streamsize>::max(), '\n');
        cout << message;
    }
    // cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');
    return n;
}

int InputInt(string message, int min, int max)
{
    int n;
    cout << message;
    while ((!(cin >> n)) || (n < min) || (n > max))
    {
        cout << "Ошибка! Неправильно введено число. Оно должно быть больше " << min << " и меньше " << max << "." << endl;
        cin.clear();
        cin.ignore(numeric_limits<streamsize>::max(), '\n');
        cout << message;
    }
    // cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');
    return n;
}

string InputString(string message)
{
    string str = "";
    cout << message << endl;
    getline(cin, str, '\n');
    return str;
}

string InputStringArray(string message)
{
    string str = ",";
    cout << message << endl;
    do
    {
        getline(cin, str, '\n');
        if ((str.size() == 0) || (str[str.size() - 1] != ','))
        {
            cout << "Ошибка! Неправильно введены значения. Они должны быть записаны через запятую (и должны оканчиваться на запятую)." << endl;
        }
    }
    while ((str.size() == 0) || (str[str.size() - 1] != ','));
    return str;
}



// Операции над файлами .......................................................................................

string CheckOpenInputFile(string message)
{
    string filename;
    bool is_open = false;
    while (!is_open)
    {
        filename = InputString(message);
        ifstream fs(filename, ios::in);
        if (!fs.is_open())
        {
            cout << "Ошибка! Имя или путь к файлу неправильный.\n";
            is_open = false;
        }
        else
        {
            fs.close();
            is_open = true;
        }
    }
    return filename;
}

string CheckOpenOutputFile(string message)
{
    string filename;
    bool is_open = false;
    while (!is_open)
    {
        filename = InputString(message);
        ofstream fs(filename, ios::out);

        if (!fs.is_open())
        {
            cout << "Ошибка! Путь к файлу неправильный.\n";
            is_open = false;
        }
        else
        {
            fs.close();
            is_open = true;
        }
    }
    return filename;
}

// Операции ввода с файла .....................................................................................

string ReadString(ifstream &fin)
{
    string str = "";
    if (!fin.eof())
    {
        getline(fin, str);
    }
    return str;
}

string ReadStringArray(ifstream &fin)
{
    string str = ",";
    if (!fin.eof())
    {
        getline(fin, str);
        if ((str.size() == 0) || (str[str.size() - 1] != ','))
        {
            cout << "Ошибка! Неправильно записаны значения. Они должны быть записаны через запятую (и оканчиваться на запятую). Программа допишет запятую в конце строки." << endl;
            str += ",";
        }
    }
    return str;
}

int StringToInt(string str)
{
    int n = 0;
    if (str.find_first_not_of("0123456789") != str.npos || str.empty())
    {
        cout << "Ошибка! В файле вместо числа стоят посторонние символы. Число будет заменено на 0." << endl;
    }
    else
    {
        //n = stoi(str.c_str());
        n = stoi(str);
    }
    return n;
}

int ReadInt(ifstream &fin)
{
    string str = ReadString(fin);
    int n = StringToInt(str);
    return n;
}