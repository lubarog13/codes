#include "functions2.h"

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

void outContent (string* mass, int mass_length) {
    for (int i=0; i<mass_length; i++) {
        cout << *(mass + i) << endl;
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
void SaveFile(string* mass, int mass_length)
{
    string filename;
        filename = checkOpenOutputFile("Введите название файла для сохранения:");
        ofstream fout(filename, ios::out);  
        for (int i=0; i< mass_length; i++) {
            fout << mass[i] << endl;
        }
        fout.close();
        cout << "Файл был сохранен."  << endl;
}

void readFile (string* mass, int& mass_length) {
    string filename;
    filename = checkOpenInputFile("Введите название файла для чтения:");
    ifstream fin(filename, ios::in);

    mass_length = 0;
    while((!fin.eof()) && (mass_length < N))
    {
        getline(fin, mass[mass_length]); //Считываем строки в массив
        //cout<<mass[mass_length]<<endl;
        mass_length++;
    }
    fin.close(); 
    cout << "Файл был считан."  << endl;
}  