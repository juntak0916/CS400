public class RadixSorter {

        /**
         * TODO: Execute radix sort in-place on the given array.
        **/
        public static void radixSort(int array[]) {
        	int Max= getMax(array);
        	int cntMaxDigit=0;
        	while(Max > 0) {
        		Max/=10;
        		cntMaxDigit++;
        	}
        	int Digit=0;
        	while(Digit < cntMaxDigit) {
        		countingSort(array,(int)Math.pow(10, Digit++));
        	}
        }

        /**
         * Does counting sort on the given array for the given digit.
         * digit is 10^(digit to sort by)
        **/
        public static void countingSort(int array[], int digit) {
                // Array to temporarily store sorted array
                int output[] = new int[array.length];
                // Stores how many elements we have of each digit
                int count[] = new int[10];

                // Count how many elements we have of each digit
                for (int num : array) {
                        count[(num / digit) % 10]++;
                }

                // Count how many elements we have of each digit less than or equal to itself
                for (int i = 1; i < 10; i++) {
                        count[i] += count[i - 1];
                }

                // Sort the array by digit
                for (int i = array.length - 1; i >= 0; i--) {
                        int num = (array[i] / digit) % 10;
                        output[count[num] - 1] = array[i];
                        count[num]--;
                }

                // Copy output array into original array
                for (int i = 0; i < array.length; i++) {
                        array[i] = output[i];
                }
        }

        /**
         * Get the maximal element of a given array
        **/
        public static int getMax(int array[]) {
                int max = array[0];
                for (int num : array) {
                        max = num > max ? num : max;
                }
                return max;
        }

        public static void main(String[] args) {
        		int arr[]= {88, 87, 91, 0, 12, 3, 9, 0, 3, 5, 5};
        		radixSort(arr);
                for(int i=0;i<arr.length;i++)
                	System.out.print(arr[i]+" ");
        }
}