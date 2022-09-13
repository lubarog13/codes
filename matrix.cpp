#include <iostream>



int main() {
    int arr[7][7] = {};
    int* a = &arr[0][0];
    for (int i=0; i<49; i++) {
        *a = 0;
        std::cout<<(*a)<<' ';
        a++;
    }
    a = &arr[0][0];
    std::cout<<'\n';
    for (int i=0; i<49; i+=2) {
        *a = 1;
        a+=2;
    }
    a = &arr[0][0];
    for (int i=0; i<49; i++) {
        std::cout<<(*a)<<' ';
        a++;
    }
}
