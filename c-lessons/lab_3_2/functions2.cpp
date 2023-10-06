#include "functions2.h"
#include <iterator>
#include <string>

// Внесение****************************************************************************************************
int inputInt(string message)
{
    int n;
    cout << message << endl;
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

int inputInt(string message, int min, int max)
{
    int n;
    do
    {
      cout << message << endl;    
      if ((!(cin >> n)) || (n < min) || (n > max))
      {
          cout << "Размер должен быть больше " << min << " и меньше " << max << endl;
          cin.clear();
          cin.ignore(numeric_limits<streamsize>::max(), '\n');
      } 
    }
    while ((n < min) || (n > max));
    // cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');;
    
    return n;
}

string inputString(string message) {
    string str = "";
    //while (str.empty()) { // ?
        cout << message << endl;
        getline(cin, str, '\n');
    //}
    return str;
}

// Чтение***************************************************************************************************

string checkOpenInputFile(string message) {
    string filename;
    bool is_open = false;
    while (!is_open) {
        filename = inputString(message);
        ifstream fs(filename, ios::in);
        if (!fs.is_open()) // fs.is_open()
        {
            cout << "Ошибка. Имя или путь к файлу неправильный.\n";
            is_open = false;
        }
        else {
            fs.close();
            is_open = true;
        }
    }
    return filename;
}

string checkOpenOutputFile(string message) {
    string filename;
    bool is_open = false;
    while (!is_open) {
        filename = inputString(message);
        ofstream fs(filename, ios::out);

        if (!fs.is_open()) // fs.is_open()
        {
            cout << "Ошибка. Имя или путь к файлу неправильный.\n";
            is_open = false;
        }
        else {
            fs.close();
            is_open = true;
        }
    }
    return filename;
}




//Замена**********************************************************************************************************

void outContent (int** mass, int* sizes, int arr_len) {
    cout << "Список массивов:" <<endl;
    for (int i=0; i<arr_len; i++) {
        cout<<(i+1)<<". ";
        for (int j=0; j<sizes[i]; j++) {
            cout << *(mass[i] + j) << " ";
        }
        cout << endl;
    }
}  

void copyElements(int* arr1, int* arr2, int count, int start = 0) {
    int arr2_index = 0;
    for(int i=start; arr2_index<count; i++) {
        arr1[i] = arr2[arr2_index];
        arr2_index++;
    }
}

void changeArr(int** mass, int* sizes, int arr_len) 
{
    if (arr_len ==0) {
        inputString("Не считан ни один массив");
        return;
    }
    int selected_index1, selected_index2;
    selected_index1 = inputInt("Введите номер первого массива для объединения", 0, arr_len) - 1;
    selected_index2 = inputInt("Введите номер второго массива для объединения", 0, arr_len) - 1;
    int* temp = new int[sizes[selected_index1] + sizes[selected_index2]]{};
    copyElements(temp, mass[selected_index1], sizes[selected_index1]);
    copyElements(temp, mass[selected_index2], sizes[selected_index2], sizes[selected_index1]);
    sort(temp, temp + sizes[selected_index1] + sizes[selected_index2],  greater<int>());
    delete [] mass[selected_index1];
    mass[selected_index1] = new int[sizes[selected_index1] + sizes[selected_index2]]{};
    copyElements(mass[selected_index1], temp, sizes[selected_index1] + sizes[selected_index2]); 
    sizes[selected_index1] = sizes[selected_index1] + sizes[selected_index2];
    delete [] temp;
}

void saveFileByIndex(int **mass, int* sizes, int arr_len) {
    if (arr_len ==0) {
        inputString("Не считан ни один массив");
        return;
    }
    int row = inputInt("Введите номер массива для сохранения", 0, arr_len) - 1;
    saveFile(mass, row, sizes[row]);
}

//Открытие и закрытие*********************************************
void saveFile(int** mass, int row, int size)
{
    string filename;
        filename = checkOpenOutputFile("Введите название файла для сохранения:");
        ofstream fout(filename, ios::out);  
        fout << size <<endl;
        for (int i=0; i< size; i++) {
            fout << *(mass[row] + i) << " ";
        }
        fout.close();
        cout << "Файл был сохранен."  << endl;
}

void readFile (int** mass, int* sizes, int& arr_len) {
    string filename;
    int rows;
    filename = checkOpenInputFile("Введите название файла для чтения:");
    ifstream fin(filename, ios::in);
    if(arr_len == N) {
        inputString("Невозможно считать массив, достигнут лимит количесва записей");
    }
    fin >> rows;
    if(fin.fail()) {
        inputString("Не задано количество строк массива");
        fin.close();
        return;
    }
    int buf = 0;
    mass[arr_len] = new int[rows]{};
    for (int j=0; j<rows && !fin.eof(); j++) {
        fin >> buf;
        if(fin.fail()) {
            inputString("Неверно считан массив");
            delete [] mass[arr_len];
            fin.close();
            return;
        }
        *(mass[arr_len]+j) = buf;
    }
    cout<<endl;
    sizes[arr_len] = rows;
    arr_len++;
    fin.close(); 
    cout << "Файл был считан."  << endl;
}  

/**Осовобождение памяти*/

void memoryFree(int** mass, int mass_length) {
    for (int i = 0; i<mass_length; i++) {
        delete [] mass[i];
    }
}