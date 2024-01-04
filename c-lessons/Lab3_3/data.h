#pragma once

#include "functions.h"

struct Track
{ 
    string name;
    string author;
    string performer;
    string genre;
    int year;
};

void NewData(Track* &data);
void InputData(Track* data);
void OutData(Track* data);
void ReadData(Track* data, ifstream &fin);
void SaveData(Track* data, ofstream &fout);
void DeleteData(Track* &data);