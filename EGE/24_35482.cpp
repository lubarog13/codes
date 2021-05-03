#include <iostream>
#include <fstream>
#include <map>
using namespace std;

int main(){
    fstream in;
    in.open("24_35482.txt");
    string s;
    string c="";
    int count=0, maxcount=0, mincount=1000;
    while (getline(in, s))
    {
        count=0;
        for(int i=0;i<s.length(); i++){
            if(s[i] == 'G') count++;
        }
        if(count<mincount){
            mincount=count;
            swap(s, c);
        }
    }
    char maxsymb;
    for (char j='A'; j <='Z';j++){
        count=0;
        for(int i=0; i<c.length(); i++){
            if(c[i]==j) count++;
        }
        if(count>=maxcount){ maxcount=count; maxsymb=j;}
    }
    cout<<maxsymb;
    return 0;
}