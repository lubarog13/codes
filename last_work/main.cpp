#include <iostream>
#include "User.hpp"
#include "example.hpp"
int main(){
   Student student;
   student.setGroup_number("Y2233");
   student.setStatus(true);
   student.setStudentName("Ivan");
   student.setStudentLogin("example@mail.com");
   student.print();

    return 0;
}