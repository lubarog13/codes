import 'package:flutter/material.dart';
import './main.dart';
import 'package:weather_icons/weather_icons.dart';
import 'package:google_fonts/google_fonts.dart';

class WeekScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: SizedBox(
          width: MediaQuery.of(context).size.width*0.8,
          height: MediaQuery.of(context).size.height*0.8,
          child: Column(
            children: <Widget>[ 
              Container(
                margin: EdgeInsets.only(top: 30),
            width: MediaQuery.of(context).size.width*0.7,
            height: 40,
            child: Text(
              "Прогноз на неделю",
              textAlign: TextAlign.center,
              style: GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  fontSize: 24),
            ),
            ),
            Container (
              margin: EdgeInsets.only(top: 30),
              width: MediaQuery.of(context).size.width*0.8,
            height: MediaQuery.of(context).size.height*0.5,
                    decoration: const BoxDecoration(
                      gradient: LinearGradient(
              begin: Alignment.topLeft,
              end: Alignment.bottomRight,
              colors: [
                Color.fromRGBO(205, 218, 245, 100),
                Color.fromRGBO(156, 188, 255, 100),
              ],
            ),
                      borderRadius: BorderRadius.only(
                          topRight: Radius.circular(20.0),
                          bottomRight: Radius.circular(20.0),
                          topLeft: Radius.circular(20.0),
                          bottomLeft: Radius.circular(20.0)),
                    ),
                    child: Row(
                      children: [
                        Expanded(
                          flex: 6,
                          child: Container(
                            width: MediaQuery.of(context).size.width*0.5,
                            height: MediaQuery.of(context).size.height*0.5-20,
                            margin: EdgeInsets.only(top: 20,left: 10),
                            child: Column(
                              children: [
                                 Text(
                                  '23 сентября',
                                  style: GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  fontSize: 24),
                                ),
                                Container(
                                  margin: EdgeInsets.only(top: 20, bottom: 10),
                                  child: Image(
                                  image: AssetImage('assets/images/partly_cloudy.png'),
                                  width: 120,
                                  height: 120,
                                  ),
                                ),
                                Row(
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  crossAxisAlignment: CrossAxisAlignment.end,
                                  children:  [
                                    Icon(
                                      WeatherIcons.thermometer,
                                      size: 30,
                                      color: Color.fromRGBO(90, 90, 90, 100),
                                    ),
                                    Text(
                                      '    8\u00B0',
                                      style:  GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  fontSize: 18),
                                    ),
                                    Text(
                                        'C',
                                        style:  GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  fontSize: 14, color: Colors.grey),
                                    )
                                  ],
                                ),
                                Container(
                                  margin: EdgeInsets.only(top: 10, bottom: 10),
                                  height: 30,
                                  alignment: Alignment.bottomLeft,
                                child: Row(
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  crossAxisAlignment: CrossAxisAlignment.end,
                                  children:  [
                                    Icon(
                                      WeatherIcons.strong_wind,
                                      size: 30,
                                      color: Color.fromRGBO(90, 90, 90, 100),
                                    ),
                                    Text(
                                      '    9',
                                      style:  GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  fontSize: 18),
                                    ),
                                    Text(
                                        'м/с',
                                        style:  GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  fontSize: 14, color: Colors.grey),
                                    )
                                  ],
                                ),
                                ),
                                Row(
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  crossAxisAlignment: CrossAxisAlignment.end,
                                  children:  [
                                    Icon(
                                      WeatherIcons.humidity,
                                      size: 30,
                                      color: Color.fromRGBO(90, 90, 90, 100),
                                    ),
                                    Text(
                                      '    87',
                                      style:  GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  fontSize: 18),
                                    ),
                                    Text(
                                        '%',
                                        style:  GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  fontSize: 14, color: Colors.grey),
                                    ),
                                  ],

                                ),
                                Container(
                                  margin: EdgeInsets.only(top: 10),
                                  height: 30,
                                child: Row(
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  crossAxisAlignment: CrossAxisAlignment.end,
                                  children:  [
                                    Icon(
                                      WeatherIcons.barometer,
                                      size: 30,
                                      color: Color.fromRGBO(90, 90, 90, 100),
                                    ),
                                    Text(
                                      '    761',
                                     style:  GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  fontSize: 18),
                                    ),
                                    Text(
                                        'мм.рт.ст',
                                        style:  GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  fontSize: 14, color: Colors.grey),
                                    )
                                  ],
                                ),
                                ),
                              ],
                              ),
                              ),
                          ),
                          const Expanded(
                            flex: 5,
                            child: Text(
                              ''
                            ),
                            ),
                      ],
                    ),

            ),
            Container(
              margin: EdgeInsets.only(
                top: 40),
                child: OutlinedButton(
                            
                            onPressed: () {_navigateToPreviousScreen(context); },
                            style: ButtonStyle(
                              shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                                  RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(8.0),
                                    side: const BorderSide(color: Colors.red,width : 2),
                                  ),
                              
                            ),
                            ),
                            child: const Text('Вернуться на главную',
                          
                            style: TextStyle(
                              color: Colors.black,
                            ),),
                          ),
            )
            ],
          ),
            )
      ),
    );
  }
  void _navigateToPreviousScreen(BuildContext context) {
    Navigator.of(context).push(MaterialPageRoute(builder: (context) => MyHomePage(title: "Погода")));
  }
}