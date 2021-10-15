import 'dart:convert';
import 'package:favorite_button/favorite_button.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:shared_preferences/shared_preferences.dart';
import './main.dart';
import 'City.dart';

class FavouriteScreen extends StatefulWidget {
 @override
  FavouriteState createState() => FavouriteState();
}
class FavouriteState extends State {
  @override
  void initState(){
    print("Hello" + city.length.toString());
    _getFavourite().then((value) => {});
    super.initState();
  }
   List<City> city = [];
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Column(
          children: [
            Container(
              padding: EdgeInsets.only(top: 35),
              height: MediaQuery.of(context).size.height * 1/2 ,
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
                            'О разработчике',
                            style: GoogleFonts.manrope(fontSize: 20, fontWeight: FontWeight.bold),
                          ),
                        ),
                      ],
                    ),
                    ConstrainedBox(
                        constraints: BoxConstraints(minHeight: 10, maxHeight: MediaQuery.of(context).size.height / 3),
                        child: ListView.separated(
                          itemCount: city.length,
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
                                    Text(
                                      city[index].local_names.toString() + ' ' + city[index].country ,
                                      style: GoogleFonts.manrope(
                                          fontSize: 13,
                                          fontWeight: FontWeight.bold
                                      ),
                                    ),
                                  ),
                                  Container(
                                    margin: EdgeInsets.only(right: 20),
                                    alignment: Alignment.centerRight,
                                    child: StarButton(
                                      iconSize: 30,
                                      valueChanged: () =>
                                      {

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
      for (String favourite in favourites!){
        var decode = jsonDecode(favourite);
        print(decode['name']);
        city.add(new City(name: decode['name'], local_names: decode['local_names'], lat: decode['lat'], lon: decode['lon'], country: decode['country'], state: decode['state']));
      }
      print(city.length);
  }
}