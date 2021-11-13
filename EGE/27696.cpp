#include <iostream>
#include <fstream>
using namespace std;

int main()
{
    ifstream in;
    in.open("27696.txt");
    /*Текстовый файл состоит не более чем из 106 символов L, D и R. Определите 
    длину самой длинной последовательности, состоящей из символов L. Хотя бы один 
    символ L находится в последовательности.*/
    int count=0, maxcount=0;
    char c;
    while(!in.eof()){
        in>>c;
        if(c=='L') count++;
        else{
        if(count>maxcount) maxcount=count;
        count=0;
        }
    }
    cout<<maxcount;
    in.close();
    return 0;
}