class City {
  final String name;
  var local_names;
  final double lat;
  final double lon;
  final String country;
  final String? state;
  City({
    required this.name,
    required this.local_names,
    required this.lat,
    required this.lon,
    required this.country,
    required this.state,
  });
  factory City.fromJson(Map<String, dynamic> json) {
    return City(name: json['name'], 
    local_names: json['local_names'],
     lat: json['lat'], lon: json['lon'], country: json['country'], state: json['state']);
  }
}