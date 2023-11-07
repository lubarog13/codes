#include "functions.h"

// Внесение****************************************************************************************************
int inputInt(string message)
{
    int n;
    cout << message << endl;
    while (!(cin >> n))
    {
        cout << "Неправильно введено число" << endl;
        cin.clear();
        cin.ignore(numeric_limits<streamsize>::max(), '\n');;
        cout << message;
    }
    // cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');;

    return n;
}

int inputInt(string message, int min)
{
    int n=min-1;
    do
    {
      cout << message << endl;    
      if ((!(cin >> n)) || (n < min))
      {
          cout << "Число должно быть больше " << min;
          n=min-1;
          cin.clear();
          cin.ignore(numeric_limits<streamsize>::max(), '\n');
      } 
    }
    while ((n < min));
    //cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');;
    //cout<<n<<endl;
    return n;
}

int inputInt(string message, int min, int max)
{
    int n=min-1;
    do
    {
      cout << message << endl;    
      if ((!(cin >> n)) || (n < min) || (n > max))
      {
          cout << "Размер должен быть больше " << min << " и меньше либо равен " << max << endl;
          n=min-1;
          cin.clear();
          cin.ignore(numeric_limits<streamsize>::max(), '\n');
      } 
    }
    while ((n < min) || (n > max));
    //cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');;
    //cout<<n<<endl;
    return n;
}

string inputString(string message) {
    string str = "";
    //while (str.empty()) { // ?
        cout << message << endl;
        getline(cin, str, '\n');
    //}
    return str;
}

// Чтение***************************************************************************************************

string checkOpenInputFile(string message) {
    string filename;
    bool is_open = false;
    while (!is_open) {
        filename = inputString(message);
        ifstream fs(filename, ios::in);
        if (!fs.is_open()) // fs.is_open()
        {
            cout << "Ошибка. Имя или путь к файлу неправильный.\n";
            is_open = false;
        }
        else {
            fs.close();
            is_open = true;
        }
    }
    return filename;
}

string checkOpenOutputFile(string message) {
    string filename;
    bool is_open = false;
    while (!is_open) {
        filename = inputString(message);
        ofstream fs(filename, ios::out);

        if (!fs.is_open()) // fs.is_open()
        {
            cout << "Ошибка. Имя или путь к файлу неправильный.\n";
            is_open = false;
        }
        else {
            fs.close();
            is_open = true;
        }
    }
    return filename;
}

// Инициализация типов**********************************************************************************************

void newList(LinkedList* list, ListNode* element) {
    list->head = element;
    list->tail = element;
}


void newNode(ListNode* node, Track* track) {
        node->track = track;
        node->next = nullptr;
        node->prev = nullptr;
}

void newTrackFromUser(Track* track) {
    track->name = inputString("Введите название трека");
    track->artist = inputString("Введите исполнителя трека");
    track->author = inputString("Введите автора текста");
    track->year = inputInt("Введите год выпуска", 0, 2023);
    if (track->genres!=nullptr) {
        delete [] track->genres;
    }
    track->genresCount = inputInt("Введите количество жанров");
    track->genres = new string[track->genresCount];
    for (int i=0; i<track->genresCount; i++) {
        track->genres[i] = inputString("Введите жанр");
    }
}

void addNode(LinkedList* list, ListNode* element) {
    ListNode* current = list->tail;
    if (list->head == nullptr) {
        list->head = element;
    }
    if (current != nullptr) {
        current->next = element;
    }
    list->tail = element;
}

void addNode(LinkedList* list, ListNode* element, int position, bool direction) {
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
            if (previous!=nullptr) previous->next = element;
            element->next = current;
            current->prev = element;
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
    if (position == 0 && direction) {
        list->head = element;
    }
    if ((position == 0 && !direction) || list->tail==nullptr) {
        list->tail = element;
    }
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
            break;
        }
        counter++;
    }
    return 1;
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