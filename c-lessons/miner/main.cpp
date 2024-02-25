#include <iostream>
#include "functions.h"
#include "objects.h"

int minesCount = 0;
int gameEnd = false;
sf::Texture mineTexture;

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

    Field startField = Field(cellsCountX, cellsCountY);
    Field gameField = Field(&startField, true);

    sf::RenderWindow window(sf::VideoMode(cellsCountX * 20 + 20, cellsCountY * 20 + 40), L"Сапер");

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
                if (!(localPosition.x < 20 || localPosition.x > (cellsCountX * 20 + 10) || localPosition.y < 30 || localPosition.y > (cellsCountY * 20 + 30))) {
                    Cell *cell;
                    if(gameMode) {
                        cell = gameField.getCellAt((localPosition.x - 10) / 20, (localPosition.y - 30) / 20);
                        cell->setMinesNear(startField.getCountMinesNearCell(cell->parentCell));
                    } else {
                        cell = startField.getCellAt((localPosition.x - 10) / 20, (localPosition.y - 30) / 20);
                    }
                    cell->clickCell(gameMode);
                }
            }
        }

         if (event.type == sf::Event::MouseButtonReleased) //Mouse button Released now.
            {
                if (event.mouseButton.button == sf::Mouse::Left) //specifies the held button       again. (You can make a variable for that if you want)
                {
                    lock_click = false; //unlock when the button has been released.
                }
            }

        for (auto cell : *startField.getCells()) {
          window.draw(cell);
          if(cell.checkHasMine()) {
              window.draw(*cell.mine);
          }
        }

        if(gameMode) {
            text.setString(L"Игра началась 😎");
            for (auto cell : *gameField.getCells()) {
                window.draw(cell);
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
