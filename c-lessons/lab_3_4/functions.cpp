#include "functions.h"

void addNodeFromMenu(LinkedList*& list) {
    Track* track = new Track;
    cout<<endl<<endl;
    newTrackFromUser(track);
    ListNode* node = new ListNode;
    newNode(node, track);
    int position = inputInt("Введите позицию, куда поставить элемент", 1) - 1;
    if(addNode(list, node, position)) {
        cout<<"Невозможно добавить в данную позицию"<<endl<<endl;
    }
    cout<<"Список после изменения: "<<endl;
    printList(list);
}

void addNodeToFrontFromMenu(LinkedList*& list) {
    Track* track = new Track;
    cout<<endl<<endl;
    newTrackFromUser(track);
    ListNode* node = new ListNode;
    newNode(node, track);
    addNode(list, node, 0);
    cout<<"Список после изменения: "<<endl;
    printList(list);
}

void addNodeToEndFromMenu(LinkedList*& list) {
    Track* track = new Track;
    cout<<endl<<endl;
    newTrackFromUser(track);
    ListNode* node = new ListNode;
    newNode(node, track);
    addNode(list, node);
    cout<<"Список после изменения: "<<endl;
    printList(list);
}

void addNode(LinkedList* list, ListNode* element) {
    ListNode* current = list->tail;
    if (list->head == nullptr) {
        list->head = element;
    }
    if (current != nullptr) {
        current->next = element;
        element->prev = current;
    }
    list->tail = element;
}


//Отладочная функция
void printElement(ListNode* node, int n) {
    cout<<"Элемент №"<<n<<endl;
    if (node==nullptr) {
        cout<<"nullptr"<<endl;
        return;
    }
    cout<<node->track->name<<" ";
    if (node->prev!=nullptr) {
         cout<<"Пред.элемент: "<<node->prev->track->name<<", ";
    } else {
        cout<<"Пред.элемент: "<<"nullptr"<<", ";
    }
    if (node->next!=nullptr) {
         cout<<"Сл.элемент: "<<node->next->track->name<<endl;
    } else {
        cout<<"Сл.элемент: "<<"nullptr"<<endl;
    }
}

int addNode(LinkedList* list, ListNode* element, int position) {
    ListNode* current;
    int error = 1;
    current = list->head;
    int counter = 0;
    while (counter!=(position+1) && current!=nullptr) {
        if (counter==position) {
            if (current->prev!=nullptr) {
                current->prev->next = element;
                element->prev = current->prev;
            }
            current->prev = element;
            element->next = current;
            error = 0;
            break;
        }
        counter++;
        current = current->next;
    }
    if (current==nullptr && position == counter) {
        addNode(list, element);
    }
    if (position == 0) {
        list->head = element;
    }
    
    return error;
}

void editNodeValueFromMenu(LinkedList *list) {
    int position = inputInt("Введите элемент списка", 1) - 1;
    if(editNodeValue(list, position)) {
        cout<<"Элемент был не найден"<<endl<<endl;
    }
    cout<<"Список после изменения: "<<endl;
    printList(list);

}

int editNodeValue(LinkedList* list, int position) {
    ListNode* current = list->head;
    int counter = 0;
    while (counter!=(position+1) && current!=nullptr) {
        if (counter==position) {
            newTrackFromUser(current->track);
            return 0;
        }
        current = current->next;
        counter++;
    }
    return 1;
}

void deleteFromMenu(LinkedList* list) {
    int position = inputInt("Введите элемент списка", 1) - 1;
    if(deleteNode(list, position)) {
        cout<<"Элемент был не найден"<<endl<<endl;
    }
    cout<<"Список после изменения: "<<endl;
    printList(list);
}

void deleteNode(LinkedList* list) {
    ListNode* current = list->tail;
    if (current != nullptr) {
        ListNode* previous = current->prev;
        if (previous != nullptr) {
            previous->next = nullptr;
        }
        list->tail = previous;
        delete [] current->track->genres;
        delete current->track;
        delete current;
    }
}

