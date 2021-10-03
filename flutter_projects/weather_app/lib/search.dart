import 'package:flutter/material.dart';
import './main.dart';
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