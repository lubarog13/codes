#include <SFML/Graphics.hpp>
#include <iostream>
#include <limits>

int inputInt(std::string message, int min, int max)
{
    int n;
    std::cout << message;
    while ((!(std::cin >> n)) || (n < min) || (n > max))
    {
        std::cout << "Ошибка! Неправильно введено число. Оно должно быть больше " << min << " и меньше " << max << "." << std::endl;
        std::cin.clear();
        std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        std::cout << message;
    }
    // cin.clear();
    std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
    return n;
}

int main()
{
    int startX, startY, endX, endY;
    startX = inputInt("Введите координату X первой точки линии: ", 0, 640);
    startY = inputInt("Введите координату Y первой точки линии: ", 0, 480);
    endX = inputInt("Введите координату X последней точки линии: ", 0, 640);
    endY = inputInt("Введите координату Y последней точки линии: ", 0, 480);
    sf::Vertex startDot = sf::Vector2f(startX, startY);
    sf::Vertex endDot = sf::Vector2f(endX, endY);
    startDot.color = sf::Color::Red;
    endDot.color= sf::Color::White;
    sf::Vertex line[] = {startDot, endDot};

    sf::RenderWindow window(sf::VideoMode(640, 480), L"Простые фигуры");

    while (window.isOpen())
    {
        sf::Event event;
        while (window.pollEvent(event))
        {
            if (event.type == sf::Event::Closed)
                window.close();
        }

        window.clear();
         window.draw(line, 2, sf::Lines);
        window.display();
    }

    std::cout << "Введите любую клавишу для завершения программы ... "<<std::flush;
    std::cin.ignore( std::numeric_limits <std::streamsize> ::max(), '\n' );

    return 0;
}
