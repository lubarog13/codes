#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;
/*
На грузовом судне необходимо перевезти контейнеры, имеющие одинаковый 
габарит и разные массы. Общая масса всех контейнеров превышает грузоподъёмность судна. 
Количество грузовых мест на судне не меньше количества контейнеров, назначенных к перевозке. 
Какое максимальное количество контейнеров можно перевезти за один рейс и какова масса самого 
тяжёлого контейнера среди всех контейнеров, которые можно перевезти за один рейс?
*/

int main()
{
    int arr[10000], n, sum=0, s, k=-1, max=0;
    ifstream in;
    in.open("36039.txt");
    in>>s;
    in>>n;
    for(int i=0; i<n;i++){
        in>>arr[i];
    }
    int *p = &arr[0];
    int *q = &arr[n];
    sort(p, q);
    while(sum<=s){
        ++k;
        sum+=arr[k];
    }
    sum-=arr[k];
    cout<<k<<" ";
    sum-=arr[k-1];
    max=arr[k-1];
    for(int i=k; i<n; i++){
        if(sum+arr[i]<=s) max=arr[i];
    }
    cout<<max;
    in.close();
    return 0;
}