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