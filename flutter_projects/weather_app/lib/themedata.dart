import 'dart:ui';

import 'package:flutter/material.dart';




class Styles {
    bool isDarkTheme = false;
    Color backgroundColor = Colors.red;
   Color textColor =  Colors.black;
   Color cardColor1 =  Color.fromRGBO(224, 233, 253, 1);
   Color menuButtonColor = Colors.blueAccent; 
   Color buttonTextColor = Colors.blueAccent;
   Color iconColor1 = Color(0xb1b1b1);
   Color menuButtonColor1 = Colors.blueAccent;
   Color gradientColor1 = Color.fromRGBO(205, 218, 245, 1);
   Color gradientColor2 = Color.fromRGBO(156, 188, 255, 1);
   Color subTextColor = Colors.grey;
   Color tfontColor = Color.fromRGBO(130, 130, 130, 1);
   Color toggleColor = Color.fromRGBO(75, 95, 136, 1);
   Color inactiveToggleColor = Color.fromRGBO(226, 235, 255, 1);
   Color closebuttonColor = Colors.blueGrey;
   Color ffontColor = Colors.grey;
   Color shadowColor = Colors.grey;
  Styles(bool isDarkTheme){
    this.isDarkTheme = isDarkTheme;
  }
    void initColors(){
      iconColor1 = isDarkTheme? Color.fromRGBO(177, 177, 177, 1):  Color.fromRGBO(90, 90, 90, 1);
      backgroundColor = isDarkTheme? Color.fromRGBO(12, 22, 43, 1) :  Color.fromRGBO(226, 235, 255, 1);
    textColor =  isDarkTheme? Colors.white : Colors.black;
    menuButtonColor1 = isDarkTheme? Color.fromRGBO(5, 19, 64, 1) : Colors.blueAccent;
    cardColor1 = isDarkTheme? Color.fromRGBO(7, 20, 39, 0.9) :  Color.fromRGBO(224, 233, 253, 1);
    menuButtonColor = isDarkTheme? Colors.white :  Colors.blueAccent; 
    gradientColor1 = isDarkTheme? Color.fromRGBO(34, 59, 112, 1):  Color.fromRGBO(205, 218, 245, 1);
    gradientColor2 = isDarkTheme? Color.fromRGBO(15, 31, 64, 1): Color.fromRGBO(156, 188, 255, 1);
    subTextColor = isDarkTheme? Color.fromRGBO(210, 210, 210, 1):  Color.fromRGBO(90, 90, 90, 1);
    buttonTextColor = isDarkTheme? Colors.white:  Colors.blueAccent;
    tfontColor = isDarkTheme? Color.fromRGBO(170, 170,170, 1) : Color.fromRGBO(130, 130, 130, 1);
    toggleColor = isDarkTheme? Color.fromRGBO(14, 24, 44, 1) : Color.fromRGBO(75, 95, 136, 1);
    inactiveToggleColor = isDarkTheme? Colors.white : Color.fromRGBO(226, 235, 255, 1);
    closebuttonColor = isDarkTheme? Color.fromRGBO(21, 42, 83, 1) : Color.fromRGBO(200, 218, 255, 1);
    ffontColor = isDarkTheme ? Color.fromRGBO(227, 227, 227, 1) : Color.fromRGBO(74, 74, 74, 1);
    shadowColor = isDarkTheme? Colors.black.withOpacity(0.5) : Colors.grey.withOpacity(0.5);
    }


}