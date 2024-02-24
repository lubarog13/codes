#include <iostream>
#include "functions.h"
#include "objects.h"

int minesCount = 0;
int gameEnd = false;

int main()
{
    int cellsCountX=10, cellsCountY=10;
    /*cellsCountX = inputInt("Введите размер поля по горизонтали (максимум - 20): ", 0, 20);
    cellsCountY = inputInt("Введите размер поля по вертикали (максимум - 20): ", 0, 20);*/

    Field startField = new Field(cellsCountX, cellsCountY);

    sf::RenderWindow window(sf::VideoMode(cellsCountX * 20 + 20, cellsCountY * 20 + 20), L"Сапер");

    while (window.isOpen())
    {
        sf::Event event;
        while (window.pollEvent(event))
        {
            if (event.type == sf::Event::Closed)
                window.close();
        }

        window.clear(sf::Color::White);
        for (auto cell : *startField.getCells()) {
            window.draw(cell);
        }
        window.display();
    }

    std::cout << "Введите любую клавишу для завершения программы ... "<<std::flush;
    std::cin.ignore( std::numeric_limits <std::streamsize> ::max(), '\n' );

    return 0;
}
