#include "header2.h"
int inputInt(string message, int min);
string inputString(string message); 

string checkOpenInputFile(string message);
string checkOpenOutputFile(string message);

void outContent (string** mass, int mass_length);
int CheckStr(string file, int& mass_length);
void deleteSymbols(string &input, string substring, int count);
void cycleDelete(string** mass, int mass_length);

void readFile (string file, string** mass, int& mass_length);
void SaveFile(string** mass, int mass_length);
