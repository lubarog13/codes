#include "User.hpp"
#include<iostream>
User::User(){}
User::User(std::string name){
    user_name = name;
}
User:: User(std::string name, std::string surname){
    user_name = name;
    user_surname = surname;
}
User::User(std::string name, std::string surname,  std::string login){
    user_name = name;
    user_surname = surname;
    user_login = login;
}
User::User(std::string name, std::string surname,  std::string login, std::string email){
    user_name = name;
    user_surname = surname;
    user_login = login;
    user_email = email;
}
User::User(std::string name, std::string surname,  std::string login, std::string email, std::string password){
    user_name = name;
    user_surname = surname;
    user_login = login;
    user_email = email;
    user_password = password;
}
void User::print(){
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
}
void User::setUserName(std::string name){
    user_name = name;
}
        void User::setUserSurname(std::string surname){
            user_surname = surname;
        }
        void User::setUserLogin(std::string login){
            user_login = login;
        }
        void User::setUserEmail(std::string email){
            user_email = email;
        }
        void User::setUserPassword(std::string password){
            user_password = password;
        }
User::~User(){}