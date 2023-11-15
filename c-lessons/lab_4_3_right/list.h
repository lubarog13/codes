#pragma once

#include "data.h"


struct Node
{
    Track* data;
    Node* next;
};

struct List
{
    Node* head;
    Node* prev;
    Node* cur;
    Node* last;
};


void NewList(List* &list_pt);
void ClearList(List* list_pt);
void NewElement(Node* &node);
void InsertElementToFront(List* list_pt, Node* node);
void InsertElement (List* list_pt, Node* node, int n);
void InsertLastElement(List* list_pt, Node* node);
void OutList(List* list_pt);
void OutElement(List* list_pt, int n);
void EditElement(List* list_pt, int n);
void DeleteFirstElement(List* list_pt);
void DeleteElement(List* list_pt, int n);
void DeleteLastElement(List* list_pt);
void SaveList(List* list_pt);
void ReadList(List* list_pt);
void DeleteList(List* &list_pt);


void NewList(List* &list_pt)
{
    list_pt = new List;
    list_pt->head = NULL;
    list_pt->prev = NULL;
    list_pt->cur = NULL;
    list_pt->last = NULL;
}

void ClearList(List* list_pt)
{
    while (list_pt->head != NULL)
    {
        list_pt->cur = list_pt->head;
        list_pt->head = list_pt->head->next;
        delete list_pt->cur->data;
        delete list_pt->cur;
    }
    list_pt->head = NULL;
    list_pt->prev = NULL;
    list_pt->cur = NULL;
    list_pt->last = NULL;
    cout << "Список очищен." << endl;
}

void NewElement(Node* &node)
{
  node = new Node;
  node->data = new Track;
  node->next = NULL;
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

void InsertElement(List* list_pt, Node* node, int n)
{   
    list_pt->cur = node;
    if (list_pt->head == NULL)
    {
        list_pt->head = list_pt->cur;
        list_pt->last = list_pt->cur;
        return;
    }
    if (n == 1)
    {
        list_pt->cur->next = list_pt->head;
        list_pt->head = list_pt->cur;
    }
    else
    {
        list_pt->prev = list_pt->head;
        int i = 1;
        while ((list_pt->prev != list_pt->last) && (i < n - 1))
        {   
            list_pt->prev = list_pt->prev->next;
            i++;
        }
        list_pt->cur->next = list_pt->prev->next;
        list_pt->prev->next = list_pt->cur;
        if (list_pt->prev == list_pt->last)
        {
            list_pt->last = list_pt->cur;
        }
    }
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

void OutList(List* list_pt)
{
    if (list_pt->head == NULL)
    {
        cout << "Ошибка! Список пустой." << endl;
        return;
    }
    list_pt->cur = list_pt->head;
    int i = 1;
    while (list_pt->cur != NULL)
    {
        cout << "Произведение №" << i << ": " << endl;
        OutData(list_pt->cur->data);
        cout << endl;
        list_pt->cur = list_pt->cur->next;
        i++;
    }
}

void OutElement(List* list_pt, int n)
{
    if (list_pt->head == NULL)
    {
        cout << "Ошибка! Список пустой." << endl;
        return;
    }
    list_pt->cur = list_pt->head;
    int i = 1;
    while ((list_pt->cur != NULL) && (i < n))
    {
        list_pt->cur = list_pt->cur->next;
        i++;
    }
    if (list_pt->cur != NULL)
    {
        cout << "Произведение №" << i << ": " << endl;
        OutData(list_pt->cur->data);
    }
    else
    {
        cout << "Неверный порядковый номер элемента." << endl;
    }
}

void EditElement(List* list_pt, int n)
{
    if (list_pt->head == NULL)
    {
        cout << "Ошибка! Список пустой." << endl;
        return;
    }
    list_pt->cur = list_pt->head;
    int i = 1;
    while ((list_pt->cur != NULL) && (i < n))
    {
        list_pt->cur = list_pt->cur->next;
        i++;
    }
    if (list_pt->cur != NULL)
    {
        EditData(list_pt->cur->data);
    }
    else
    {
        cout << "Неверный порядковый номер элемента." << endl;
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

void DeleteElement(List* list_pt, int n)
{
    if (list_pt->head == NULL)
    {
        cout << "Ошибка! Список пустой." << endl;
        return;
    }
    list_pt->cur = list_pt->head;
    list_pt->prev = NULL;
    int i = 1;
    if (n == 1)
    {
        list_pt->head = list_pt->head->next;
        if (list_pt->head == NULL)
        {
            list_pt->last = NULL;
        }
    }
    else 
    {
        while ((list_pt->cur->next != NULL) && (i < n))
        {
            list_pt->prev = list_pt->cur;
            list_pt->cur = list_pt->cur->next;
            i++;
        }
        if (list_pt->prev == NULL)
        { 
            list_pt->head = list_pt->head->next;
        }
        else
        {
            list_pt->prev->next = list_pt->cur->next;
        }
        if (list_pt->cur == list_pt->last)
        {
            list_pt->last = list_pt->prev;
        }
    }
    delete list_pt->cur->data;
    delete list_pt->cur;
    cout << "Элемент удалён." << endl;
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

void ReadList(List* list_pt)
{
    ClearList(list_pt);
    string filename = CheckOpenInputFile("Введите название файла для чтения: ");
    ifstream fin(filename, ios::in);
    while (!fin.eof())
    {
        NewElement(list_pt->cur);
        ReadData(list_pt->cur->data, fin);
        InsertLastElement(list_pt, list_pt->cur);
        string str = InputStringFile(fin);
    }
    fin.close(); 
    cout << "Файл был считан."  << endl;
}

void SaveList(List* list_pt)
{
    string filename = CheckOpenOutputFile("Введите название файла для сохранения: ");
    ofstream fout(filename, ios::out); 
    list_pt->cur = list_pt->head;
    while (list_pt->cur != NULL)
    {
        SaveData(list_pt->cur->data, fout);
        if (list_pt->cur->next != NULL)
        {
            fout << endl;
        }
        list_pt->cur = list_pt->cur->next;
    }
    fout.close();
    cout << "Файл был сохранён."  << endl;
}

void DeleteList(List* &list_pt)
{
    ClearList(list_pt);
    delete list_pt;
    cout << "Формуляр удалён." << endl;
}
