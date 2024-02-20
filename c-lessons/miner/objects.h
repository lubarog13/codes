#pragma once
#include "header.h"

class Mine : public sf::Sprite {
    public:
        Mine(int xIndex, int yIndex, int size);
        void touch();
};


class Cell : public sf::RectangleShape
{
    public:
        Cell(int xIndex, int yIndex,  int size);
        Cell(Cell *parentCell);
        int setMine();
        int checkHasMine();
        int getXIndex();
        int getYIndex();
        int getSize();
        void clickCell(bool gameMode);
        ~Cell();
    private:
        Cell *parentCell;
        Mine *mine;
        int xIndex;
        int yIndex;
        int size;
};

class Field {
private:
    int N = 8;
    int M = 8;
    int minesCount = 0;
    int cellSize = 20;
    std::vector<Cell> cells;

public:
    Field(int n, int m);
    Field(Field* parentField);
    ~Field();
    int getCountMinesNearCell(Cell* cell);
    int getN();
    int getM();
    Cell* getCellAt(int xIndex, int yIndex);
    std::vector<Cell>* getCells();
};
