import java.util.Arrays;

public class SpinWords {

    public static String spinWords(String sentence) {
        return String.join(" ", Arrays.stream(sentence.split(" ")).map(word -> {
            if (word.length() >= 5) {
                return new StringBuilder(word).reverse().toString();
            }
            return word;
        }).toArray(String[]::new));
    }
}