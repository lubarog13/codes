#include "list.h"
int main ()
{
    setlocale(LC_ALL, "Russian");

    List *list_pt = new List;
    list_pt->head=NULL;
    list_pt->prev=NULL;
    list_pt->cur=NULL;
    list_pt->last=NULL;

    Track* data = NULL;
    string performer = "";
    string genre="";
    int menu_item; 
    int i = 0; // порядковый номер выбираемого элемента списка
    do
    {
        string menu_text = "";
        menu_text += "\n1 - Очистить список";
        menu_text += "\n2 - Вставить новый элемент";
        menu_text += "\n3 - Вывод списка";
        menu_text += "\n4 - Вывод элемента списка";
        menu_text += "\n5 - Редактирование элемента";
        menu_text += "\n6 - Поиск по исполнителю";
        menu_text += "\n7 - Поиск по жанру";//разделение строки!
        menu_text += "\n8 - Сортировка списка по названию";
        menu_text += "\n9 - Сортировка списка по году";
        menu_text += "\n10 - Удаление элемента";
        menu_text += "\n11- Сохранить список в файл";
        menu_text += "\n12- Считать  список из файла"; 
        menu_text += "\n0 - Выход из программы\n";
        menu_text += "\nВведите номер пункта меню: ";
        menu_item = InputInt(menu_text, 0, 12);
        cout << endl;
        switch (menu_item)
        {
            
            case 1:
                ClearList(list_pt);
                break;

            case 2:
                data = new Track;
                InputData(data);
                i = InputInt("Введите порядковый номер элемента списка: ", 1);
                //AddLast(list_pt, data);
                InsertElement(list_pt, data, i);
                break;

            case 3:
                OutList(list_pt);
                break;

            case 4:
                i = InputInt("Введите порядковый номер элемента списка: ", 1);
                OutNode(list_pt, i);
                break;

            case 5:
                i = InputInt("Введите порядковый номер элемента списка: ", 1);
                EditElement(list_pt, i);
                break;

            case 6:
                performer = InputString("Введите имя исполнителя: ");
                FindNodeByPerformer(list_pt, performer);
                break;

            case 7:
                genre = InputString("Введите жанр: ");
                FindNodeByGenre(list_pt, genre);
                break;

            case 8:
                SortListByName(list_pt);
                break;

            case 9:
                SortListByYear(list_pt);
                break;

            case 10:
                i = InputInt("Введите порядковый номер элемента списка: ", 1);
                DeleteElement(list_pt, i);
                break;   

            case 11:
                SaveFile(list_pt);
                break;
 
            case 12:
                ReadFile(list_pt);
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
