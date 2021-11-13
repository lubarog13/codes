#pragma once
#include "Pokemon.hpp"
class Skvirtl : public Pokemon{
    private:
        int special_attack;
        int special_deffense;
    public:
        Skvirtl();
        Skvirtl(std::string l_name);
        ~Skvirtl();
        void setSpecial_Attack(int l_sa);
        void setSpecialDeffence(int l_sd);
        int getSpecialAttack(){return special_attack;};
        int getSpecialDeffence() {return special_deffense;};
        void print();
        void get_out_of_json();
};