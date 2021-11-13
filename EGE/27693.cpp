#include <iostream>
#include <fstream>
using namespace std;

/*
Текстовый файл состоит не более чем из 106 символов A, B и C. Определите максимальное количество идущих подряд символов C.
*/
int main()
{
    ifstream in;
    in.open("27693.txt");
    int count=0, maxcount=0;
    char c;
    while(!in.eof())
    {
        in>>c;
        if(c=='C') count++;
        else{
            if(count>maxcount) maxcount=count;
            count=0;
        }
    }
    cout<<maxcount;
    return 0;
}
