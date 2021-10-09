class City {
  final String name;
  final List<String> local_name;
  final double lat;
  final double lon;
  final String country;
  final String state;
  City({
    required this.name,
    required this.local_name,
    required this.lat,
    required this.lon,
    required this.country,
    required this.state,
  });
  factory City.fromJson(Map<String, dynamic> json) {
    return City(name: json['name'], local_name: json['local_name'], lat: json['lat'], lon: json['lon'], country: json['country'], state: json['state']);
  }
}