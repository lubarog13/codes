public class Maskify {
    public static String maskify(String str) {
        int count = str.length() > 4 ? str.length() - 4 : 0;
        return "#".repeat(count) + str.substring(count);
    }
}
