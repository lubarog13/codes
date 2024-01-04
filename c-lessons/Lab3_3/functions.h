#pragma once

#include "header.h"

int InputInt(string message, int min);
int InputInt(string message, int min, int max);
string InputString(string message);
string InputStringArray(string message);

string CheckOpenInputFile(string message);
string CheckOpenOutputFile(string message);

string ReadString(ifstream &fin);
string ReadStringArray(ifstream &fin);
int StringToInt(string s);
int ReadInt(ifstream &fin);