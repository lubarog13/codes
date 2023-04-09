import java.util.HashMap;
import java.util.Map;

public class PangramChecker {
    public static boolean check(String sentence){
        Map<Character, Boolean> letters = new HashMap<>();
        int lettersCount = 0;
        String copyString = sentence.toLowerCase();
        for(char a = 'a'; a<='z'; a++) {
            letters.put(a, false);
        }
        for (int i=0; i<sentence.length(); i++) {
            char ch = copyString.charAt(i);
            if(letters.get(ch)!=null && !letters.get(ch)) {
                lettersCount++;
                letters.put(ch, true);
            }
        }
        return lettersCount==letters.size();
    }
}