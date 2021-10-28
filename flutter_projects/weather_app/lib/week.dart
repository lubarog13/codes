import 'package:flutter/material.dart';
import 'package:weather_app/parser.dart';
import 'package:weather_app/weather.dart';
import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import './main.dart';
import 'package:weather_icons/weather_icons.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:http/http.dart' as http;
import 'package:flutter_swiper_null_safety/flutter_swiper_null_safety.dart';
import 'package:intl/intl.dart';
import 'package:intl/date_symbol_data_local.dart';
import './themedata.dart';

import 'City.dart';

class WeekScreen extends StatefulWidget{
  @override
  State<WeekScreen> createState() => WeekState();
}

class WeekState extends State<WeekScreen> {
   Future<int> myfuture = Future(() => 1);
  @override
  void initState(){
    style = Styles(iDarkTheme);
      myfuture = callWeather();
      style.initColors();
      super.initState();
  }
  List<Weather>? weather;
   bool iDarkTheme = false;
  late Styles style;
   bool hasLoading = false;
  Parser parser = Parser(t: 0, s: 0, p: 0);
  City city = new City(name: 'Saint Petersburg', local_names: 'Санкт Петербург', lat: 59.8944, lon: 30.2642, country: 'RU', state: null);
  Future<void> _getSelected() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    var coded = preferences.getString('selected');
    setState(() {
      if(coded!=null){
      var decoded = jsonDecode(coded);
      city = new City(name: decoded['name'], local_names: decoded['local_names'], lat: decoded['lat'], lon: decoded['lon'], country: decoded['country'], state: decoded['state']);
    }
    });
  }
  Future<List<Weather>> fetcWeather() async {
    print('ok1');
    final response = await http
        .get(Uri.parse('http://api.openweathermap.org/data/2.5/onecall?lat=${city.lat}&lon=${city.lon}&exclude=minutely,hourly&appid=728abff1d97171ab28a008e3e5800944'));
    print('ok2');
    if (response.statusCode == 200) {
      print('ok3');
      return parseWeather(response.body);
    } else {
      throw Exception('Failed to load album');
    }
  }
  List<Weather> parseWeather(String responseBody) {
    final parsed = jsonDecode(responseBody);
    List<Weather> weather_ = [];
     for (int i=0; i<7; i++){
       weather_.add(new Weather(name: city.name, main_: parsed['daily'][i]['weather'][0]['main'], temp: parsed['daily'][i]['temp']['day'] - 273.15, 
    wind:  parsed['daily'][i]['wind_speed'], pressure: parsed['daily'][i]['pressure'],
     humidity: parsed['daily'][i]['humidity'], dt_txt: parsed['daily'][i]['dt'].toString()));
     print(i);
     }
     return weather_;
  }
  Future<int> callWeather() async {
     if(!hasLoading){
        parser.initValues();
        await _getSelected();
        weather = await fetcWeather();
        print('ok4');
        hasLoading=true;
      setState (() {
      });
     }
      return 0;
  }
  @override
  Widget build(BuildContext context) {
    return FutureBuilder<int>(
      future: myfuture,
      builder: (BuildContext context, AsyncSnapshot<int> snapshot){
        Widget child;
        if(snapshot.hasData){
          child = Scaffold(
            backgroundColor: style.backgroundColor,
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
                  color: style.textColor,
                  fontSize: 24),
            ),
            ),
            Container(
              margin: EdgeInsets.only(top: 30),
              width: MediaQuery.of(context).size.width*0.8 + 50,
            height: MediaQuery.of(context).size.height*0.5,
            
            child: new Swiper(
              itemBuilder: (BuildContext context, int index) {
                return new Container (
                    decoration: BoxDecoration(
                      gradient: LinearGradient(
              begin: Alignment.topLeft,
              end: Alignment.bottomRight,
              colors: [
                style.gradientColor1,
                style.gradientColor2
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
                          flex: 10,
                          child: Container(
                            width: MediaQuery.of(context).size.width*0.7,
                            height: MediaQuery.of(context).size.height*0.5-20,
                            margin: EdgeInsets.only(top: 20,left: 10),
                            child: Column(
                              children: [
                                 Text(
                                  DateFormat.yMMMd('ru').format(DateTime.fromMillisecondsSinceEpoch(int.parse(weather![index].dt_txt)*1000)),
                                  style: GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  color: style.textColor,
                  fontSize: 24),
                                ),
                                Container(
                                  margin: EdgeInsets.only(top: 20, bottom: 10),
                                  child: Image(
                                  image: AssetImage(weather![index].weekMainPicture()),
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
                                      color: style.iconColor1,
                                    ),
                                    Text(
                                      '    ' + parser.parseTemp(weather![index].temp.toInt()),
                                      style:  GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  color: style.textColor,
                  fontSize: 18),
                                    ),
                                    Text(
                                        parser.parseTempName(),
                                        style:  GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  fontSize: 14, color: style.subTextColor),
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
                                      color: style.iconColor1,
                                    ),
                                    Text(
                                      '    ' + parser.parseSpeed(weather![index].wind),
                                      style:  GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  color: style.textColor,
                  fontSize: 18),
                                    ),
                                    Text(
                                        parser.parseSpeedName(),
                                        style:  GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  fontSize: 14, color: style.subTextColor),
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
                                      color: style.iconColor1,
                                    ),
                                    Text(
                                      '    ' +  weather![index].humidity.toString(),
                                      style:  GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  color: style.textColor,
                  fontSize: 18),
                                    ),
                                    Text(
                                        '%',
                                        style:  GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  fontSize: 14, color: style.subTextColor),
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
                                      color: style.iconColor1,
                                    ),
                                    Text(
                                      '    ' + parser.parsePressure(weather![index].pressure),
                                     style:  GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  color: style.textColor,
                  fontSize: 18),
                                    ),
                                    Text(
                                        parser.parsePressureName(),
                                        style:  GoogleFonts.manrope(
                  fontWeight: FontWeight.bold, 
                  fontSize: 14, color: style.subTextColor),
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
            );
              },
            itemCount: 7,
            loop: false,
            itemWidth: MediaQuery.of(context).size.width*0.8,
            layout: SwiperLayout.CUSTOM,
              customLayoutOption: new CustomLayoutOption(
                  startIndex: -1,
                  stateCount: 3
              ).addRotate([
                -45.0/180,
                0.0,
                45.0/180
              ]).addTranslate([
                new Offset(-370.0, -40.0),
                new Offset(0.0, 0.0),
                new Offset(370.0, -40.0)
  ]),
            ),
            ),
            Container(
              margin: EdgeInsets.only(
                top: 40),
                
                child: TextButton(
                            
                            onPressed: () {_navigateToPreviousScreen(context); },
                            style: ButtonStyle(
                              shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                                  RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(8.0),
                                    side:  BorderSide(color: style.textColor, width: 2),
                                  ),
                              
                            ),
                            ),
                            
                            child:  Text('Вернуться на главную',

                            style: TextStyle(
                              color: style.textColor,
                            ),),
                          ),
            )
            ],
          ),
            )
      ),
    );
        }
        else {
          child = Scaffold(
            backgroundColor: style.backgroundColor,
            body: Center(
              child: Stack(
                children: [
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(bottom: MediaQuery.of(context).size.height / 4),
                    child: Text(
                      'Weather',
                      style: GoogleFonts.manrope(
                        fontSize: 35,
                        color: style.textColor,
                        fontWeight: FontWeight.w600
                      ),
                    )
                  ),
                  Center(
                     child: CircularProgressIndicator(
                color: style.textColor,
                  ),
                  )
                ],
              )
            ),
          );
      }
      return Container(
        child: child,
      );
      }
      );
  }
  void _navigateToPreviousScreen(BuildContext context) {
    Navigator.of(context).push(MaterialPageRoute(builder: (context) => MyHomePage(title: "Погода")));
  }
}