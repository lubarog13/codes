#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;

int main(){
    fstream file;
    file.open("28128_B.txt");
    int  n, mass[10000], sum=1;
    file>>n;
    for(int i=0; i<n; i++){
        file>>mass[i];
    }
    int *p = &mass[0];
    int *q = &mass[n];
    sort(p, q);
    cout<<mass[n-2]<<" ";
    for(int i=n-1; i>=0; i--){
        for(int j=i-1; j>=0; j--){
            if((mass[i]+mass[j])%3==0){
                sum=mass[i]+mass[j];
                break;
            }
        }
        if(sum!=1) break;
    }
    cout<<sum;
    return 0;
}