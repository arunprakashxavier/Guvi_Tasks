/* 4. Rajesh has been given a task to create an app which takes the user's birthdate as input and
calculates their age, you have to help him to build this app using the java.time.LocalDate class.

    * Input: Enter your birthdate (yyyy-mm-dd): 1990-05-15

    * Output: Your age is: 33 years, 4 months,and 13 Days.*/


import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AgeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for their birthdate
        System.out.print("Enter your birthdate (yyyy-MM-dd): ");
        String birthdateInput = scanner.nextLine();

        // Parse input date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdate = LocalDate.parse(birthdateInput, formatter);

        // Get current date
        LocalDate currentDate = LocalDate.now();

        // Calculate the period between birthdate and current date
        Period age = Period.between(birthdate, currentDate);

        // Display the result
        System.out.printf("Your age is: %d years, %d months, and %d days.%n",
                age.getYears(), age.getMonths(), age.getDays());

        scanner.close();
    }
}

/*If the input is 1990-05-15 and the current date is 2023-09-28, the output will be:
        Your age is: 33 years, 4 months, and 13 days.                                */