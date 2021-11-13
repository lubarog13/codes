#include <iostream>
#include <fstream>
using namespace std;


int main(){
    ifstream in;
    in.open("27986_B.txt");
    int c=0, max=0, maxs=0;
    do{
        in>>c;
        if(c%7==0&&c>maxs) maxs=c;
        else if(c%7!=0&&c>max) max = c;
    }while (c!=0);
    std::cout<<maxs*max;
    return 0;
}