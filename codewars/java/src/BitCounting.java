public class BitCounting {

    public static int countBits(int n){
        return Integer.toString(n, 2).replaceAll("0", "").length();
    }

}