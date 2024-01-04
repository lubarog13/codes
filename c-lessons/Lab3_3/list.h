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
void OutList(List* list);
void OutElement(List* list, int n);
void SaveList(List* list);
void ReadList(List* list);
void DeleteList(List* &list);