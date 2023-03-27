package algorithms;

import java.util.ArrayList;
import java.util.List;

public class SelectionSort {
    static int findSmallest(List<Integer> list) {
        int min = list.get(0);
        int smallest_index = 0;
        for (int i = 0; i<list.size(); i++) {
            if(list.get(i)<min) {
                min = list.get(i);
                smallest_index = i;
            }
        }
        return smallest_index;
    }

    static List<Integer> selectionSort(List<Integer> arr) {
        List<Integer> newArr = new ArrayList<>(0);
        List<Integer> copyArr = new ArrayList<>(arr);
        for (int i=0; i<arr.size();i++) {
            int smallestIndex = findSmallest(copyArr);
            newArr.add(copyArr.get(smallestIndex));
            copyArr.remove(smallestIndex);
        }
        return newArr;
    }


}
