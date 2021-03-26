#include "Pokemon.hpp"
#include <iostream>
#include <cstring>
#include <fstream>

Pokemon ::Pokemon(){
    setHP(0);
    setDefense(0);
    setAttack(0);
    setSpeed(0);
}
Pokemon ::Pokemon(std::string l_name){
    name = l_name;
    setHP(0);
    setDefense(0);
    setAttack(0);
    setSpeed(0);
}
void Pokemon::setHP(int l_hp){
    hp = l_hp;
}
 void Pokemon::setAttack(int l_attack){
     attack = l_attack;
 }
void Pokemon::setDefense(int l_defense){
    defense = l_defense;
}
void Pokemon::setSpeed(int l_speed){
    speed = l_speed;
}
void Pokemon::setName(std::string l_name){
    name = l_name;
}
void Pokemon::get_out_of_json(){
    std::string line;
    std::ifstream in("Pokemon.json"); // окрываем файл для чтения
    if (in.is_open())
    {
        while (getline(in, line))
        {
            if (line[2]=='N'){

            std::string sname = "";
            for(int i=9; line[i]!='\"';i++){
                sname +=line[i];
            }
                if (sname==name){
                   getline(in, line);
                   hp = search_int(line);
                   getline(in, line);
                   attack = search_int(line);
                   getline(in, line);
                   defense = search_int(line);
                   getline(in, line);
                   speed = search_int(line);
                }
            }
        }
    }
    in.close();     // закрываем файл
};
int Pokemon::search_int(std::string l){
    std::string sn;
    for(int i=0; l[i]!='\0';i++){
        if ((l[i]>='0')&&(l[i]<='9')){
            sn+=l[i];
        }
    }
    int s;
    s = atoi(sn.c_str());
    return s;
};
Pokemon::~Pokemon(){};
void Pokemon::print(){
    if (name.length()!=0){
        std::cout<<"\n Pokemon name: "<<name;
    }
    else
    {
        std::cout<<"No name";
    }
    if (hp!=0){
        std::cout<<"\nPokemon HP: "<<hp;
    }
    else std::cout<<"\n No hp";
    if (attack!=0){
        std::cout<<"\nPokemon Attack: "<<attack;
    }
    else std::cout<<"\n No Attack";
    if (defense!=0){
        std::cout<<"\nPokemon Defense: "<<defense;
    }
    else std::cout<<"\n No Defense";
    if (speed!=0){
        std::cout<<"\nPokemon Speed: "<<speed;
    }
    else std::cout<<"\n No speed";
}