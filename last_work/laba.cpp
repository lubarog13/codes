#include <iostream>
#include "Katerpy.hpp"
#include "Skvirtl.hpp"
#include "Pokemon.hpp"
#include "Bulbazavr.hpp"
#include <fstream>
void open_json_file(void);
void print_to_json(Pokemon &p, bool not_last);
void print_to_json(Bulbazavr &p, bool not_last);
void print_to_json(Skvirtl &p, bool not_last);
void print_to_json(Katerpy &p, bool not_last);
void close_json_file(void);
int main(){
   Pokemon picachu("Pikachu");
   picachu.setHP(20);
   picachu.setDefense(30);
   picachu.setAttack(100);
   picachu.setSpeed(200);
   open_json_file();
   print_to_json(picachu, true);
    Bulbazavr genius("Genius");
    genius.setHP(45);
    genius.setSpeed(80);
    genius.setSpecial_Attack(150);
    print_to_json(genius, true);
    genius.setHP(1000);
    genius.print();
    genius.get_out_of_json();
    genius.print();
   Pokemon chamander("Chamander");
   chamander.setHP(200);
   chamander.setSpeed(45);
   chamander.setAttack(50);
   chamander.setDefense(500);
    print_to_json(chamander, false);
    close_json_file();
    chamander.get_out_of_json();
    return 0;
}
void open_json_file(void){
    std::ofstream myfile;
    myfile.open ("Pokemon.json");
  if (myfile.is_open())
  {
    myfile << "{\n";
    myfile<<"\"pokemons\" : [ \n";
    myfile.close();
  }
}
void print_to_json(Pokemon &p, bool not_last)
{
    std::ofstream myfile;
    myfile.open ("Pokemon.json", std::ios::app);
  if (myfile.is_open())
  {
    myfile << "{\n";
    myfile << " \"Name\":\""<<p.getName()<<"\"";
    myfile << ",\n \"HP\":"<<p.getHP();
    myfile << ",\n \"Attack\":"<<p.getAttack();
    myfile << ",\n\"Defense\":"<<p.getDefense();
    myfile << ",\n \"Speed\":"<<p.getSpeed();
    myfile << "\n}";
    if(not_last)
    myfile<<",";
    myfile<<"\n";
    myfile.close();
  }
  else std::cout << "Unable to open file";
}
void print_to_json(Bulbazavr &p, bool not_last)
{
    std::ofstream myfile;
    myfile.open ("Pokemon.json", std::ios::app);
  if (myfile.is_open())
  {
    myfile << "{\n";
    myfile << " \"Name\":\""<<p.getName()<<"\"";
    myfile << ",\n \"HP\":"<<p.getHP();
    myfile << ",\n \"Attack\":"<<p.getAttack();
    myfile << ",\n\"Defense\":"<<p.getDefense();
    myfile << ",\n \"Speed\":"<<p.getSpeed();
    myfile<<", \n \"Special Attack\":"<<p.getSpecialAttack();
    myfile<<", \n \"Special Defense\":"<<p.getSpecialDeffence();
    myfile << "\n}";
    if(not_last)
    myfile<<",";
    myfile<<"\n";
    myfile.close();
  }
  else std::cout << "Unable to open file";
}
void print_to_json(Katerpy &p, bool not_last)
{
    std::ofstream myfile;
    myfile.open ("Pokemon.json", std::ios::app);
  if (myfile.is_open())
  {
    myfile << "{\n";
    myfile << " \"Name\":\""<<p.getName()<<"\"";
    myfile << ",\n \"HP\":"<<p.getHP();
    myfile << ",\n \"Attack\":"<<p.getAttack();
    myfile << ",\n\"Defense\":"<<p.getDefense();
    myfile << ",\n \"Speed\":"<<p.getSpeed();
    myfile<<", \n \"Special Attack\":"<<p.getSpecialAttack();
    myfile<<", \n \"Special Defense\":"<<p.getSpecialDeffence();
    myfile << "\n}";
    if(not_last)
    myfile<<",";
    myfile<<"\n";
    myfile.close();
  }
  else std::cout << "Unable to open file";
}
void print_to_json(Skvirtl &p, bool not_last)
{
    std::ofstream myfile;
    myfile.open ("Pokemon.json", std::ios::app);
  if (myfile.is_open())
  {
    myfile << "{\n";
    myfile << " \"Name\":\""<<p.getName()<<"\"";
    myfile << ",\n \"HP\":"<<p.getHP();
    myfile << ",\n \"Attack\":"<<p.getAttack();
    myfile << ",\n\"Defense\":"<<p.getDefense();
    myfile << ",\n \"Speed\":"<<p.getSpeed();
    myfile<<", \n \"Special Attack\":"<<p.getSpecialAttack();
    myfile<<", \n \"Special Defense\":"<<p.getSpecialDeffence();
    myfile << "\n}";
    if(not_last)
    myfile<<",";
    myfile<<"\n";
    myfile.close();
  }
  else std::cout << "Unable to open file";
}
void close_json_file(void){
    std::ofstream myfile;
    myfile.open ("Pokemon.json", std::ios::app);
  if (myfile.is_open())
  {
    myfile << "]\n";
    myfile << "\n}\n";
    myfile.close();
  }
  else std::cout << "Unable to open file";
}