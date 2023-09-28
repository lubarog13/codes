#include "header2.h"
int inputInt(string message, int min);
string inputString(string message); 
void replaceSymbols(string &input, string substring, int count, string replacement);
string checkOpenInputFile(string message);
string checkOpenOutputFile(string message);
void outContent (int* mass, int mass_length);
void ChangeSub(int* mass, int mass_length);
void readFile (int** mass, int& rows, int &columns);
void SaveFile(int* mass, int mass_length);