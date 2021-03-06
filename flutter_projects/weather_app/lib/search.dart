import 'dart:convert';
import 'package:weather_app/themedata.dart';

import 'debouncher.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:flutter/material.dart';
import 'package:favorite_button/favorite_button.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;
import './main.dart';
import 'City.dart';
class NewScreen extends StatefulWidget {

  @override
  _Search createState() => _Search();
}
class _Search extends State<NewScreen>{
  @override
  void initState(){
  }
  var _controller = TextEditingController(
  );
  final _city  = <City>[];
  late Styles styles;
  late bool isDarkTheme;
  final _debouncer = Debouncer(milliseconds: 1000);
  void _navigateToPreviousScreen(BuildContext context) {
    Navigator.of(context).push(MaterialPageRoute(builder: (context) => MyHomePage(title: "Погода")));
  }
  List<City> parseCities(String responseBody) {
    final parsed = jsonDecode(responseBody).cast<Map<String, dynamic>>();
    print('ok4');
    return parsed.map<City>((json) => City.fromJson(json)).toList();
  }
  Future<List<City>> fetchCity(String q) async {
    print('ok1');
    final response = await http
        .get(Uri.parse('http://api.openweathermap.org/geo/1.0/direct?q=$q&limit=10&appid=728abff1d97171ab28a008e3e5800944'));
    print('ok2');
    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      print('ok3');
      return parseCities(response.body);
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      throw Exception('Failed to load album');
    }
  }
  void callCity(String q) async {
    var citys = await fetchCity(q);
    print(citys.length);
    setState(() {
      _city.clear();
    _city.addAll(citys);
    });
  }

  @override
  Widget build(BuildContext context) {
    isDarkTheme = MediaQuery.of(context).platformBrightness == Brightness.dark;
    styles = Styles(isDarkTheme);
    styles.initColors();
    return Scaffold(
      backgroundColor: styles.backgroundColor,
      body: Container(
        margin: EdgeInsets.only(top: 35),
        width: MediaQuery.of(context).size.width,
        child:
        Column(
          children: [
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
                Container(
                  width: MediaQuery.of(context).size.width*0.80,
                  height: 35.0,
                  child: TextFormField(
                    controller: _controller,
                    onChanged: (string) {
                        _debouncer.run(callCity, string);
                    },
                    style: GoogleFonts.manrope(
                      fontWeight: FontWeight.w600,
                      color: styles.textColor,
                    ),
                    decoration: InputDecoration(
                      hintText: 'Введите название города...',
                      focusColor: styles.backgroundColor,
                      hintStyle: TextStyle(color: styles.textColor),
                      
                      suffixIcon: IconButton(
                        onPressed: _controller.clear,
                        icon: Icon(Icons.clear, color: styles.textColor),
                      ),
                    ),
                  ),
                ),
              ],
            ),
            ConstrainedBox(
                constraints: BoxConstraints(minHeight: 10, maxHeight: MediaQuery.of(context).size.height / 3),
                child: ListView.separated(
                  itemCount: _city.length,
                  itemBuilder: (BuildContext context, int index){
                    return Container(
                      width: MediaQuery.of(context).size.width - 30,
                      padding: EdgeInsets.only(left: 20),
                      alignment: Alignment.topLeft,
                      height: 25,
                      child: Row (
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Expanded(
                            flex: 5,
                            child:
                            GestureDetector(
                              onTap: () => {_selectCity(_city[index]).then((value) => _navigateToPreviousScreen(context))},
                              child: Text(
                               _city[index].local_names.toString() + ' ' + _city[index].country ,
                              style: GoogleFonts.manrope(
                                  fontSize: 13,
                                  color: styles.textColor,
                                  fontWeight: FontWeight.bold
                              ),
                            ),
                            )
                          ),
                          Container(
                            margin: EdgeInsets.only(right: 20),
                            alignment: Alignment.centerRight,
                            child: StarButton(
                              iconSize: 30,
                              iconColor: styles.textColor,
                              valueChanged: (is_Favorite) {
                                 _saveCity(_city[index]).then((value) => null);
                              },
                            ),
                          )
                        ],
                      ),
                    );
                  },
                  separatorBuilder: (context, position) {
                    return Divider();
                  },
                )
            ),
          ],
        ),
      ),
    );
  }
  Future<void> _saveCity(City city) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    var _cities = await preferences.getStringList('favourite') ?? [];
    _cities.add(jsonEncode(city.toJson()));
    print("Count: " + _cities.length.toString());
    await preferences.setStringList('favourite', _cities);
  }
  Future<void> _selectCity(City city) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    var encode = jsonEncode(city.toJson());
    await preferences.setString('selected', encode);
  }
}