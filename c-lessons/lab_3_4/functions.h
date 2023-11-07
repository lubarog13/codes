#include "models.h"


int inputInt(string message);
int inputInt(string message, int min);
int inputInt(string message, int min, int max);
string inputString(string message);

string checkOpenInputFile(string message);
string checkOpenOutputFile(string message);


void newList(LinkedList* list, ListNode* element = nullptr);
void newNode(ListNode* node, Track* track);
void newTrackFromUser(Track* track);

void addNode(LinkedList* list, ListNode* element);
void addNode(LinkedList* list, ListNode* element, int position, bool direction=true);

int editNodeValue(LinkedList* list, int position);

void deleteNode(LinkedList* list);
int deleteNode(LinkedList* list, int position, bool direction=true);

void printTrack(Track* track);
void printList(LinkedList* list, int node=-1);

void readFile (LinkedList*& list);
void saveFile(LinkedList* list);

void clearList(LinkedList* list);
void memoryFree(LinkedList* list);