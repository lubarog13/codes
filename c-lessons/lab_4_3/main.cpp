#include <SFML/Graphics.hpp>
#include <SFML/System.hpp>
#include <iostream>
#include <limits>

sf::RenderWindow window;
sf::Mutex mutex;
sf::CircleShape circle;
sf::CircleShape triangle;
int screenWidth = 640;
int screenHeight = 480;

void drawCircle(int R)
{
    circle.setOutlineThickness(5);
    circle.setOutlineColor(sf::Color::Red);
    circle.setFillColor(sf::Color::White);
    circle.setPosition(screenWidth -(R + 190), screenHeight - 190);
    for(int i = 0; i < 1; i++)
    {
        if (circle.getPosition().x > i) {
          circle.move(-i, 0.0f);
        }
        sf::sleep(sf::milliseconds(70));
    }
    return;
}

void drawTriangle(int W)
{
    triangle.setOutlineThickness(5);
    triangle.setOutlineColor(sf::Color::Yellow);
    triangle.setFillColor(sf::Color::Yellow);
    triangle.setPosition(screenWidth - 190, screenHeight - (W + 190));
    for(int i = 0; i < 20; i++)
    {
      if (triangle.getPosition().y > i) {
        triangle.move(0.0f, -i);
      }
        sf::sleep(sf::milliseconds(70));
    }
    return;
}

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
    std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
    return n;
}

int main(int argc, char **argv)
{
    int radius, width;
    radius = inputInt("Введите радиус круга: ", 0, screenWidth - 190);
    width = inputInt("Введите размер треугольника: ", 0, screenHeight - 190);
    circle.setRadius(radius);
    triangle.setRadius(width);
    triangle.setPointCount(3);
    window.create(sf::VideoMode(screenWidth,screenHeight), L"Двигающиеся фигуры");
    window.clear();
    window.setActive(false);

    sf::Thread thread1(&drawCircle, radius);
    sf::Thread thread2(&drawTriangle, width);
  thread1.launch();

  thread2.launch();

  while (window.isOpen())
  {
    sf::Event event;
    while (window.pollEvent(event))
    {
      if (event.type == sf::Event::Closed)
      {
        window.close();
      }
    }

        mutex.lock();
        window.clear();
        window.draw(triangle);
        window.draw(circle);
        window.display();
        mutex.unlock();

  }
  std::cout << "Введите enter для завершения программы ... "<<std::flush;
    std::cin.ignore( std::numeric_limits <std::streamsize> ::max(), '\n' );
    return 0;
}
