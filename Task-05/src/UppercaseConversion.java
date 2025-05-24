/* 1. Write a program using map() method, to convert a list of Strings into uppercase.If the given
List is: Stream names = Stream.of("aBc","d","ef"); */


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UppercaseConversion {
    public static void main(String[] args) {
        Stream<String> names = Stream.of("aBc", "d", "ef");

        // Convert each string to uppercase
        List<String> upperCaseNames = names
                .map(String::toUpperCase)      /* map(String::toUpperCase): This applies the toUpperCase() method to
                                                  each element in the stream, converting each string to uppercase */
                .collect(Collectors.toList()); /* collect(Collectors.toList()): This collects the transformed
                                                  elements into a List */

        System.out.println(upperCaseNames);
    }
}
