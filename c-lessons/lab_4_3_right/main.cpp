#include "list.h"

int main()
{
    setlocale(LC_ALL, "Russian");
    
    List* list_pt = NULL;
    NewList(list_pt);
    
    Node* node = NULL;
    int n = 0;
    
    int menu_item = 0;
    
    do
    {
        string menu_text = "";
        menu_text += "\n1 - Очистка списка";
        menu_text += "\n2 - Вставка нового элемента в начало списка";
        menu_text += "\n3 - Вставка нового элемента в произвольное место списка";
        menu_text += "\n4 - Вставика нового элемента в конец списка";
        menu_text += "\n5 - Вывод списка";
        menu_text += "\n6 - Вывод элемента списка";
        menu_text += "\n7 - Редактирование элемента списка";
        menu_text += "\n8 - Удаление элемента из начала списка";
        menu_text += "\n9 - Удаление элемента из произвольного места списка";
        menu_text += "\n10 - Удаление элемента из конца списка";
        menu_text += "\n11 - Сохранение списка в файл";
        menu_text += "\n12 - Чтение (восстановление) списка из файла"; 
        menu_text += "\n0 - Выход из программы";
        menu_text += "\n";
        menu_text += "\nВведите номер пункта меню: ";
        menu_item = InputInt(menu_text, 0, 12);
        cout << endl;
        
        switch (menu_item)
        {
            case 1: 
                ClearList(list_pt);
                break;

            case 2: 
                NewNode(node);
                InputData(node->data);
                InsertElementToFront(list_pt, node);
                break;

            case 3: 
                NewNode(node);
                InputData(node->data);
                n = InputInt("Введите порядковый номер элемента списка: ", 1);
                InsertElement(list_pt, node, n);
                break;

            case 4: 
                NewNode(node);
                InputData(node->data);
                InsertLastElement(list_pt, node);
                break;

            case 5: 
                OutList(list_pt);
                break;

            case 6: 
                n = InputInt("Введите порядковый номер элемента списка: ", 1);
                OutElement(list_pt, n);
                break;

            case 7: 
                n = InputInt("Введите порядковый номер элемента списка: ", 1);
                EditElement(list_pt, n);
                break;

            case 8: 
                DeleteFirstElement(list_pt);
                break;  

            case 9: 
                n = InputInt("Введите порядковый номер элемента списка: ", 1);
                DeleteElement(list_pt, n);
                break;

            case 10: 
                DeleteLastElement(list_pt);
                break;    

            case 11: 
                SaveList(list_pt);
                break;
 
            case 12: 
                ReadList(list_pt);
                break;

            case 0: 
                break;

            default: 
                cout << "Неверный пункт меню!\n";
                continue;
        } 
    }
    while (menu_item != 0);

    DeleteList(list_pt);
    return 0;
}
