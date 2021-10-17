import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:toggle_switch/toggle_switch.dart';
import './main.dart';
class SettingsScreen extends StatefulWidget {
  @override
  SettingsState createState() => SettingsState();

}

class SettingsState extends State {
  int temp = 0;
  int speed = 0;
  int pressure = 0;
  @override
  void initState(){
    initValues().then((value) => null);
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        padding: EdgeInsets.only(top: 35),
        height: MediaQuery.of(context).size.height,
        width: MediaQuery.of(context).size.width,
        color: Color.fromRGBO(226, 235, 255, 1),
        child: Column(
        children: <Widget>[ 
          Row(
          children: [
            IconButton(
              onPressed: () => _navigateToPreviousScreen(context),
              icon: const Icon(
                Icons.arrow_back_ios,
                size: 20,
              ),
            ),
            Center(
              child: Text(
                'Настройки',
                style: GoogleFonts.manrope(fontSize: 20, fontWeight: FontWeight.bold),
              ),
            ),
          ],
        ),
          Container(
            height: 30,
            alignment: Alignment.bottomLeft,
            margin: EdgeInsets.only(top: 10, left: 30),
            child: Text(
              'Единицы измерения',
              style: GoogleFonts.manrope(
                fontSize: 10,
                color: Colors.grey
              ),
            ),
          ),
          Container(
            margin: EdgeInsets.only(top: 20),
            width: MediaQuery.of(context).size.width,
            padding: EdgeInsets.only(top: 20, left: 20, right: 20, bottom: 20),
            decoration: BoxDecoration(
              color: Color.fromRGBO(226, 235, 255, 1),
              borderRadius: const BorderRadius.only(
                          topRight: Radius.circular(30.0),
                          bottomRight: Radius.circular(30.0),
                          topLeft: Radius.circular(30.0),
                          bottomLeft: Radius.circular(30.0)),
              boxShadow: [BoxShadow(
                color: Colors.grey.withOpacity(0.5),
                blurRadius: 4,
                spreadRadius: 5,
                offset: const Offset(4, 8),
              )
              ],
            ),
            child: Column(
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.start,
                 crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Expanded(
                      flex: 4,
                      child: Text(
                      'Температура',
                      style: GoogleFonts.manrope(
                        fontSize: 14,
                        fontWeight: FontWeight.w500,
                        color: Colors.black,
                      ),
                    ),
                    ),
                    Container(
                        decoration: BoxDecoration(
              borderRadius: const BorderRadius.only(
                          topRight: Radius.circular(20.0),
                          bottomRight: Radius.circular(20.0),
                          topLeft: Radius.circular(20.0),
                          bottomLeft: Radius.circular(20.0)),
              boxShadow: [BoxShadow(
                color: Colors.grey.withOpacity(0.5),
                blurRadius: 5,
                spreadRadius: 1,
                offset: const Offset(2, 5),
              )
              ]),
              child: ToggleSwitch(
                        minWidth: 79.5,
                          initialLabelIndex: temp,
                          cornerRadius: 20.0,
                          activeBgColors: [[Color.fromRGBO(75, 95, 136, 1)], [Color.fromRGBO(75, 95, 136, 1)]],
                          activeFgColor: Colors.white,
                          inactiveBgColor: Color.fromRGBO(226, 235, 255, 1),
                          inactiveFgColor: Colors.black,
                          totalSwitches: 2,
                          labels: ['\u00B0C', '\u00B0F'],
                          radiusStyle: true,
                          onToggle: (index) {
                            temp=index;
                            setTemp();
                          },
                        ),
                      )
                  ]
                ),
                Divider(
                  color: Color(0x26000000),
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.start,
                 crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Expanded(
                      flex: 4,
                      child: Text(
                      'Сила ветра',
                      style: GoogleFonts.manrope(
                        fontSize: 14,
                        fontWeight: FontWeight.w500,
                        color: Colors.black,
                      ),
                    ),
                    ),
                     Container(
                        decoration: BoxDecoration(
              borderRadius: const BorderRadius.only(
                          topRight: Radius.circular(20.0),
                          bottomRight: Radius.circular(20.0),
                          topLeft: Radius.circular(20.0),
                          bottomLeft: Radius.circular(20.0)),
              boxShadow: [BoxShadow(
                color: Colors.grey.withOpacity(0.5),
                blurRadius: 5,
                spreadRadius: 1,
                offset: const Offset(2, 5),
              )
              ]),
                        child: ToggleSwitch(
                        minWidth: 79.5,
                          cornerRadius: 20.0,
                          activeBgColors: [[Color.fromRGBO(75, 95, 136, 1)], [Color.fromRGBO(75, 95, 136, 1)]],
                          activeFgColor: Colors.white,
                          inactiveBgColor: Color.fromRGBO(226, 235, 255, 1),
                          inactiveFgColor: Colors.black,
                          initialLabelIndex: speed,
                          totalSwitches: 2,
                          labels: ['м/с', 'км/ч'],
                          radiusStyle: true,
                          onToggle: (index) {
                            speed=index;
                            setSpeed();
                          },
                        ),
                    )
                  ]
                ),
                Divider(
                  color: Color(0x26000000),
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.start,
                 crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Expanded(
                      flex: 4,
                      child: Text(
                      'Давление',
                      style: GoogleFonts.manrope(
                        fontSize: 14,
                        fontWeight: FontWeight.w500,
                        color: Colors.black,
                      ),
                    ),
                    ),
                      Container(
                        decoration: BoxDecoration(
              borderRadius: const BorderRadius.only(
                          topRight: Radius.circular(20.0),
                          bottomRight: Radius.circular(20.0),
                          topLeft: Radius.circular(20.0),
                          bottomLeft: Radius.circular(20.0)),
              boxShadow: [BoxShadow(
                color: Colors.grey.withOpacity(0.5),
                blurRadius: 5,
                spreadRadius: 1,
                offset: const Offset(2, 5),
              )
              ]),
                        child: ToggleSwitch(
                        minWidth: 79.5,
                        fontSize: 12,
                          cornerRadius: 30.0,
                          activeBgColors: [[Color.fromRGBO(75, 95, 136, 1)], [Color.fromRGBO(75, 95, 136, 1)]],
                          activeFgColor: Colors.white,
                          inactiveBgColor: Color.fromRGBO(226, 235, 255, 1),
                          inactiveFgColor: Colors.black,
                          initialLabelIndex: pressure,
                          totalSwitches: 2,
                          labels: ['мм.рт.ст.', 'гПа'],
                          radiusStyle: true,
                          onToggle: (index) {
                            pressure = index;
                            setPressure();
                          },
                        ),
                    )
                  ]
                ),
              ],
            )
          )
        ]
        ),
      ),
    );
  }
  void _navigateToPreviousScreen(BuildContext context) {
    Navigator.of(context).push(MaterialPageRoute(builder: (context) => MyHomePage(title: "Погода")));
  }

  void setTemp() async{
    SharedPreferences preferences = await SharedPreferences.getInstance();
      preferences.setInt('temp', temp);
  }

  void setSpeed() async{
    SharedPreferences preferences = await SharedPreferences.getInstance();
      preferences.setInt('speed', speed);
  }

  void setPressure() async{
    SharedPreferences preferences = await SharedPreferences.getInstance();
      preferences.setInt('pressure', pressure);
  }

  Future<void> initValues() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    setState(() {
      temp = preferences.getInt('temp')?? 0;
    pressure = preferences.getInt('pressure')?? 0;
    speed = preferences.getInt('speed')?? 0;
    });
  }
}