int deleteNode(LinkedList* list, int position, bool direction) {
    ListNode* current;
    if (direction) { current = list->head; }
    else { 
        current = list->tail;
    }
    int counter = 0;
    ListNode* previous;
    if (current!=nullptr) {
        previous = current->prev;
    }
    while (counter!=(position+1) && current!=nullptr) {
        if (counter==position) {
            if (previous!=nullptr && current->next!=nullptr) {
                previous->next = current->next;
                current->next->prev = previous;
                delete [] current->track->genres;
                delete current->track;
                delete current;
            } else if (previous!=nullptr) {
                previous->next = nullptr;
                list->tail = previous;
                delete [] current->track->genres;
                delete current->track;
                delete current;
            } else if (current->next!=nullptr)  {
                list->head = current->next;
                current->next->prev = nullptr;
                delete [] current->track->genres;
                delete current->track;
                delete current;
            } else {
                list->tail = nullptr;
                list->head = nullptr;
                delete [] current->track->genres;
                delete current->track;
                delete current;
            }
            return 0;
            break;
        }
        counter++;
        if (direction) { 
            current = current->next;
        }
        else {
            current = current->prev;
        }
        previous = current->prev;
    }
    return 1;
}

void printFromMenu(LinkedList *list) {
    int position = inputInt("Введите элемент списка", 1);
    printList(list, position);
}

void printTrack(Track* track) {
    if (track == nullptr) {return;}
    cout<<"\""<<track->name<<"\" "<<track->artist<<endl;
    cout<<"Автор текста: "<<track->author<<", "<<track->year<<" год"<<endl;
    cout<<"Жанры: ";
    for (int i=0; i<track->genresCount; i++) {
        cout<<track->genres[i]<<" ";
    }
    cout<<endl<<endl;
}


void printList(LinkedList* list, int node) {
    if (list == nullptr) {return;}
    int counter = 0;
    ListNode* current = list->head;
    while (current!=nullptr) {
        counter++;
        if (node==-1 || counter == node) {
            cout<<"Трек №" << counter<<" ";
            printTrack(current->track);
        }
        current = current->next;
    }
}

//Открытие и закрытие*********************************************
void saveFile (LinkedList* list)
{
    string filename;
        filename = checkOpenOutputFile("Введите название файла для сохранения:");
        ofstream fout(filename, ios::out);  
        ListNode* current = list->head;
        while (current!=nullptr) {
            fout << current->track->name << endl << current->track->artist << endl << current->track->author << endl << current->track->year << endl << current->track->genresCount;
            for (int i=0; i<current->track->genresCount; i++) {
                fout << endl<<current->track->genres[i];
            }
            if (current->track->genresCount==0 && current->next!=nullptr) {
                fout<<endl;
            }
            current = current->next;
        }
        cout << "Файл был сохранен."  << endl;
        fout.close();
}

void readFile (LinkedList*& list) {
    string filename;
    int rows;
    filename = checkOpenInputFile("Введите название файла для чтения:");
    ifstream fin(filename, ios::in);
    memoryFree(list);
    list = new LinkedList;
    newList(list);
    string buf;
    while(!fin.eof()) {
        ListNode* node = new ListNode;
        Track* track = new Track;
        try {
            getline(fin, track->name);
            getline(fin, track->artist);
            getline(fin, track->author);
            fin>>track->year;
            fin>>track->genresCount;
            getline(fin, buf);
        } catch (exception e) {
            cout<<e.what();
            track->genresCount = 0;
            track->genres = new string[0];
            break;
        }
        if (track->genresCount>0) {
            track->genres = new string[track->genresCount];
            for(int i=0; i<track->genresCount; i++) {
                try {
                    getline(fin, track->genres[i]);
                } catch (exception e) {
                    cout<<e.what();
                    track->genresCount = 0;
                    delete [] track->genres;
                    track->genres = new string[0];
                }
            }
        } else {
            track->genres = new string[0];
        }
        newNode(node, track);
        addNode(list, node);
    }
    fin.close(); 
    cout << "Файл был считан."  << endl;
    printList(list);
}  

void clearList(LinkedList* list) {
    ListNode* current = list->head;
    ListNode* next = nullptr;
    while (current!=nullptr)  {
        next = current->next;
        delete [] current->track->genres;
        delete current->track;
        delete current;
        current = next;
    }
    list->head = nullptr;
    list->tail = nullptr;
}


void memoryFree(LinkedList* list) {
    ListNode* current = list->head;
    ListNode* next = nullptr;
    while (current!=nullptr)  {
        next = current->next;
        delete [] current->track->genres;
        delete current->track;
        delete current;
        current = next;
    }
    delete list;
}