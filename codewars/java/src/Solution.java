import java.util.Arrays;

public class Solution {

    public static int find(int[] array, int start, int value) {
        for(int i=start; i<array.length; i++)
            if(array[i] == value)
                return i;
        return -1;
    }

    public static int[] twoSum(int[] numbers, int target) {
        for(int i =0; i<numbers.length; i++) {
            int index = find (numbers, i+1,target - numbers[i]);
            if(index>=0) {
                return new int[]{i, index};
            }
        }
        return null;
    }
}