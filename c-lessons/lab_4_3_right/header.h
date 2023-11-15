#pragma once

#include <iostream>
#include <fstream>
#include <limits>
#include <string>

using namespace std;


int InputInt(string message, int min);
int InputInt(string message, int min, int max);
string InputString(string message);
string InputGenre(string message);

string CheckOpenInputFile(string message);
string CheckOpenOutputFile(string message);

string InputStringFile(ifstream &fin);
string InputGenreFile(ifstream &fin);
int StringToInt(string s);
int InputIntFile(ifstream &fin);


// Операции ввода с консоли ...................................................................................

int InputInt(string message, int min)
{
    int n;
    cout << message << endl;
    while (!(cin >> n) || (n < min))
    {
        cout << "Ошибка: Неправильно введено число!" << endl;
        cin.clear();
        cin.ignore(numeric_limits<streamsize>::max(), '\n');;
        cout << message;
    }
    // cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');;
    return n;
}

int InputInt(string message, int min, int max)
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
    while ((n < min) || (n > max));
    // cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');;
    
    return n;
}

string InputString(string message)
{
    string str = "";
    cout << message << endl;
    getline(cin, str, '\n');
    return str;
}

string InputGenre(string message)
{
    string str = "";
    cout << message << endl;
    while (str[str.size()-1]!=',' )
    {
        getline(cin, str, '\n');
        if (str[str.size()-1]!=',')
        {
            cout << "Ошибка! Неверно записаны жанры: " << str << " Они должны быть через запятую!" << endl;
        }
    }
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
            cout << "Ошибка. Имя или путь к файлу неправильный.\n";
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
            cout << "Ошибка. Имя или путь к файлу неправильный.\n";
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

string InputStringFile(ifstream &fin)
{
    string str = "";
    if (!fin.eof())
    {
        getline(fin, str);
    }
    return str;
}

string InputGenreFile(ifstream &fin)
{
    string str = ",";
    int x=0;
    int i=0;
    if (!fin.eof())
    {
        getline(fin, str);
        /*while (str[i]!='\0')
        {
            if (str[i]==',')
            {
                x++;
                //cout << "Считано " << x << "жанров"<<endl;
            }
            i++;
        }*/
        if (str[str.size()-1]!=',')
        {
            cout << "Ошибка! Неверно записаны жанры: " << str << " Они должны быть через запятую!" << endl;
            cout << "Строка жанры станет пустой"<< endl;
            str=",";

        }
    }
    /*if (x==0)
    {
        cout << "Срока жанры пуста" << endl;
    }*/
    return str;
}

int StringToInt(string s)
{
    int x = 0;
    if (s.find_first_not_of("0123456789") != s.npos || s.empty())
    {
        cout << "Ошибка! В файле вместо числа стоят посторонние символы: "<< s <<" Число будет заменено на 0." << endl;
    }
    else
    {
        //x = stoi(s.c_str());
        x = stoi(s);
    }
    return x;
}

int InputIntFile(ifstream &fin)
{
    string str = InputStringFile(fin);
    int x = StringToInt(str);
    return x;
}
