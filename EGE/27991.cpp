#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;

int main()
{
    /*Дана последовательность N целых положительных чисел. Рассматриваются все пары элементов 
    последовательности, разность которых чётна, и в этих парах, по крайней мере, одно из чисел пары делится на 17.
     Порядок элементов в паре неважен. Среди всех таких пар нужно найти и вывести пару с максимальной суммой элементов.
      Если одинаковую максимальную сумму имеет несколько пар, можно вывести любую из них. Если подходящих пар 
      в последовательности нет, нужно вывести два нуля.*/
    ifstream in;
    in.open("27991_B.txt");
    int arr[10001], n, max=0, max17=0, maxsum, k;
    in>>n;
    for(int i=0; i<n; i++){
        in>>arr[i];
    }
    sort(arr, arr+n);
    max = arr[n-1];
    if(max%17==0){
        max17=max;
        for(int j=n-2; j>=0; j--){
            if(abs(arr[j]-max17)%2==0){
                max = arr[j];
                break;
            }
        }
    }
    else{
        for(int j=n-2; j>=0; j--){
            if(arr[j]%17==0){
                max17 = arr[j];
                k=j;
                break;
            }
        }
        for(int j=n-1; j>=0; j--){
            if(abs(arr[j]-max17)%2==0 && j!=k){
                 max = arr[j];
                break;
            }
        }
    }
    cout<<max<<" "<<max17;
    in.close();
    return 0;
}