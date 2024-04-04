package resumes.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        int[] values = {1, 2, 3, 3, 2, 3};
        System.out.println(minValue(values)); // Output: 123

        int[] values2 = {9, 8};
        System.out.println(minValue(values2)); // Output: 89

        int[] values3 = {3, 5, 2, 1}; //Output 1235
        System.out.println(minValue(values3));

        int[] values4 = {8, 8, 9, 1, 3, 5, 7};
        System.out.println(minValue(values4));//Output 135789

        List<Integer> integers1 = List.of(1, 2, 3, 4, 5, 6);
        List<Integer> integers2 = List.of(1, 2, 3, 4, 5, 6, 7);
        List<Integer> result1 = oddOrEven(integers1);
        List<Integer> result2 = oddOrEven(integers2);

        System.out.println("Original List: " + integers1);
        System.out.println("Filtered List: " + result1);
        System.out.println("Original List: " + integers2);
        System.out.println("Filtered List: " + result2);
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> a * 10 + b);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(Integer::intValue).sum();
        return integers.stream()
                .filter(num -> (sum % 2 == 0) == (num % 2 != 0))
                .collect(Collectors.toList());
    }
}
