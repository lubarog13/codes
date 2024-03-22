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
    int maxCellsCountX = (maxScreenWidth - 20) / cellSize;
    int maxCellsCountY = (maxScreenHeight - 70) / cellSize;
    cellsCountX = inputInt("Введите размер поля по горизонтали (максимум - " + std::to_string(maxCellsCountX) + "): ", 0, maxCellsCountX);
    cellsCountY = inputInt("Введите размер поля по вертикали (максимум - " +  std::to_string(maxCellsCountY) + "): ", 0, maxCellsCountY);
    sf::sleep(sf::milliseconds(70));;

    Field startField = Field(cellsCountX, cellsCountY, false);
    Field gameField = Field(cellsCountX, cellsCountY, true);

    sf::RenderWindow window(sf::VideoMode(std::max(cellsCountY * cellSize + 20, minScreenWidth), std::max(cellsCountX * cellSize + 70, minScreenHeight)), L"Сапер");

    closedCellsCount = cellsCountX * cellsCountY;

    sf::Text text;
    sf::Text textBottom;
    sf::Font font;
    if (!font.loadFromFile("VelaSans-SemiBold.ttf"))
    {
        std::cout<<"Невозможно найти шрифт"<<std::endl;
        return 1;
    }
    text.setFont(font);
    textBottom.setFont(font);
    text.setString(L"Разместите мины и нажмите enter");

    text.setCharacterSize(10);
    text.setFillColor(sf::Color::Black);

    text.setPosition(10, 5);
    textBottom.setPosition(10, 30 + cellSize * cellsCountX + 10);
    textBottom.setCharacterSize(10);
    textBottom.setFillColor(sf::Color::Black);

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
                if (!(localPosition.x < 10 || localPosition.x > (cellsCountY * cellSize + 10) || localPosition.y < 30 || localPosition.y > (cellsCountX * cellSize + 30))) {
                    Cell *cell = nullptr;
                    Cell* parentCell = nullptr;
                    if(gameStatus==1) {
                        cell = gameField.getCellAt((localPosition.x - 10) / cellSize, (localPosition.y - 30) / cellSize);
                        parentCell=startField.getCellAt(cell->getXIndex(), cell->getYIndex());
                        cell->setMinesNear(startField.getCountMinesNearCell(parentCell));
                        if(parentCell->checkHasMine()) {
                            cell->setParentMine(true);
                        }

                    } else if (gameStatus==0) {
                        cell = startField.getCellAt(ceil((localPosition.x - 10) / cellSize), ceil((localPosition.y - 30) / cellSize));
                    }
                    if (cell!=nullptr) {
                        cell->clickCell(gameStatus==1);
                        if (parentCell!=nullptr) {
                            parentCell->clickCell(gameStatus==1);
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
                text.setString(L"Разместите мины и нажмите enter");
                text.setCharacterSize(10);
                text.setFillColor(sf::Color::Black);
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
          window.draw(*it);
          if(it->checkHasMine()) {
              window.draw(*it->getMine());
          }
        }

        if(gameStatus == 2) {
            text.setString(L"Вы проиграли");
            text.setFillColor(sf::Color::Red);
            text.setCharacterSize(minesCount<10? 13 : 30);
            textBottom.setString(L"Нажмите пробел, \nчтобы начать заново");
            window.draw(textBottom);
        }
        else if (gameStatus == 3) {
            text.setString(L"Вы выиграли!!!");
            text.setFillColor(sf::Color::Green);
            text.setCharacterSize(minesCount<10? 13 : 30);
            textBottom.setString(L"Нажмите пробел, \nчтобы начать заново");
            window.draw(textBottom);
        }
        else {
            if(gameStatus==1) {
                text.setString(L"Игра началась");
                for(std::vector<Cell>::iterator it = gameField.getCells()->begin(); it != gameField.getCells()->end(); ++it) {
                    window.draw(*it);
                    if (it->checkIsOpened() && !it->checkHasParentMine()) {
                        sf::Text number;
                        number.setFont(font);
                        number.setString(std::to_string(it->getMinesNear()));

                        number.setCharacterSize(15);
                        number.setFillColor(sf::Color::Red);

                        number.setPosition(it->getPosition().x + 5, it->getPosition().y);
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
