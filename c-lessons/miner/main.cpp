#include <iostream>
#include "functions.h"
#include "objects.h"

int minesCount = 0;
int gameEnd = false;
sf::Texture mineTexture;
int cellSize = 20;

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
    int cellsCountX=10, cellsCountY=10;
    bool gameMode = false;
    /*cellsCountX = inputInt("Введите размер поля по горизонтали (максимум - 20): ", 0, 20);
    cellsCountY = inputInt("Введите размер поля по вертикали (максимум - 20): ", 0, 20);*/

    Field startField = Field(cellsCountX, cellsCountY, false);
    Field gameField = Field(cellsCountX, cellsCountY, true);

    sf::RenderWindow window(sf::VideoMode(cellsCountX * cellSize + 20, cellsCountY * cellSize + 40), L"Сапер");

    sf::Text text;
    sf::Font font;
    if (!font.loadFromFile("VelaSans-Regular.ttf"))
    {
        std::cout<<"Невозможно найти шрифт"<<std::endl;
        return 1;
    }
    text.setFont(font);
    text.setString(L"Разместите мины и нажмите enter");

    text.setCharacterSize(10);
    text.setFillColor(sf::Color::Black);

    text.setPosition(10, 5);

    while (window.isOpen())
    {
        sf::Event event;
        while (window.pollEvent(event))
        {
            if (event.type == sf::Event::Closed)
                window.close();
        }

        window.clear(sf::Color::White);

         static bool lock_click;

        if (event.type == sf::Event::MouseButtonPressed)
        {
            if (event.mouseButton.button == sf::Mouse::Left && lock_click != true) {//specifies
                sf::Vector2i localPosition = sf::Mouse::getPosition(window);
                std::cout<<"pressed"<<std::endl;
                lock_click = true;
                if (!(localPosition.x < 20 || localPosition.x > (cellsCountX * cellSize + 10) || localPosition.y < 30 || localPosition.y > (cellsCountY * cellSize + 30))) {
                    Cell *cell;
                    Cell* parentCell = nullptr;
                    if(gameMode) {
                        cell = gameField.getCellAt((localPosition.x - 10) / cellSize, (localPosition.y - 30) / cellSize);
                        parentCell=startField.getCellAt(cell->getXIndex(), cell->getYIndex());
                        cell->setMinesNear(startField.getCountMinesNearCell(parentCell));
                        if(parentCell->checkHasMine()) {
                            cell->setParentMine(true);
                        }

                    } else {
                        cell = startField.getCellAt((localPosition.x - 10) / cellSize, (localPosition.y - 30) / cellSize);
                    }
                    cell->clickCell(gameMode);
                    if (parentCell!=nullptr) {
                        parentCell->clickCell(gameMode);
                    }
                }
            }
        }

        if (sf::Keyboard::isKeyPressed(sf::Keyboard::Enter)) {
            gameMode = true;
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
              window.draw(*it->mine);
          }
        }

        if(gameEnd) {
            text.setString(L"Вы проиграли");
            text.setFillColor(sf::Color::Red);
            text.setPosition(10, window.getSize().y / 2 - 15);
            text.setCharacterSize(30);
        } else {
            if(gameMode) {
                text.setString(L"Игра началась");
                for(std::vector<Cell>::iterator it = gameField.getCells()->begin(); it != gameField.getCells()->end(); ++it) {
                    window.draw(*it);
                    if (it->checkIsOpened() && !it->checkHasParentMine()) {
                        std::cout<<it->getMinesNear()<<std::endl;;
                        sf::Text number;
                        number.setFont(font);
                        number.setString(std::to_string(it->getMinesNear()));

                        number.setCharacterSize(15);
                        number.setFillColor(sf::Color::Red);

                        number.setPosition(it->getPosition().x + 3, it->getPosition().y);
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
