import 'dart:convert';
import 'debouncher.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:flutter/material.dart';
import 'package:favorite_button/favorite_button.dart';
import 'package:http/http.dart' as http;
import './main.dart';
import 'City.dart';
class NewScreen extends StatefulWidget {

  @override
  _Search createState() => _Search();
}
class _Search extends State<NewScreen>{
  var _controller = TextEditingController(
  );
  final _city  = <City>[];
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
    _city.clear();
    _city.addAll(citys);
}

  @override
  Widget build(BuildContext context) {
    return Scaffold(
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
              icon: const Icon(
                Icons.arrow_back_ios,
                size: 20,
              ),
            ),
            Container(
              width: MediaQuery.of(context).size.width*0.80,
              height: 35.0,
              child: TextFormField(
                controller: _controller,
                onChanged: (string) {
                  setState(() {
                    _debouncer.run(callCity, string);
                  });
                },
                decoration: InputDecoration(
                  hintText: 'Введите название города...',
                  suffixIcon: IconButton(
                    onPressed: _controller.clear,
                    icon: Icon(Icons.clear),
                  ),
                ),
              ),
            ),
          ],
        ),
        ConstrainedBox(
        constraints: BoxConstraints(minHeight: 10, maxHeight: 400),
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
                      Text(
                        _city[index].name  + ' ' + _city[index].country,
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
                          valueChanged: () => {},
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
}