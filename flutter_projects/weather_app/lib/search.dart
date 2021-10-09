import 'dart:convert';
import 'debouncher.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import './main.dart';
import 'City.dart';
class NewScreen extends State {
  var _controller = TextEditingController(

  );
  final _city  = <City>[];
  final _debouncer = Debouncer(milliseconds: 500);
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
                    callCity(string);
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
        constraints: BoxConstraints(minHeight: 10, maxHeight: 450),
        child: ListView.separated(
          itemCount: _city.length,
          itemBuilder: (BuildContext context, int index){
              return Container(
                width: MediaQuery.of(context).size.width - 30,
                padding: EdgeInsets.only(left: 30),
                height: 20,
                child: Row (
                  children: [
                      Text(
                        _city[index].name
                      )
                  ],
                  ),
              );
          },
           separatorBuilder: (context, position) {
        return Container(
          height: 10,
        );
      },
          )
        ),
          ],
        ),
      ),
    );
  }
  void _navigateToPreviousScreen(BuildContext context) {
    Navigator.of(context).push(MaterialPageRoute(builder: (context) => MyHomePage(title: "Погода")));
  }
  List<City> parseCities(String responseBody) {
  final parsed = jsonDecode(responseBody).cast<Map<String, dynamic>>();

  return parsed.map<City>((json) => City.fromJson(json)).toList();
}
  Future<List<City>> fetchCity(String q) async {
  final response = await http
      .get(Uri.parse('http://api.openweathermap.org/geo/1.0/direct?q=$q&limit=5&appid=728abff1d97171ab28a008e3e5800944'));

  if (response.statusCode == 200) {
    // If the server did return a 200 OK response,
    // then parse the JSON.
    return parseCities(response.body);
  } else {
    // If the server did not return a 200 OK response,
    // then throw an exception.
    throw Exception('Failed to load album');
  }
}
void callCity(String q) async {
    _city.addAll(fetchCity(q) as List<City>);
}
}