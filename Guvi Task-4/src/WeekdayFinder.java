/*Store name of weekdays in an array (starting from "Sunday" at 0 index). Ask day position
from user and print day name. Handle array index out of bound exception and give proper
message if user enters day index outside range (0-6).*/


import java.util.Scanner;

public class WeekdayFinder {
    public static void main(String[] args) {
        // Array storing names of weekdays starting from Sunday at index 0
        String[] weekdays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        // Scanner to take user input
        Scanner scanner = new Scanner(System.in);

        // Ask for the day position
        System.out.print("Enter a day position (0 for Sunday, 6 for Saturday): ");
        int dayPosition = scanner.nextInt();

        try {
            // Print the day corresponding to the entered position
            System.out.println("The day is: " + weekdays[dayPosition]);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Handle invalid input
            System.out.println("Error: Invalid day position! Please enter a number between 0 and 6.");
        } finally {
            // Close the scanner
            scanner.close();
        }
    }
}
