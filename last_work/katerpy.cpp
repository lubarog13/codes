#include "Katerpy.hpp"
#include <iostream>
#include <fstream>

Katerpy ::Katerpy(){
    setHP(0);
    setDefense(0);
    setAttack(0);
    setSpeed(0);
    setSpecialDeffence(0);
    setSpecial_Attack(0);
}
Katerpy ::Katerpy(std::string l_name){
    setName(l_name);
    setHP(0);
    setDefense(0);
    setAttack(0);
    setSpeed(0);
    setSpecialDeffence(0);
    setSpecial_Attack(0);
}

Katerpy::~Katerpy(){}
void Katerpy::setSpecial_Attack(int l_sa){
    special_attack = l_sa;
}
void Katerpy::setSpecialDeffence(int l_sd){
    special_deffense = l_sd;
}
void Katerpy::print(){
    if (getName().length()!=0){
        std::cout<<"\nPokemon katerpy name: "<<getName();
    }
    else
    {
        std::cout<<"No name";
    }
    if (getHP()!=0){
        std::cout<<"\nPokemon skvirtl HP: "<<getHP();
    }
    else std::cout<<"\n No hp";
    if (getAttack()!=0){
        std::cout<<"\nPokemon katerpy Attack: "<<getAttack();
    }
    else std::cout<<"\n No Attack";
    if (getDefense()!=0){
        std::cout<<"\nPokemon katerpy Defense: "<<getDefense();
    }
    else std::cout<<"\n No Defense";
    if (getSpeed()!=0){
        std::cout<<"\nPokemon katerpy Speed: "<<getSpeed();
    }
    else std::cout<<"\n No speed";
    if (special_attack!=0){
        std::cout<<"\nPokemon katerpy Special Attack: "<<special_attack;
    }
    else std::cout<<"\n No Special Attack";
    if (special_deffense!=0){
        std::cout<<"\nPokemon katerpy Special Defense: "<<special_deffense;
    }
    else std::cout<<"\n No special Defence";
}
void Katerpy::get_out_of_json(){
    std::string line;
    std::ifstream in("Pokemon.json"); // окрываем файл для чтения
    if (in.is_open())
    {
        std::string lname = getName();
        while (getline(in, line))
        {
            if (line[2]=='N'){
            
            std::string sname = "";
            for(int i=9; line[i]!='\"';i++){
                sname +=line[i];
            }
                if (sname==lname){
                   getline(in, line);
                   setHP(search_int(line));
                   getline(in, line);
                   setAttack(search_int(line));
                   getline(in, line);
                   setDefense(search_int(line));
                   getline(in, line);
                   setSpeed(search_int(line));
                   getline(in, line);
                   special_attack = search_int(line);
                   getline(in, line);
                   special_deffense = search_int(line);
                }
            }
        }
    }
    in.close();     // закрываем файл
}