#include <iostream>

int func(int a){
    if(a!=1) return func(a-1) + a;
    else return 1;
}
int main()
{
    std::cout<<func(40);
    return 0;
}