#include <iostream>
#include <fstream>
#include <algorithm>
#include <vector>
using namespace std;

int main()
{
    ifstream in;
    in.open("27986_B.txt");
    /*По каналу связи передавались данные в виде последовательности положительных целых чисел. 
    Количество чисел заранее неизвестно, но не менее двух, признаком конца данных считается число 0. 
    Контрольное значение равно такому максимально возможному произведению двух чисел из переданного набора, 
    которое делится на 7, но не делится на 49. Если такое произведение получить нельзя, контрольное значение считается равным 1.*/
    int i, max=0,max7=0;
    /*vector<int> arr;
    while(i!=0){
        in>>i;
        arr.push_back(i);
    }
    arr.erase(arr.end()-1);
    sort(arr.begin(), arr.end());*/
    while(!(i==0)){
        in>>i;
        if(i!=0){
        if(i%7==0 && i>max7 && i%49!=0 ) max7=i;
        else if(i>max && i%7!=0) max=i;
        }
    }
    cout<<max*max7;
    in.close();
    return 0;
}