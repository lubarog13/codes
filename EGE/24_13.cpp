#include <iostream>
#include <fstream>
using namespace std;

int main(){
    fstream in;
    in.open("zadanie24_13.txt");
    int count=0, maxcount=0;
    char c=0;
    while(c!=EOF){
        in>>c;
        if(c=='R') count++;
        else{
            if(count>maxcount){
                maxcount=count;
                cout<<maxcount;
            }
        count=0;
    }
    
}
std::cout<<endl<<maxcount;
}