#pragma once
#include "header.h"

int inputInt(string message);
int inputInt(string message, int min);
int inputInt(string message, int min, int max);
string inputString(string message);

string checkOpenInputFile(string message);
string checkOpenOutputFile(string message);