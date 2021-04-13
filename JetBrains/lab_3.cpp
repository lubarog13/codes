#include<windows.h>
#include <iostream>
#include <conio.h>

int main(){
    int n;
    const int NUM=100;
    std::cout<<"Введите n"<<std::endl;
    std::cin>>n;
    double f[100][100];
    for(int i=0; i<n; i++){
        std::cout <<"Введите пару x, y"<<std::endl;
        std::cin>>f[0][i];
    }
}