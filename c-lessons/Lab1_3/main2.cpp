#include "functions2.h"
int main ()
{
    string **pt;
    int count=0;
    int i;
    int mass_length=0;
    setlocale(LC_ALL, "Russian");
    string filename;
    filename = checkOpenInputFile("Введите название файла для чтения:");
    count = CheckStr(filename, mass_length);
    pt = new string* [count];
    for (i=0;i<count;i++)
    {
        pt[i]=new string;
    }
    readFile(filename, pt, count);
    outContent(pt, count);
    cycleDelete(pt, mass_length);
    outContent(pt, count);
    SaveFile(pt, mass_length);
    for (i=0; i<count;i++)
    {
        delete pt[i]; //c [] выдает ошибку сегментирования
        //delete[] pt[1];
    }
    delete[] pt;
    return 0;
}
