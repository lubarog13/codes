#include <iostream>
#include <math.h>

int main(){
    float a=-2, b=2, c, f1, f2, e=0.001;
    int n=0;
    do
    {
        c = (a+b)*0.5;
        f1=1.5*cos(0.7*(a)-0.5)-0.6*abs(pow(a, 3));
        f2=1.5*cos(0.7*(c)-0.5)-0.6*abs(pow(c, 3));
        if(f1*f2<0) b=c;
        else a=c;
        n++;

    }while(abs(f2)>e);
    std::cout<<c<<" "<<n;
    return 0;
}