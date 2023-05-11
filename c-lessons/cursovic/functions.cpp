#include "functions.h"
#include <string>

string checkOpenInputFile(string message) {
    string filename;
    bool is_open = false;
    while (!is_open) {
        filename = inputString(message);
        ifstream fs(filename, ios::in);
        cout<<filename;
        if (!fs) // fs.is_open
        {
            cout << "Ошибка. Имя или путь к файлу неправильный.\n"; //Если ошибка открытия файла, то завершаем программу
            is_open = false;
        }
        else {
            cout<<filename;
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

        if (!fs) // fs.is_open
        {
            cout << "Ошибка. Имя или путь к файлу неправильный.\n"; //Если ошибка открытия файла, то завершаем программу
            is_open = false;
        }
        else {
            fs.close();
            is_open = true;
        }
    }
    return filename;
}



void readFile (string message, string* mass, int& count) {
    string filename;
    string temp;
    filename = checkOpenInputFile("Введите название файла для чтения:");
    ifstream fin(filename, ios::in);

    count = 0;
    while((!fin.eof()) && (count < N))
    {
        getline(fin, temp); //Считываем строки в массив
        mass[count] = temp;
        cout<<mass[count]<<endl;
        count++;
    }
    fin.close(); 
}

void outContent (string* mass, int start, int count) {
    int lines_count;
    lines_count = inputInt("Введите количество строк для вывода:", 0, count - start);
    for (int i=start; i<(start + lines_count); i++) {
        cout << "Строка номер " << i + 1 << " = " << *(mass + i) << endl;
    }
}

int switchLine(int oldline_index, int count, bool under) {
    int count2=0;
    if(under) {
        count2 = inputInt("Введите смещение вниз: ", 0, (count - oldline_index - 1));
        return oldline_index + count2;
    }
    else {
        count2 = inputInt("Введите смещение вверх: ", 0, oldline_index);
        return oldline_index - count2;
    }
    
}       

string inputString(string message) {
    string str = "";
    while (str.empty()) {
        cout << message << endl;
        getline(cin, str, '\n');
    }
    return str;
}

char inputChar(string message) {
    char ch;
    cout << message << endl;
    while (!(cin >> ch))
    {
         cout << "Неправильно введен символ" << endl;
        cin.clear();
        cin.ignore(numeric_limits<streamsize>::max(), '\n');
        cout << message;
    }
    cin.ignore(numeric_limits<streamsize>::max(), '\n');
    return ch;
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
    while (n < min || n > max);
    // cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');;
    
    return n;
}

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

void cycleAdd(int start,int array_length, string* mass) {
    int under, count;
    string substring;
    char symbol;
    under = inputInt("Введите 1, если хотите изменить ниже текущей строки и 2, если выше", 1, 2);
    substring = inputString("Введите подстроку:");
    symbol = inputChar("Введите символ:");
    count = inputInt("Сколько символов добавить?");
    if(under == 2) {
        cout<<"Новые строки:"<<endl;
        for (int i = 0; i<= start; i++) {
            mass[i] = addSymbols(mass[i], substring, symbol, count);
            cout<<mass[i]<<endl;
        }
    }
    if (under == 1) {
         cout<<"Новые строки:"<<endl;
        for (int i = start; i< array_length; i++) {
            mass[i] = addSymbols(mass[i], substring, symbol, count);
            cout<<mass[i]<<endl;
        }
    }
}

string addSymbols(string input, string substring, char symbol, int count) {
    size_t i = input.find(substring);
    if (i==string::npos) {
        return input;
    }
    input.insert(i, count, symbol);
    return input;
}

void cycleRelace(int start,int array_length, string* mass) {
    int under, count;
    string substring, replacement;
    under = inputInt("Введите 1, если хотите изменить ниже подстроки и 2, если выше", 1, 2);
    substring = inputString("Введите подстроку:");
    replacement = inputString("Введите подстроку, на которую заменяем:");
    count = inputInt("Сколько символов заменить?");
    if(under == 2) {
        cout<<"Новые строки:"<<endl;
        for (int i = 0; i<= start; i++) {
            mass[i] = replaceSymbols(mass[i], substring, replacement, count);
            cout<<mass[i]<<endl;
        }
    }
    if (under == 1) {
         cout<<"Новые строки:"<<endl;
        for (int i = start; i< array_length; i++) {
            mass[i] = replaceSymbols(mass[i], substring, replacement, count);
            cout<<mass[i]<<endl;
        }
    }
}


string replaceSymbols(string input, string substring, string replacement, int count) {
    size_t i = input.find(substring);
    if (i==string::npos) {
        return input;
    }
    if (i < count) {
        count = i;
    }
    i -= count;
    input.replace(i, count, replacement);
    return input;
}

void cycleDelete(int start, int array_length, string* mass) {
    int under, count;
    string substring;
    under = inputInt("Введите 1, если хотите изменить ниже подстроки и 2, если выше", 1, 2);
    substring = inputString("Введите подстроку:");
    count = inputInt("Введите число символов для удаления:");
    if(under == 2) {
        cout<<"Новые строки:"<<endl;
        for (int i = 0; i<= start; i++) {
            mass[i] = deleteSymbols(mass[i], substring, count);
            cout<<mass[i]<<endl;
        }
    }
    if (under == 1) {
         cout<<"Новые строки:"<<endl;
        for (int i = start; i< array_length; i++) {
            mass[i] = deleteSymbols(mass[i], substring, count);
            cout<<mass[i]<<endl;
        }
    }
}

string deleteSymbols(string input, string substring, int count) {
    size_t i = input.find(substring);
    if (i==string::npos) {
        return input;
    }
    if (i < count) {
        count = i;
    }
    i -= count;
    input.erase(i, count);
    return input;
}

void SafeFile(string* mass, int count)
{
    string filename;
        filename = checkOpenOutputFile("Введите название файла для сохранения:");
        ofstream fout(filename, ios::out);  
        for (int i=0; i<count; i++) {
            fout << mass[i] << endl;
        }
        fout.close();
        cout << "Файл был сохранен"  << endl;
   
}