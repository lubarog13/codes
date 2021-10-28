import 'dart:ui';

import 'package:flutter/material.dart';




class Styles {
    bool isDarkTheme = false;
    Color backgroundColor = Color.fromRGBO(226, 235, 255, 1);
   Color textColor =  Colors.black;
   Color cardColor1 =  Color.fromRGBO(224, 233, 253, 1);
   Color menuButtonColor = Colors.blueAccent; 
   Color buttonTextColor = Colors.blueAccent;
   Color iconColor1 = Color(0xb1b1b1);
   Color menuButtonColor1 = Colors.blueAccent;
   Color gradientColor1 = Color.fromRGBO(205, 218, 245, 100);
   Color gradientColor2 = Color.fromRGBO(156, 188, 255, 100);
   Color subTextColor = Colors.grey;
  Styles(bool isDarkTheme){
    this.isDarkTheme = isDarkTheme;
  }
    void initColors(){
      iconColor1 = isDarkTheme? Color.fromRGBO(177, 177, 177, 100):  Color.fromRGBO(90, 90, 90, 100);
      backgroundColor = isDarkTheme? Color.fromRGBO(7, 20, 39, 0.9) :  Color.fromRGBO(226, 235, 255, 1);
    textColor =  isDarkTheme? Colors.white : Colors.black;
    menuButtonColor1 = isDarkTheme? Color.fromRGBO(5, 19, 64, 100) : Colors.blueAccent;
    cardColor1 = isDarkTheme? Color.fromRGBO(7, 20, 39, 0.9) :  Color.fromRGBO(224, 233, 253, 1);
    menuButtonColor = isDarkTheme? Colors.white :  Colors.blueAccent; 
    gradientColor1 = isDarkTheme? Color.fromRGBO(34, 59, 112, 100):  Color.fromRGBO(205, 218, 245, 100);
    gradientColor2 = isDarkTheme? Color.fromRGBO(15, 31, 64, 100): Color.fromRGBO(156, 188, 255, 100);
    subTextColor = isDarkTheme? Color.fromRGBO(210, 210, 210, 100):  Color.fromRGBO(90, 90, 90, 100);
    buttonTextColor = isDarkTheme? Colors.white:  Colors.blueAccent;
    }


}