#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;

bool hasSred(int i, int sr, int arr[5001]){
    for(int j=0; j<i; j++){
        if(arr[j]==sr) return true;
    }
    return false;
}

int main()
{
    /*В текстовом файле записан набор натуральных чисел, не превышающих 109. Гарантируется,
     что все числа различны. Необходимо определить, сколько в наборе таких пар нечётных чисел,
      что их среднее арифметическое тоже присутствует в файле, и чему равно наибольшее из средних арифметических таких пар.*/
    ifstream in;
    in.open("35915_1.txt");
    int n, arr[5001], count=0, max=0, sr;
    in>>n;
    for(int i=0; i<n; i++){
        in>>arr[i];
    }
    sort(arr, arr+n);
    for(int i=2; i<n; i++){
        if(arr[i]%2==1){
            for(int j=0; j<i; j++){
                if(arr[j]%2==1){
                    sr = (arr[i]+arr[j])*0.5;
                    if(hasSred(i, sr, arr)){
                        count++;
                        if(sr>max) max=sr;
                    }
                }
            }
        }
    }
    cout<<count<<" "<<max;
    in.close();
    return 0;
}