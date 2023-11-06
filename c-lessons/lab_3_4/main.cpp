#include "functions.h"


int main() {
    setlocale(LC_ALL, "Russian");
    LinkedList* list = new LinkedList;
    int menu_item; 
    //readFile(text_array, array_length);
    do
    {
        string menu_text = "";
        menu_text += "\n1 - Считать список из файла";
        menu_text += "\n2 - Добавить новый трек в конец списка"; 
        menu_text += "\n3 - Добавить новый трек в определенное место списка";
        menu_text += "\n4 - Изменить трек из списка";
        menu_text += "\n5 - Удалить трек из списка";
        menu_text += "\n6 - Вывести трек на экран";
        menu_text += "\n7 - Вывести все треки на экран";
        menu_text += "\n8 - Сохранить список в файл";
        menu_text += "\n0 - Выход из программы\n";
        menu_text += "\nВведите номер пункта меню: ";
        //Поменять команды меню: считать данные, сместиться, вывести, вставить, заменить, удалить, сохранить
        menu_item = inputInt(menu_text, 0, 8);
        switch (menu_item)
        {
            
            case 1:
                
                break;
            case 2:
                
                break;
            case 3:
                
                break;
            case 4:
                
                break;
            case 5:
                
                break;
            case 6:
               
                break;
            case 7:
                
                break;   
            case 8:
            {
                
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
        memoryFree(list);
        return 0;
}
