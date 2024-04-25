#include <SFML/Graphics.hpp>
#include <iostream>
#include <string>
#include <limits>


int winXsize = 640;
int winYsize = 480;

// Функция запрашивает у пользователя целое число в заданном диапазоне
int inputInt(std::string message, int min, int max)
{
    int n;
    std::cout << message;
    // Цикл продолжается до тех пор, пока пользователь не введет корректное число
    while ((!(std::cin >> n)) || (n < min) || (n > max))
    {
        std::cout << "Ошибка! Неправильно введено число. Оно должно быть больше " << min << " и меньше " << max << "." << std::endl;
        std::cin.clear(); // Очистка флага ошибки
        std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n'); // Игнорование некорректного ввода
        std::cout << message;// Вывод сообщения для запроса нового ввода
    }
    // Очистка ввода для подготовки потока для следующего ввода
    std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
    return n;
}


int main()
{
    setlocale(LC_ALL, "Russian");

    // Создание окна размером 400x400
    sf::RenderWindow window(sf::VideoMode(winXsize, winYsize), "Изменение цвета квадрата");

    int sizeMax;
    if (winXsize <= winYsize)
    {
        sizeMax = winXsize;
    }
    else
    {
        sizeMax = winYsize;
    }

    // Запрашивание у пользователя размера стороны квадрата
    int size = inputInt("Введите размер стороны квадрата (1-" + std::to_string(sizeMax) + "): ", 1, sizeMax);

    sf::RectangleShape square(sf::Vector2f(size, size)); // Создание квадрата размером 100x100
    square.setOrigin(size/ 2, size / 2); // Центр квадрата установлен в центре окна
    square.setPosition(winXsize / 2, winYsize / 2); // Размещение квадрата в центр окна
    square.setFillColor(sf::Color::White); // Изначально квадрат белого цвета

    // Программа работает до тех пор, пока открыто окно
    while (window.isOpen())
    {
        // Проверка всех событий окна, которые были вызваны с последней итерацией цикла
        sf::Event event;
        while (window.pollEvent(event))
        {
            // Запрос закрытия события: закрытие окна
            if (event.type == sf::Event::Closed)
            {
                window.close();
            }
            // Иначе, если нажата клавиша
            else if (event.type == sf::Event::KeyPressed)
            {
                // Обрабаботка нажатия клавиш
                if (event.key.code == sf::Keyboard::Num1)
                {
                    // При нажатии "1" цвет квадрата меняется на зеленый
                    square.setFillColor(sf::Color::Green);
                }
                else if (event.key.code == sf::Keyboard::Num2)
                {
                    // При нажатии "2" цвет квадрата меняется на голубой
                    square.setFillColor(sf::Color::Blue);
                }
            }
        }

        window.clear(); // Очистка содержимого окна
        window.draw(square); // Отрисовка квадрата
        window.display(); // Отображение нарисованного содержимого
    }

    std::cout << "Введите enter для завершения программы ... "<<std::flush;
    std::cin.ignore( std::numeric_limits <std::streamsize> ::max(), '\n' );

    return 0;
}
