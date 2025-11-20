#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <vector>
#include <random>
#include <iomanip>

static std::random_device rd;
static std::mt19937 gen(rd());
static std::uniform_int_distribution<> dist_size(1, 8);
static std::uniform_int_distribution<> dist_lifetime(1, 8);

int processId = 1;

struct process {
    int size;
    int id;
    int lifetime;
    bool isAllocated;
};

struct counter {
    int allocated;
    int notAllocated;
    int total;
};

counter placeBlocksFirst(int* memory, int memorySize, std::vector<process> *processes) {
    counter count;
    count.allocated = 0;
    count.notAllocated = 0;
    count.total = 0;
    int size = processes->size();
    for (int i = 0; i < size; i++) {
        count.total++;
        if (processes->at(i).lifetime <= 0) {
            processes->at(i).isAllocated = false;
            for (int j = 0; j < memorySize; j++) {
                if (memory[j] == processes->at(i).id) {
                    memory[j] = 0;
                }
            }
            processes->erase(processes->begin() + i);
            size--;
            i--;
            continue;
        }
        if (processes->at(i).isAllocated) {
            continue;
        }
        int freeMemory = 0;
        int startIndex = -1;
        for (int j = 0; j < memorySize; j++) {
            if (memory[j] == 0) {
                freeMemory += 1;
                if (startIndex == -1) {
                    startIndex = j;
                }
                if (freeMemory >= processes->at(i).size) {
                    processes->at(i).isAllocated = true;
                    count.allocated++;
                    for (int k = 0; k < processes->at(i).size; k++) {
                        memory[startIndex + k] = processes->at(i).id;
                    }
                    break;
                }
            } else {
                if (freeMemory >= processes->at(i).size) {
                    processes->at(i).isAllocated = true;
                    count.allocated++;
                    for (int k = 0; k < processes->at(i).size; k++) {
                        memory[startIndex + k] = processes->at(i).id;
                    }
                    break;
                }
                freeMemory = 0;
                startIndex = -1;
            }
        }
        if (!processes->at(i).isAllocated) {
            count.notAllocated++;
        }
    }
    std::cout << "Memory: ";
    for (int i = 0; i < memorySize; i++) {
        std::cout << memory[i] << " ";
    }
    std::cout << std::endl;
    return count;
}

