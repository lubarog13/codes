#include <iostream>
#include <fstream>
using namespace std;

int main()
{
    /*Текстовый файл состоит не более чем из 1 200 000 символов X, Y, и Z.
     Определите максимальное количество идущих подряд символов, 
     среди которых нет подстроки XZZY. Для выполнения этого задания следует написать программу.
      Ниже приведён файл, который необходимо обработать с помощью данного алгоритма.*/
      ifstream in;
      in.open("36037.txt");
      int count=0, maxcount=0, cnt=0;
      char c;
      while(!in.eof())
      {
          in>>c;
          count++;
          if((c=='X' && cnt%4==0)||(c=='Z' && cnt%4==1)||(c=='Z' && cnt%4==2)||(c=='Y' && cnt%4==3)) cnt++;
          else if(c=='X') cnt=1;
          else cnt=0;
          if(cnt==4) {count=3;
          cnt=0;}
          if(count>maxcount) maxcount=count;
      }
      cout<<maxcount;
      in.close();
    return 0;
}