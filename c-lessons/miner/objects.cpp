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
    this->setPosition(xIndex*size + 10, yIndex*size + 30);
};
Cell::Cell(const Cell& c)
{
    this->xIndex = c.xIndex;
    this->yIndex = c.yIndex;
    this->size = c.size;
    this->setSize(sf::Vector2f(size, size));
    this->setOutlineColor(sf::Color::Blue);
    this->setFillColor(sf::Color::White);
    this->minesNear = c.minesNear;
    this->setOutlineThickness(2);
    this->mine = c.mine;
    this->setPosition(xIndex*size + 10, yIndex*size + 30);
    if(c.parentCell!=nullptr) {
        this->parentCell=c.parentCell;
        this->setFillColor(sf::Color::Blue);
        this->setOutlineColor(sf::Color::White);
        this->setIsOpened();
    }
}


Cell::Cell(Cell* parentCell, bool gameMode) : Cell(parentCell->getXIndex(), parentCell->getYIndex(), parentCell->getSize())
{
    this->parentCell=parentCell;
    if(gameMode) {
        this->setFillColor(sf::Color::Blue);
        this->setOutlineColor(sf::Color::White);
    }
};


Cell::~Cell() {
};

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

void Cell::setMinesNear(int mines)
{
    minesNear = mines;
};

void Cell::setIsOpened() {
    if(this->isOpened) {
        this->setFillColor(sf::Color::Transparent);
        sf::Text text;
        sf::Font font;
        if (!font.loadFromFile("VelaSans-Regular.ttf"))
        {
            std::cout<<"Невозможно найти шрифт"<<std::endl;
        }
        text.setFont(font);
        text.setString(std::to_string(minesNear));

        text.setCharacterSize(15);
        text.setFillColor(sf::Color::Red);

        text.setPosition(this->getPosition().x + 3, this->getPosition().y + 3);
    }
};

void Cell::clickCell(bool gameMode)
{
    if (!gameMode) {
        setMine();
    } else {
        if (parentCell!=nullptr) {
            parentCell->clickCell(true);
            isOpened = true;
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


Field::Field(int n, int m)
{
    N=n;
    M=m;

    for (int i=0;i<n;i++) {
        for (int j=0;j<m;j++) {
            cells.push_back(Cell(i, j, cellSize));
        }
    }
};

// //@ToDo: разобраться с конструкторами копий для поля

Field::Field(Field* parentField, bool gameMode): Field(parentField->getN(), parentField->getM())
{
    cells = std::vector<Cell>();
    for (int i=0;i<parentField->getN();i++) {
        for (int j=0;j<parentField->getM();j++) {
            cells.push_back(Cell(parentField->getCellAt(i, j), true));
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

Mine::~Mine()
{
    std::cout<<"destroyed"<<std::endl;
};


void Mine::touch()
{
    gameEnd = true;
}




