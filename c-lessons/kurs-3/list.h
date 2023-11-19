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

void NewNode(Node* &node);
void DeleteNode(Node* &node);

void NewList(List* &list);
void ClearList(List* list);
void AddElement(List* list, Node* node);
void InsertElement (List* list, Node* node, int n);
void OutList(List* list);
void OutElement(List* list, int n);
void EditElement(List* list, int n);
void FindElementByPerformer(List* list, string performer);
void FindElementByGenre(List* list, string genre);
void SortListByName(List* list);
void SortListByYear(List* list);
void DeleteElement(List* list, int n);
void SaveList(List* list);
void ReadList(List* list);
void DeleteList(List* &list);
