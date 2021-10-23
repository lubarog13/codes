import 'dart:convert';

import 'package:shared_preferences/shared_preferences.dart';

class Parser {
  int t;
  int s;
  int p;

  Parser({required this.t, required this.s, required this.p});


  String parseTemp(int temp) {
    if(t==0) {
      return temp.toString();
    } else{
      return (temp * 9/5 + 32).toString();
    }
  }

  String parseSpeed(double speed) {
    if(s==0) {
      return speed.toString();
    } else {
      return (speed*3.6).toString();
    }
  }

  String parsePressure(int pressure) {
    if(p==0) {
      return pressure.toString();
    } else {
      return (pressure*1.33).toStringAsFixed(2);
    }
  }

  String parsePressureName() {
    if(p==0) {
      return 'мм.рт.ст';
    } else {
      return 'гПа';
    }
  }

  String parseSpeedName() {
    if(s==0) {
      return 'м/с';
    } else {
      return 'км/ч';
    }
  }

  String parseTempName() {
    if(t==0){
      return '\u00B0C';
    } else {
      return '\u00B0F';
    }
  }

  void initValues() async{
    SharedPreferences preferences = await SharedPreferences.getInstance();
    t = preferences.getInt('temp') ?? 0;
    s = preferences.getInt('speed') ?? 0;
    p = preferences.getInt('pressure') ?? 0;
  }
}