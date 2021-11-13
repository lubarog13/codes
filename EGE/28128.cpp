#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;

int main(){
    ifstream in;
    in.open("28128_B.txt");
    int n;
    in>>n;
    int arr[10000], r=0, maxr=0;
    for(int i=0; i<n; i++){
        in>>arr[i];
    }
    int *p =&arr[0];
    int *q = &arr[n];
    sort(p, q);
    for(int i=n-1; i>=0; i--){
        r=0;
        for(int j =i; j>=0;j--){
            if((arr[i]+arr[j])%3==0){
                r = arr[i]+arr[j];
                break;}
        }
        if(r>maxr) maxr=r;
    }
    cout<<maxr;
    in.close();
    return 0;
}