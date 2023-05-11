#include "functions.h"

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

char inputChar(string message) {
    char ch;
    cout << message << endl;
    while (!(cin >> ch))
    {
        cout << "Неправильно введен символ" << endl;
        cin.clear();
        cin.ignore(numeric_limits<streamsize>::max(), '\n');
        cout << message << endl;
    }
    cin.ignore(numeric_limits<streamsize>::max(), '\n');
    return ch;
}

string inputString(string message) {
    string str = "";
    //while (str.empty()) { // ?
        cout << message << endl;
        getline(cin, str, '\n');
    //}
    return str;
}

//**********************************************************************

string checkOpenInputFile(string message) {
    string filename;
    bool is_open = false;
    while (!is_open) {
        filename = inputString(message);
        ifstream fs(filename, ios::in);
        if (!fs) // fs.is_open()
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

        if (!fs) // fs.is_open()
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

//************************************************************************************//

// Вопрос Как пользователь поймёт, на какой строке он сейчас стоит

int switchLine(int current_index, int array_length, bool under) {
    int count=0;
    if(under) {
        count = inputInt("Введите смещение вниз: ", 0, (array_length - current_index - 1));
        return current_index + count;
    }
    else {
        count = inputInt("Введите смещение вверх: ", 0, current_index);
        return current_index - count;
    }
}   

void outContent (string* mass, int array_length, int current) {
    int lines_count;
    lines_count = inputInt("Введите количество строк для вывода:", 0, array_length - current);
    for (int i=current; i<(current + lines_count); i++) {
        cout << "Строка номер " << i + 1 << " = " << *(mass + i) << endl;
    }
}  

//************************************************************************************//

void addSymbols(string &input, string substring, char symbol, int count) {
    size_t i = input.find(substring);
    if (i==string::npos) {
        return;
    }
    input.insert(i, count, symbol);
    return;
}

void cycleAdd(string* mass, int array_length, int current) {
    int under, count;
    string substring;
    char symbol;
    under = inputInt("Введите 1, если хотите изменить ниже текущей строки и 2, если выше", 1, 2);
    substring = inputString("Введите подстроку:");
    symbol = inputChar("Введите символ:");
    count = inputInt("Сколько символов добавить?");
    if(under == 2) {
        //cout<<"Новые строки:"<<endl;
        for (int i = 0; i<= current; i++) {
            addSymbols(mass[i], substring, symbol, count);
            //cout<<mass[i]<<endl;
        }
    }
    if (under == 1) {
        // cout<<"Новые строки:"<<endl;
        for (int i = current; i< array_length; i++) {
            addSymbols(mass[i], substring, symbol, count);
            //cout<<mass[i]<<endl;
        }
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

void cycleRelace(string* mass, int array_length, int current) {
    int under, count;
    string substring, replacement;
    under = inputInt("Введите 1, если хотите изменить ниже подстроки и 2, если выше", 1, 2);
    substring = inputString("Введите подстроку:");
    count = inputInt("Сколько символов заменить?");
    replacement = inputString("Введите подстроку, на которую заменяем:");
    if(under == 2) {
        //cout<<"Новые строки:"<<endl;
        for (int i = 0; i<= current; i++) {
            replaceSymbols(mass[i], substring, count, replacement);
            //cout<<mass[i]<<endl;
        }
    }
    if (under == 1) {
         //cout<<"Новые строки:"<<endl;
        for (int i = current; i< array_length; i++) {
            replaceSymbols(mass[i], substring, count, replacement);
            //cout<<mass[i]<<endl;
        }
    }
}

void deleteSymbols(string &input, string substring, int count) {
    size_t i = input.find(substring);
    if (i==string::npos) {
        return;
    }
    if (i < count) {
        count = i;
    }
    //i -= count;
    input.erase(i-count, count);
    return;
}

void cycleDelete(string* mass, int array_length, int current) {
    int under, count;
    string substring;
    under = inputInt("Введите 1, если хотите изменить ниже подстроки и 2, если выше", 1, 2);
    substring = inputString("Введите подстроку:");
    count = inputInt("Введите число символов для удаления:");
    if(under == 2) {
        cout<<"Новые строки:"<<endl;
        for (int i = 0; i<= current; i++) {
            deleteSymbols(mass[i], substring, count);
            //cout<<mass[i]<<endl;
        }
    }
    if (under == 1) {
         cout<<"Новые строки:"<<endl;
        for (int i = current; i< array_length; i++) {
            deleteSymbols(mass[i], substring, count);
            //cout<<mass[i]<<endl;
        }
    }
}

//************************************************************************************//

void readFile (string* mass, int& array_length) {
    string filename;
    filename = checkOpenInputFile("Введите название файла для чтения:");
    ifstream fin(filename, ios::in);

    array_length = 0;
    while((!fin.eof()) && (array_length < N))
    {
        getline(fin, mass[array_length]); //Считываем строки в массив
        //cout<<mass[array_length]<<endl;
        array_length++;
    }
    fin.close(); 
    cout << "Файл был считан."  << endl;
}  

void saveFile(string* mass, int array_length)
{
    string filename;
        filename = checkOpenOutputFile("Введите название файла для сохранения:");
        ofstream fout(filename, ios::out);  
        for (int i=0; i<array_length; i++) {
            fout << mass[i] << endl;
        }
        fout.close();
        cout << "Файл был сохранен."  << endl;
}
