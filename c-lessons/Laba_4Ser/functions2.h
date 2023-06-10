#include "header2.h"
int inputInt(string message, int min);
string inputString(string message); 
void replaceSymbols(string &input, string substring, int count, string replacement);
string checkOpenInputFile(string message);
string checkOpenOutputFile(string message);
void outContent (string* mass, int mass_length);
void ChangeSub(string* mass, int mass_length);
void readFile (string* mass, int& mass_length);
void SaveFile(string* mass, int mass_length);