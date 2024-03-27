#pragma once
#include "header.h"

class Mine  {
    public:
        Mine(int xIndex, int yIndex);
        void touch();
        int getXIndex();
        int getYIndex();

    private:
        int xIndex;
        int yIndex;
};


class Cell
{
    public:
        Cell(int xIndex, int yIndex, bool gameField);
        Cell(const Cell &c);
        void setMine();
        Mine* getMine();
        int checkHasMine();
        int getXIndex();
        int getYIndex();
        bool checkIsOpened();
        void setIsOpened();
        void clickCell();
        void setMinesNear(int mines);
        bool checkHasParentMine();
        void setParentMine(bool mine);
        int getMinesNear();
        sf::Color getFillColor();
        sf::Color getOutlineColor();
        void clear();

private:
        int minesNear;
        int xIndex;
        int yIndex;
        bool isOpened = false;
        bool gameField;
        bool hasParentMine;
        Mine *mine = nullptr;
        sf::Color outlineColor;
        sf::Color fillColor;
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
