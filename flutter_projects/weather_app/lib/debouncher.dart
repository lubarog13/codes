import 'dart:async';

import 'dart:ui';

class Debouncer {
  final int milliseconds;
  Function? action;
  Timer? _timer;

  Debouncer({required this.milliseconds});

  run(Function action, String s) {
    if (null != _timer) {
      _timer?.cancel();
    }
    _timer = Timer(Duration(milliseconds: milliseconds), action(s));
  }
}