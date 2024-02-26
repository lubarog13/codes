#pragma once
#include "header.h"

class Mine : public sf::Sprite {
    public:
        Mine(int xIndex, int yIndex, int size);
        ~Mine();
        void touch();
};


class Cell : public sf::RectangleShape
{
    public:
        Cell(int xIndex, int yIndex,  int size);
        Cell(Cell *parentCell, bool gameMode);
        Cell(const Cell &c);
        Cell *parentCell = nullptr;
        Mine *mine = nullptr;
        void setMine();
        int checkHasMine();
        int getXIndex();
        int getYIndex();
        int getSize();
        void setIsOpened();
        void clickCell(bool gameMode);
        void setMinesNear(int mines);
        ~Cell();

private:
        int minesNear;
        int xIndex;
        int yIndex;
        int size;
        bool isOpened;
};

class Field {
private:
    int N = 8;
    int M = 8;
    int cellSize = 20;
    std::vector<Cell> cells;

public:
    Field(int n, int m);
    Field(Field* parentField, bool gameMode);
    int getCountMinesNearCell(Cell* cell);
    int getN();
    int getM();
    Cell* getCellAt(int xIndex, int yIndex);
    std::vector<Cell>* getCells();
};
