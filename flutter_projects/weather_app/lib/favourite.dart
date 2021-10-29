import 'dart:convert';
import 'package:favorite_button/favorite_button.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:weather_app/themedata.dart';
import './main.dart';
import 'City.dart';

class FavouriteScreen extends StatefulWidget {
 @override
  FavouriteState createState() => FavouriteState();
}
class FavouriteState extends State {
  late Styles styles;
  late bool isDarkTheme;
  @override
  void initState(){
    _getFavourite().then((value) => {});
    super.initState();
  }
   List<City> city = [];
  @override
  Widget build(BuildContext context) {
    isDarkTheme = MediaQuery.of(context).platformBrightness == Brightness.dark;
    styles = Styles(isDarkTheme);
    styles.initColors();
    return Scaffold(
      backgroundColor:styles.backgroundColor,
        body: Column(
          children: [
            Container(
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
                            'Избранное',
                            style: GoogleFonts.manrope(fontSize: 20, fontWeight: FontWeight.bold, color: styles.textColor),
                          ),
                        ),
                      ],
                    ),
                    ConstrainedBox(
                        constraints: BoxConstraints(minHeight: 10, maxHeight: MediaQuery.of(context).size.height -100),
                        child: ListView.builder(
                          itemCount: city.length,
                          itemBuilder: (BuildContext context, int index){
                            return Container(
                              width: MediaQuery.of(context).size.width - 30,
                              height: 40,
                              padding: EdgeInsets.only(left: 20),
                              decoration: BoxDecoration(
              color: styles.backgroundColor,
              borderRadius: const BorderRadius.only(
                          topRight: Radius.circular(10.0),
                          bottomRight: Radius.circular(10.0),
                          topLeft: Radius.circular(10.0),
                          bottomLeft: Radius.circular(10.0)),
              boxShadow: [BoxShadow(
                color: Colors.grey.withOpacity(0.5),
                blurRadius: 4,
                spreadRadius: 5,
                offset: const Offset(4, -2),
              )
              ],
            ), 
                              alignment: Alignment.topLeft,
                              margin: EdgeInsets.all(20),
                              child: Row (
                                crossAxisAlignment: CrossAxisAlignment.center,
                                children: [
                                  Expanded(
                                    flex: 5,
                                    child:
                                    GestureDetector(
                                      onTap: () => { _selectCity(city[index]).then((value) => _navigateToPreviousScreen(context))},
                                    child: Text(
                                      city[index].local_names.toString() + ' ' + city[index].country ,
                                      style: GoogleFonts.manrope(
                                          fontSize: 13,
                                          color: styles.textColor,
                                          fontWeight: FontWeight.bold
                                      ),
                                    ),
                                    ),
                                  ),
                                  Container(
                                    alignment: Alignment.centerRight,
                                    decoration:  BoxDecoration(
                                      borderRadius: const BorderRadius.only(
                          topRight: Radius.circular(10.0),
                          bottomRight: Radius.circular(10.0),
                                      ),
                                      color: styles.closebuttonColor,
                                    ),
                                    child: IconButton(
                                      onPressed: () => { _deleteFavourite(city[index])},
                                      icon:  Icon(
                                        Icons.close,
                                        size: 30,
                                        color: styles.textColor,
                                        )
                                    )
                                    ),
                                ],
                              ),
                            );
                          },
                        )
                    ),
          ],
        )
    )
    ]
    )
    );
  }
  void _navigateToPreviousScreen(BuildContext context) {
    Navigator.of(context).push(MaterialPageRoute(builder: (context) => MyHomePage(title: "Погода")));
  }
  Future<void> _getFavourite() async{
    print('ok');
      SharedPreferences preferences = await SharedPreferences.getInstance();
      var favourites = preferences.getStringList('favourite');
      setState(() {
        for (String favourite in favourites!){
        var decode = jsonDecode(favourite);
        print(decode['name']);
        city.add(new City(name: decode['name'], local_names: decode['local_names'], lat: decode['lat'], lon: decode['lon'], country: decode['country'], state: decode['state']));
      }
      });
      print(city.length);
  }
  Future<void> _deleteFavourite(City _city) async{
    SharedPreferences preferences = await SharedPreferences.getInstance();
    var favourites = preferences.getStringList('favourite');
    for (int i=0; i<favourites!.length; i++){
        var decode = jsonDecode(favourites[i]);
        if(_city.lat==decode['lat'] && _city.lon==decode['lon']){
          favourites.removeAt(i);
          break;
        }
    }
    await preferences.setStringList('favourite', favourites);
    setState(() {
      city.remove(_city);
    });
  }
  Future<void> _selectCity(City city) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    var encode = jsonEncode(city.toJson());
    await preferences.setString('selected', encode);
  }
}