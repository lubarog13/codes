class Weather {
  final String name;
  final String main_;
  final double temp ;
  final double wind ;
  final int pressure;
  final int humidity;
  final String dt_txt;

  Weather({required this.name, required this.main_, required this.temp, required this.wind, required this.pressure, required this.humidity, required this.dt_txt});

  factory Weather.fromJson(Map<String,dynamic> json) {
    return Weather(name: json['name'], main_: json['weather'][0]['main'], 
    temp: double.parse((json['main']['temp'] - 273.15).toStringAsFixed(2)) ,
     wind: double.parse(json['wind']['speed'].toString()), pressure: int.parse(json['main']['pressure'].toString()), 
     humidity: int.parse(json['main']['humidity'].toString()), dt_txt: '');
  }

  factory Weather.fromSpecificJson(Map<String,dynamic> json, int index) {
    return Weather(name: json['city']['name'], main_: json['list'][index]['weather'][0]['main'], 
    temp: double.parse((json['list'][index]['main']['temp'] - 273.15).toStringAsFixed(2)), 
    wind: double.parse(json['list'][index]['wind']['speed'].toString()), pressure: int.parse(json['list'][index]['main']['pressure'].toString()), 
    humidity: int.parse(json['list'][index]['main']['humidity'].toString()), dt_txt: json['list'][index]['dt_txt']);
  }
  String pictureMain(){
    switch(this.main_){
      case 'Clear': 
        return "assets/images/sun.png";
      case 'Clouds':
        return "assets/images/partly_cloudy.png";
      case 'Rain':
        return "assets/images/rain.png";
      case 'Light':
        return "assets/images/light.png";
      default:
        return "assets/images/sun.png"; 
    }
  }
}