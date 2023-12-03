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

void outContent (string** mass, int mass_length) {
    for (int i=0; i<mass_length; i++) {
        cout << mass[i][0] << endl;
    }
}  
void deleteSymbols(string &input, string substring, int count2) {
    int i = input.find(substring);
    //cout << i << endl << substring << endl << count2 << endl;
    if (i==string::npos) {
        //cout<< "Здесь1" << endl;
        return;
    }
    if (i < count2) {
        //cout<< "Здесь2" << endl;
        count2 = i;
    }
    //i -= count;
    input.erase(i-count2, count2);
    //return;
}

void cycleDelete(string** mass, int mass_length)
{
    int under, count2;
    string substring;
    substring = inputString("Введите подстроку:");
    count2 = inputInt("Введите число символов для удаления:",0);
        cout<<"Новые строки:"<<endl;
        for (int i = 0; i< mass_length; i++) {
            deleteSymbols(mass[i][0], substring, count2);
            //cout<<mass[i][0]<<endl;
        }

}


//Открытие и закрытие*********************************************
void SaveFile(string** mass, int mass_length)
{
    string filename;
    filename = checkOpenOutputFile("Введите название файла для сохранения:");
    ofstream fout(filename, ios::out);  
    for (int i=0; i< mass_length; i++) {
        fout << mass[i][0] << endl;
    }
    fout.close();
    cout << "Файл был сохранен."  << endl;
}

void readFile (string file, string** mass, int& mass_length) {

    ifstream fin(file, ios::in);
    int i = 0;
    for (i=0; i<mass_length; i++)
    {
        getline(fin, mass[i][0]); //Считываем строки в массив
    }
    fin.close(); 
    cout << "Файл был считан."  << endl;
}  
int CheckStr (string file, int& mass_length) 
{
    string str;
    ifstream fin(file, ios::in);
    mass_length = 0;
    while((!fin.eof()) && (mass_length < N))
    {
        getline(fin, str);
        mass_length++;
    }
    fin.close(); 
    cout << "Количество строк: "  << mass_length << endl;
    return mass_length;
}  