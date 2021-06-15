#include <iostream>
#include <fstream>
#include <string>
using namespace std;
/*Текстовый файл содержит строки различной длины. Общий объём файла не превышает 1 Мбайт. 
\Строки содержат только заглавные буквы латинского алфавита (ABC…Z).

Необходимо найти строку, содержащую наименьшее количество букв N (если таких строк несколько, надо взять ту,
 которая находится в файле раньше), и определить, какая буква встречается в этой строке чаще всего. Если таких букв несколько,
  надо взять ту, которая позже стоит в алфавите.*/
int count(string s, char c){
    int cnt=0;
    for(int i=0; i<s.length(); i++){
        if(s[i]==c) cnt++;
    }
    return cnt;
}

int main()
{
    ifstream in;
    string s, minstr;
    int n, min=10000, max=0;
    char mxchar;
    in.open("35913.txt");
    while (getline(in, s))
    {
        n = count(s, 'N');
        if(n<min){ min=n;
        minstr = s;
        }
    }
    for(char c= 'A'; c<'Z'; c++){
        n = count(minstr, c);
        if(n>=max){
            max = n;
            mxchar = c;
        }
    }
    cout<<mxchar;
    return 0;
}