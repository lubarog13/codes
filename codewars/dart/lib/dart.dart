import 'dart:math';

int calculate() {
  return 6 * 7;
}

bool comp(List<int> a1, List<int> a2) {
  var a3 = a1.map((e) => e * e).toList();
  a3.sort();
  a2.sort();
  if(a2.length!=a3.length) return false;
  for (int i = 0;i<a3.length; i++) {
    if (a2[i]!=a3[i]) return false;
  }
  return true;
}

int findNb(int m) {
  int sum = 0, k=0;
  while (sum<m) {
    k++;
    sum += pow(k, 3) as int;
  }
  return sum==m? k : -1;
}

int find(List integers) {
  for (int i=0; i<integers.length; i++) {
      var smallIndex = i==0? 2: i-1;
      var bigIndex = i==integers.length-1? integers.length -3 : i + 1;
      if ((integers[i]%2)!=(integers[smallIndex]%2) && (integers[i]%2)!=(integers[bigIndex]%2)) return integers[i];
  }
  return -1;
}

String rgb(int r, int g, int b)
{
  return '${max(min(r, 255), 0).toRadixString(16).toString().padLeft(2, '0')}${max(min(g, 255), 0).toRadixString(16).toString().padLeft(2, '0')}${max(min(b, 255), 0).toRadixString(16).toString().padLeft(2, '0')}'.toUpperCase();
}

Map<String, int> hexToRGB(String hex) {
 return {'r': int.parse(hex.substring(1, 3), radix: 16), 'g': int.parse(hex.substring(3, 5), radix: 16), 'b': int.parse(hex.substring(5, 7), radix: 16)};
}