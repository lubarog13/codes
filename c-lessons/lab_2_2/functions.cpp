#include "functions.h"
#include <iostream>


int inputInt(string message, int min, int max)
{
    int n;
    do
    {
      cout << message;    
      if ((!(cin >> n)) || (n <= min) || (n >= max))
      {
          cout << "Размер должен быть больше " << min << " и меньше " << max << endl;
          cin.clear();
          cin.ignore(numeric_limits<streamsize>::max(), '\n');
      } 
    }
    while (n <= min || n >= max);
    // cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');;
    
    return n;
}

int inputInt(string message)
{
    int n;
      cout << message;    
      while (!(cin >> n))
      {
         cout << "Неправильно введено число" << endl;
          cin.clear();
          cin.ignore(numeric_limits<streamsize>::max(), '\n');;
          cout << message;  
      } 
    // cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');;
    
    return n;
}


void assign(int *arr, int n)
{
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++) {
            *(arr+ i*n + j) = inputInt("Введите элемент ["+to_string(i)+"]["+to_string(j)+"]\n");
        }
    }
    cout<<endl;
}

void output(int *arr, int n)
{
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++) {
            cout << setw(20/n) << right << *(arr+ i*n + j);
        }
        cout << endl;
    }
    cout<<endl;
}

void subArray(int *arr, int n, int k) {
     for (int i = 0; i < n; i++)
    {
        for (int j = 0; j <  n - i - 1; j++)
        {
             *(arr+ i*n + j) -= k;
        }
    }
}