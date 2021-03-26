#pragma once
#include "User.hpp"
class Student : protected User{
    public:
        std::string group_number;
        bool status;
        void setGroup_number(std::string number);
        void setStatus(bool l_status);
        std::string getGroup_number(){return group_number;};
        bool getStatus(){return status;};
        void print();

        void setStudentName(std::string l_userName) {
            user_name = l_userName;
        }
        std::string getStudentname(){return user_name;};
        void setStudentEmail(std::string email) {
            user_email = email;
        }
        std::string getStudentemail(){return user_email;};
        void setStudentSurname(std::string l_usersurame) {
            user_surname = l_usersurame;
        }
        std::string getStudentSurname(){return user_surname;};
        void setStudentPassword(std::string l_userPassword) {
            user_password = l_userPassword;
        }
        std::string getStudentPassword(){return user_password;};
        void setStudentLogin(std::string l_userlogin) {
            user_login = l_userlogin;
        }
        std::string getStudentLogin(){return user_login;};
};