#include <iostream>
#include <fstream>
#include <string>
using namespace std;

int main()
{
    /*Текстовый файл состоит не более чем из 106 символов X, Y и Z. Определите максимальную длину цепочки вида XYZXYZXYZ...
    (составленной из фрагментов XYZ, последний фрагмент может быть неполным).*/
    ifstream in;
    in.open("27689.txt");
    char c;
    int count=0, maxcount=0, i=0;
    string s;
    while (getline(in, s))
    {
        for(int i=0; i<s.length(); i++){
            if((count%3==0 && s[i]=='X')||(count%3==1 && s[i]== 'Y')||(count%3==2 && s[i]=='Z')) count++;
            else {
                if (count>maxcount) maxcount=count;
                count=0;
            }
        }
    }
    cout<<maxcount;
    return 0;
}
