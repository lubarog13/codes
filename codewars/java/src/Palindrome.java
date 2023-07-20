
// Небольшой эксперимент по скорости нахождения полиндромов разными способами
public class Palindrome {
    public static void main(String[] args) {
        String longPalindrome = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        long startTime = System.nanoTime();
        System.out.print("1: " + is_palindrome1(longPalindrome));
        System.out.println(" " + (System.nanoTime() - startTime));
        startTime = System.nanoTime();
        System.out.print("2: " + is_palindrome2(longPalindrome));
        System.out.println(" " + (System.nanoTime() - startTime));
        startTime = System.nanoTime();
        System.out.print("3: " + is_palindrome3(longPalindrome));
        System.out.println(" " + (System.nanoTime() - startTime));
    }

    public static boolean is_palindrome1(String str) {
        int length = str.length();
        int median = (int) Math.floor((double) length / 2);
        for (int i = 0; i<median; i++) {
            if(str.charAt(i)!=str.charAt(length - i -1)) return false;
        }
        return true;
    }

    public static boolean is_palindrome2(String str) {
        StringBuilder reverseString = new StringBuilder();
        for (int i =str.length()-1; i>=0; i--) {
            reverseString.append(str.charAt(i));
        }
        return reverseString.toString().equals(str);
    }

    public static boolean is_palindrome3(String str) {
        return new StringBuilder(str).reverse().toString().equals(str);
    }
}
