/*  3. You are a teacher in school In your class there are 10 students, you have decided to give
special gifts to those students whose names start with "A". you are asked to separate those
students with the help of a java program.

Requirement:
    * Use List interface to store the student name
    * Use a lambda expression and the Stream API to filter the students */


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SpecialGiftForA {
    public static void main(String[] args) {
        // List of student names
        List<String> students = Arrays.asList("Arun", "Priya", "Aari", "Barath", "Aadhavan", "David",
                "Hari", "Amudha", "Durga", "Ravi");

        // Filter students whose names start with "A"
        List<String> specialGiftStudents = students.stream()
                .filter(name -> name.startsWith("A")) // Lambda expression to filter names starting with "A"
                .collect(Collectors.toList()); // Collect results into a List

        // Print students eligible for special gifts
        System.out.println("Students eligible for special gifts: " + specialGiftStudents);
    }
}
