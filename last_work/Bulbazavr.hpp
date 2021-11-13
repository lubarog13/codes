#pragma once
#include "Pokemon.hpp"
class Bulbazavr : public Pokemon{
    private:
        int special_attack;
        int special_deffense;
    public:
        Bulbazavr();
        Bulbazavr(std::string l_name);
        ~Bulbazavr();
        void setSpecial_Attack(int l_sa);
        void setSpecialDeffence(int l_sd);
        int getSpecialAttack(){return special_attack;};
        int getSpecialDeffence() {return special_deffense;};
        void print();
        void get_out_of_json();
};