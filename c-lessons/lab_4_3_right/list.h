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

void NewList(List* &list_pt);
void ClearList(List* list_pt);
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
