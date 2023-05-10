#include "functions.h"

//Добавить цикл для замены(etc) по строкам
int main() {
    setlocale(LC_ALL, "Russian");
    string* last_file;
    string full_string, substring, adding_string, replace, new_string, safe;
    int selected_index = 0;
    int array_length = 0;
    char ch;
    int number, a; 
    readFile("Записано в файле", last_file, array_length);
    do
    {
        cout << " 1 - Вывести данные из файла \n 2 - Сместиться вверх\n 3 - Сместиться вниз\n 4 - Удалить перед \n 5 - Дублировать подстроку \n 6 - Дублировать символ \n 0 - Выход и сохранение \n";
        //Поменять команды меню: считать данные, сместиться, вывести, вставить, заменить, удалить, сохранить
        cin >> a;
        switch (a)
        {
            
            case 1:
                outContent(last_file, selected_index, array_length); // учесть кол-во строк
                break;
            case 2:
                selected_index = switchLine(selected_index, array_length, false);
                break;
            case 3:
                selected_index = switchLine(selected_index, array_length, true);
                break;
            case 4:      
                substring = inputString("Введите подстроку:");
                number = inputInt("Введите число:");
                *(last_file + selected_index) = deleteSymbols(full_string, substring, number);
                cout << "Новая строка: " << endl << *(last_file + selected_index) << endl;
                break;
            case 5:
                substring = inputString("Введите подстроку:");
               // ch = inputChar("Введите символ:");
                number = inputInt("Где заменить?");
               // adding_string = addSymbols(full_string, substring, ch, number);
                *(last_file + selected_index) = replaceSymbols(full_string, substring, substring, number);
                cout << "Новая строка: " << endl << *(last_file + selected_index) << endl;
                break;
            case 6:
            {
                substring = inputString("Введите подстроку:");
                ch = inputChar("Введите символ:");
                number = inputInt("Сколько дублировать?");
                *(last_file + selected_index) = addSymbols(full_string, substring, ch, number);
                cout << "Новая строка: " << endl << *(last_file + selected_index) << endl;
                continue;
            }
            case 0:
            {
                SafeFile(last_file, array_length);
                continue;
            }
            default:
            {
                cout << "Ошибка!\n";
                continue;
            }
        } 
    }while (a != 0);
        return 0;
}