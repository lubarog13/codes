#pragma once

#include "header.h"
#include "base.h"

struct Track {
    string name;
    string author;
    string artist;
    string* genres;
    int genresCount;
    int year;
};

struct ListNode {
    Track* track;
    ListNode* next;
    ListNode* prev;
};

struct LinkedList {
    ListNode* head;
    ListNode* tail;
};

void newList(LinkedList* list, ListNode* element = nullptr);
void newNode(ListNode* node, Track* track);
void newTrackFromUser(Track* track);