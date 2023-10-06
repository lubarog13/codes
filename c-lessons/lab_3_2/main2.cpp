#include "functions2.h"
int main ()
{
    int* int_mass[N];
    int sizes[N];
    int arr_len=0;
    setlocale(LC_ALL, "Russian");
    int menu_item; 
    do
    {
        string menu_text = "";
        menu_text += "\n1 - Считать массив из файла";
        menu_text += "\n2 - Объединить массивы"; 
        menu_text += "\n3 - Сохранить массив в файл";
        menu_text += "\n4 - Сохранить все";
        menu_text += "\n0 - Выход из программы\n";
        menu_text += "\nВведите номер пункта меню: ";
        //Поменять команды меню: считать данные, сместиться, вывести, вставить, заменить, удалить, сохранить
        menu_item = inputInt(menu_text, 0, 8);
        switch (menu_item)
        {
            
            case 1:
                readFile(int_mass, sizes, arr_len);
                cout << " \n";
                outContent (int_mass, sizes, arr_len);
                cout << " \n";
                break;
            case 2:
                changeArr(int_mass, sizes, arr_len);
                outContent(int_mass, sizes, arr_len);
                break;
            case 3:
                saveFileByIndex(int_mass, sizes, arr_len);
                break;
            case 4:
                for (int i = 0; i<arr_len; i++) {
                    saveFile(int_mass, i, sizes[i]);
                }
                break;
            case 0:
                break;
            default:
            {
                cout << "Ошибка!\n";
                continue;
            }
        } 
    }while (menu_item != 0);
    memoryFree(int_mass, arr_len);
    return 0;
}
