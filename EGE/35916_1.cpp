#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;

int main()
{
    /*В текстовом файле записан набор натуральных чисел, не превышающих 108. Гарантируется, что все числа различны. 
    Из набора нужно выбрать три числа, сумма которых делится на 3. Какую наименьшую сумму можно при этом получить?*/
    ifstream in;
    in.open("35916-A.txt");
    int arr[100001], n=0, min=0, k=10, t=0, minr, arr3[3], arr2[3], arr1[3], a=0, b=0, c=0, sum[5];
    bool hasMin=false;
    in>>n;
    for(int i=0; i<n; i++){
        in>>arr[i];
    }
    sort(arr, arr+n);
    for(int i=0; i<n; i++){
        if(arr[i]%3==0 && a<3){
            arr3[a]=arr[i];
            a++;
        }
        if(arr[i]%3==1 && b<3){
            arr1[b]=arr[i];
            b++;
        }
        if(arr[i]%3==2 && c<3){
            arr2[c]=arr[i];
            c++;
        }
    }
    sum[0]=arr3[0]+arr3[1]+arr3[2];
    sum[1]=arr1[0]+arr1[1]+arr1[2];
    sum[2]=arr2[0]+arr2[1]+arr2[2];
    sum[3]=arr3[0]+arr2[0]+arr1[0];
    sort(sum, sum+4);
    cout<<sum[0];
    in.close();
    return 0;
}