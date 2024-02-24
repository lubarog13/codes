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
    this->setPosition(xIndex*size + 10, yIndex*size + 10);
};
Cell::Cell(const Cell& c)
{
    this->xIndex = c.xIndex;
    this->yIndex = c.yIndex;
    this->size = c.size;
    this->setSize(sf::Vector2f(size, size));
    this->setOutlineColor(sf::Color::Blue);
    this->setFillColor(sf::Color::White);
    this->setOutlineThickness(2);
    this->setPosition(xIndex*size + 10, yIndex*size + 10);
    if(c.parentCell!=nullptr) {
        this->parentCell=c.parentCell;
        this->setFillColor(sf::Color::Blue);
        this->setOutlineColor(sf::Color::White);
    }
}


Cell::Cell(Cell* parentCell, bool gameMode) : Cell(parentCell->getXIndex(), parentCell->getYIndex(), parentCell->getSize())
{
    this->parentCell=parentCell;
    if(gameMode) {
        this->setFillColor(sf::Color::Blue);
        this->setOutlineColor(sf::Color::White);
    }
    std::cout<<"ok1 "<<std::endl;
};


Cell::~Cell() {
    if (this->mine!=nullptr) {
        delete mine;
    }
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
       minesCount-=1;
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
    std::cout<<n<<" "<<m<<std::endl;

    for (int i=0;i<n;i++) {
        for (int j=0;j<m;j++) {
            std::cout<<i<<" "<<j<<std::endl;
            cells.push_back(Cell(i, j, cellSize));
        }
    }
};

// //@ToDo: разобраться с конструкторами копий для поля

Field::Field(Field* parentField): Field(parentField->getN(), parentField->getM())
{
    cells = std::vector<Cell>();
    std::cout<<"ok2"<<std::endl;
    for (int i=0;i<parentField->getN();i++) {
        for (int j=0;j<parentField->getM();j++) {
            cells.push_back(Cell(parentField->getCellAt(i, j), true));
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
    this->setPosition(xIndex*size, yIndex*size);
    sf::Vector2f targetSize(15.0f, 15.0f);
    sf::Texture texture;
    if (!texture.loadFromFile("mine.png"))
    {
        std::cout<<"Ошибка загрузки изображения! "<<std::endl;
    }
    else {
        this->setTexture(texture);
        this->setScale(
        targetSize.x / this->getLocalBounds().width,
        targetSize.y / this->getLocalBounds().height);
    }
}

void Mine::touch()
{
    gameEnd = true;
}




