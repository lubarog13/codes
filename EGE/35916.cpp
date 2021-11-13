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
    int arr[100001], n=0, min=0, k=10, t=0;
    bool hasMin=false;
    in>>n;
    for(int i=0; i<n; i++){
        in>>arr[i];
    }
    sort(arr, arr+n);
    for(int i=0; i<10; i++){
        cout<<arr[i]<<endl;
    }
    cout<<endl;
    while(!hasMin){
        for(int i=t; i<k; i++){
            for(int j=i+1; j<k; j++){
                for(int z=j+1; z<k; z++){
                    if((arr[i]+arr[j]+arr[z])%3==0){
                        if((arr[i]+arr[j]+arr[z])<min || min==0){
                            min = arr[i]+arr[j]+arr[z];
                        }
                    }
                }
            }
        }
        if(min!=0) hasMin=true;
        else{
            t+=10;
            k+=10;
        }
    }
    cout<<min;
    in.close();
    return 0;
}

