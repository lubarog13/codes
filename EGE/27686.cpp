#include <iostream>
#include <fstream>
using namespace std;

int main()
{
    /*Текстовый файл состоит не более чем из 106 символов X, Y и Z. Определите длину самой длинной 
    последовательности, состоящей из символов X. Хотя бы один символ X находится в последовательности.*/
    ifstream in;
    in.open("27686.txt");
    char c;
    int count=0, max=0;
    while (!in.eof())
    {
        in>>c;
        if(c=='X') count++;
        else{
            if(count>max) max=count;
            count=0;
        }
    }
    in.close();
    cout<<max;
    return 0;
}