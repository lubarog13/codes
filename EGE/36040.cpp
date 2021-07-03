#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;

int main()
{
    /*Имеется набор данных, состоящий из троек положительных целых чисел. 
    Необходимо выбрать из каждой тройки ровно одно число так, чтобы сумма всех
     выбранных чисел не делилась на k = 109 и при этом была максимально возможной. 
     Гарантируется, что искомую сумму получить можно. Программа должна напечатать одно число — 
     максимально возможную сумму, соответствующую условиям задачи.*/
    ifstream in;
    in.open("36040_B.txt");
    long sum=0, min=100000, arr[3], count=0 ;
    in>>arr[0];
    while(!in.eof()){
        count++;
        in>>arr[0];
        in>>arr[1];
        in>>arr[2];
        sort(arr, arr+3);
        if(count<=10) cout<<arr[2]<<endl;
        sum+=arr[2];
        if(min>(arr[2]-arr[1]) && (arr[2]-arr[1])%109!=0) min=arr[2]-arr[1];
        else if(min>(arr[2]-arr[0]) && (arr[2]-arr[0])%109!=0) min=arr[2]-arr[0];
    }
    cout<<sum<<" ";
    cout<<arr[2]<<" ";
    if(sum%109==0) sum-=min;
    cout<<sum;
    in.close();
    return 0;
}