#include "list.h"

void NewNode(Node* &node)
{
    node = new Node;
    NewData(node->data);
    node->next = NULL;
}

void DeleteNode(Node* &node)
{
    DeleteData(node->data);
    delete node;
    node = NULL;
}

void NewList(List* &list)
{
    list = new List;
    list->head = NULL;
    list->prev = NULL;
    list->cur = NULL;
    list->last = NULL;
    cout << "Формуляр списка создан." << endl;
}

void ClearList(List* list)
{
    while (list->head != NULL)
    {
        list->cur = list->head;
        list->head = list->head->next;
        DeleteNode(list->cur);
    }
    list->head = NULL;
    list->prev = NULL;
    list->cur = NULL;
    list->last = NULL;
    cout << "Список очищен." << endl;
}

void AddElement(List* list, Node* node)
{
    list->cur = node;
    if (list->last == NULL)
    {
        list->head = list->cur;
        list->last = list->cur;
    }
    else 
    {
        list->last->next = list->cur;
        list->last = list->cur;
    }    
    cout << "Новый элемент добавлен в конец списка." << endl;
}

void OutList(List* list)
{
    if (list->head == NULL)
    {
        cout << "Список пустой." << endl;
        return;
    }
    list->cur = list->head;
    int i = 1;
    while (list->cur != NULL)
    {
        cout << "Произведение №" << i << ": " << endl;
        OutData(list->cur->data);
        cout << endl;
        list->cur = list->cur->next;
        i++;
    }
}

void OutElement(List* list, int n)
{
    if (list->head == NULL)
    {
        cout << "Ошибка! Список пустой." << endl;
        return;
    }
    list->cur = list->head;
    int i = 1;
    while ((list->cur != NULL) && (i < n))
    {
        list->cur = list->cur->next;
        i++;
    }
    if (list->cur != NULL)
    {
        cout << "Произведение №" << i << ": " << endl;
        OutData(list->cur->data);
        cout << endl;
    }
    else
    {
        cout << "Ошибка! Неверный порядковый номер элемента." << endl;
    }
}

void ReadList(List* list)
{
    ClearList(list);
    string filename = CheckOpenInputFile("Введите название файла для чтения: ");
    ifstream fin(filename, ios::in);
    while (!fin.eof())
    {
        NewNode(list->cur);
        ReadData(list->cur->data, fin);
        AddElement(list, list->cur);
        string str = ReadString(fin);
    }
    fin.close(); 
    cout << "Список считан из файла."  << endl;
}

void SaveList(List* list)
{
    string filename = CheckOpenOutputFile("Введите название файла для сохранения: ");
    ofstream fout(filename, ios::out); 
    list->cur = list->head;
    while (list->cur != NULL)
    {
        SaveData(list->cur->data, fout);
        if (list->cur != list->last)
        {
            fout << endl;
        }
        list->cur = list->cur->next;
    }
    fout.close();
    cout << "Список сохранён в файл."  << endl;
}

void DeleteList(List* &list)
{
    ClearList(list);
    delete list;
    list = NULL;
    cout << "Формуляр списка удалён." << endl;
}