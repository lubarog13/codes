#include "header2.h"
int inputInt(string message);
int inputInt(string message, int min, int max);
string inputString(string message); 
void replaceSymbols(string &input, string substring, int count, string replacement);
string checkOpenInputFile(string message);
string checkOpenOutputFile(string message);
void outContent (int** mass, int* sizes, int arr_len);
void copyElements(int* arr1, int* arr2, int count, int start);
void changeArr(int** mass, int* sizes, int arr_len) ;
void readFile (int** mass, int* sizes, int& arr_len);
void SaveFile(int** mass, int row, int size);
void memoryFree(int** mass, int mass_length);