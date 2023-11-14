//#pragma once

#include "header.h"

//const int G = 5;

struct Track
{ 
    string name;
    string author;
    string performer;
    string genre; // string genre[G] Поставить запятые между жанрами, даже в конце, ПОСЛЕ ПОСЛЕДНЕГО. иСПОЛЬЗОВАТЬ FING в сортировке 
    int year;
};

void InputData(Track* data);
void OutData (Track* data);
void EditData (Track* data);
void ReadData(Track* data, ifstream &fin);
void SaveData (Track* data, ofstream &fout);
void DeleteData();

// +
void InputData(Track* data)
{
    data->name = InputString("Введите название произведения: ");
    data->author = InputString("Введите автора: ");
    data->performer = InputString("Введите исполнителя: ");
    data->genre = InputString("Введите жанры: (с запятой на конце)"); // Учесть ситуацию, когда пользователь случайно не ставит запятую
    data->year = InputInt("Введите год: ", 0, 2023);
}

// +
void OutData (Track* data)
{
    cout << "Название произведения: " << data->name << endl;
    cout << "Автор: " << data->author << endl;
    cout << "Исполнитель: " << data->performer << endl;
    cout << "Жанры: " << data->genre << endl;
    cout << "Год: " << data->year << endl;
    cout << endl;
}

// +
void EditData (Track* data)
{
    cout << "Название произведения: " << data->name << endl;
    data->name = InputString("Введите новое название: ");
    cout << "Автор: " << data->author << endl;
    data->author=InputString("Введите нового автора: ");
    cout << "Исполнитель: " << data->performer << endl;
    data->performer=InputString("Введите нового исполнителя: ");
    cout << "Жанры: " << data->genre << endl;
    data->genre = InputGenre("Введите новые жанры: ");
    cout << "Год: " << data->year << endl;
    data->year = InputInt("Введите новый год: ", 0, 2023);
    cout << endl;
}

// +
void ReadData(Track* data, ifstream &fin)
{
    data->name = InputStringFile(fin);
    data->author = InputStringFile(fin);
    data->performer = InputStringFile(fin);
    data->genre = InputGenreFile(fin); // Учесть ситуацию, когда пользователь случайно не ставит запятую
    data->year = InputIntFile(fin);
}

// +
void SaveData (Track* data, ofstream &fout)
{
    fout << data->name << endl;
    fout << data->author << endl;
    fout << data->performer << endl;
    fout << data->genre << endl;
    fout << data->year << endl;
    fout << endl;
}