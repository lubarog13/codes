#include "functions.h"

int main ()
{
    int *mass1, *mass2, *result_mass;
    int arr_len1=-1, arr_len2=-1, result_arr_len=-1;

    cout << "Объединение двух массивов\n" << endl;

    cout << "Чтение первого массива из файла" << endl;
    readFile(mass1, arr_len1);
    if(arr_len1 == -1) return 0;
    outContent(mass1, arr_len1);

    cout << endl <<"Чтение второго массива из файла" << endl;
    readFile(mass2, arr_len2);
    if(arr_len2 == -1) {
        delete [] mass1;
        return 0;
    }
    outContent(mass2, arr_len2);

    changeArr(result_mass, result_arr_len, mass1, mass2, arr_len1, arr_len2);
    if(result_arr_len == -1) {
        delete [] mass1;
        delete [] mass2;
        return 0;
    }
    cout<<endl<<"Итоговый массив: "<<endl;
    outContent(result_mass, result_arr_len);
    cout<<endl;
    
    saveFile(result_mass, result_arr_len);
    delete [] mass1;
    delete [] mass2;
    delete [] result_mass;
    return 0;
}
