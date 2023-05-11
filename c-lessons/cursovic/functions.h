#include "header.h"

int inputInt(string message);
int inputInt(string message, int min, int max);
char inputChar(string message);
string inputString(string message);

string checkOpenInputFile(string message);
string checkOpenOutputFile(string message);

int switchLine(int current_index, int array_length, bool under);

void outContent (string* mass, int array_length, int current);

void addSymbols(string &input, string substring, char symbol, int count);
void cycleAdd(string* mass, int array_length, int current);
void replaceSymbols(string &input, string substring, int count, string replacement);
void cycleRelace(string* mass, int array_length, int current);
void deleteSymbols(string &input, string substring, int count);
void cycleDelete(string* mass, int array_length, int current);

void readFile (string* mass, int& array_length);
void saveFile(string* mass, int array_length);
