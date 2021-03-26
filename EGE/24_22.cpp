#include <iostream>
#include <fstream>
using namespace std;
int main(){
    ifstream in;
    in.open("zadanie2422.txt");
    int count =0, maxcount =0;
    char c=0;
    while (c!=EOF)
    {
        in>>c;
        if(c=='D'){
            count++;
        }
        else if (count>maxcount){
            maxcount=count;
            count=0;
        }
        else count=0;
    }
    in.close();
    cout<<maxcount;
    return 0;
}