#include <iostream>
#include "functions.h"
#include "objects.h"

int minesCount = 0;
int gameStatus = 0;
sf::Texture mineTexture;
int cellSize = 30;
int closedCellsCount =0;
int minScreenHeight = 200;
int minScreenWidth = 200;
int maxScreenWidth = 680;
int maxScreenHeight = 730;
int border[] = {30, 10, 40, 10};

int main()
{
    if (!mineTexture.loadFromFile("mine.png"))
    {
        std::cout<<"Ошибка загрузки изображения! "<<std::endl;
        return 1;
    }
    else {
        mineTexture.setSmooth(true);
    }
    int cellsCountX=4, cellsCountY=4;
    int maxCellsCountY = (maxScreenWidth - border[1] - border[3]) / cellSize;
    int maxCellsCountX = (maxScreenHeight - border[0] - border[2]) / cellSize;
    cellsCountY = inputInt("Введите размер поля по горизонтали (максимум - " + std::to_string(maxCellsCountX) + "): ", 0, maxCellsCountX);
    cellsCountX = inputInt("Введите размер поля по вертикали (максимум - " +  std::to_string(maxCellsCountY) + "): ", 0, maxCellsCountY);
    sf::sleep(sf::milliseconds(70));

    sf::RectangleShape cell = sf::RectangleShape(sf::Vector2f(cellSize, cellSize));
    cell.setOutlineThickness(2);
    sf::Sprite mine = sf::Sprite(mineTexture);

    Field startField = Field(cellsCountX, cellsCountY, false);
    Field gameField = Field(cellsCountX, cellsCountY, true);

    sf::RenderWindow window(sf::VideoMode(std::max(cellsCountY * cellSize + border[1] + border[3], minScreenWidth), std::max(cellsCountX * cellSize + border[0] + border[2], minScreenHeight)), L"Сапер");

    closedCellsCount = cellsCountX * cellsCountY;

    sf::Text text;
    sf::Text textBottom;
    sf::Font font;
    if (!font.loadFromFile("VelaSans-SemiBold.ttf"))
    {
        std::cout<<"Невозможно найти шрифт"<<std::endl;
        return 1;
    }

    createTexts(text, textBottom, font, cellsCountX, cellsCountY);

    while (window.isOpen())
    {
        sf::Event event;
        while (window.pollEvent(event))
        {
            if (event.type == sf::Event::Closed)
                window.close();
        }

        window.clear(sf::Color::White);
        window.setKeyRepeatEnabled(false);

         static bool lock_click;

        if (event.type == sf::Event::MouseButtonPressed)
        {
            if (event.mouseButton.button == sf::Mouse::Left && lock_click != true) {//specifies
                sf::Vector2i localPosition = sf::Mouse::getPosition(window);
                lock_click = true;
                if (!(localPosition.x < border[3] || localPosition.x > (cellsCountY * cellSize + border[1]) || localPosition.y < border[0] || localPosition.y > (cellsCountX * cellSize + border[0]))) {
                    Cell *cell = nullptr;
                    Cell* parentCell = nullptr;
                    if(gameStatus==1) {
                        cell = gameField.getCellAt((localPosition.x - border[3]) / cellSize, (localPosition.y - border[0]) / cellSize);
                        parentCell=startField.getCellAt(cell->getXIndex(), cell->getYIndex());
                        cell->setMinesNear(startField.getCountMinesNearCell(parentCell));
                        if(parentCell->checkHasMine()) {
                            cell->setParentMine(true);
                        }

                    } else if (gameStatus==0) {
                        cell = startField.getCellAt(ceil((localPosition.x - border[3]) / cellSize), ceil((localPosition.y - border[0]) / cellSize));
                    }
                    if (cell!=nullptr) {
                        cell->clickCell();
                        if (parentCell!=nullptr) {
                            parentCell->clickCell();
                        }
                    }
                    if (closedCellsCount == minesCount && gameStatus==1) gameStatus = 3;
                }
            }
        }

        if (sf::Keyboard::isKeyPressed(sf::Keyboard::Enter)) {
            if(gameStatus == 0) {
                gameStatus = 1;
            }
        }

        if (sf::Keyboard::isKeyPressed(sf::Keyboard::Space)) {
            if (gameStatus == 2 || gameStatus==3) {
                startField.clearField();
                gameField.clearField();
                minesCount = 0;
                closedCellsCount = cellsCountX * cellsCountY;
                createTexts(text, textBottom, font, cellsCountX, cellsCountY);
                gameStatus = 0;
            }
        }

         if (event.type == sf::Event::MouseButtonReleased) //Mouse button Released now.
            {
                if (event.mouseButton.button == sf::Mouse::Left) //specifies the held button       again. (You can make a variable for that if you want)
                {
                    lock_click = false; //unlock when the button has been released.
                }
            }
        for(std::vector<Cell>::iterator it = startField.getCells()->begin(); it != startField.getCells()->end(); ++it) {
          cell.setPosition((*it).getXIndex()*cellSize + border[3], (*it).getYIndex()*cellSize + border[0]);
          cell.setFillColor(it->getFillColor());
          cell.setOutlineColor(it->getOutlineColor());
          window.draw(cell);
          if(it->checkHasMine()) {
              mine.setPosition((*it->getMine()).getXIndex()*cellSize + border[3], (*it->getMine()).getYIndex()*cellSize + border[0]);
              window.draw(mine);
          }
        }

        if(gameStatus == 2) {
            text.setString(L"Вы проиграли");
            text.setFillColor(sf::Color::Red);
            text.setCharacterSize(minesCount<10? 13 : 30);
            window.draw(textBottom);
        }
        else if (gameStatus == 3) {
            text.setString(L"Вы выиграли!!!");
            text.setFillColor(sf::Color::Green);
            text.setCharacterSize(minesCount<10? 13 : 30);
            window.draw(textBottom);
        }
        else {
            if(gameStatus==1) {
                text.setString(L"Игра началась");
                for(std::vector<Cell>::iterator it = gameField.getCells()->begin(); it != gameField.getCells()->end(); ++it) {
                    int positionX = (*it).getXIndex()*cellSize + border[3];
                    int positionY = (*it).getYIndex()*cellSize + border[0];
                    cell.setPosition(positionX, positionY);
                    cell.setFillColor(it->getFillColor());
                    cell.setOutlineColor(it->getOutlineColor());
                    window.draw(cell);
                    if(it->checkHasMine()) {
                        mine.setPosition((*it->getMine()).getXIndex()*cellSize + border[3], (*it->getMine()).getYIndex()*cellSize + border[0]);
                        window.draw(mine);
                    }
                    if (it->checkIsOpened() && !it->checkHasParentMine()) {
                        sf::Text number;
                        number.setFont(font);
                        number.setString(std::to_string(it->getMinesNear()));

                        number.setCharacterSize(15);
                        number.setFillColor(sf::Color::Red);

                        number.setPosition(positionX + 5, positionY);
                        window.draw(number);
                    }
                }
            }
        }

         window.draw(text);

        window.display();
    }

    std::cout << "Введите enter для завершения программы ... "<<std::flush;
    std::cin.ignore( std::numeric_limits <std::streamsize> ::max(), '\n' );

    clear(&startField);

    return 0;
}
