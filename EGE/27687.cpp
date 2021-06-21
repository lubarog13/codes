#include <iostream>
#include <fstream>
using namespace std;

int main()
{
    /*Текстовый файл состоит не более чем из 106 символов X, Y и Z. Определите длину самой длинной 
    последовательности, состоящей из символов Y. Хотя бы один символ Y находится в последовательности.*/
    ifstream in;
    in.open("27687.txt");
    int count=0, maxcount=0;
    char c;
    while(!in.eof()){
        in>>c;
        if(c=='Y') count++;
        else{
            if(count>maxcount) maxcount =count;
            count=0;
        }
    }
    cout<<maxcount;
    in.close();
    return 0;
}