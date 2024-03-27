#pragma once
#include "header.h"
#include "objects.h"
int inputInt(std::string message, int min, int max);

void clear(Field* field);

void createTexts(sf::Text& textTop, sf::Text& textBottom, sf::Font& font, int cellsCountX, int cellsCountY);
