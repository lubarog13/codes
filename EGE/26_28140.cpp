#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;

int main(){
    fstream file;
    file.open("26_28140.txt");
    int s, n, mass[10000], sum=0, k=-1;
    file>>s;
    file>>n;
    for(int i=0; i<n; i++){
        file>>mass[i];
    }
    int *p = &mass[0];
    int *q = &mass[n-1];
    sort(p, q);
    while(sum<=s){
        k++;
        sum+=mass[k];
    }
    sum-=mass[k];
    k--;
    cout<<k+1<<" ";
    sum-=mass[k];
    int w=k;
    for(int i=k; i<n; i++){
        if(sum+mass[i]<=s) w=i;
    }
    std::cout<<mass[w];
}