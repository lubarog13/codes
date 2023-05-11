#include "functions.h"

//Добавить цикл для замены(etc) по строкам
int main() {
    setlocale(LC_ALL, "Russian");
    string text_array[N];
    int array_length = 0;
    int selected_index = 0;
    int menu_item; 
    //readFile(text_array, array_length);
    do
    {
        string menu_text = "";
        menu_text += "\n1 - Считать текст из файла";
        menu_text += "\n2 - Сместиться на n строк вверх"; 
        menu_text += "\n3 - Сместиться на n строк вниз";
        menu_text += "\n4 - Вывести n строк (начиная с текущей)";
        menu_text += "\n5 - Вставить символы перед контекстом";
        menu_text += "\n6 - Заменить символы перед контекстом";
        menu_text += "\n7 - Удалить символы перед контекстом";
        menu_text += "\n8 - Сохранить текст в файл";
        menu_text += "\n0 - Выход из программы\n";
        menu_text += "\nВведите номер пункта меню: ";
        //Поменять команды меню: считать данные, сместиться, вывести, вставить, заменить, удалить, сохранить
        menu_item = inputInt(menu_text, 0, 8);
        switch (menu_item)
        {
            
            case 1:
                readFile(text_array, array_length);
                break;
            case 2:
                selected_index = switchLine(selected_index, array_length, false);
                break;
            case 3:
                selected_index = switchLine(selected_index, array_length, true);
                break;
            case 4:
                outContent(text_array, array_length, selected_index);
                break;
            case 5:
                cycleAdd(text_array, array_length, selected_index);
                break;
            case 6:
                cycleRelace(text_array, array_length, selected_index);
                break;
            case 7:
                cycleDelete(text_array, array_length, selected_index);
                break;   
            case 8:
            {
                saveFile(text_array, array_length);
                break;
            }
            case 0:
                break;
            default:
            {
                cout << "Ошибка!\n";
                continue;
            }
        } 
    }while (menu_item != 0);
        return 0;
}