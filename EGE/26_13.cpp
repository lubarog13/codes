#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;

int main(){
    fstream in;
    in.open("28141.txt");
    int s, n, m[5000], sum=0, max=0;
    in>>s;
    in>>n;
    for(int i=0;i<n;i++){
        in>>m[i];
    }
    int *p=&m[0], *q=&m[n-1];
    sort(p, q);
    for(int i=0;i<n;i++){
        sum+=m[i];
        if(sum>s) sum-=m[i];
        else max=i;
    }
    cout<<++max<<endl;
    sum-=m[max];
    for(int i=max+1; i<n;i++){
        sum+=m[i];
        if(sum<=s) max=i;
        sum-=m[i];
    }
    cout<<" "<<m[max];
    return 0;
}