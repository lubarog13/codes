#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;

int main()
{
    /*Дана последовательность N целых положительных чисел. 
    Необходимо определить количество пар элементов этой последовательности, сумма которых делится на m = 80 
    и при этом хотя бы один элемент из пары больше b = 50.*/
    int n, arr[11000], k=0, count=0;
    ifstream in;
    in.open("28130_B.txt");
    in>>n;
    for(int i=0; i<n; i++){
        in>>arr[i];
    }
    sort(arr, arr+n);
    for(int i=0; i<n; i++){
        if(arr[i]>50){
            k=i;
            break;
        }
    }    
    for(int i=k; i<n; i++){
        for(int j=0; j<i; j++){
            if((arr[i]+arr[j])%80==0){
                count++;
            }
        }
    }
    cout<<count;
    in.close();
    return 0;
}