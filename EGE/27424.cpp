#include <iostream>
#include <fstream>
using namespace std;

int main()
{
    /*Имеется набор данных, состоящий из пар положительных целых чисел. Необходимо выбрать из 
    каждой пары ровно одно число так, чтобы сумма всех выбранных чисел не делилась на 3 и при этом была максимально возможной. 
    Гарантируется, что искомую сумму получить можно
    . Программа должна напечатать одно число — максимально возможную сумму, соответствующую условиям задачи.*/
    ifstream in;
    in.open("27424-B_demo.txt");
    int arr1, arr2, mindiff=0, sum=0, n;
    in>>n;
    for(int i=0; i<n; i++){
        in>>arr1;
        in>>arr2;
        if(arr1>arr2) sum+=arr1;
        else sum+=arr2;
        if(mindiff!=0 && abs(arr1-arr2)%3!=0 && abs(arr1-arr2)<mindiff) mindiff = abs(arr1-arr2);
        else if(mindiff==0&& abs(arr1-arr2)%3!=0)  mindiff = abs(arr1-arr2);
    }
    if(sum%3==0) sum-=mindiff;
    cout<<sum;
    in.close();
    return 0;
}
