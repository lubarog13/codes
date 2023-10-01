#include "header2.h"
int inputInt(string message);
int inputInt(string message, int min, int max);
string inputString(string message); 
void replaceSymbols(string &input, string substring, int count, string replacement);
string checkOpenInputFile(string message);
string checkOpenOutputFile(string message);
void outContent (int** mass, int rows, int columns);
void ChangeArr(int** mass, int& rows, int &columns);
void readFile (int** mass, int& rows, int &columns);
void SaveFile(int** mass, int rows, int columns);