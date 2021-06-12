#include <iostream>
#include <fstream>
using namespace std;

int main(){
    ifstream in;
    in.open("27692.txt");
   char c;
   int count =0, maxcount =0;
    while (c!= EOF)
    {
        in>>c;
        if(c=='B'){
            count++;
        }
        else{
            if(count>maxcount){
                maxcount=count;
                cout<<maxcount<<" ";
            } 
            
            count=0;
        }
    }
    cout<<maxcount;
    in.close();
    return 0;
}