#include <iostream>
#include <fstream>
using namespace std;

int main()
{
    /*Текстовый файл состоит не более чем из 1 200 000 символов X, Y, и Z. Определите максимальное 
    количество идущих подряд символов, среди которых нет подстроки XZZY. Для выполнения этого задания 
    следует написать программу. Ниже приведён файл, который необходимо обработать с помощью данного алгоритма.*/
    ifstream in;
    in.open("36037.txt");
    int count=0, maxcount=0, ch=0;
    char c;
    while(!in.eof()){
        in>>c;
        count++;
        if((c=='X' && ch%4==0)||(c=='Z' && ch%4==1)||(c=='Z' && ch%4==2)||(c=='Y' && ch%4==3)) ch++;
        else if (c=='X'){
            ch=1;
        } 
        else ch=0;
        if(ch==4){
            count-=1;
            count=3;
        }
        if(count>maxcount) maxcount=count;
    }
    cout<<maxcount;
    in.close();
    return 0;
}