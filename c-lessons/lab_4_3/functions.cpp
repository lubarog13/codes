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

// Инициализация типов**********************************************************************************************

void newList(LinkedList* list, ListNode* element = nullptr) {
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
    track->year = inputInt("Введите год издания текста", 0, 2023);
    track->genresCount = inputInt("Введите количество жанров");
    track->genres = new string[track->genresCount];
    for (int i=0; i<track->genresCount; i++) {
        track->genres[i] = inputString("Введите жанр");
    }
}



void printTrack(Track* track) {
    if (track == nullptr) {return;}
    cout<<track->name<<" "<<track->artist<<endl;
    cout<<"Автор текста: "<<track->author<<" "<<track->year<<" год"<<endl;
    cout<<"Жанры: ";
    for (int i=0; i<track->genresCount; i++) {
        cout<<track->genres[i]<<" ";
    }
    cout<<endl;
}


void printList(LinkedList* list) {
    if (list == nullptr) {return;}
    int counter = 0;
    ListNode* current = list->head;
    while (current!=nullptr) {
        counter++;
        cout<<"Трек №" << counter;
        printTrack(current->track);
    }
}