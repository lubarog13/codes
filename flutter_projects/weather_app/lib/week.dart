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

import 'City.dart';

class WeekScreen extends StatefulWidget{
  @override
  State<WeekScreen> createState() => WeekState();
}

class WeekState extends State<WeekScreen> {
  @override
  void initState(){
    callWeather();
      super.initState();
  }
  List<Weather>? weather;
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
     print(DateTime.fromMillisecondsSinceEpoch(int.parse(weather_[i].dt_txt) * 1000));
     }
     return weather_;
  }
  Future<int> callWeather() async {
        parser.initValues();
        await _getSelected();
        weather = await fetcWeather();
      setState (() {
      });
      return 0;
  }
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
            Container(
              margin: EdgeInsets.only(top: 30),
              width: MediaQuery.of(context).size.width*0.8 + 50,
            height: MediaQuery.of(context).size.height*0.5,
            
            child: new Swiper(
              itemBuilder: (BuildContext context, int index) {
                return new Container (
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
                  fontSize: 24),
                                ),
                                Container(
                                  margin: EdgeInsets.only(top: 20, bottom: 10),
                                  child: Image(
                                  image: AssetImage(weather![index].pictureMain()),
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
                                      '    ' + weather![index].temp.toInt().toString() + '\u00B0',
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
                                      '    ' + weather![index].wind.toString(),
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
                                      '    ' + weather![index].humidity.toString(),
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
                                      '    ' + weather![index].pressure.toString(),
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