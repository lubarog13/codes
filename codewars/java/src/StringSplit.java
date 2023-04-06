public class StringSplit {
    public static String[] solution(String s) {
        String copy = s + (s.length()%2==0? "":"_");
        String[] res = new String[copy.length() / 2];
        for(int i = 0; i<copy.length(); i+=2) {
            res[i/2] = copy.substring(i, i+2);
        }
        return res;
    }
}
