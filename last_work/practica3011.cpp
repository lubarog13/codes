#include <iostream>
#include <array>
#include<vector>
#include<time.h>
#include <chrono>

int main(){
    std::array<int, 10>array;
    for(auto &item : array) {
    std::cin>>item;
    }
    std::vector<int>vect;
    for (int i=0; i<100; i++){
        vect.push_back(1);
    }
    auto start = std::chrono::high_resolution_clock::now();
    vect.insert(vect.begin(), 5);
    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> elapsed_seconds = end-start;
    std::cout << "elapsed time: " << elapsed_seconds.count() << "s\n";
    for (long i=0; i<1000000; i++){
        vect.push_back(2);
    }
    start = std::chrono::high_resolution_clock::now();
    vect.insert(vect.begin(), 6);
    end = std::chrono::high_resolution_clock::now();
    elapsed_seconds = end-start;
    std::cout << "elapsed time: " << elapsed_seconds.count() << "s\n";
    return 0;
}