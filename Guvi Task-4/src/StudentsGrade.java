/*Q4. Create a HashMap where keys are student names (strings) and values are their corresponding
grades (integers). Create methods to add a new student, remove a student, and Display up a student's
grade by name.*/


import java.util.HashMap;
import java.util.Scanner;

public class StudentsGrade {
    // HashMap to store student names and their grades
    private HashMap<String, Integer> studentGrades;

    // Constructor to initialize the HashMap
    public StudentsGrade() {
        studentGrades = new HashMap<>();
    }

    // Method to add a new student
    public void addStudent(String name, int grade) {
        studentGrades.put(name, grade);
        System.out.println(name + " has been added with grade: " + grade);
    }

    // Method to remove a student
    public void removeStudent(String name) {
        if (studentGrades.containsKey(name)) {
            studentGrades.remove(name);
            System.out.println(name + " has been removed.");
        } else {
            System.out.println(name + " does not exist in the records.");
        }
    }

    // Method to display a student's grade by name
    public void displayGrade(String name) {
        if (studentGrades.containsKey(name)) {
            System.out.println(name + "'s grade is: " + studentGrades.get(name));
        } else {
            System.out.println(name + " is not found in the records.");
        }
    }

    public static void main(String[] args) {
        // Creating the StudentGrades object
        StudentsGrade studentGrades = new StudentsGrade();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Displaying the menu
            System.out.println("\nStudent Grades Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Display Student Grade");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (option) {
                case 1:
                    // Add a student
                    System.out.print("Enter student name: ");
                    String nameToAdd = scanner.nextLine();
                    System.out.print("Enter student grade: ");
                    int gradeToAdd = scanner.nextInt();
                    studentGrades.addStudent(nameToAdd, gradeToAdd);
                    break;

                case 2:
                    // Remove a student
                    System.out.print("Enter student name to remove: ");
                    String nameToRemove = scanner.nextLine();
                    studentGrades.removeStudent(nameToRemove);
                    break;

                case 3:
                    // Display student grade
                    System.out.print("Enter student name to display grade: ");
                    String nameToDisplay = scanner.nextLine();
                    studentGrades.displayGrade(nameToDisplay);
                    break;

                case 4:
                    // Exit
                    System.out.println("Exiting program...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}