counter placeBlocksBest(int* memory, int memorySize, std::vector<process> *processes) {
    counter count;
    count.allocated = 0;
    count.notAllocated = 0;
    count.total = 0;
    int size = processes->size();
    for (int i = 0; i < size; i++) {
        count.total++;
        if (processes->at(i).lifetime <= 0) {
            processes->at(i).isAllocated = false;
            for (int j = 0; j < memorySize; j++) {
                if (memory[j] == processes->at(i).id) {
                    memory[j] = 0;
                }
            }
            processes->erase(processes->begin() + i);
            size--;
            i--;
            continue;
        }
        if (processes->at(i).isAllocated) {
            continue;
        }
        int freeMemory[memorySize];
        for (int j = 0; j < memorySize; j++) {
            freeMemory[j] = 0;
        }
        int startIndex = -1;
        for (int j = 0; j < memorySize; j++) {
            if (memory[j] == 0) {
                if (startIndex == -1) {
                    startIndex = j;
                }
                freeMemory[startIndex] += 1;
            } else {
                startIndex = -1;
            }
        }
        int minFreeMemory = memorySize;
        int minFreeMemoryIndex = -1;
        for (int j = 0; j < memorySize; j++) {
            if (freeMemory[j] <= minFreeMemory && freeMemory[j] != 0 && freeMemory[j] >= processes->at(i).size) {
                minFreeMemory = freeMemory[j];
                minFreeMemoryIndex = j;
            }
        }
        if (minFreeMemoryIndex != -1) {
            processes->at(i).isAllocated = true;
            for (int k = 0; k < processes->at(i).size; k++) {
                memory[minFreeMemoryIndex + k] = processes->at(i).id;
            }
            count.allocated++;
            break;
        }
        if (!processes->at(i).isAllocated) {
            count.notAllocated++;
        }
    }
    std::cout << "Memory: ";
    for (int i = 0; i < memorySize; i++) {
        std::cout << memory[i] << " ";
    }
    std::cout << std::endl;
    return count;
}
counter placeBlocksWorst(int* memory, int memorySize, std::vector<process> *processes) {
    counter count;
    count.allocated = 0;
    count.notAllocated = 0;
    count.total = 0;
    int size = processes->size();
    for (int i = 0; i < size; i++) {
        count.total++;
        if (processes->at(i).lifetime <= 0) {
            processes->at(i).isAllocated = false;
            for (int j = 0; j < memorySize; j++) {
                if (memory[j] == processes->at(i).id) {
                    memory[j] = 0;
                }
            }
            processes->erase(processes->begin() + i);
            size--;
            i--;
            continue;
        }
        if (processes->at(i).isAllocated) {
            continue;
        }
        int freeMemory[memorySize];
        for (int j = 0; j < memorySize; j++) {
            freeMemory[j] = 0;
        }
        int startIndex = -1;
        for (int j = 0; j < memorySize; j++) {
            if (memory[j] == 0) {
                if (startIndex == -1) {
                    startIndex = j;
                }
                freeMemory[startIndex] += 1;
            } else {
                startIndex = -1;
            }
        }
        int maxFreeMemory = 0;
        int maxFreeMemoryIndex = -1;
        for (int j = 0; j < memorySize; j++) {
            if (freeMemory[j] >= maxFreeMemory && freeMemory[j] != 0 && freeMemory[j] >= processes->at(i).size) {
                maxFreeMemory = freeMemory[j];
                maxFreeMemoryIndex = j;
            }
        }
        if (maxFreeMemoryIndex != -1) {
            processes->at(i).isAllocated = true;
            for (int k = 0; k < processes->at(i).size; k++) {
                memory[maxFreeMemoryIndex + k] = processes->at(i).id;
            }
            count.allocated++;
            break;
        }
        if (!processes->at(i).isAllocated) {
            count.notAllocated++;
        }
    }
    std::cout << "Memory: ";
    for (int i = 0; i < memorySize; i++) {
        std::cout << memory[i] << " ";
    }
    std::cout << std::endl;
    return count;
}
int main() {

    int memorySize = 32;
    int* memory = new int[memorySize];
    std::vector<process> *processes = new std::vector<process>();
    int takts = 0;
    counter count;
    count.allocated = 0;
    count.notAllocated = 0;
    counter count2;
    count2.allocated = 0;
    count2.notAllocated = 0;
    counter count3;
    count3.allocated = 0;
    count3.notAllocated = 0;
    
    while(takts < 1000) {
        takts++;
        {
            processes->push_back(process{dist_size(gen), processId, dist_lifetime(gen), false});
            count.total++;
        }
        processId++;
        counter temp = placeBlocksFirst(memory, memorySize, processes);
        count.allocated += temp.allocated;
        count.notAllocated += temp.notAllocated;
        for (int i = 0; i < processes->size(); i++) {
            if (processes->at(i).lifetime >= 0) {
                processes->at(i).lifetime -= 1;
            }
        }
    }
    takts = 0;
    for (int i = 0; i < memorySize; i++) {
        memory[i] = 0;
    }
    delete processes;
    processes = new std::vector<process>();
    while(takts < 1000) {
        takts++;
        processes->push_back(process{dist_size(gen), processId, dist_lifetime(gen), false});
        count2.total++;
        processId++;
        counter temp = placeBlocksBest(memory, memorySize, processes);
        count2.allocated += temp.allocated;
            count2.notAllocated += temp.notAllocated;   
        for (int i = 0; i < processes->size(); i++) {
            if (processes->at(i).lifetime >= 0) {
                processes->at(i).lifetime -= 1;
            }
        }
    }
    takts = 0;
    for (int i = 0; i < memorySize; i++) {
        memory[i] = 0;
    }
    delete processes;
    processes = new std::vector<process>();
    while(takts < 1000) {
        takts++;
        processes->push_back(process{dist_size(gen), processId, dist_lifetime(gen), false});
        std::cout << "Process " << processes->at(processes->size() - 1).id << " is created" << std::endl;
        count3.total++;
        processId++;
        counter temp = placeBlocksWorst(memory, memorySize, processes);
        count3.allocated += temp.allocated;
        count3.notAllocated += temp.notAllocated;
        for (int i = 0; i < processes->size(); i++) {
            if (processes->at(i).lifetime >= 0) {
                processes->at(i).lifetime -= 1;
            }
        }
    }
    delete processes;
    delete[] memory;
    std::cout << std::endl << std::endl << std::endl;
    std::cout <<"Algorithm |\tNot Allocated |\tAllocated |\tTotal|\tError Rate" << std::endl;
    std::cout << "---------------------------------------------------------" << std::endl;
    std::cout << "First Fit |\t" << count.notAllocated << "\t\t" << count.allocated << "\t\t" << count.total << "\t\t" << std::fixed << std::setprecision(2) << (double)count.notAllocated / count.total * 100 << "%" << std::endl;
    std::cout << "Best Fit |\t" << count2.notAllocated << "\t\t" << count2.allocated << "\t\t" << count2.total << "\t\t" << std::fixed << std::setprecision(2) << (double)count2.notAllocated / count2.total * 100 << "%" << std::endl;
    std::cout << "Worst Fit |\t" << count3.notAllocated << "\t\t" << count3.allocated << "\t\t" << count3.total << "\t\t" << std::fixed << std::setprecision(2) << (double)count3.notAllocated / count3.total * 100 << "%" << std::endl;
    std::cout << "---------------------------------------------------------" << std::endl;
    return 0;
}