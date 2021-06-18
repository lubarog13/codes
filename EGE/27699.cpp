#include <iostream>
#include <fstream>
using namespace std;

int main()
{
    ifstream in; 
    in.open("27699.txt");
    /*Текстовый файл состоит не более чем из 106 символов L, D и R. 
    Определите максимальную длину цепочки вида LDRLDRLDR... (составленной из фрагментов LDR, последний фрагмент может быть неполным).*/
    int count=0, maxcount=0;
    char c;
    while (!in.eof())
    {
        in>>c;
        if((c=='L' && count%3==0) ||
        (c=='D' && count%3==1) ||
        (c=='R' && count%3==2)) count++;
        else {
            if (count>maxcount){
                maxcount=count;
            }
            if(c=='L') count=1;
            else count=0;
        }

    }
    cout<<maxcount;
    in.close();
    return 0;
}