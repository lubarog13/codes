#include <SFML/Graphics.hpp>
#include <iostream>
#include <string>
#include <limits>
#include <math.h>

float a = (float) M_PI / 4; // Угол наклона плечей дерева
float q = (float) 1 / 3; // Коэффициент сокращения длины плеча дерева на каждом уровне рекурсии

int offsetY = 10; // Отступ дерева от окна по вертикали
int offsetX = 10; // Отступ дерева от окна по горизонтали

int winYsize = 400; // Размер окна по вертикали
int winXsize = 2 * (winYsize - 2*offsetY) * tan(a) + 2*offsetX; // Максимально необходимый размер окна по горизонтали

float Lmax = (float) (winYsize - 2*offsetY) * (1 - q) / cos(a); // Максимально возможная длина плеча с учётом размеров окна
int Nmax = 18; // Максимально возможное число уровней рекурсии


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
    // std::cin.clear();
    std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
    return n;
}


// Структура для узла бинарного дерева
struct TreeNode
{
    int data;           // Данные узла
    TreeNode* left;     // Указатель на левое поддерево
    TreeNode* right;    // Указатель на правое поддерево

    // Конструктор для создания узла с данными и пустыми указателями на поддеревья
    TreeNode(int val) : data(val), left(nullptr), right(nullptr) {}
};

// Класс для работы с бинарным деревом
class BinaryTree
{
public:
    TreeNode* root; // Указатель на корень дерева

    // Конструктор класса, инициализирующий корень как nullptr
    BinaryTree() : root(nullptr) {}

    // Метод для построения бинарного дерева заданной глубины
    void buildTree(int levels)
    {
        root = buildTreeRecursive(levels, 1);
    }

    // Метод для отрисовки дерева на окне SFML
    void draw(sf::RenderWindow &window, float L, float x, float y, float dx, float dy)
    {
        drawTreeLines(window, root, L, x, y, dx, dy);
    }

private:
    // Рекурсивный метод для построения бинарного дерева заданной глубины
    TreeNode* buildTreeRecursive(int levels, int currentLevel)
    {
        // Если текущий уровень превышает заданную глубину, возвращаем nullptr
        if (currentLevel > levels)
        {
            return nullptr;
        }

        // Создаем новый узел с данными текущего уровня
        TreeNode* newNode = new TreeNode(currentLevel);
        // Рекурсивно строим левое и правое поддеревья
        newNode->left = buildTreeRecursive(levels, currentLevel + 1);
        newNode->right = buildTreeRecursive(levels, currentLevel + 1);

        return newNode;
    }

    // Рекурсивный метод для отрисовки линий связей между узлами дерева
    void drawTreeLines(sf::RenderWindow &window, TreeNode* node, float L, float x, float y, float dx, float dy)
    {
        // Если текущий узел пуст, прекращаем отрисовку
        if (node == nullptr)
        {
            return;
        }

        // Отрисовка линии к левому узлу, если он существует
        drawLine(window, x, y, x - dx, y + dy, node->left != nullptr);
        // Отрисовка линии к правому узлу, если он существует
        drawLine(window, x, y, x + dx, y + dy, node->right != nullptr);

        // Рекурсивный вызов для левого поддерева
        drawTreeLines(window, node->left, L * q, x - dx, y + dy, (L * q) * sin(a), (L * q) * cos(a));
        // Рекурсивный вызов для правого поддерева
        drawTreeLines(window, node->right, L * q, x + dx, y + dy, (L * q) * sin(a), (L * q) * cos(a));
    }

    // Метод для отрисовки линии между двумя точками, если условие выполнено
    void drawLine(sf::RenderWindow &window, float x1, float y1, float x2, float y2, bool condition)
    {
        // Если условие истинно, создаем и отрисовываем линию
        if (condition)
        {
            sf::Vertex line[] =
            {
                sf::Vertex(sf::Vector2f(x1, y1), sf::Color::White),
                sf::Vertex(sf::Vector2f(x2, y2), sf::Color::White)
            };
            window.draw(line, 2, sf::Lines);
        }
    }
};

// Основная функция программы
int main()
{
    setlocale(LC_ALL, "Russian");

    int L = inputInt("Введите исходную длину плеча дерева (от 1 до " + std::to_string(Lmax) + "): ", 1, Lmax);

    int levels = inputInt("Введите количество уровней (от 0 до " + std::to_string(Nmax) + "): ", 0, Nmax);

    // Создание объекта бинарного дерева
    BinaryTree tree;
    // Построение дерева заданной глубины
    tree.buildTree(levels);

    // Создание окна SFML
    sf::RenderWindow window(sf::VideoMode(winXsize, winYsize), L"Binary Tree Lines");


    float X0 = winXsize / 2;
    float Y0 = offsetY;
    float dx = L * sin(a);
    float dy = L * cos(a);

    // Очистка окна
    window.clear(sf::Color::Black);
    // Отрисовка бинарного дерева на окне
    tree.draw(window, L, winXsize / 2, offsetY, L * sin(a), L * cos(a));
    // Отображение окна
    window.display();

    // Обработка событий окна
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

    std::cout << "Введите enter для завершения программы ... "<<std::flush;
    std::cin.ignore( std::numeric_limits <std::streamsize> ::max(), '\n' );

    return 0;
}
