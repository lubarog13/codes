#include <iostream>
#include <stack>
#include <cctype>
#include <map>
#include <algorithm>
#include <string>
 

std::string RPN(std::string str)
{
    std::string srpn;
 
    for (int i = 0; i < str.size(); ++i) // учёт отрицательных чисел
    {
        if ((str[i] == '+' || str[i] == '-') && (0 == i || (!isalnum(str[i - 1])  && str[i - 1] != ')')))
        {
            auto it = std::find_if(str.begin() + i + 1, str.end(), [](char const c) {return !isalnum(c);});
            str.insert(it, ')');
            str.insert(i, "(0");
            int nnn = 0;
        }
    }
 
    std::cout << str << std::endl;
 
    std::map<char, size_t> map; // карта весов символов
    map.insert(std::make_pair('*', 3));
    map.insert(std::make_pair('/', 3));
    map.insert(std::make_pair('+', 2));
    map.insert(std::make_pair('-', 2));
    map.insert(std::make_pair('(', 1));
    std::stack<char> stack;
    for (auto c : str) // формировка результирующей строки в ОПЗ
    {
        if (!isalnum(c) && ('.' != c))
        {
            srpn += ' ';
            if (')' == c)
            {
                while (stack.top() != '(')
                {
                    srpn += stack.top();
                    stack.pop();
                    srpn += ' ';
                }
                stack.pop();
            }
            else if ('(' == c)
            {
                stack.push(c);
            }
            else if (stack.empty() || (map[stack.top()] < map[c]))
            {
                stack.push(c);
            }
            else
            {
                do
                {
                    srpn += stack.top();
                    srpn += ' ';
                    stack.pop();
                } while (!(stack.empty() || (map[stack.top()] < map[c])));
                stack.push(c);
            }
        }
        else
        {
            srpn += c;
        }
    }
    while (!stack.empty())// остаток из стека добавляется в результ. строку
    {
        srpn += stack.top();
        srpn += ' ';
        stack.pop();
    }
    std::cout << srpn << std::endl; // результирующая строка в ОПЗ
 
    return srpn;
}
 
int main()
{
    std::string str;
    std::cin>>str;
    std::string srpn = RPN(str);
    std::cout << "Answer: " << srpn << std::endl; // результат
    return 0;
}