#include <iostream>
#include <fstream>
using namespace std;

int main()
{
    /*екстовый файл состоит не более чем из 106 символов A, B и C.
    Определите максимальную длину цепочки вида ABABAB... (составленной из фрагментов AB, последний фрагмент может быть неполным).*/
    ifstream in;
    in.open("27694.txt");
    int count =0, maxcount =0;
    char c;
    while(!in.eof())
    {
        in>>c;
        if((c=='A' && count%2==0)|| (c=='B' && count%2==1)) count++;
        else if(c=='A'){
            if(count>maxcount) maxcount=count;
            count=1;
        }
        else{
           if(count>maxcount) maxcount=count;
            count=0; 
        }
    }
    cout<<maxcount;
    return 0;
}
