#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;

int main()
{
    /*истемный администратор раз в неделю создаёт архив пользовательских файлов. Однако объём диска, 
    куда он помещает архив, может быть меньше, чем суммарный объём архивируемых файлов. Известно, какой 
    объём занимает файл каждого пользователя.

    По заданной информации об объёме файлов пользователей и свободном объёме на архивном диске определите 
    максимальное число пользователей, чьи файлы можно сохранить в архиве, а также максимальный размер имеющегося файла, 
    который может быть сохранён в архиве, при условии, что сохранены файлы максимально возможного числа пользователей.*/
    ifstream in;
    in.open("28139.txt");
    int s, n, sum=0, k=-1, max=0, arr[2962];
    in>>s;
    in>>n;
    for(int i=0; i<n; i++){
        in>>arr[i];
    }
    sort(arr, arr+n);
    while(sum<=s){
        k++;
        sum+=arr[k];
    }
    sum-=arr[k];
    cout<<k<<" ";
    sum-=arr[k-1];
    for(int i=k; i<n; i++){
        if(sum+arr[i]<=s) max=arr[i];
        else break;
    }
    cout<<max;
    in.close();
    return 0;
}