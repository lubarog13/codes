import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import './main.dart';
class AboutScreen extends StatelessWidget {
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
                    Container(
                      height: 50,
                      width: 220,
                      alignment: Alignment.center,
                      margin: EdgeInsets.only(top: MediaQuery.of(context).size.height / 4 - 30),
                      decoration: BoxDecoration(
                        color: Color.fromRGBO(226, 235, 255, 1),
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
                            fontWeight: FontWeight.bold
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
                  decoration: const BoxDecoration(
                      color: Color.fromRGBO(226, 235, 255, 1)
                  ),
                ),
                Container(
                  height: MediaQuery.of(context).size.height * 0.5 ,
                  width: MediaQuery.of(context).size.width,
                  decoration:  BoxDecoration(
                    color: Color.fromRGBO(226, 235, 255, 1),
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
                              fontWeight: FontWeight.bold
                          ),
                        ),

                      ),
                      Container(
                        margin: EdgeInsets.only(top: 8),
                        child: Text(
                          'Версия 1.0',
                          style: GoogleFonts.manrope(
                              color: Color.fromRGBO(74, 74, 74, 1),
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
                              color: Color.fromRGBO(74, 74, 74, 1),
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