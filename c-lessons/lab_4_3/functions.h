#include "models.h"


int inputInt(string message);
int inputInt(string message, int min, int max);
string inputString(string message);


void newList(LinkedList* list, ListNode* element = nullptr);
void newNode(ListNode* node, Track* track);
void newTrackFromUser(Track* track);


void printTrack(Track* track);