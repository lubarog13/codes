#pragma once
#include <string>
class Pokemon{
    private:
    std::string name;
    int hp;
    int attack;
    int defense;
    int speed;

        public:
        Pokemon();
        Pokemon(std::string l_name);
        ~Pokemon();
        void setName (std::string l_name);
        void setHP(int l_hp);
        void setAttack(int l_attack);
        void setDefense(int l_defense);
        void setSpeed(int l_speed);
        std::string getName (){return name;};
        int getHP(){return hp;};
        int getAttack(){return attack;};
        int getDefense(){return defense;};
        int getSpeed(){return speed;};
        void get_out_of_json();
        void print();
        int search_int (std::string l);
};