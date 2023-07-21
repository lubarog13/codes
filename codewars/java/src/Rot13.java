import java.util.ArrayList;
import java.util.List;

public class Rot13 {
    public static String rot13(String message) {
        List<Character> letters = new ArrayList<>(26);
        StringBuilder string = new StringBuilder(message);
        boolean uppercase;
        for (char c = 'a'; c<='z'; c++) {
            letters.add(c);
        }
        for (int i = 0; i<message.length(); i++) {
            char letter = message.charAt(i);
            uppercase = letter>='A' && letter <= 'Z';
            letter = Character.toLowerCase(letter);
            if (letter<'a' || letter > 'z') continue;
            int count = letter>'m'? letters.indexOf(letter) - 13 : letters.indexOf(letter) + 13;
            string.setCharAt(i, uppercase? Character.toUpperCase(letters.get(count)) : letters.get(count));
        }
        return string.toString();
    }
}