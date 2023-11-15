#include "list.h"

int main()
{
    setlocale(LC_ALL, "Russian");
    
    List* list_pt = NULL;
    NewList(list_pt);
    
    Node* node = NULL;
    string performer = "";
    string genre = "";
    int n = 0;
    
    int menu_item = 0;
    
    do
    {
        string menu_text = "";
        menu_text += "\n1 - Очистить список";
        menu_text += "\n2 - Вставить новый элемент в список";
        menu_text += "\n3 - Вывод списка";
        menu_text += "\n4 - Вывод элемента списка";
        menu_text += "\n5 - Редактирование элемента списка";
        menu_text += "\n6 - Поиск элементов списка по исполнителю";
        menu_text += "\n7 - Поиск элементов списка по жанру";
        menu_text += "\n8 - Сортировка списка по названию";
        menu_text += "\n9 - Сортировка списка по году";
        menu_text += "\n10 - Удаление элемента из списка";
        menu_text += "\n11 - Сохранить список в файл";
        menu_text += "\n12 - Считать  список из файла"; 
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
                NewElement(node);
                InputData(node->data);
                n = InputInt("Введите порядковый номер элемента списка: ", 1);
                InsertElement(list_pt, node, n);
                break;

            case 3: 
                OutList(list_pt);
                break;

            case 4: 
                n = InputInt("Введите порядковый номер элемента списка: ", 1);
                OutElement(list_pt, n);
                break;

            case 5: 
                n = InputInt("Введите порядковый номер элемента списка: ", 1);
                EditElement(list_pt, n);
                break;

            case 6: 
                performer = InputString("Введите имя исполнителя: ");
                FindElementByPerformer(list_pt, performer);
                break;

            case 7: 
                genre = InputString("Введите жанр: ");
                FindElementByGenre(list_pt, genre);
                break;

            case 8: 
                SortListByName(list_pt);
                break;

            case 9: 
                SortListByYear(list_pt);
                break;

            case 10: 
                n = InputInt("Введите порядковый номер элемента списка: ", 1);
                DeleteElement(list_pt, n);
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
