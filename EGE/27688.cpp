#include <iostream>
#include <fstream>
#include <algorithm>
#include <chrono>
using namespace std;

int main()
{
    auto start = chrono::high_resolution_clock::now();
    ifstream in;
    in.open("27688.txt");
    char c;
    int count=0, maxcount=0;
    while(!in.eof()){
        in>>c;
        if(c=='Z') count++;
        else if (count>maxcount){ maxcount=count;
        count=0;}
        else count=0;
    }
    cout<<maxcount;
    in.close();
    auto end = chrono::high_resolution_clock::now();
    auto itog = chrono::duration_cast<chrono::nanoseconds>(end-start).count();
    cout<<itog;
    return 0;
}