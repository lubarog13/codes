#include "functions.h"

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
    int n=min-1;
    do
    {
      cout << message << endl;    
      if ((!(cin >> n)) || (n < min) || (n > max))
      {
          cout << "Размер должен быть больше " << min << " и меньше либо равен " << max << endl;
          n=min-1;
          cin.clear();
          cin.ignore(numeric_limits<streamsize>::max(), '\n');
      } 
    }
    while ((n < min) || (n > max));
    //cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');;
    //cout<<n<<endl;
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


/*Вывод массива на экран*/
void outContent (int* mass,  int arr_len) {
    for (int i=0; i<arr_len; i++) {
        cout << mass[i] << " ";
    }
    cout<<endl;
}  

int copyElements(int* arr1, int* arr2, int count, int start = 0) {
    int arr2_index = 0;
    for(int i=start; arr2_index<count; i++, arr2_index++) {
        try {
            arr1[i] = arr2[arr2_index];
        } catch (exception e) {
            cout << "Неверный размер массива для копирования"<<endl;
            return 1;
        }
    }
    return 0;
}

/*Для сортировки*/
void swap(int& a, int& b) {
    int temp = a;
    a = b;
    b = temp;
}

int partition(int *arr, int low, int high) {
    int pivot = arr[high];
    int i = (low - 1);

    for (int j = low; j <= high - 1; j++) {
        if (arr[j] > pivot) {
            i++;
            swap(arr[i], arr[j]);
        }
    }
    swap(arr[i + 1], arr[high]);
    return (i + 1);
}

void quickSort(int* arr, int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}

int duplicatesCount(int *arr, int arr_len) {  
    int duplicates = 0;  
    for (int i=0; i<arr_len-1; i++) {
        if (arr[i]==arr[i+1]) {
            duplicates++;
        }
    }
    return duplicates;
}

/*Объединение массивов*/

void changeArr(int* &arr, int &size, int* mass1, int* mass2, int arr_len1, int arr_len2) 
{
    if (arr_len1 ==0 || arr_len2 ==0) {
        cout<< "Не считаны массивы"<<endl;
        return;
    }
    if (size>=0) delete [] arr;
    size = arr_len1 + arr_len2;
    try {
        arr = new int[size]{};
    } catch (exception e) {
        cout<<"Недостаточно памяти"<<endl;
        size = -1;
        return;
    }
    int error = 0;
    error =copyElements(arr, mass1, arr_len1);
    if (error == 1) {
        delete [] arr;
        size = -1;
        return;
    }
    error = copyElements(arr,mass2, arr_len2, arr_len1);
    if (error == 1) {
        delete [] arr;
        size = -1;
        return;
    }
    cout<<endl;
    cout<<"Объединенный массив"<<endl;
    outContent(arr, size);
    quickSort(arr, 0, size-1);
    cout<<"Отсортированный массив"<<endl;
    outContent(arr, size);
    try {
        int duplicate = duplicatesCount(arr, size);
        int* temp = new int[size - duplicate]{};
        int index = 0;
        for (int i =0;i<size;i++) {
            if((i==size-1) || (arr[i]!=arr[i+1])) {
                temp[index] = arr[i];
                index++;
            }
        }
        delete [] arr;
        arr = temp;
        size = size - duplicate; // index
    } catch (exception e) {
        return;
    }
}

//Открытие и закрытие*********************************************
void saveFile(int* mass,int size)
{
    string filename;
        filename = checkOpenOutputFile("Введите название файла для сохранения:");
        ofstream fout(filename, ios::out);  
        fout << size <<endl;
        for (int i=0; i< size; i++) {
            fout << *(mass + i) << " ";
        }
        fout.close();
        cout << "Файл был сохранен."  << endl;
}

void readFile (int*& mass, int& arr_len) {
    string filename;
    int rows;
    filename = checkOpenInputFile("Введите название файла для чтения:");
    ifstream fin(filename, ios::in);
    fin >> rows;
    if(fin.fail()) {
        cout<<"Не задано количество строк массива"<<endl;
        fin.close();
        return;
    }
    int buf = 0;
    try {
        mass = new int[rows]{};
    } catch (exception e) {
        cout<<"Недостаточно памяти"<<endl;
        arr_len = -1;
        fin.close();
        return;
    }
    for (int j=0; j<rows && !fin.eof(); j++) {
        fin >> buf;
        if(fin.fail()) {
            cout<<"Неверно считан массив"<<endl;
            arr_len = -1;
            delete[] mass;
            fin.close();
            return;
        }
        *(mass+j) = buf;
    }
    cout<<endl;
    arr_len=rows;
    fin.close(); 
    cout << "Файл был считан."  << endl;
}  