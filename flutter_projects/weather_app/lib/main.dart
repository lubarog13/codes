import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:weather_app/City.dart';
import 'package:weather_app/themedata.dart';
import './search.dart';
import './week.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:weather_icons/weather_icons.dart';
import './settings.dart';
import 'package:http/http.dart' as http;
import './about.dart';
import 'package:intl/intl.dart';
import 'package:intl/date_symbol_data_local.dart';
import 'favourite.dart';
import 'weather.dart';
import 'parser.dart';
void main() {
  initializeDateFormatting('ru', null);
  runApp(const MyApp());
}
enum WhyFarther { harder, smarter, selfStarter, tradingCharter }

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
      ),
      home: const MyHomePage(title: "Погода"),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);
  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;
  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  Future<int> myfuture = Future(() => 1);
  @override
  void initState(){
    styles = Styles(iDarkTheme);
    styles.initColors();
    myfuture = callWeather();
      super.initState();
  }
  var _scaffoldKey = new GlobalKey<ScaffoldState>();
  bool iDarkTheme = false;
  Weather currentWeather = new Weather(name: 'Saint Petersburg', main_: 'Sunny', temp: 10, wind: 9, pressure: 761, humidity: 87, dt_txt: '');
  City city = new City(name: 'Saint Petersburg', local_names: 'Санкт Петербург', lat: 59.8944, lon: 30.2642, country: 'RU', state: null);
  List<Weather>? weather;
  bool hasLoading = false;
  late Styles styles;
  Parser parser = Parser(t: 0, s: 0, p: 0);

  Weather parseWeather(String responseBody) {
    final parsed = jsonDecode(responseBody);
    print(parsed['main']['temp']);
    return Weather.fromJson(parsed);
  }
  List<Weather> parseWeatherByHours(String responseBody) {
    final parsed = jsonDecode(responseBody);
    List<Weather>? weather_ = [];
    for(int i=0; i<8; i++){
      weather_.add(Weather.fromSpecificJson(parsed, i));
      print(weather_[i].temp);
    }
    return weather_;
  }
  Future<int> printSomething() async {
    print(hasLoading);
    return 0;
  }
  Future<Weather> fetcWeather() async {
    print('ok1');
    final response = await http
        .get(Uri.parse('http://api.openweathermap.org/data/2.5/weather?lat=${city.lat}&lon=${city.lon}&appid=728abff1d97171ab28a008e3e5800944'));
    print('ok2');
    if (response.statusCode == 200) {
      print('ok3');
      return parseWeather(response.body);
    } else {
      throw Exception('Failed to load album');
    }
  }
  Future<List<Weather>> fetcWeatherByHours() async {
    print('ok1');
    final response = await http
        .get(Uri.parse('http://api.openweathermap.org/data/2.5/forecast?lat=${city.lat}&lon=${city.lon}&appid=728abff1d97171ab28a008e3e5800944'));
    print('ok2');
    print("ok5");
    if (response.statusCode == 200) {
      print('ok3');
      return parseWeatherByHours(response.body);
    } else {
      throw Exception('Failed to load album');
    }
  }
