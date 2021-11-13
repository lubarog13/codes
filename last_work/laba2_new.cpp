#include <iostream>
#include <stack>
#include <vector>
#include <stack>
#include <fstream>
#include <algorithm>
#include <exception>
std::vector<char>line;
std::stack<char>num;
std::string search_brackets(std::string str);
float matematic(std::string str_in);
void arithmetic(std::string str);

int main()
{
    std::string i_line;
    std::string o_line;
    std::cin>>i_line;
    arithmetic(i_line);
    for (char n : line){
        std::cout<<n;
        o_line+=n;
    }
    std::cout<<std::endl;
    try{
    std::cout<<matematic(o_line);} 
    catch(std::exception &err){
        std::cerr<<err.what()<<std::endl;
    }
    return 0;
}
void arithmetic(std::string str){
    for (int i=0; str[i]!='\0';i++){
        int j=i;
        if (str[i]>='0'&& str[i]<='9'){
                line.push_back(str[i]);
        }
        if(str[i]=='+'||str[i]=='-'){
            line.push_back(' ');
            if((str[i]=='-' && str[i-1]=='(')||i==0){
                line.push_back(' ');
                line.push_back('0');
            }
            if (num.empty()||num.top()=='('){
                num.push(str[i]);
            }
            else{
                while((num.empty()==false)&& (num.top()!='(')){
                    line.push_back(' ');
                    line.push_back(num.top());
                    num.pop();
                }
                line.push_back(' ');
                num.push(str[i]);
            }

        }
        if(str[i]=='('){
            line.push_back(' ');
            num.push(str[i]);
        }
        if(str[i]==')'){
            line.push_back(' ');
            while(num.top()!='('){
                line.push_back(' ');
                line.push_back(num.top());
                num.pop();
            }
            num.pop();
        }
        if(str[i]=='*'||str[i]=='/'){
            line.push_back(' ');
            if(num.empty()||num.top()=='-'||num.top()=='+'||num.top()=='('){
                num.push(str[i]);
            }
            else{
                while((num.empty()==false)&& ((num.top()=='*')||(num.top()=='/'))){
                    line.push_back(' ');
                    line.push_back(num.top());
                    num.pop();
                }
                line.push_back(' ');
                num.push(str[i]);
            }
        }
    }
    while (!num.empty()){
        line.push_back(' ');
        line.push_back(num.top());
        num.pop();
    }
}
float matematic(std::string str_in){
    std::stack <float> result;
    for(int i=0; i<str_in.length(); i++){
        if (str_in[i]>='0'&& str_in[i]<='9'){
               std::string n_buf;
               while(str_in[i]!=' '){
                   n_buf+=str_in[i];
                   i++;
               }
                float s=0;
                s = atof(n_buf.c_str());
                result.push(s);
        }
        if(str_in[i]=='+'){
            float n1 = result.top();
            result.pop();
            float n2 = result.top();
            result.pop();
            float r = n2+n1;
            result.push(r);
        }
        if(str_in[i]=='*'){
            float n1 = result.top();
            result.pop();
            float n2 = result.top();
            result.pop();
            float r = n2*n1;
            result.push(r);
        }
        if(str_in[i]=='-'){
            float n1 = result.top();
            result.pop();
            float n2 = result.top();
            result.pop();
            float r = n2-n1;
            result.push(r);
        }
        if(str_in[i]=='/'){
            float n1 = result.top();
            result.pop();
            float n2 = result.top();
            if (n1==0) throw std::invalid_argument("Divide by zero error in fraction!");
            result.pop();
            float r = n2/n1;
            result.push(r);
        }
    }

    return result.top();
}


