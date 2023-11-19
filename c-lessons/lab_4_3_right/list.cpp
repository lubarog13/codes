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

void InsertElementToFront(List* list_pt, Node* node)
{   
    list_pt->cur = node;
    if (list_pt->head == NULL)
    {
        list_pt->head = list_pt->cur;
        list_pt->last = list_pt->cur;
        return;
    }
    list_pt->cur->next = list_pt->head;
    list_pt->head = list_pt->cur;
}

void InsertElement(List* list, Node* node, int n)
{   
    list->cur = node;
    if (list->head == NULL)
    {
        list->head = list->cur;
        list->last = list->cur;
    }
    else
    {
        if (n == 1)
        {
            list->cur->next = list->head;
            list->head = list->cur;
        }
        else
        {
            list->prev = list->head;
            int i = 1;
            while ((list->prev != list->last) && (i < n - 1))
            {   
                list->prev = list->prev->next;
                i++;
            }
            list->cur->next = list->prev->next;
            list->prev->next = list->cur;
            if (list->prev == list->last)
            {
                list->last = list->cur;
            }
        }
    }
    cout << "Новый элемент добавлен в список." << endl;
}

void InsertLastElement(List* list_pt, Node* node)
{
    list_pt->cur = node;
    if (list_pt->last == NULL)
    {
        list_pt->head = list_pt->cur;
        list_pt->last = list_pt->cur;
    }
    else 
    {
        list_pt->last->next = list_pt->cur;
        list_pt->last = list_pt->cur;
    }
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

void EditElement(List* list, int n)
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
        EditData(list->cur->data);
        cout << "Элемент списка был отредактирован." << endl;
    }
    else
    {
        cout << "Ошибка! Неверный порядковый номер элемента." << endl;
    }
}

void DeleteFirstElement(List* list_pt)
{
    if (list_pt->head == NULL)
    {
        cout << "Ошибка! Список пустой." << endl;
        return;
    }
    list_pt->cur = list_pt->head;
    list_pt->head = list_pt->head->next;
    if (list_pt->head == NULL)
    {
        list_pt->last = NULL;
    }
    delete list_pt->cur->data;
    delete list_pt->cur;
    cout << "Элемент удалён." << endl;
}


void DeleteElement(List* list, int n)
{
    if (list->head == NULL)
    {
        cout << "Ошибка! Список пустой." << endl;
        return;
    }
    list->cur = list->head;
    if (list->head == list->last)
    {
        list->head = NULL;
        list->last = NULL;
    }
    else
    {
        if (n == 1)
        {
            list->head = list->head->next;
        }
        else
        {
            int i = 1;
            while ((list->cur != list->last) && (i < n))
            {
                list->prev = list->cur;
                list->cur = list->cur->next;
                i++;
            }
            list->prev->next = list->cur->next;
            if (list->cur == list->last)
            {
                list->last = list->prev;
            }
        }
    }
    DeleteNode(list->cur);
    cout << "Элемент списка удалён." << endl;
}

void DeleteLastElement(List* list_pt)
{
    if (list_pt->head == NULL)
    {
        cout << "Ошибка! Список пустой." << endl;
        return;
    }
    list_pt->cur = list_pt->head;
    list_pt->prev = NULL;
    while (list_pt->cur->next != NULL)
    {
        list_pt->prev = list_pt->cur;
        list_pt->cur = list_pt->cur->next;
    }
    if (list_pt->prev == NULL)
    {
        list_pt->head = NULL;
        list_pt->last = NULL;
    }
    else
    {
        list_pt->prev->next = NULL;
        list_pt->last = list_pt->prev;
    }
    delete list_pt->cur->data;
    delete list_pt->cur;
    cout << "Элемент удалён." << endl;
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
