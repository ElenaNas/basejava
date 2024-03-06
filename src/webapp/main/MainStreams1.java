package webapp.main;

import java.util.Arrays;

public class MainStreams1 {
    public static void main(String[] args) {
        int[] values = {1, 2, 3, 3, 2, 3};
        System.out.println(minValue(values)); // Output: 123

        int[] values2 = {9, 8};
        System.out.println(minValue(values2)); // Output: 89

        int[] values3 = {3, 5, 2, 1}; //Output 1235
        System.out.println(minValue(values3));

        int[] values4 = {8, 8, 9, 1, 3, 5, 7};
        System.out.println(minValue(values4));//Output 135789
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .boxed()
                .sorted()
                .reduce(0, (a, b) -> a * 10 + b);
    }
}
