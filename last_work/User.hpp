#pragma once
#include <string>
class User{
    protected:
    std::string user_name;
    std::string user_surname;
    std::string user_login;
    std::string user_password;
    std::string user_email;
        public:
        User();
        User(std::string name);
        User(std::string name, std::string surname);
        User(std::string name, std::string surname,  std::string login);
        User(std::string name, std::string surname,  std::string login, std::string email);
        User(std::string name, std::string surname,  std::string login, std::string email, std::string password);
        ~User();
        void setUserName(std::string name);
        void setUserSurname(std::string surname);
        void setUserLogin(std::string login);
        void setUserEmail(std::string email);
        void setUserPassword(std::string password);
        std::string getUserName(){return user_name;};
        std::string getUserSurname(){return user_surname;};
        std::string getUserLogin(){return user_login;};
        std::string getUserEmail(){return user_email;};
        std::string getUserPassword(){return user_password;};
        void print();


};