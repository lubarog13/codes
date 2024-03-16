#include "objects.h"
#include "functions.h"

Cell::Cell(int xIndex, int yIndex, int size, bool gameField) : RectangleShape()
{
    this->xIndex = xIndex;
    this->yIndex = yIndex;
    this->size = size;
    this->setSize(sf::Vector2f(size, size));
    if(gameField) {
        this->setOutlineColor(sf::Color::White);
        this->setFillColor(sf::Color::Blue);
    }
    else {
        this->setOutlineColor(sf::Color::Blue);
        this->setFillColor(sf::Color::White);
    }
    if(this->isOpened) {
        this->setFillColor(sf::Color::White);
    }
    this->setOutlineThickness(2);
    this->gameField = gameField;
    this->setPosition(xIndex*size + 10, yIndex*size + 30);
};
Cell::Cell(const Cell& c)
{
    this->xIndex = c.xIndex;
    this->yIndex = c.yIndex;
    this->size = c.size;
    this->setSize(sf::Vector2f(size, size));
    this->minesNear = c.minesNear;
    this->setOutlineThickness(2);
    this->mine = c.mine;
    this->gameField = c.gameField;
    this->setPosition(xIndex*size + 10, yIndex*size + 30);
    if(this->gameField) {
        this->setFillColor(sf::Color::Blue);
        this->setOutlineColor(sf::Color::White);
    } else {
        this->setOutlineColor(sf::Color::Blue);
        this->setFillColor(sf::Color::White);
    }
    this->isOpened = c.isOpened;
    if(this->isOpened) {
        std::cout<<"Opened"<<std::endl;
        this->setFillColor(sf::Color::White);
    }
}

int Cell::checkHasMine() {
    return mine!=nullptr;
}

void Cell::setMine()
{
    if (!checkHasMine()) {
        mine = new Mine(xIndex, yIndex, size);
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
        this->setFillColor(sf::Color::Transparent);
        this->setScale(0, 0);
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




void Cell::clickCell(bool gameMode)
{
    if (!gameMode) {
        setMine();
    } else {
        if (gameField) {
            isOpened = true;
            closedCellsCount--;
            this->setIsOpened();
        } else if (mine!=nullptr) {
            this->setFillColor(sf::Color::Transparent);
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


Field::Field(int n, int m, bool gameField)
{
    N=n;
    M=m;

    for (int i=0;i<n;i++) {
        for (int j=0;j<m;j++) {
            cells.push_back(Cell(i, j, cellSize, gameField));
        }
    }
};


Cell * Field::getCellAt(int xIndex, int yIndex)
{
    if (cells.size()==0) return nullptr;
    return &cells[xIndex*N + yIndex];
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
        } else if (cell->getYIndex()<M-1) {
            count+= getCellAt(cell->getXIndex()-1, cell->getYIndex()+1)->checkHasMine();
        }
    } else if (cell->getXIndex()>0) {
        count += getCellAt(cell->getXIndex()-1, cell->getYIndex())->checkHasMine();
        if(cell->getXIndex()<N-1 && cell->getYIndex()<M-1) {
            count += getCellAt(cell->getXIndex()-1, cell->getYIndex()+1)->checkHasMine();
        } else if (cell->getYIndex()<M-1) {
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
    } else if (cell->getYIndex()<M-1) {
        count+= getCellAt(cell->getXIndex(), cell->getYIndex()+1)->checkHasMine();
    }
    return count;
}

int Field::getN()
{
    return N;
}

int Field::getM()
{
    return M;
}

std::vector<Cell> * Field::getCells()
{
    return &this->cells;
}

Mine::Mine(int xIndex, int yIndex, int size) : sf::Sprite()
{
    this->setPosition(xIndex*size + 10, yIndex*size + 30);
    this->setTexture(mineTexture);
        /*this->setScale(
        targetSize.x / this->getLocalBounds().width,
        targetSize.y / this->getLocalBounds().height);*/
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
    this->setScale(1, 1);
    if(this->gameField) {
        this->setFillColor(sf::Color::Blue);
        this->setOutlineColor(sf::Color::White);
    } else {
        this->setOutlineColor(sf::Color::Blue);
        this->setFillColor(sf::Color::White);
    }
}


void Field::clearField()
{
    clear(this);
    for(std::vector<Cell>::iterator it = cells.begin(); it != cells.end(); ++it) {
        it->clear();
    }
};




