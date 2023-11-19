#include "models.h"

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
    /*if (track->genres!=nullptr) {
        try {
            delete [] track->genres;
        } catch (exception e) {}
    }*/
    track->genresCount = inputInt("Введите количество жанров");
    track->genres = new string[track->genresCount];
    for (int i=0; i<track->genresCount; i++) {
        track->genres[i] = inputString("Введите жанр");
    }
}