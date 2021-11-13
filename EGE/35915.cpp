#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;

bool search(int j, long sr, long arr[5000]){
    for(int i=0;i<j; i++){
        if (arr[i]==sr){
            cout<<arr[i]<<" "<<sr<<endl;
            return true;
        }
    }
    return false;
}

int main(){
    ifstream in;
    in.open("35915.txt");
    long n, sr, count=0, maxsr=0;
    in>>n;
    long arr[5000];
    for (int i=0; i<n ;i++){
        in>>arr[i];
    }
    long *p =&arr[0];
    long *q = &arr[n];
    sort(p,q);
    cout<<arr[0]<<" "<<arr[1]<<" "<<arr[2]<<endl;
    
    for (int i=0; i<n ;i++){
        sr = 0;
        if(i%2==1){
            for (int j=i; j<n; j++){
                if(j%2==1){
                    sr = (arr[i]+arr[j])/2;
                    if(search(j, sr, arr)){
                        count++;
                        cout<<count<<endl;
                        maxsr = sr;
                    }
                }
            }
        }
    }
    cout<<count<<maxsr;
    return 0;
}