#include "objects.h"

Cell::Cell(int xIndex, int yIndex, int size) : RectangleShape()
{
    this->xIndex = xIndex;
    this->yIndex = yIndex;
    this->size = size;
    this->setSize(sf::Vector2f(size, size));
    this->setOutlineColor(sf::Color::Blue);
    this->setFillColor(sf::Color::White);
    this->setOutlineThickness(2);
    this->setPosition(xIndex*size, yIndex*size);
};

Cell::Cell(Cell* parentCell) : Cell(parentCell->getXIndex(), parentCell->getYIndex(), parentCell->getSize())
{
    this->parentCell=parentCell;
};


Cell::~Cell() {
    delete mine;
};

int Cell::checkHasMine() {
    return mine!=nullptr;
}

int Cell::setMine()
{
    if (!checkHasMine()) {
        mine = new Mine(xIndex, yIndex, size);
        return 1;
    } else {
        delete mine;
        return -1;
    }
};

void Cell::clickCell(bool gameMode)
{
    if (!gameMode) {
        setMine();
    } else {
        if (parentCell!=nullptr) {
            parentCell->clickCell(true);
            this->~Cell();
        } else if (mine!=nullptr) {
            mine->touch();
        }
    }
}

int Cell::getXIndex() {return xIndex;};
int Cell::getYIndex()
{
    return yIndex;
};
int Cell::getSize() {
    return size;
};


Field::Field(int n, int m)
{
    N=n;
    M=m;
    cells = std::vector<Cell>();
    for (int i=0;i<n;i++) {
        for (int j=0;j<m;j++) {
            cells.push_back(Cell(i, j, cellSize));
        }
    }
};

Field::Field(Field* parentField): Field(parentField->getN(), parentField->getM())
{
    cells = std::vector<Cell>();
    for (int i=0;i<parentField->getN();i++) {
        for (int j=0;j<parentField->getM();j++) {
            cells.push_back(parentField->getCellAt(i, j));
        }
    }
};

Cell * Field::getCellAt(int xIndex, int yIndex)
{
    if (cells.size()==0) return nullptr;
    return &cells[xIndex + yIndex*M];
}

int Field::getCountMinesNearCell(Cell* cell)
{
    if(cell->checkHasMine()) return 0;
    int count=0;
    if(cell->getXIndex()>0 && cell->getYIndex()>0) {
        count += getCellAt(cell->getXIndex()-1, cell->getYIndex())->checkHasMine();
        count += getCellAt(cell->getXIndex()-1, cell->getYIndex()-1)->checkHasMine();
        count+= getCellAt(cell->getXIndex(), cell->getYIndex()-1)->checkHasMine();
        if(cell->getXIndex()<N-1 && cell->getYIndex()<M-1) {
            count += getCellAt(cell->getXIndex()-1, cell->getYIndex()+1)->checkHasMine();
            count += getCellAt(cell->getXIndex()+1, cell->getYIndex()-1)->checkHasMine();
        } else if (cell->getXIndex()<N-1) {
            count += getCellAt(cell->getXIndex()+1, cell->getYIndex()-1)->checkHasMine();
        } else if (cell->getYIndex()<M+1) {
            count+= getCellAt(cell->getXIndex()-1, cell->getYIndex()+1)->checkHasMine();
        }
    } else if (cell->getXIndex()>0) {
        count += getCellAt(cell->getXIndex()-1, cell->getYIndex())->checkHasMine();
        if(cell->getXIndex()<N-1 && cell->getYIndex()<M-1) {
            count += getCellAt(cell->getXIndex()-1, cell->getYIndex()+1)->checkHasMine();
        } else if (cell->getYIndex()<M+1) {
            count+= getCellAt(cell->getXIndex()-1, cell->getYIndex()+1)->checkHasMine();
        }
    } else if (cell->getYIndex()>0) {
        count+= getCellAt(cell->getXIndex(), cell->getYIndex()-1)->checkHasMine();
        if(cell->getXIndex()<N-1 && cell->getYIndex()<M-1) {
            count += getCellAt(cell->getXIndex()+1, cell->getYIndex()-1)->checkHasMine();
        } else if (cell->getXIndex()<N-1) {
            count += getCellAt(cell->getXIndex()+1, cell->getYIndex()-1)->checkHasMine();
        }
    }
    if(cell->getXIndex()<N-1 && cell->getYIndex()<M-1) {
        count += getCellAt(cell->getXIndex()+1, cell->getYIndex())->checkHasMine();
        count += getCellAt(cell->getXIndex()+1, cell->getYIndex()+1)->checkHasMine();
        count+= getCellAt(cell->getXIndex(), cell->getYIndex()+1)->checkHasMine();
    } else if (cell->getXIndex()<N-1) {
        count += getCellAt(cell->getXIndex()+1, cell->getYIndex())->checkHasMine();
    } else if (cell->getYIndex()<M+1) {
        count+= getCellAt(cell->getXIndex(), cell->getYIndex()+1)->checkHasMine();
    }
    return count;
}
