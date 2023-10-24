#include "header.h"
int inputInt(string message);
int inputInt(string message, int min, int max);
string inputString(string message); string checkOpenInputFile(string message);
string checkOpenOutputFile(string message);
void outContent (int* mass,  int arr_len);
void copyElements(int* arr1, int* arr2, int count, int start);
//void stretchArray(int*& mass, int arr_len);
//для сортировки
void swap(int* a, int* b);
int partition(int *arr, int low, int high);
void quickSort(int* arr, int low, int high);
void changeArr(int* &arr, int &size, int* mass1, int* mass2, int arr_len1, int arr_len2) ;
void readFile (int*& mass, int& arr_len);
void saveFile(int* mass, int size);