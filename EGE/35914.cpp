#include <iostream>


int main(){
    int count=0;
    for(long i=45000000; i<=50000000; i++){
        count=0;
        for(long j=1; j<=i; j+=2){
            if(i%j==0 ) count++;
            if(count==6) break;
        }
        if(count==5) std::cout<<i<<" ";
    }
    return 0;
}