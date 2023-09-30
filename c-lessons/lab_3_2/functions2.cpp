#include "functions2.h"
#include <string>

// Внесение****************************************************************************************************
int inputInt(string message, int min)
{
    int n;
    do
    {
      cout << message;    
      if ((!(cin >> n)) || (n < min))
      {
          cout << "Размер должен быть больше " << min << endl;
          cin.clear();
          cin.ignore(numeric_limits<streamsize>::max(), '\n');
      } 
    }
    while (n < min);
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

void outContent (int** mass, int rows, int columns) {
    for (int i=0; i<rows; i++) {
        for (int j=0; j<columns; j++) {
            cout << *(mass[i] + j) << " ";
        }
        cout << endl;
    }
}  

void replaceSymbols(string &input, string substring, int count, string replacement) {
    size_t i = input.find(substring);
    if (i==string::npos) {
        return;
    }
    if (i < count) {
        count = i;
    }
    //i -= count;
    input.replace(i-count, count, replacement);
    return;
}

void ChangeSub(string* mass, int mass_length) 
{
    int count;
    string substring, replacement;
    substring = inputString("Введите подстроку:");
    count = inputInt("Сколько символов заменить?", 0);
    replacement = inputString("Введите подстроку, на которую заменяем:");
        //cout<<"Новые строки:"<<endl;
        for (int i = 0; i<= mass_length; i++) {
            replaceSymbols(mass[i], substring, count, replacement);
            //cout<<mass[i]<<endl;
        }
}

//Открытие и закрытие*********************************************
void SaveFile(int** mass, int rows, int columns)
{
    string filename;
        filename = checkOpenOutputFile("Введите название файла для сохранения:");
        ofstream fout(filename, ios::out);  
        fout << rows << " " << columns;
        for (int i=0; i< rows; i++) {
            fout << endl;
            for (int j=0; j< columns; j++) {
                fout << *(mass[i] + j) << " ";
            }
        }
        fout.close();
        cout << "Файл был сохранен."  << endl;
}

void readFile (int** mass, int& rows, int& columns) {
    string filename;
    filename = checkOpenInputFile("Введите название файла для чтения:");
    ifstream fin(filename, ios::in);
    fin >> rows;
    if(fin.fail()) {
        inputString("Не задано количество строк массива");
        fin.close();
        return;
    }
    if(rows > N) {
        inputString("Слишком большое число строк массива, будет использавано " + to_string(N) + " строк");
        fin.close();
        return;
    }
    fin >> columns;
    if(fin.fail()) {
        inputString("Не задано количество колонок массива");
        fin.close();
        return;
    }
    int buf = 0;
    for (int i=0; i<rows && !fin.eof(); i++)
    {
        mass[i] = new int[columns]{};
        for (int j=0; j<columns && !fin.eof(); j++) {
            fin >> buf;
            if(fin.fail()) {
                inputString("Неверно считан массив");
                fin.close();
                return;
            }
           *(mass[i]+j) = buf;
        }
        cout<<endl;
        
    }
    fin.close(); 
    cout << "Файл был считан."  << endl;
}  