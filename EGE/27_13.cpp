#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;

int main(){
    fstream in;
    in.open("28129_B.txt");
    int n=0;
    int a=0, max=0, max_s = 0, m[1000];
    in>>n;
    for(int i=0;i<n;i++){
        in>>m[i];
    }
    int *p=&m[0];
    int j;
    int *q =&m[n];
    sort(p, q);
    max=m[n-1];
    for(int i=n-1;i>=0;i--){
        if(m[i]%7==0&&m[i]!=max&&(m[i]%160!=max%160)){
            max_s=m[i];
            break;
        }
        else if (m[i]%7==0&&m[i]!=max){
            max_s=m[i];
            j=i-1;
            while((max%160==m[j]%160)||(m[j]%7!=0)) {j--; max_s =m[j];}
            break;
        }
        else if(m[i]%7==0){
            max_s=m[i];
            j=i-1;
            while((max_s%160==m[j]%160)) {j--; max =m[j];}
            break;
        }
    }
    cout<<max<<" "<<max_s;

}