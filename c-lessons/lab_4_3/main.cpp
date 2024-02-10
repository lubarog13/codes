#include <SFML/Graphics.hpp>
#include <SFML/System.hpp>
#include <iostream>
#include <limits>

sf::RenderWindow window;
sf::Mutex mutex;

void drawCircle(int R)
{
	int X = R + 200;
	int Y = 300;
    sf::CircleShape circle(R);
    circle.setOutlineThickness(5);
    circle.setOutlineColor(sf::Color::Red);
    circle.setFillColor(sf::Color::White);
    circle.setPosition(X, Y);
    for(int i = 0; i < 20; i++)
    {
        mutex.lock();
        window.setActive(true);
        window.clear();
        circle.move(-i, 0.0f);
        window.draw(circle);
        sleep(sf::milliseconds(50));
        window.display();
        window.setActive(false);
        mutex.unlock();
    }
    return;
}

void drawTriangle(int W)
{
	int X = 300;
	int Y = W + 200;
    sf::CircleShape triangle(W, 3);
    triangle.setOutlineThickness(5);
    triangle.setOutlineColor(sf::Color::Yellow);
    triangle.setFillColor(sf::Color::Yellow);
    triangle.setPosition(X, Y);
    for(int i = 0; i < 20; i++)
    {
        mutex.lock();
        window.setActive(true);
        window.clear();
        triangle.move(0.0f, -i);
        window.draw(triangle);
        sleep(sf::milliseconds(50));
        window.display();
        window.setActive(false);
        mutex.unlock();
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
    // cin.clear();
    std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
    return n;
}

int main(int argc, char **argv)
{
    int radius, width;
    radius = inputInt("Введите радиус круга: ", 0, 600);
    width = inputInt("Введите длину стороны треугольника: ", 0, 600);
    sf::CircleShape circle(radius);
    sf::CircleShape triangle(radius, 3);
    window.create(sf::VideoMode(radius + 400, width + 400), L"Двигающиеся фигуры");

    window.clear();
    window.setActive(false);

  sf::Thread thread1(&drawCircle, radius);
  thread1.launch();

  sf::Thread thread2(&drawTriangle, width);
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
  }
  std::cout << "Введите любую клавишу для завершения программы ... "<<std::flush;
    std::cin.ignore( std::numeric_limits <std::streamsize> ::max(), '\n' );
    return 0;
}
