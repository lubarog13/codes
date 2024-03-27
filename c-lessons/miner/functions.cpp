#include "functions.h"

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


void clear(Field* field) {
     for(std::vector<Cell>::iterator it = field->getCells()->begin(); it != field->getCells()->end(); ++it) {
        if(it->checkHasMine()) {
            it->setMine();
        }
    }
}

void createTexts(sf::Text& textTop, sf::Text& textBottom, sf::Font& font, int cellsCountX, int cellsCountY) {
    textTop.setFont(font);
    textBottom.setFont(font);
    textTop.setString(L"Разместите мины и нажмите enter");

    textTop.setCharacterSize(10);
    textTop.setFillColor(sf::Color::Black);

    textTop.setPosition(border[3], 5);
    textBottom.setPosition(border[3], border[0] + cellSize * cellsCountX + border[3]);
    textBottom.setCharacterSize(10);
    textBottom.setFillColor(sf::Color::Black);
    textBottom.setString(L"Нажмите пробел, \nчтобы начать заново");
}
