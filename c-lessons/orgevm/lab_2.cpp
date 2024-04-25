#include <windows.h>
#include <iostream>

using namespace std;

void SetCoord(int x, int y)
{
    COORD point;
    point.X = x;
    point.Y = y;
    SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), point);
}

void SetColorOutput(int text, int background)
{
    HANDLE hstdout = GetStdHandle(STD_OUTPUT_HANDLE);
    SetConsoleTextAttribute(hstdout, (WORD)((background  << 4) | text));
}


void GetScroll(unsigned int sx, unsigned int sy, unsigned int sxx, unsigned int syy, int dx, int dy)
{
    HANDLE hstdout = GetStdHandle(STD_OUTPUT_HANDLE);
    CONSOLE_SCREEN_BUFFER_INFO csbiInfo;
    GetConsoleScreenBufferInfo(hstdout, &csbiInfo);
    SMALL_RECT srctScrollRect;
    if (sx != 0)
        srctScrollRect.Left = sx;
    else
        srctScrollRect.Left = 0;
    if (sxx != 0)
        srctScrollRect.Right = sxx;
    else
        srctScrollRect.Right = csbiInfo.dwSize.X;
    if (sy != 0)
        srctScrollRect.Top = sy;
    else
        srctScrollRect.Top = 0;
    if (syy != 0)
        srctScrollRect.Bottom = syy;
    else
        srctScrollRect.Bottom = csbiInfo.dwSize.Y;
    COORD coordDest;
    coordDest.X = 0 + dx;
    coordDest.Y = 0 + dy;
    SMALL_RECT srctClipRect;
    srctClipRect = srctScrollRect;
    CHAR_INFO chiFill;
    chiFill.Attributes = 0 | 0;
    chiFill.Char.UnicodeChar = '*';
    ScrollConsoleScreenBuffer(hstdout, &srctScrollRect, coordDest, &chiFill);
}


enum ConsoleColor
{
    Black = 0,
    Blue = 1,
    Green = 2,
    Cyan = 3,
    Red = 4,
    Magenta = 5,
    Brown = 6,
    LightGray = 7,
    DarkGray = 8,
    LightBlue = 9,
    LightGreen = 10,
    LightCyan = 11,
    LightRed = 12,
    LightMagenta = 13,
    Yellow = 14,
    White = 15
};
void GetPrintColor(int text, int background)
{
    switch (background)
    {
        case Black:
            cout << "черный ";
            break;
        case Blue:
            cout << "синий ";
            break;
        case Green:
            cout << "зеленый ";
            break;
        case Cyan:
            cout << "сине-зеленый ";
            break;
        case Red:
            cout << "красный ";
            break;
        case Magenta:
            cout << "красно-синий ";
            break;
        case Brown:
            cout << "коричневый ";
            break;
        case LightGray:
            cout << "светло-серый ";
            break;
        case DarkGray:
            cout << "темно-серый ";
            break;
        case LightBlue:
            cout << "ярко-синий ";
            break;
        case LightGreen:
            cout << "ярко-зеленый ";
            break;
        case LightCyan:
            cout << "яркий сине-зеленый ";
            break;
        case LightRed:
            cout << "ярко-красный ";
            break;
        case LightMagenta:
            cout << "яркий сине-зеленый ";
            break;
        case Yellow:
            cout << "желтый ";
            break;
        case White:
            cout << "белый ";
            break;
    }
    switch (text)
    {
        case Black:
            cout << "0 ";
            break;
        case Blue:
            cout << "1 ";
            break;
        case Green:
            cout << "2 ";
            break;
        case Cyan:
            cout << "3 ";
            break;
        case Red:
            cout << "4 ";
            break;
        case Magenta:
            cout << "5 ";
            break;
        case Brown:
            cout << "6 ";
            break;
        case LightGray:
            cout << "7 ";
            break;
        case DarkGray:
            cout << "8 ";
            break;
        case LightBlue:
            cout << "9 ";
            break;
        case LightGreen:
            cout << "10 ";
            break;
        case LightCyan:
            cout << "11 ";
            break;
        case LightRed:
            cout << "12 ";
            break;
        case LightMagenta:
            cout << "13 ";
            break;
        case Yellow:
            cout << "14 ";
            break;
        case White:
            cout << "15 ";
            break;
    }
}

int main()
{
    setlocale(LC_ALL, "Russian");
    for (int background = 0; background <= 15; background++) {
        for (int text = 0; text <= 15; text++) {
            for (int t =0; t <3; t++) {
                SetCoord(10, 20);
                SetColorOutput(text, background);
                GetPrintColor(text, background);
                GetScroll(10, 10, 70, 20, 10, 9);
            }
            Sleep(1300);
        }
        SetColorOutput(15, 0);
    }
    SetCoord(0, 0);
}