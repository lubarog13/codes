package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println(BinarySearch.binary_search(new int[]{1, 2, 3, 4, 5}, 4));
        System.out.println(SelectionSort.selectionSort(new ArrayList<>(Arrays.asList(4,6,7,2,3))));
    }
}
