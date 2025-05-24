/*2. Write a program to check whether the Strings in the List are empty or not and print the list
having non-empty strings. If the given List is: Liststrings = Arrays.asList("abc", "", "bc", "efg",
"abcd","","jkl");*/


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NonEmptyStrings {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");

        // Filter out empty strings
        List<String> nonEmptyStrings = strings.stream()
                .filter(s -> !s.isEmpty())          /* filter(s -> !s.isEmpty()): This filters out empty strings by
                                                       retaining only those strings that are not empty */
                .collect(Collectors.toList());      /* collect(Collectors.toList()): This collects the filtered
                                                        elements into a List */

        System.out.println(nonEmptyStrings);
    }
}
