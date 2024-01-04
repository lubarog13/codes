#include "data.h"

void NewData(Track* &data)
{
    data = new Track;
}

void InputData(Track* data)
{
    data->name = InputString("Введите название произведения: ");
    data->author = InputString("Введите автора: ");
    data->performer = InputString("Введите исполнителя: ");
    data->genre = InputStringArray("Введите жанры (с запятой на конце): ");
    data->year = InputInt("Введите год (от 0 до 2023): ", 0, 2023);
}

void OutData(Track* data)
{
    cout << "Название произведения: " << data->name << endl;
    cout << "Автор: " << data->author << endl;
    cout << "Исполнитель: " << data->performer << endl;
    cout << "Жанры: " << data->genre << endl;
    cout << "Год: " << data->year << endl;
}

void ReadData(Track* data, ifstream &fin)
{
    data->name = ReadString(fin);
    data->author = ReadString(fin);
    data->performer = ReadString(fin);
    data->genre = ReadStringArray(fin);
    data->year = ReadInt(fin);
}

void SaveData(Track* data, ofstream &fout)
{
    fout << data->name << endl;
    fout << data->author << endl;
    fout << data->performer << endl;
    fout << data->genre << endl;
    fout << data->year << endl;
}

void DeleteData(Track* &data)
{
    delete data;
    data = NULL;
}