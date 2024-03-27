#include "objects.h"
#include "functions.h"

Cell::Cell(int xIndex, int yIndex, bool gameField)
{
    this->xIndex = xIndex;
    this->yIndex = yIndex;
    if(gameField) {
        this->outlineColor = sf::Color::White;
        this->fillColor = sf::Color::Blue;
    }
    else {
        this->outlineColor = sf::Color::Blue;
        this->fillColor = sf::Color::White;
    }
    if(this->isOpened) {
        this->fillColor = sf::Color::White;
    }

    this->gameField = gameField;
};
Cell::Cell(const Cell& c)
{
    this->xIndex = c.xIndex;
    this->yIndex = c.yIndex;
    this->minesNear = c.minesNear;
    this->mine = c.mine;
    this->gameField = c.gameField;
    if(this->gameField) {
        this->outlineColor = sf::Color::White;
        this->fillColor = sf::Color::Blue;
    }
    else {
        this->outlineColor = sf::Color::Blue;
        this->fillColor = sf::Color::White;
    }
    this->isOpened = c.isOpened;
    if(this->isOpened) {
        this->fillColor = sf::Color::White;
    }
}

int Cell::checkHasMine() {
    return mine!=nullptr;
}

void Cell::setMine()
{
    if (!checkHasMine()) {
        mine = new Mine(xIndex, yIndex);
        minesCount+=1;
    } else {
        delete mine;
        mine = nullptr;
       minesCount-=1;
    }
};

Mine* Cell::getMine()
{
    return mine;
}

void Cell::setMinesNear(int mines)
{
    minesNear = mines;
};

void Cell::setIsOpened() {
    if(this->isOpened) {
        this->fillColor = sf::Color::Transparent;
    }
};

bool Cell::checkIsOpened()
{
    return this->isOpened;
}

int Cell::getMinesNear()
{
    return minesNear;
}

bool Cell::checkHasParentMine()
{
    return this->hasParentMine;
}

void Cell::setParentMine(bool mine)
{
    this->hasParentMine = mine;
}




void Cell::clickCell()
{
    if (gameStatus==0) {
        setMine();
    } else if (gameStatus==1) {
        if (gameField) {
            isOpened = true;
            closedCellsCount--;
            this->setIsOpened();
        } else if (mine!=nullptr) {
            this->fillColor = sf::Color::Red;
            mine->touch();
        }
    }
}

int Cell::getXIndex() {return xIndex;};
int Cell::getYIndex()
{
    return yIndex;
};
sf::Color Cell::getFillColor() {
    return this->fillColor;
};

sf::Color Cell::getOutlineColor() {
    return this->outlineColor;
};

Field::Field(int countX, int countY, bool gameField)
{
    this->countX=countX;
    this->countY=countY;

    for (int i=0;i<countX;i++) {
        for (int j=0;j<countY;j++) {
            cells.push_back(Cell(j, i, gameField));
        }
    }
};


Cell * Field::getCellAt(int xIndex, int yIndex)
{
    if (cells.size()==0) return nullptr;
    return &cells[yIndex*countY + xIndex];
}

int Field::getCountMinesNearCell(Cell* cell)
{
    if(cell->checkHasMine()) return 0;
    int count=0;
    if(cell->getXIndex()>0 && cell->getYIndex()>0) {
        count += getCellAt(cell->getXIndex()-1, cell->getYIndex())->checkHasMine();
        count += getCellAt(cell->getXIndex()-1, cell->getYIndex()-1)->checkHasMine();
        count+= getCellAt(cell->getXIndex(), cell->getYIndex()-1)->checkHasMine();
        if(cell->getXIndex()<countX-1 && cell->getYIndex()<countX-1) {
            count += getCellAt(cell->getXIndex()-1, cell->getYIndex()+1)->checkHasMine();
            count += getCellAt(cell->getXIndex()+1, cell->getYIndex()-1)->checkHasMine();
        } else if (cell->getXIndex()<countY-1) {
            count += getCellAt(cell->getXIndex()+1, cell->getYIndex()-1)->checkHasMine();
        } else if (cell->getYIndex()<countX-1) {
            count+= getCellAt(cell->getXIndex()-1, cell->getYIndex()+1)->checkHasMine();
        }
    } else if (cell->getXIndex()>0) {
        count += getCellAt(cell->getXIndex()-1, cell->getYIndex())->checkHasMine();
        if(cell->getXIndex()<countY-1 && cell->getYIndex()<countX-1) {
            count += getCellAt(cell->getXIndex()-1, cell->getYIndex()+1)->checkHasMine();
        } else if (cell->getYIndex()<countX-1) {
            count+= getCellAt(cell->getXIndex()-1, cell->getYIndex()+1)->checkHasMine();
        }
    } else if (cell->getYIndex()>0) {
        count+= getCellAt(cell->getXIndex(), cell->getYIndex()-1)->checkHasMine();
        if(cell->getXIndex()<countY-1 && cell->getYIndex()<countX-1) {
            count += getCellAt(cell->getXIndex()+1, cell->getYIndex()-1)->checkHasMine();
        } else if (cell->getXIndex()<countY-1) {
            count += getCellAt(cell->getXIndex()+1, cell->getYIndex()-1)->checkHasMine();
        }
    }
    if(cell->getXIndex()<countY-1 && cell->getYIndex()<countX-1) {
        count += getCellAt(cell->getXIndex()+1, cell->getYIndex())->checkHasMine();
        count += getCellAt(cell->getXIndex()+1, cell->getYIndex()+1)->checkHasMine();
        count+= getCellAt(cell->getXIndex(), cell->getYIndex()+1)->checkHasMine();
    } else if (cell->getXIndex()<countY-1) {
        count += getCellAt(cell->getXIndex()+1, cell->getYIndex())->checkHasMine();
    } else if (cell->getYIndex()<countX-1) {
        count+= getCellAt(cell->getXIndex(), cell->getYIndex()+1)->checkHasMine();
    }
    return count;
}

int Field::getN()
{
    return countX;
}

int Field::getM()
{
    return countX;
}

std::vector<Cell> * Field::getCells()
{
    return &this->cells;
}

Mine::Mine(int xIndex, int yIndex)
{
    this->xIndex = xIndex;
    this->yIndex = yIndex;
};

int Mine::getXIndex() {return xIndex;};
int Mine::getYIndex()
{
    return yIndex;
};


void Mine::touch()
{
    gameStatus = 2;
}

void Cell::clear()
{
    this->isOpened = false;
    this->minesNear = 0;
    this->hasParentMine = false;
    if(gameField) {
        this->outlineColor = sf::Color::White;
        this->fillColor = sf::Color::Blue;
    }
    else {
        this->outlineColor = sf::Color::Blue;
        this->fillColor = sf::Color::White;
    }
}


void Field::clearField()
{
    clear(this);
    for(std::vector<Cell>::iterator it = cells.begin(); it != cells.end(); ++it) {
        it->clear();
    }
};




