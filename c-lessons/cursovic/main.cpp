#include "functions.h"
#include <iostream>

//Добавить цикл для замены(etc) по строкам
int main() {
    setlocale(LC_ALL, "Russian");
    string text_array[N];
    string full_string, substring, adding_string, replace, new_string, safe;
    int selected_index = 0;
    int array_length = 0;
    char ch;
    int number, a; 
   readFile("Записано в файле", text_array, array_length);
    do
    {
        //Поменять команды меню: считать данные, сместиться, вывести, вставить, заменить, удалить, сохранить
        a = inputInt("\n1 - Считать данные из файла \n2 - Сместиться вверх\n3 - Сместиться вниз\n4 - Вывести данные из файла \n5 - Вставить символы \n6 - Заменить символы \n7 - Удалить символы  \n8 - Сохранить \n0 - Выход и сохранение \n", 0, 8);
        switch (a)
        {
            
            case 1:
                readFile("Записано в файле", text_array, array_length);
                break;
            case 2:
                selected_index = switchLine(selected_index, array_length, false);
                break;
            case 3:
                selected_index = switchLine(selected_index, array_length, true);
                break;
            case 4:
                outContent(text_array, selected_index, array_length); // учесть кол-во строк
                break;
            case 5:
                cycleAdd(selected_index, array_length, text_array);
                break;
            case 6:
                cycleRelace(selected_index, array_length, text_array);
                break;
            case 7:
                cycleDelete(selected_index, array_length, text_array);
                break;   
            case 8:
            case 0:
            {
                SafeFile(text_array, array_length);
                break;
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