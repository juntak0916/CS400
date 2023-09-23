import java.util.Arrays;

public class SortPrinter {
        public static void main(String args[]) {
                RadixSorter sorter = new RadixSorter();

                int[][] myArrays = {
                        {5, 7, 3, 2, 8},
                        { 15, 5, 20, 12, 80, 49, 2},
                        {31,25,13,05,15,33,20,32,10}
                };

                for (int[] arr : myArrays) {
                        sorter.radixSort(arr);
                        System.out.println(Arrays.toString(arr));
                }
        }
}