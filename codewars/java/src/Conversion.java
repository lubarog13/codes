import java.util.HashMap;
import java.util.Map;

public class Conversion {

    public String solution(int n) {
        int[] decimals = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman    = new String[] {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        String resultString = "";
        for (int i=0; i< decimals.length; i++) {
            while (n >= decimals[i]) {
                resultString += roman[i];
                n -= decimals[i];
            }
        }
        return resultString;
    }
}