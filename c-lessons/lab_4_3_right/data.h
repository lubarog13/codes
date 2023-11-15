#pragma once

#include "header.h"


struct Track
{ 
    string name;
    string author;
    string performer;
    string genre;
    int year;
};


void InputData(Track* data);
void OutData(Track* data);
void EditData(Track* data);
void SaveData(Track* data, ofstream &fout);
void ReadData(Track* data, ifstream &fin);
//void DeleteData(Track* data);



void InputData(Track* data)
{
    data->name = InputString("Введите название произведения: ");
    data->author = InputString("Введите автора: ");
    data->performer = InputString("Введите исполнителя: ");
    //data->genre = InputGenre("Введите жанры: (с запятой на конце)");
    data->genre = InputString("Введите жанры: (с запятой на конце)");
    data->year = InputInt("Введите год: ", 0, 2023);
}

void OutData(Track* data)
{
    cout << "Название произведения: " << data->name << endl;
    cout << "Автор: " << data->author << endl;
    cout << "Исполнитель: " << data->performer << endl;
    cout << "Жанры: " << data->genre << endl;
    cout << "Год: " << data->year << endl;
}

void EditData(Track* data)
{
    cout << "Название произведения: " << data->name << endl;
    data->name = InputString("Введите новое название: ");
    cout << "Автор: " << data->author << endl;
    data->author=InputString("Введите нового автора: ");
    cout << "Исполнитель: " << data->performer << endl;
    data->performer=InputString("Введите нового исполнителя: ");
    cout << "Жанры: " << data->genre << endl;
    //data->genre = InputGenre("Введите новые жанры: ");
    data->genre = InputString("Введите новые жанры: ");
    cout << "Год: " << data->year << endl;
    data->year = InputInt("Введите новый год: ", 0, 2023);
}

void SaveData(Track* data, ofstream &fout)
{
    fout << data->name << endl;
    fout << data->author << endl;
    fout << data->performer << endl;
    fout << data->genre << endl;
    fout << data->year << endl;
}

void ReadData(Track* data, ifstream &fin)
{
    data->name = InputStringFile(fin);
    data->author = InputStringFile(fin);
    data->performer = InputStringFile(fin);
    data->genre = InputGenreFile(fin);
    data->year = InputIntFile(fin);
}
