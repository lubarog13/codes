import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:weather_app/themedata.dart';
import './main.dart';
class AboutScreen extends StatefulWidget {
  @override
  AboutState createState() => AboutState();
}
class AboutState extends State {
  late bool isDarkTheme ;
  late Styles styles;
  @override
  Widget build(BuildContext context) {
    isDarkTheme = MediaQuery.of(context).platformBrightness == Brightness.dark;
    styles = Styles(isDarkTheme);
    styles.initColors();
    return Scaffold(
      backgroundColor: styles.backgroundColor,
        body: Column(
          children: [
            Container(
              padding: EdgeInsets.only(top: 35),
              height: MediaQuery.of(context).size.height * 1/2 ,
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
                            'О разработчике',
                            style: GoogleFonts.manrope(fontSize: 20, fontWeight: FontWeight.bold, color: styles.textColor),
                          ),
                        ),
                      ],
                    ),
                    Container(
                      height: 50,
                      width: 220,
                      alignment: Alignment.center,
                      margin: EdgeInsets.only(top: MediaQuery.of(context).size.height / 4 - 30),
                      decoration: BoxDecoration(
                        color: styles.backgroundColor,
                        borderRadius: const BorderRadius.only(
                            topRight: Radius.circular(10.0),
                            bottomRight: Radius.circular(10.0),
                            topLeft: Radius.circular(10.0),
                            bottomLeft: Radius.circular(10.0)),
                        boxShadow: [BoxShadow(
                          color: Colors.grey.withOpacity(0.3),
                          blurRadius: 4,
                          spreadRadius: -5,
                          offset: const Offset(4, -8),
                        )
                        ],
                      ),
                      child: Text(
                        'Weather app',
                        style: GoogleFonts.manrope(
                            fontSize: 25,
                            fontWeight: FontWeight.bold,
                            color: styles.textColor
                        ),
                      ),
                    ),
                  ]
              ),
            ),
            Stack(

              children: [
                Container(
                  height: MediaQuery.of(context).size.height * 0.5 ,
                  decoration:  BoxDecoration(
                      color: styles.backgroundColor,
                  ),
                ),
                Container(
                  height: MediaQuery.of(context).size.height * 0.5 ,
                  width: MediaQuery.of(context).size.width,
                  decoration:  BoxDecoration(
                    color: styles.backgroundColor,
                    borderRadius: BorderRadius.only(
                        topRight: Radius.circular(20.0),
                        bottomRight: Radius.circular(0.0),
                        topLeft: Radius.circular(20.0),
                        bottomLeft: Radius.circular(0.0)),
                    boxShadow: [BoxShadow(
                      color: Colors.grey.withOpacity(0.3),
                      blurRadius: 4,
                      spreadRadius: -5,
                      offset: const Offset(4, -8),
                    )
                    ],
                  ),
                  alignment: Alignment.center,
                  child: Column(
                    children: [
                      Container(
                        margin: EdgeInsets.only(top: 25),
                        child: Text(
                          'by ITMO University',
                          style: GoogleFonts.manrope(
                              fontSize: 15,
                              color: styles.textColor,
                              fontWeight: FontWeight.bold
                          ),
                        ),

                      ),
                      Container(
                        margin: EdgeInsets.only(top: 8),
                        child: Text(
                          'Версия 1.0',
                          style: GoogleFonts.manrope(
                              color: styles.ffontColor,
                              fontSize: 10,
                              fontWeight: FontWeight.bold
                          ),
                        ),

                      ),
                      Container(
                        margin: EdgeInsets.only(top: 4),
                        child: Text(
                          'от 30 сентября 2021',
                          style: GoogleFonts.manrope(
                              color: styles.ffontColor,
                              fontSize: 10,
                              fontWeight: FontWeight.bold
                          ),
                        ),
                      ),
                    ],),
                ),
              ],
            ),
          ],
        )
    );
  }
  void _navigateToPreviousScreen(BuildContext context) {
    Navigator.of(context).push(MaterialPageRoute(builder: (context) => MyHomePage(title: "Погода")));
  }
}