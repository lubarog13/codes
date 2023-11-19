#pragma once

#include "models.h"
#include "base.h"

void addNode(LinkedList* list, ListNode* element);
int addNode(LinkedList* list, ListNode* element, int position);

int editNodeValue(LinkedList* list, int position);

void addNodeToFrontFromMenu(LinkedList*& list);
void addNodeFromMenu(LinkedList*& list);
void addNodeToEndFromMenu(LinkedList*& list);
void editNodeValueFromMenu(LinkedList *list);
void deleteFromMenu(LinkedList* list);
void printFromMenu(LinkedList* list);

void deleteNode(LinkedList* list);
int deleteNode(LinkedList* list, int position, bool direction=true);

void printTrack(Track* track);
void printList(LinkedList* list, int node=-1);

void readFile (LinkedList*& list);
void saveFile(LinkedList* list);

void clearList(LinkedList* list);
void memoryFree(LinkedList* list);