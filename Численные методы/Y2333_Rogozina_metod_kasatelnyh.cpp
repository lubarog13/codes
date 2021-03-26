#include <iostream>
#include <math.h>

int main(){
    float a, b, x0, x, e, n=0, f1, f2;
    std::cout<<"Input a, b, e"<<std::endl;
    std::cin>>a;
    std::cin>>b;
    std::cin>>e;
    f1=pow(exp(b), (-0.5))-pow((b-0.8), 2) + 0.1;
    f2=(1-8 * exp(0.5*b))/(4*exp(0.5*b));
    if(f1*f2>0) x=b;
    else x=a;
    do{
        x0=x;
        f1=pow(exp(x0), (-0.5))-pow((x0-0.8), 2) + 0.1;
        f2=exp(-0.5*x0)*(0.5)-2*x0+1.6;
        x=x0-f1/f2;
        n++;
    }while (abs(x-x0)>e);
    std::cout<<x<<" "<<n;
    return 0;
}