#include "functions.h"


int main() {
    setlocale(LC_ALL, "Russian");
    LinkedList* list = new LinkedList;
    newList(list);
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
                readFile(list);
                break;
            case 2:
                addNodeToEndFromMenu(list);
                break;
            case 3:
                addNodeFromMenu(list);
                break;
            case 4:
                editNodeValueFromMenu(list);
                break;
            case 5:
                deleteFromMenu(list);
                break;
            case 6:
               printFromMenu(list);
;                break;
            case 7:
                printList(list);
                break;   
            case 8:
            {
                saveFile(list);   
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
