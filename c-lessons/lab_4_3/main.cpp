#include "functions.h"


int main() {
    LinkedList* list = new LinkedList;
    readFile(list);
    printList(list);
    saveFile(list);
    return 0;
}
