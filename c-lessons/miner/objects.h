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
        Cell(int xIndex, int yIndex,  int size, bool gameField);
        Cell(const Cell &c);
        Mine *mine = nullptr;
        void setMine();
        int checkHasMine();
        int getXIndex();
        int getYIndex();
        int getSize();
        bool checkIsOpened();
        void setIsOpened();
        void clickCell(bool gameMode);
        void setMinesNear(int mines);
        bool checkHasParentMine();
        void setParentMine(bool mine);
        int getMinesNear();
        ~Cell();

private:
        int minesNear;
        int xIndex;
        int yIndex;
        int size;
        bool isOpened;
        bool gameField;
        bool hasParentMine;
};

class Field {
private:
    int N = 8;
    int M = 8;
    int cellSize = 20;
    std::vector<Cell> cells;

public:
    Field(int n, int m, bool gameField);
    int getCountMinesNearCell(Cell* cell);
    int getN();
    int getM();
    Cell* getCellAt(int xIndex, int yIndex);
    std::vector<Cell>* getCells();
};
