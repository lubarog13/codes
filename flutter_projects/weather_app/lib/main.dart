
import 'package:flutter/material.dart';

void main() {
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
  var _scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
        key: _scaffoldKey,
        backgroundColor: Colors.lightBlue,
        drawer: Drawer(
          child: ListView(
            padding: EdgeInsets.zero,
            children: const <Widget>[
              Padding(padding: EdgeInsets.only(top: 30, left: 20, bottom: 20),
                child: Text(
                  'Weather App',
                  style: TextStyle(
                    color: Colors.black,
                    fontWeight: FontWeight.bold,
                    fontSize: 24,
                  ),
                ),
              ),

              ListTile(
                leading: Icon(Icons.settings),
                title: Text('Настройки'),
              ),
              ListTile(
                leading: Icon(Icons.favorite_outline),
                title: Text('Избранное'),
              ),
              ListTile(
                leading: Icon(Icons.person),
                title: Text('О приложении'),
              ),
            ],
          ),
        ),
        body: Stack(
          children: <Widget>[
            Container(
              height: MediaQuery.of(context).size.height,
              width: MediaQuery.of(context).size.width,
              decoration: const BoxDecoration(
                image: DecorationImage(
                  image: AssetImage(
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
                children: const <Widget>[
                  Text(
                    '10 \u00B0C',
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
                children: const <Widget>[
                  Text(
                    '23 сент. 2021',
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
                        color: Colors.blueAccent,
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
                          child: const Text(
                            'Санкт-Петербург',
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
                            onPressed: () {},
                            color: Colors.blueAccent,
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
                height: 270,
                child: Container (
                    decoration: const BoxDecoration(
                      color: Color(0xFFDAE5EA),
                      borderRadius: BorderRadius.only(
                          topRight: Radius.circular(20.0),
                          bottomRight: Radius.circular(0.0),
                          topLeft: Radius.circular(20.0),
                          bottomLeft: Radius.circular(0.0)),
                    ),
                    child: SizedBox(
                      width: MediaQuery.of(context).size.width,
                      child: Column(
                        children: [
                          Column(
                            children: [
                              Container(
                                  width: 80,
                                  height: 3.3,
                                  margin: EdgeInsets.only(top: 10),
                                  decoration: BoxDecoration(
                                    color: Colors.blue,
                                  )
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
                                      decoration: const BoxDecoration(
                                        color: Color(0xFFDAE5EA),
                                        borderRadius: BorderRadius.only(
                                            topRight: Radius.circular(10.0),
                                            bottomRight: Radius.circular(10.0),
                                            topLeft: Radius.circular(10.0),
                                            bottomLeft: Radius.circular(10.0)
                                        ),
                                        boxShadow: [
                                          BoxShadow(
                                            color: Colors.grey,
                                            spreadRadius: 1,
                                            blurRadius: 3,
                                            offset: Offset(0, 2), // changes position of shadow
                                          ),
                                        ],
                                      ),
                                      margin: const EdgeInsets.only(top:20, left: 45, bottom: 20),
                                      child: Column (
                                        children: const [
                                          Text(
                                            '06:00',
                                            style: TextStyle(
                                                fontSize: 18.0,
                                                fontFamily: 'Roboto',
                                                color: Colors.black
                                            ),
                                          ),
                                          SizedBox(
                                            height: 80,
                                            width: 40,
                                            child: Image(
                                                image: AssetImage("assets/images/light.png"),
                                            width: 40,
                                            height: 40,)
                                          ),
                                          Text(
                                            '10 \u00B0C',
                                            style: TextStyle(
                                                fontSize: 20.0,
                                                fontFamily: 'Roboto',
                                                color: Colors.black
                                            ),
                                          ),

                                        ],
                                      )
                                  ),
                                  Container(
                                      height: 120,
                                      width: 70,
                                      decoration: const BoxDecoration(
                                        color: Color(0xFFDAE5EA),
                                        borderRadius: BorderRadius.only(
                                            topRight: Radius.circular(10.0),
                                            bottomRight: Radius.circular(10.0),
                                            topLeft: Radius.circular(10.0),
                                            bottomLeft: Radius.circular(10.0)
                                        ),
                                        boxShadow: [
                                          BoxShadow(
                                            color: Colors.grey,
                                            spreadRadius: 1,
                                            blurRadius: 3,
                                            offset: Offset(0, 2), // changes position of shadow
                                          ),
                                        ],
                                      ),
                                      margin: const EdgeInsets.only(top:20, left: 45, bottom: 20),
                                      child: Column (
                                        children: const [
                                          Text(
                                            '12:00',
                                            style: TextStyle(
                                                fontSize: 18.0,
                                                fontFamily: 'Roboto',
                                                color: Colors.black
                                            ),
                                          ),
                                          SizedBox(
                                            height: 80,
                                              width: 40,
                                              child: Image(
                                                image: AssetImage("assets/images/sun.png"),
                                                width: 40,
                                                height: 40,)
                                          ),
                                          Text(
                                            '10 \u00B0C',
                                            style: TextStyle(
                                                fontSize: 20.0,
                                                fontFamily: 'Roboto',
                                                color: Colors.black
                                            ),
                                          ),

                                        ],
                                      )
                                  ),
                                  Container(
                                      height: 120,
                                      width: 70,
                                      decoration: const BoxDecoration(
                                        color: Color(0xFFDAE5EA),
                                        borderRadius: BorderRadius.only(
                                            topRight: Radius.circular(10.0),
                                            bottomRight: Radius.circular(10.0),
                                            topLeft: Radius.circular(10.0),
                                            bottomLeft: Radius.circular(10.0)
                                        ),
                                        boxShadow: [
                                          BoxShadow(
                                            color: Colors.grey,
                                            spreadRadius: 1,
                                            blurRadius: 3,
                                            offset: Offset(0, 2), // changes position of shadow
                                          ),
                                        ],
                                      ),
                                      margin: const EdgeInsets.only(top:20, left: 45, bottom: 20),
                                      child: Column (
                                        children: const [
                                          Text(
                                            '18:00',
                                            style: TextStyle(
                                                fontSize: 18.0,
                                                fontFamily: 'Roboto',
                                                color: Colors.black
                                            ),
                                          ),
                                          SizedBox(
                                            height: 80,
                                              width: 40,
                                              child: Image(
                                                image: AssetImage("assets/images/rain.png"),
                                                width: 40,
                                                height: 40,)
                                          ),
                                          Text(
                                            '10 \u00B0C',
                                            style: TextStyle(
                                                fontSize: 20.0,
                                                fontFamily: 'Roboto',
                                                color: Colors.black
                                            ),
                                          ),

                                        ],
                                      )
                                  ),
                                  Container(
                                      height: 120,
                                      width: 70,
                                      decoration: const BoxDecoration(
                                        color: Color(0xFFDAE5EA),
                                        borderRadius: BorderRadius.only(
                                            topRight: Radius.circular(10.0),
                                            bottomRight: Radius.circular(10.0),
                                            topLeft: Radius.circular(10.0),
                                            bottomLeft: Radius.circular(10.0)
                                        ),
                                        boxShadow: [
                                          BoxShadow(
                                            color: Colors.grey,
                                            spreadRadius: 1,
                                            blurRadius: 3,
                                            offset: Offset(0, 2), // changes position of shadow
                                          ),
                                        ],
                                      ),
                                      margin: const EdgeInsets.only(top:20, left: 45, bottom: 20, right: 45),
                                      child: Column (
                                        children: const [
                                          Text(
                                            '00:00',
                                            style: TextStyle(
                                                fontSize: 18.0,
                                                fontFamily: 'Roboto',
                                                color: Colors.black
                                            ),
                                          ),
                                          SizedBox(
                                            height: 80,
                                              width: 40,
                                              child: Image(
                                                image: AssetImage("assets/images/much_rain.png"),
                                                width: 40,
                                                height: 40,)
                                          ),
                                          Text(
                                            '10 \u00B0C',
                                            style: TextStyle(
                                                fontSize: 20.0,
                                                fontFamily: 'Roboto',
                                                color: Colors.black
                                            ),
                                          ),

                                        ],
                                      )
                                  ),
                                ]
                            ),
                          ),
                          OutlinedButton(
                            onPressed: () {},
                            child: const Text('Прогноз на неделю'),
                          ),
                        ],
                      ),
                    )
                )
            ),
          ],
        )
      // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
  void _navigateToNextScreen(BuildContext context) {
    Navigator.of(context).push(MaterialPageRoute(builder: (context) => NewScreen()));
  }
}
class NewScreen extends StatelessWidget {
  var _controller = TextEditingController();
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        margin: EdgeInsets.only(top: 35),
        height: 35.0,
        width: MediaQuery.of(context).size.width,
        child: Row(
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
      ),
    );
  }
  void _navigateToPreviousScreen(BuildContext context) {
    Navigator.of(context).push(MaterialPageRoute(builder: (context) => MyHomePage(title: "Погода")));
  }
}
