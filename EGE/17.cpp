#include <iostream>
#include <math.h>

int main(){
    /*int k =0;
    for(int i=2050; i<=9166; i++){
        if(i%7==0 && i%13!=0 && i%14!=0 && i%19!=0 && i%22!=0) k++;
    }
    std::cout<<k;*/
    /*int x;
    std::cin>>x;
    int a=0, b=0;
    while (x>0)
    {
        a++;
        b+=x%100;
        x/=100;
    }
    std::cout<<a<<" "<<b;*/
    /*int del[4], kolvo=0;
    for(int i=110203; i<110245; i++){
        for(int j=2;j<=i;j+=2){
            if(i%j==0){
                if(kolvo<4){
                del[kolvo]=j;
                kolvo++;}
                else{ kolvo=0; break;}
            }
        }
        if(kolvo==4){
            std::cout<<i<<" "<<del[0]<<" "<<del[1]<<" "<<del[2]<<" "<<del[3]<<std::endl;
        }
    }*/
    /*int colvo=0, max=0;
    for(int i=5883;i<=15906;i++){
        if(((i%9==0)||(i%23==0))&&(i%13!=0)&&(i%18!=0)&&(i%19!=0)&&(i%22!=0)){
            colvo++;
            if (i>max) max=i;
        }
    }
    std::cout<<colvo<<" "<<max;*/
    /*bool pr = true; int k=1;
    for(long i=2422000;i<=2422080; i++){
        pr=true;
        for(long j=2;j<i;j++){
            if(i%j==0) pr=false;
        }
        if(pr){
            std::cout<<k<<" "<<i<<std::endl;
            k++;
        }
    }*/
    /*long long min=0, colvo=0;
    for (long long i= 20000000000; i <=40000000000; i++){
        if((i%7==0) && (i%100000==0) && (i%13!=0) && (i%29!=0) && (i%43!=0) && (i%101!=0)){
            colvo++;
            if(min==0) min = i;
            std::cout<<colvo<<" "<<min<<std::endl;
        }
    } 
    std::cout<<colvo<<" "<<min;*/
    long long count =0;
    for(long long i=101000000;i<=102000000; i++){
        count=0;
        for(long long j=2;j<=i;j+=2){
            if(i%j==0) count++;
        }
        if(count==3) std::cout<<i;
    }
}