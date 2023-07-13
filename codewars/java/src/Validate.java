import java.util.Arrays;

public class Validate{
    public static boolean validate(String n){
        int[] arr = Arrays.stream(n.split("")).mapToInt(Integer::parseInt).toArray();
        int sum = 0;
        for(int i=(arr.length-1); i>=0; i--) {
            if ((arr.length - i) % 2 == 0 ) {
                arr[i] = arr[i] * 2;
                arr[i] = arr[i] >= 10 ? arr[i] - 9 : arr[i];
            }
            sum+=arr[i];
        }
        return sum%10==0;
    }
}
