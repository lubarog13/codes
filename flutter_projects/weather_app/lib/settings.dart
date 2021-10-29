import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:toggle_switch/toggle_switch.dart';
import './themedata.dart';
import './main.dart';
class SettingsScreen extends StatefulWidget {
  @override
  SettingsState createState() => SettingsState();

}

class SettingsState extends State {
  int temp = 0;
  int speed = 0;
  int pressure = 0;
  bool isDarkTheme = true;
  late Styles styles;
  @override
  void initState(){
    initValues().then((value) => null);
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    isDarkTheme = MediaQuery.of(context).platformBrightness == Brightness.dark;
    styles = Styles(isDarkTheme);
    styles.initColors();
    return Scaffold(
      backgroundColor: styles.backgroundColor,
      body: Container(
        padding: EdgeInsets.only(top: 35),
        height: MediaQuery.of(context).size.height,
        width: MediaQuery.of(context).size.width,
        child: Column(
        children: <Widget>[ 
          Row(
          children: [
            IconButton(
              onPressed: () => _navigateToPreviousScreen(context),
              icon:  Icon(
                Icons.arrow_back_ios,
                color: styles.textColor,
                size: 20,
              ),
            ),
            Center(
              child: Text(
                'Настройки',
                style: GoogleFonts.manrope(fontSize: 20, fontWeight: FontWeight.bold, color: styles.textColor),
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
                color: styles.tfontColor,
              ),
            ),
          ),
          Container(
            margin: EdgeInsets.only(top: 20),
            width: MediaQuery.of(context).size.width,
            padding: EdgeInsets.only(top: 20, left: 20, right: 20, bottom: 20),
            decoration: BoxDecoration(
              color: styles.backgroundColor,
              borderRadius: const BorderRadius.only(
                          topRight: Radius.circular(30.0),
                          bottomRight: Radius.circular(30.0),
                          topLeft: Radius.circular(30.0),
                          bottomLeft: Radius.circular(30.0)),
              boxShadow: [BoxShadow(
                color: styles.shadowColor,
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
                        color: styles.textColor,
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
                color: styles.shadowColor,
                blurRadius: 5,
                spreadRadius: 1,
                offset: const Offset(2, 5),
              )
              ]),
              child: ToggleSwitch(
                        minWidth: 79.5,
                          initialLabelIndex: temp,
                          cornerRadius: 20.0,
                          activeBgColors: [[styles.toggleColor], [styles.toggleColor]],
                          activeFgColor: Colors.white,
                          inactiveBgColor: styles.inactiveToggleColor,
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
                  color: styles.tfontColor,
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
                        color: styles.textColor
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
                color: styles.shadowColor,
                blurRadius: 5,
                spreadRadius: 1,
                offset: const Offset(2, 5),
              )
              ]),
                        child: ToggleSwitch(
                        minWidth: 79.5,
                          cornerRadius: 20.0,
                          activeBgColors: [[styles.toggleColor], [styles.toggleColor]],
                          activeFgColor: Colors.white,
                          inactiveBgColor: styles.inactiveToggleColor,
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
                  color: styles.tfontColor,
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
                        color: styles.textColor
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
                color: styles.shadowColor,
                blurRadius: 5,
                spreadRadius: 1,
                offset: const Offset(2, 5),
              )
              ]),
                        child: ToggleSwitch(
                        minWidth: 79.5,
                        fontSize: 12,
                          cornerRadius: 30.0,
                          activeBgColors: [[styles.toggleColor], [styles.toggleColor]],
                          activeFgColor: Colors.white,
                          inactiveBgColor: styles.inactiveToggleColor,
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