//#pragma once

#include "data.h"

struct Node
{
    Track* data;
    Node* next;
};

struct List
{
    Node* head;
    Node* prev;
    Node* cur;
    Node* last;
};


void ClearList(List* list_pt);
void AddLast(List* list_pt, Track* data);
void InsertElement (List* &list_pt, Track* data, int i);
void OutList(List* list_pt);
void OutNode(List* list_pt, int i); //вывод одного выбранного элемента списка
void EditElement(List* list_pt, int i);
void FindNodeByPerformer(List* list_pt, string performer);
void FindNodeByGenre(List* list_pt, string genre);
void SortListByName(List* list_pt);
void SortListByYear(List* list_pt);
void DeleteElement(List* &list_pt, int i);
void SaveFile(List* list_pt);
void ReadFile (List* list_pt);
void DeleteList(List* &list_pt, int &i);


// +
void ClearList (List* list_pt)
{
    while(list_pt->head != NULL)
    {
        list_pt->cur = list_pt->head;
        list_pt->head = list_pt->head->next;
        delete list_pt->cur->data;
        delete list_pt->cur;
    }
    list_pt->head = NULL;
    list_pt->prev = NULL;
    list_pt->cur = NULL;
    list_pt->last = NULL;
    cout << "Список очищен." << endl;
}


// +
void AddLast (List* list_pt, Track* data)
{
    list_pt->cur = new Node;
    list_pt->cur->data = data;
    list_pt->cur->next = NULL;
    if (list_pt->last == NULL)
    {
        list_pt->head = list_pt->cur;
        list_pt->last = list_pt->cur;
    }
    else 
    {
        list_pt->last->next = list_pt->cur;
        list_pt->last = list_pt->cur;
    }    
}

void InsertElement (List* &list_pt, Track* data, int i)
{   
    list_pt->cur = new Node;
    list_pt->cur->data = data;
    list_pt->cur->next = NULL;
    //int y = inputInt("Куда вставить новый элемент?\n1- в начало\n2 - произвольно\n3 - в конец", 1,3);
    if (list_pt->head == NULL)
    {
        list_pt->head = list_pt->cur;
        list_pt->last = list_pt->cur;
        return;
    }
    if (i == 1)
    {
        list_pt->cur->next = list_pt->head;
        list_pt->head = list_pt->cur;
    }
    else
    {
        int a = 1;
        list_pt->prev = list_pt->head;
        while ((list_pt->prev != list_pt->last) && (a < i - 1))
        {   
            list_pt->prev = list_pt->prev->next;
            a++;
        }
        list_pt->cur->next = list_pt->prev->next;
        list_pt->prev->next = list_pt->cur;
        if (list_pt->prev == list_pt->last)
        {
            list_pt->last = list_pt->cur;
        }
    }
    // AddLast() ???
}

// +
void OutList (List* list_pt)
{
    int a = 1;
    if (list_pt->head == NULL)
    {
        cout << "Ошибка! Список пустой." << endl;
        return;
    }
    list_pt->cur = list_pt->head;
    while(list_pt->cur != NULL)
    {
        cout << "Произведение №" << a << endl;
        OutData(list_pt->cur->data);
        list_pt->cur = list_pt->cur->next;
        a++;
    }
}

// +
void OutNode(List* list_pt, int i)
{
    int a = 1;
    if (list_pt->head == NULL)
    {
        cout << "Ошибка! Список пустой." << endl;
        return;
    }
    list_pt->cur = list_pt->head;
    while ((list_pt->cur != NULL) && (a < i))
    {
        list_pt->cur = list_pt->cur->next;
        a++;
    }
    if (list_pt->cur != NULL)
    {
        cout << "Произведение №" << i << endl;
        OutData(list_pt->cur->data);
    }
    else
    {
        cout << "Неверный порядковый номер элемента." << endl;
    }
}

// +
void EditElement(List* list_pt, int i)
{
    int a = 1;
    list_pt->cur = list_pt->head;
    while ((list_pt->cur != NULL) && (a < i))
    {
        list_pt->cur = list_pt->cur->next;
        a++;
    }
    if (list_pt->cur != NULL)
    {
        EditData(list_pt->cur->data);
    }
    else
    {
        cout << "Неверный порядковый номер элемента." << endl;
    }
}

void FindNodeByPerformer(List* list_pt, string performer)
{
    if (list_pt->head == NULL)
    {
        cout << "Ошибка! Список пустой." << endl;
        return;
    }
    int a = 1;
    list_pt->cur = list_pt->head;
    while (list_pt->cur != NULL)
    {
        if (list_pt->cur->data->performer == performer)
        {
            cout << "Произведение №" << a << endl;
            OutData(list_pt->cur->data);
        }
        list_pt->cur = list_pt->cur->next;
        a++;
    }
}

void FindNodeByGenre(List* list_pt, string genre)
{
    if (list_pt->head == NULL)
    {
        cout << "Ошибка! Список пустой." << endl;
        return;
    }
    //cout << "Здесь начало" << endl;
    int l;
    int a = 1;
    int i;
    string str = "";
    char cut = ',';
    string sub = "\0";
    list_pt->cur = list_pt->head;
    while (list_pt->cur != NULL)
    { 
        //cout << "Здесь цикл 1" << endl;
        str = list_pt->cur->data->genre;
        //cout << str << endl;
        l=str.size();
        //cout << l << endl;
        for (i=0; i<l;i++)
        {
            //cout << i << endl;
            //cout << sub << endl;
            if (str[i]==cut)
            {
                //cout << genre << endl;
                if (sub==genre) //ВАЖНО! Пробел тоже считывает! " Рок" и "Рок" - разные строки!
                {
                    cout << "Произведение №" << a << endl;
                    OutData(list_pt->cur->data);
                }
                sub="\0";
            }
            if(str[i]!=cut)
            {
                sub+=str[i];
            }
        }
        list_pt->cur = list_pt->cur->next;
        a++;
    }
    cout << "Поис завершен" << endl;
}

