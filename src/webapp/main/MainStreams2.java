package webapp.main;

import java.util.List;
import java.util.stream.Collectors;

public class MainStreams2 {
    public static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream()
                .filter(i -> (integers.stream().mapToInt(Integer::intValue).sum() % 2 != 0) == (i % 2 == 0))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Integer> integers1 = List.of(1, 2, 3, 4, 5, 6);
        List<Integer> integers2 = List.of(1, 2, 3, 4, 5, 6, 7);
        List<Integer> result1 = oddOrEven(integers1);
        List<Integer> result2 = oddOrEven(integers2);

        System.out.println("Original List: " + integers1);
        System.out.println("Filtered List: " + result1);
        System.out.println("Original List: " + integers2);
        System.out.println("Filtered List: " + result2);
    }
}