Future<int> callWeather() async {
  if(!hasLoading){
        parser.initValues();
        await _getSelected();
        weather = await fetcWeatherByHours();
        currentWeather = await fetcWeather();
        hasLoading = true;
      setState (() {
      });
  }
      return 0;
  }
  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return FutureBuilder<int>(
      future: myfuture,
      builder: (BuildContext context, AsyncSnapshot<int> snapshot){
        Widget child;
        if (snapshot.hasData) {
            child = Scaffold(
        key: _scaffoldKey,
        backgroundColor: Colors.lightBlue,
        drawer: Drawer(
          child: Container(
            color: this.styles.backgroundColor,
            child: ListView(
              padding: EdgeInsets.zero,
              children: <Widget>[
                 Padding(padding: EdgeInsets.only(top: 30, left: 20, bottom: 20),
                  child: Text(
                    'Weather App',
                    style: TextStyle(
                      color: this.styles.textColor,
                      fontWeight: FontWeight.bold,
                      fontSize: 24,
                    ),
                  ),
                ),

                ListTile(
                  onTap: () => {_navigateToSettingsScreen(context)},
                  leading: Icon(Icons.settings, color: styles.textColor),
                  title: Text('Настройки', style: TextStyle(color: styles.textColor),),
                ),
                ListTile(
                  leading: Icon(Icons.favorite_outline, color: styles.textColor),
                  title: Text('Избранное', style: TextStyle(color: styles.textColor),),
                  onTap: () => _navigateToFavouriteScreen(context),
                ),
                ListTile(
                  leading: Icon(Icons.person, color: styles.textColor),
                  title: Text('О приложении', style: TextStyle(color: styles.textColor),),
                  onTap: () => {_navigateToAboutScreen(context)},
                ),
              ],
            ),
          ),
        ),
        body: Stack(
          children: <Widget>[
            Container(
              height: MediaQuery.of(context).size.height,
              width: MediaQuery.of(context).size.width,
              decoration:  BoxDecoration(
                image: DecorationImage(
                  image: iDarkTheme? AssetImage(
                      'assets/images/background3.png') : AssetImage(
                      'assets/images/background2.jpg'),
                  fit: BoxFit.fill,
                ),
              ),
            ),
            Container(
              padding: const EdgeInsets.only(top: 100.0),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  Text(
                    parser.parseTemp(currentWeather.temp.toInt()) + parser.parseTempName(),
                    style: TextStyle(
                        fontSize: 72.0,
                        fontFamily: 'Roboto',
                        color: Colors.white
                    ),
                  ),
                ],
              ),
            ),
            Container(
              padding: const EdgeInsets.only(top: 180.0),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisAlignment: MainAxisAlignment.center,
                children:  <Widget>[
                  Text(
                    DateFormat.yMMMd('ru').format(DateTime.now()),
                    style: TextStyle(
                        fontSize: 22.0,
                        fontFamily: 'Roboto',
                        color: Colors.white
                    ),
                  )
                ],
              ),
            ),
            Container(
                margin: EdgeInsets.only(top: 30),
                child: Row(
                  children: [
                    Expanded(
                      flex: 1,
                      child: Container(
                          child: MaterialButton(
                            onPressed: () => _scaffoldKey.currentState?.openDrawer(),
                            color: this.styles.menuButtonColor1,
                            textColor: Colors.white,
                            child: const Icon(
                              Icons.menu,
                              size: 20,
                            ),
                            shape: CircleBorder(),
                          )
                      ),
                    ),
                    Expanded(
                      flex: 2,
                      child:
                      GestureDetector(
                        child: Container(
                          child: Text(
                            city.local_names,
                            textAlign: TextAlign.center,
                            style: TextStyle(
                              color: Colors.white,
                              fontSize: 18.0,
                              fontFamily: 'Roboto',
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                        onTap: () => _navigateToNextScreen(context),
                      ),
                    ),
                    Expanded(
                      flex: 1,
                      child: Container(
                          child: MaterialButton(
                            onPressed: () => {_navigateToNextScreen(context)},
                            color: this.styles.menuButtonColor1,
                            textColor: Colors.white,
                            child: Icon(
                              Icons.add_circle_outline,
                              size: 20,
                            ),
                            shape: CircleBorder(),
                          )
                      ),
                    )
                  ],
                )
            ),
            Positioned(
                bottom: 00,
                width: MediaQuery.of(context).size.width,
                child: ConstrainedBox(
                    constraints: BoxConstraints(minHeight: 100, maxHeight: 450),
                    child:  ListView.builder(
                      shrinkWrap: true,
                      itemBuilder: (BuildContext context, int index) {
                        return Container(
                            height: 440,
                            margin: EdgeInsets.only(top: 150),
                            decoration: BoxDecoration(
                              color: this.styles.backgroundColor,
                              borderRadius: const BorderRadius.only(
                                  topRight: Radius.circular(20.0),
                                  bottomRight: Radius.circular(0.0),
                                  topLeft: Radius.circular(20.0),
                                  bottomLeft: Radius.circular(0.0)),
                            ),
                            child: Column(
                              children: [
                                Column(
                                  children: [
                                    Container(
                                      key: Key('_pB'),
                                      width: 80,
                                      height: 3.3,
                                      margin: EdgeInsets.only(top: 10),
                                      child: RawMaterialButton(
                                        onPressed: () => {
                                        },
                                        fillColor: this.styles.buttonTextColor,
                                      ),
                                    )
                                  ],
                                ),
                                SizedBox(
                                  height: 180.0,
                                  width: MediaQuery.of(context).size.width,
                                  child: ListView (
                                      scrollDirection: Axis.horizontal,
                                      semanticChildCount: 4,
                                      children: [
                                        Container(
                                            height: 120,
                                            width: 70,
                                            decoration:  BoxDecoration(
                                              color: this.styles.cardColor1,
                                              borderRadius:const BorderRadius.only(
                                                  topRight: Radius.circular(10.0),
                                                  bottomRight: Radius.circular(10.0),
                                                  topLeft: Radius.circular(10.0),
                                                  bottomLeft: Radius.circular(10.0)
                                              ),
                                              boxShadow:  [
                                                BoxShadow(
                                                  color: styles.iconColor1,
                                                  spreadRadius: 1,
                                                  blurRadius: 3,
                                                  offset: Offset(0, 2), // changes position of shadow
                                                ),
                                              ],
                                            ),
                                            margin: const EdgeInsets.only(top:20, left: 45, bottom: 20),
                                            child: Column (
                                              children:  [
                                                Text(
                                                  DateFormat('hh:mm').format(DateTime.parse(weather![1].dt_txt)),
                                                  style: TextStyle(
                                                      fontSize: 18.0,
                                                      fontFamily: 'Roboto',
                                                      color: this.styles.textColor,
                                                  ),
                                                ),
                                                SizedBox(
                                                    height: 80,
                                                    width: 40,
                                                    child: Image(
                                                      image: AssetImage(weather![1].pictureMain()),
                                                      width: 40,
                                                      height: 40,)
                                                ),
                                                Text(
                                                  parser.parseTemp(weather![1].temp.toInt()) + parser.parseTempName(),
                                                  style: TextStyle(
                                                      fontSize: 20.0,
                                                      fontFamily: 'Roboto',
                                                      color: this.styles.textColor,
                                                  ),
                                                ),

                                              ],
                                            )
                                        ),
                                        Container(
                                            height: 120,
                                            width: 70,
                                            decoration:  BoxDecoration(
                                              color: this.styles.cardColor1,
                                              borderRadius: BorderRadius.only(
                                                  topRight: Radius.circular(10.0),
                                                  bottomRight: Radius.circular(10.0),
                                                  topLeft: Radius.circular(10.0),
                                                  bottomLeft: Radius.circular(10.0)
                                              ),
                                              boxShadow: [
                                                BoxShadow(
                                                  color: this.styles.iconColor1,
                                                  spreadRadius: 1,
                                                  blurRadius: 3,
                                                  offset: Offset(0, 2), // changes position of shadow
                                                ),
                                              ],
                                            ),
                                            margin: const EdgeInsets.only(top:20, left: 45, bottom: 20),
                                            child: Column (
                                              children: [
                                                Text(
                                                  DateFormat('hh:mm').format(DateTime.parse(weather![3].dt_txt)),
                                                  style: TextStyle(
                                                      fontSize: 18.0,
                                                      fontFamily: 'Roboto',
                                                      color: this.styles.textColor,
                                                  ),
                                                ),
                                                SizedBox(
                                                    height: 80,
                                                    width: 40,
                                                    child: Image(
                                                      image: AssetImage(weather![3].pictureMain()),
                                                      width: 40,
                                                      height: 40,)
                                                ),
                                                Text(
                                                  parser.parseTemp(weather![3].temp.toInt()) + parser.parseTempName(),
                                                  style: TextStyle(
                                                      fontSize: 20.0,
                                                      fontFamily: 'Roboto',
                                                      color: this.styles.textColor,
                                                  ),
                                                ),

                                              ],
                                            )
                                        ),
                                        Container(
                                            height: 120,
                                            width: 70,
                                            decoration:  BoxDecoration(
                                              color: this.styles.cardColor1,
                                              borderRadius: BorderRadius.only(
                                                  topRight: Radius.circular(10.0),
                                                  bottomRight: Radius.circular(10.0),
                                                  topLeft: Radius.circular(10.0),
                                                  bottomLeft: Radius.circular(10.0)
                                              ),
                                              boxShadow: [
                                                BoxShadow(
                                                  color: this.styles.iconColor1,
                                                  spreadRadius: 1,
                                                  blurRadius: 3,
                                                  offset: Offset(0, 2), // changes position of shadow
                                                ),
                                              ],
                                            ),
                                            margin: const EdgeInsets.only(top:20, left: 45, bottom: 20),
                                            child: Column (
                                              children: [
                                                Text(
                                                  '18:00',
                                                  style: TextStyle(
                                                      fontSize: 18.0,
                                                      fontFamily: 'Roboto',
                                                      color: this.styles.textColor,
                                                  ),
                                                ),
                                                SizedBox(
                                                    height: 80,
                                                    width: 40,
                                                    child: Image(
                                                      image: AssetImage(weather![5].pictureMain()),
                                                      width: 40,
                                                      height: 40,)
                                                ),
                                                Text(
                                                  parser.parseTemp(weather![5].temp.toInt()) + parser.parseTempName(),
                                                  style: TextStyle(
                                                      fontSize: 20.0,
                                                      fontFamily: 'Roboto',
                                                      color: this.styles.textColor,
                                                  ),
                                                ),

                                              ],
                                            )
                                        ),
                                        Container(
                                            height: 120,
                                            width: 70,
                                            decoration:  BoxDecoration(
                                              color: this.styles.cardColor1,
                                              borderRadius: BorderRadius.only(
                                                  topRight: Radius.circular(10.0),
                                                  bottomRight: Radius.circular(10.0),
                                                  topLeft: Radius.circular(10.0),
                                                  bottomLeft: Radius.circular(10.0)
                                              ),
                                              boxShadow: [
                                                BoxShadow(
                                                  color: this.styles.iconColor1,
                                                  spreadRadius: 1,
                                                  blurRadius: 3,
                                                  offset: Offset(0, 2), // changes position of shadow
                                                ),
                                              ],
                                            ),
                                            margin: const EdgeInsets.only(top:20, left: 45, bottom: 20, right: 45),
                                            child: Column (
                                              children: [
                                                Text(
                                                  '00:00',
                                                  style: TextStyle(
                                                      fontSize: 18.0,
                                                      fontFamily: 'Roboto',
                                                      color: this.styles.textColor,
                                                  ),
                                                ),
                                                SizedBox(
                                                    height: 80,
                                                    width: 40,
                                                    child: Image(
                                                      image: AssetImage(weather![7].pictureMain()),
                                                      width: 40,
                                                      height: 40,)
                                                ),
                                                Text(
                                                  parser.parseTemp(weather![7].temp.toInt()) + parser.parseTempName(),
                                                  style: TextStyle(
                                                      fontSize: 20.0,
                                                      fontFamily: 'Roboto',
                                                      color: this.styles.textColor,
                                                  ),
                                                ),

                                              ],
                                            )
                                        ),
                                      ]
                                  ),
                                ),
                                
                                 TextButton(
                                  onPressed: () { _navigateToWeatherScreen(context);},
                                  style: ButtonStyle(
                              shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                                  RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(8.0),
                                    side:  BorderSide(color: styles.menuButtonColor,width : 2),
                                  ),
                              
                            ),
                                  ),
                                  child:  Text( 'Прогноз на неделю', style: TextStyle(color: this.styles.buttonTextColor),),
                                ),
                                Container(
                                    margin: EdgeInsets.only(top: 20),
                                    padding: EdgeInsets.only(left: 30, right: 30),
                                    child: Column(
                                        children: [
                                          Row(
                                            children:
                                            [
                                              Expanded(
                                                  flex: 2,
                                                  child: Container(
                                                    height: 70,
                                                    margin: EdgeInsets.only(right: 10),
                                                    decoration:  BoxDecoration(
                                                      color: this.styles.cardColor1,
                                                      borderRadius: BorderRadius.only(
                                                          topRight: Radius.circular(10.0),
                                                          bottomRight: Radius.circular(10.0),
                                                          topLeft: Radius.circular(10.0),
                                                          bottomLeft: Radius.circular(10.0)
                                                      ),
                                                      boxShadow: [
                                                        BoxShadow(
                                                          color: this.styles.iconColor1,
                                                          spreadRadius: 1,
                                                          blurRadius: 3,
                                                          offset: Offset(0, 2), // changes position of shadow
                                                        ),
                                                      ],
                                                    ),
                                                    child: Row(
                                                      mainAxisAlignment: MainAxisAlignment.center,
                                                      crossAxisAlignment: CrossAxisAlignment.center,
                                                      children:  [
                                                        Icon(
                                                          WeatherIcons.thermometer,
                                                          size: 30,
                                                          color: this.styles.iconColor1,
                                                        ),
                                                        Text(
                                                          '    '+ parser.parseTemp(currentWeather.temp.toInt()),
                                                          style:  GoogleFonts.manrope(
                                                              fontWeight: FontWeight.bold,
                                                              color: this.styles.textColor,
                                                              fontSize: 18),
                                                        ),
                                                         Text(
                                                          parser.parseTempName(),
                                                          style:  GoogleFonts.manrope(
                                                              fontWeight: FontWeight.bold,
                                                              fontSize: 14, color: this.styles.iconColor1),
                                                        ),
                                                      ],
                                                    ),
                                                  )
                                              ),
                                              Expanded(
                                                  flex: 2,

                                                  child: Container(
                                                    height: 70,
                                                    margin: EdgeInsets.only(left: 10),
                                                    decoration:  BoxDecoration(
                                                      color: this.styles.cardColor1,
                                                      borderRadius: BorderRadius.only(
                                                          topRight: Radius.circular(10.0),
                                                          bottomRight: Radius.circular(10.0),
                                                          topLeft: Radius.circular(10.0),
                                                          bottomLeft: Radius.circular(10.0)
                                                      ),
                                                      boxShadow: [
                                                        BoxShadow(
                                                          color: this.styles.iconColor1,
                                                          spreadRadius: 1,
                                                          blurRadius: 3,
                                                          offset: Offset(0, 2), // changes position of shadow
                                                        ),
                                                      ],
                                                    ),
                                                    child: Row(
                                                      mainAxisAlignment: MainAxisAlignment.center,
                                                      crossAxisAlignment: CrossAxisAlignment.center,
                                                      children:  [
                                                        Icon(
                                                          WeatherIcons.humidity,
                                                          size: 30,
                                                          color: this.styles.iconColor1,
                                                        ),
                                                        Text(
                                                          '    ' + currentWeather.humidity.toString(),
                                                          style:  GoogleFonts.manrope(
                                                              fontWeight: FontWeight.bold,
                                                              color: this.styles.textColor,
                                                              fontSize: 18),
                                                        ),
                                                        Text(
                                                          '%',
                                                          style:  GoogleFonts.manrope(
                                                              fontWeight: FontWeight.bold,
                                                              fontSize: 14, color: this.styles.iconColor1),
                                                        ),
                                                      ],

                                                    ),
                                                  )
                                              )
                                            ],),
                                          Row(
                                            children:
                                            [
                                              Expanded(
                                                  flex: 2,
                                                  child: Container(
                                                    height: 70,

                                                    margin: EdgeInsets.only(right: 10, top: 20),
                                                    decoration:  BoxDecoration(
                                                      color: this.styles.cardColor1,
                                                      borderRadius: BorderRadius.only(
                                                          topRight: Radius.circular(10.0),
                                                          bottomRight: Radius.circular(10.0),
                                                          topLeft: Radius.circular(10.0),
                                                          bottomLeft: Radius.circular(10.0)
                                                      ),
                                                      boxShadow: [
                                                        BoxShadow(
                                                          color: this.styles.iconColor1,
                                                          spreadRadius: 1,
                                                          blurRadius: 3,
                                                          offset: Offset(0, 2), // changes position of shadow
                                                        ),
                                                      ],
                                                    ),
                                                    child: Row(
                                                      mainAxisAlignment: MainAxisAlignment.center,
                                                      crossAxisAlignment: CrossAxisAlignment.center,
                                                      children:  [
                                                        Icon(
                                                          WeatherIcons.strong_wind,
                                                          size: 30,
                                                          color: this.styles.iconColor1,
                                                        ),
                                                        Text(
                                                          '    ' + parser.parseSpeed(currentWeather.wind),
                                                          style:  GoogleFonts.manrope(
                                                              fontWeight: FontWeight.bold,
                                                              color: this.styles.textColor,
                                                              fontSize: 18),
                                                        ),
                                                        Text(
                                                          parser.parseSpeedName(),
                                                          style:  GoogleFonts.manrope(
                                                              fontWeight: FontWeight.bold,
                                                              fontSize: 14, color: this.styles.iconColor1),
                                                        )
                                                      ],
                                                    ),
                                                  )
                                              ),
                                              Expanded(
                                                  flex: 2,

                                                  child: Container(
                                                    height: 70,
                                                    padding: EdgeInsets.only(bottom: 30),
                                                    margin: EdgeInsets.only(left: 10, top: 20),
                                                    decoration:  BoxDecoration(
                                                      color: this.styles.cardColor1,
                                                      borderRadius: BorderRadius.only(
                                                          topRight: Radius.circular(10.0),
                                                          bottomRight: Radius.circular(10.0),
                                                          topLeft: Radius.circular(10.0),
                                                          bottomLeft: Radius.circular(10.0)
                                                      ),
                                                      boxShadow: [
                                                        BoxShadow(
                                                          color: this.styles.iconColor1,
                                                          spreadRadius: 1,
                                                          blurRadius: 3,
                                                          offset: Offset(0, 2), // changes position of shadow
                                                        ),
                                                      ],
                                                    ),
                                                    child: Row(
                                                      mainAxisAlignment: MainAxisAlignment.center,
                                                      crossAxisAlignment: CrossAxisAlignment.end,

                                                      children:  [
                                                        Icon(
                                                          WeatherIcons.barometer,
                                                          size: 30,
                                                          color: this.styles.iconColor1,
                                                        ),
                                                        Text(
                                                          parser.parsePressure(currentWeather.pressure),
                                                          style:  GoogleFonts.manrope(
                                                              fontWeight: FontWeight.bold,
                                                              color: this.styles.textColor,
                                                              fontSize: 18),
                                                        ),
                                                        Text(
                                                          parser.parsePressureName(),
                                                          style:  GoogleFonts.manrope(
                                                              fontWeight: FontWeight.bold,
                                                              fontSize: 14, color: this.styles.iconColor1),
                                                        )
                                                      ],

                                                    ),
                                                  )
                                              )
                                            ],),
                                        ]
                                    )
                                )
                              ],
                            ));
                      },
                      itemCount: 1,
                    )

                )
            )
          ],
        )
      // This trailing comma makes auto-formatting nicer for build methods.
    );
      } else {
          child = Scaffold(
            backgroundColor: styles.backgroundColor,
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
                        fontWeight: FontWeight.w600,
                        color: styles.textColor
                      ),
                    )
                  ),
                  Center(
                     child: CircularProgressIndicator(
                color: this.styles.textColor,
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
  void _navigateToNextScreen(BuildContext context) {
    Navigator.of(context).push(MaterialPageRoute(builder: (context) => NewScreen()));
  }
  void _navigateToWeatherScreen(BuildContext context) {
    Navigator.of(context).push(MaterialPageRoute(builder: (context) => WeekScreen()));
  }
  void _navigateToSettingsScreen(BuildContext context) {
    Navigator.of(context).push(MaterialPageRoute(builder: (context) => SettingsScreen()));
  }
  void _navigateToAboutScreen(BuildContext context) {
    Navigator.of(context).push(MaterialPageRoute(builder: (context) => AboutScreen()));
  }
  void _navigateToFavouriteScreen(BuildContext context) {
    Navigator.of(context).push(MaterialPageRoute(builder: (context) => FavouriteScreen()));
  }

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
}