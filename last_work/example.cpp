#include "example.hpp"
#include <iostream>
void Student::print(){
    if (user_name.length()!=0){
        std::cout<<user_name<<std::endl;
    }
    else std::cout<<"No name"<<std::endl;
    if (user_surname.length()!=0){
        std::cout<<user_surname<<std::endl;
    }
    else std::cout<<"No surname"<<std::endl;
    if (user_login.length()!=0){
        std::cout<<user_login<<std::endl;
    }
    else std::cout<<"No login"<<std::endl;
    if (user_email.length()!=0){
        std::cout<<user_email<<std::endl;
    }
    else std::cout<<"No email"<<std::endl;
    if (user_password.length()!=0){
        std::cout<<user_password<<std::endl;
    }
    else std::cout<<"No password"<<std::endl;
    if (group_number.length()!=0){
        std::cout<<group_number<<std::endl;
    }
    else std::cout<<"No group number"<<std::endl;
    if (status){
        std::cout<<"Study"<<std::endl;
    }
    else std::cout<<"Not study"<<std::endl;
}
void Student::setGroup_number(std::string number){
    group_number = number;
}
void Student::setStatus(bool l_status){
    status = l_status;
}