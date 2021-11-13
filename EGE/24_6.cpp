#include <iostream>
#include <stdio.h>
#include <fstream>
using namespace std;

int main(){
    ifstream in;
    in.open("zadanie24_2.txt");
    char c;
    int count=0, maxcount=0;
    while (c!=EOF)
    {
        in>>c;
        if(c=='R'){
            count++;
        }
        else {
            if(count>maxcount) maxcount=count;
            cout<<maxcount;
            count=0;
        }
    }
    in.close();
    cout<<maxcount;
    return 0;
}