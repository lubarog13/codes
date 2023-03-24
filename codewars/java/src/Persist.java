import java.util.List;

class Persist {
    public static int persistence(long n) {
        String str = Long.valueOf(n).toString();
        int counter = 0;
        while (str.length() > 1) {
            int currentM = 1;
            List<String> arr = List.of(str.split(""));
            for (String i : arr) {
                currentM *= Integer.parseInt(i);
            }
            str = Integer.toString(currentM);
            counter++;
        }
        return counter;
    }
}