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
        menu_text += "\n 1 - Очистка списка";
        menu_text += "\n 2 - Вставка нового элемента в конец списка";
        menu_text += "\n 3 - Вывод списка";
        menu_text += "\n 4 - Вывод элемента списка";
        menu_text += "\n 5 - Сохранение списка в файл";
        menu_text += "\n 6 - Чтение (восстановление) списка из файла"; 
        menu_text += "\n 0 - Выход из программы";
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
                AddElement(list_pt, node);
                break;
                
            case 3: 
                OutList(list_pt);
                break;
                
            case 4: 
                n = InputInt("Введите порядковый номер элемента списка: ", 1);
                OutElement(list_pt, n);
                break;
                
            case 5: 
                SaveList(list_pt);
                break;
                
            case 6: 
                ReadList(list_pt);
                break;
                
            case 0: 
                break;
                
            default: 
                cout << "Ошибка! Неверный пункт меню.\n";
                continue;
        }
    }
    while (menu_item != 0);
    
    DeleteList(list_pt);
    cout << endl;
    
    return 0;
}