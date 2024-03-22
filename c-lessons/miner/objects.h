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
        Cell(int xIndex, int yIndex,  int size, bool gameField);
        Cell(const Cell &c);
        void setMine();
        Mine* getMine();
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
        void clear();

private:
        int minesNear;
        int xIndex;
        int yIndex;
        int size;
        bool isOpened = false;
        bool gameField;
        bool hasParentMine;
        Mine *mine = nullptr;
};

class Field {
private:
    int countX = 8;
    int countY = 8;
    std::vector<Cell> cells;

public:
    Field(int countX, int countY, bool gameField);
    int getCountMinesNearCell(Cell* cell);
    int getN();
    int getM();
    Cell* getCellAt(int xIndex, int yIndex);
    std::vector<Cell>* getCells();
    void clearField();
};
