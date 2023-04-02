import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SumDigPower {

    public static List<Long> sumDigPow(long a, long b) {
        List<Long> result = new ArrayList<>();
        for (long i = a; i<=b; i++) {
            if (i<10) {
                result.add(i);
                continue;
            }
            int[] ints = Arrays.stream(Long.toString(i).split("")).mapToInt(Integer::parseInt).toArray();
            long k = 0;
            for (int j = 0; j<ints.length; j++) {
                k += Math.pow(ints[j], j+1);
            }
            if (k==i) {
                result.add(k);
            }
        }
        return result;
    }
}