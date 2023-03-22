public class XO {

    public static boolean getXO (String str) {
        return str.replaceAll("[^xX]", "").length() == str.replaceAll("[^oO]", "").length();
    }
}
