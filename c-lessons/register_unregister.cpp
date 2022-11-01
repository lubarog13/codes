#include <iostream>
#include <ctime>

unsigned int i;
unsigned int delay;

int main() {
    register unsigned int j;
    long start, end;
    start = clock();
    for(delay=0; delay<50;delay++)
        for(i=0;i<640000;i++)
    end = clock();
    std::cout<<"Для unregister: "<<end-start<<"\n";
    start=clock();
    for(delay=0; delay<50;delay++)
        for(j=0;j<640000;j++)
    end = clock();
    std::cout<<"Для register: "<<end-start<<"\n";
    return 0;

}
