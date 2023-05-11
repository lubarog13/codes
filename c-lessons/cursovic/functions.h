#include "header.h"

string checkOpenInputFile(string message);
string checkOpenOutputFile(string message);
void outContent (string* mass, int start, int count);
void readFile (string message, string* mass, int& count);
int switchLine(int oldline_index, int count, bool under);
string inputString(string message);
char inputChar(string message);
int inputInt(string message);
int inputInt(string message, int min, int max);
string addSymbols(string input, string substring, char symbol, int count);
void cycleAdd(int start,int array_length, string* mass);
string replaceSymbols(string input, string substring, string replacement, int count);
void cycleRelace(int start,int array_length, string* mass);
string deleteSymbols(string input, string substring, int count);
void cycleDelete(int start, int array_length, string* mass);
void SafeFile(string* mass, int array_length);