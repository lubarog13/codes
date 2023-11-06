#include "header.h"


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