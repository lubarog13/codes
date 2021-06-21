#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;
bool hasSred(int i, int sred, int arr[5001]){
    for(int j=0; j<i; j++){
        if(arr[j]==sred) return true;
    }
    return false;
}

int main()
{
    /*В текстовом файле записан набор натуральных чисел, не превышающих 109. Гарантируется, что все числа различны.
     Необходимо определить, сколько в наборе таких пар чётных чисел, что их среднее арифметическое тоже присутствует в файле, 
     и чему равно наибольшее из средних арифметических таких пар.*/
     ifstream in;
     in.open("35484.txt");
     int n, arr[5001], brr[5001];
     in>>n;
     for(int i=0; i<n; i++){
         in>>arr[i];
     }
     sort(arr, arr+n);
    int count=0, max=0, sred;
    for(int i=2; i<n; i++)
    {
        if(arr[i]%2==0){
        for(int j=0; j<i; j++){
            if(arr[j]%2==0){
            sred = (arr[i] + arr[j])*0.5;
            if(hasSred(i, sred, arr)){
                count++;
                if(max<sred) max = sred;
            }}
        }}
    }
    cout<<count<<" "<<max;
    return 0;
}