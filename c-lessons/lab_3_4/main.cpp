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
        menu_text += "\n2 - Добавить новый трек в начало списка"; 
        menu_text += "\n3 - Добавить новый трек в определенное место списка";
        menu_text += "\n4 - Добавить новый трек в конец списка";
        menu_text += "\n5 - Изменить трек из списка";
        menu_text += "\n6 - Удалить трек из начала списка";
        menu_text += "\n7 - Удалить трек из определенного места списка";
        menu_text += "\n8 - Удалить трек из конца списка";
        menu_text += "\n9 - Вывести определенный трек на экран";
        menu_text += "\n10 - Вывести все треки на экран";
        menu_text += "\n11 - Сохранить список в файл";
        menu_text += "\n12 - Очистить список";
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
                addNodeToFrontFromMenu(list);
                break;
            case 3:
                addNodeFromMenu(list);
                break;
            case 4:
                addNodeToEndFromMenu(list);
                break;
            case 5:
                editNodeValueFromMenu(list);
                break;
            case 6:
                deleteNode(list);
                break;
            case 7:
                deleteFromMenu(list);
;                break;
            case 8:
                deleteNode(list, 0);
                break;   
            case 9:
                printFromMenu(list);   
                break;
            case 10:
                printList(list);
                break;
            case 11:
                saveFile(list);
                break;
            case 12:
                clearList(list);
                break;
            case 0:
                break;
            default:
                cout << "Ошибка!\n";
                continue;
        } 
    }while (menu_item != 0);
        memoryFree(list);
        return 0;
}
