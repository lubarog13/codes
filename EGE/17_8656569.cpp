#include <iostream>

int main(){
    int count=0, max=0;
    /*for(int i=3361;i<=9205;i++){
        if((i%4==0||i%5==0) && i%17!=0 && i%9!=0 && i%11!=0 && i%23!=0){
            count++;
            max=i;
        }
    }
    std::cout<<count<<max;
    return 0;*/
    long del[100000];
    for (long i=125256; i<=125330; i++){
        count=0;
        for(long j=2; j<=i; j+=2){
            if(i%j==0){
                del[count] = j;
                count++;
            }
        }
        if(count==6){
            for(int l=0; l<6; l++) std::cout<<del[l]<<" ";
            std::cout<<std::endl;
        }
    }

}