/*void swap (Track* Data, Track* Data2)
{
    Track Bufer;
    Bufer.name=Data->name;
    Bufer.author=Data->author;
    Bufer.performer=Data->performer;
    Bufer.genre=Data->genre;
    Bufer.year=Data->year;
    Data=Data2;
    Data2=Bufer;

}

void quicksort1(int* year, int first, int last)
{
    int mid, count, c = 0, c1 = 0, s = 0, s1 = 0;
    int f = first, l = last;
    mid = list_pt->[(f + l) / 2]; 
    do
    {
        c++;
        while (mas[f] < mid) f++;
        while (mas[l] > mid) l--;
        if (f <= l)  
        {
            s++;
            count = mas[l];
            mas[l] = mas[f];
            mas[f] = count;
            f--;
            l++;
        }
    } while (f < l);
    if (first < l) quicksort1(mas, first, l);
    if (f < last) quicksort1(mas, f, last);
}

void SortList(List* list_pt, int i)
{
    if (list_pt->head==NULL)
        {
            cout << "Ошибка! Список пустой" << endl;
            return;
        }
    int x=inputInt("Сортировка:\n1 - По жанру\n2 - По году ", 1, 2);
    if (x==1)
    {

    }
    else
    {   
        int x;
        int a=1;
        Track Buffer[i];
        list_pt->cur=list_pt->head;
        while(x<=1)
        {

            while(a<=x)
            {
                Buffer[a]=list_pt->cur->data;
                list_pt->cur=list_pt->cur->next;
                a++;
            }
        }

    }

}*/

void DeleteElement(List* &list_pt, int i)
{
    int a=1;
    list_pt->cur=list_pt->head;
    list_pt->prev=NULL;
    if (list_pt->head == NULL)
    {
        cout << "Ошибка! Список пустой." << endl;
        return;
    }
    if (i==1)
    {
        list_pt->head=list_pt->head->next;
    }
    else 
    {
        while ((a<i) && ( list_pt->cur->next!=NULL))
        {
            cout << list_pt->cur->data->name << endl;
            list_pt->prev=list_pt->cur;
            list_pt->cur=list_pt->cur->next;
            a++;
        }
        list_pt->prev->next=list_pt->cur->next;
        if (list_pt->last==list_pt->cur)
        {
            list_pt->last=list_pt->prev;
        }
    }
    delete list_pt->cur->data;
    delete list_pt->cur;
    cout << "Элемент удален" << endl;
}

void SortListByName(List* list_pt)
{
    if (list_pt->head == NULL)
    {
        cout << "" << endl;
        return;
    }
    list_pt->prev = list_pt->head;
    while (list_pt->prev != list_pt->last)
    {
        list_pt->cur = list_pt->prev->next;
        while(list_pt->cur != NULL)
        {
            if(list_pt->prev->data->name > list_pt->cur->data->name) //для строки также < >
            {
                Track* data = list_pt->prev->data;
                list_pt->prev->data = list_pt->cur->data;
                list_pt->cur->data = data;
            }
            list_pt->cur = list_pt->cur->next;
        }
        list_pt->prev = list_pt->prev->next;
    }
    cout << "" << endl;
}

void SortListByYear(List* list_pt)
{
    if (list_pt->head == NULL)
    {
        cout << "" << endl;
        return;
    }
    list_pt->prev = list_pt->head;
    while (list_pt->prev != list_pt->last)
    {
        list_pt->cur = list_pt->prev->next;
        while(list_pt->cur != NULL)
        {
            if(list_pt->prev->data->year < list_pt->cur->data->year) //для строки также < >
            {
                Track* data = list_pt->prev->data;
                list_pt->prev->data = list_pt->cur->data;
                list_pt->cur->data = data;
            }
            list_pt->cur = list_pt->cur->next;
        }
        list_pt->prev = list_pt->prev->next;
    }
    cout << "" << endl;

}



// +
void ReadFile (List* list_pt)
{
    ClearList(list_pt);
    string filename = CheckOpenInputFile("Введите название файла для чтения: ");
    ifstream fin(filename, ios::in);
    while (!fin.eof())
    {
        Track* data = new Track;
        ReadData(data, fin);
        AddLast(list_pt, data);
        string str = InputStringFile(fin);
    }
    fin.close(); 
    cout << "Файл был считан."  << endl;
}

// +
void SaveFile(List* list_pt)
{
    string filename = CheckOpenOutputFile("Введите название файла для сохранения: ");
    ofstream fout(filename, ios::out); 
    list_pt->cur = list_pt->head;
    while(list_pt->cur != NULL)
    {
        SaveData(list_pt->cur->data, fout);
        list_pt->cur = list_pt->cur->next;
    }
    fout.close();
    cout << "Файл был сохранен."  << endl;
}

// +
void DeleteList(List* &list_pt)
{
    ClearList(list_pt);
    delete list_pt;
    cout << "Формуляр удален." << endl;
}
