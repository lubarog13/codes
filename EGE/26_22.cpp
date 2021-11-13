#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;

int main(){
    ifstream in;
    in.open("28140.txt");
    int c, n;
    in>>n;
    int mass[4000];
    in>>c;
    cout<<n<<endl<<c<<endl;
    for(int i=0;i<c;i++){
        in>>mass[i];
    }
    int *p= &mass[0];
    int *q = &mass[c-1];
    sort(p, q);
    int n_1=0;
    cout<<c<<" "<<*p<<" "<<*q<<endl;
    int i=0, count=0;
    while (n_1<=n){
        n_1+=mass[i];
        i++;
        count++;
    }
    cout<<count-1<<" ";
    n_1-=mass[i-1];
    n_1-=mass[i-2];
    int max = mass[i-2];
    for(int j=i-1;j<c;j++){
        if(n_1+mass[j]<=n){
            max = mass[j];
        }
    }
    cout<<max;
    in.close();
    return 0;

}