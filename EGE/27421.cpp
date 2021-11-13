#include <iostream>
#include <fstream>
using namespace std;

int main()
{
    /*Текстовый файл состоит не более чем из 106 символов X, Y и Z.
     Определите максимальное количество идущих подряд символов, среди которых каждые два соседних различны.*/
    ifstream in;
    in.open("27421.txt");
    int count=1, maxcount=0;
    char c, last;
    in>>last;
    while(!in.eof()){
        in>>c;
        if(c!=last) count++;
        else{
             if (count>maxcount) maxcount=count;
             count=1;
        }
        last=c;
    }
    cout<<maxcount;
    return 0;
}