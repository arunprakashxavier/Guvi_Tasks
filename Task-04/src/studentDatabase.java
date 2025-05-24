/*Q1. Ramesh is developing a student management system for a university. In this system,
you have a Student class to represent student information. You are asked to help Ramesh
to handle exception which can be occurred into program according to following Scenarios:

    (*)class Student with attributes roll no, name, age and course. Initialize values through parameterized
constructor.

    (*)If the age of the student is not between 15 and 21 then generate a user-defined exception "AgeNot
WithinRangeException".

    (*)If a name contains numbers or special symbols, raise exception "NameNotValidException". Define the two
exception classes.*/


// Custom Exception for Age
class AgeNotWithinRangeException extends Exception {
    public AgeNotWithinRangeException(String message) {
        super(message);
    }
}

// Custom Exception for Name
class NameNotValidException extends Exception {
    public NameNotValidException(String message) {
        super(message);
    }
}

// Student Class
class Student {
    private int rollNo;
    private String name;
    private int age;
    private String course;

    // Constructor
    public Student(int rollNo, String name, int age, String course) throws AgeNotWithinRangeException, NameNotValidException {
        this.rollNo = rollNo;
        this.name = name;
        this.age = age;
        this.course = course;
        validateStudent();  // Validate age and name
    }

    // Method to validate age and name
    private void validateStudent() throws AgeNotWithinRangeException, NameNotValidException {
        // Check if age is within the specified range
        if (age < 15 || age > 21) {
            throw new AgeNotWithinRangeException("Age is not within the range of 15 to 21");
        }

        // Check if name contains only alphabets
        if (!name.matches("[a-zA-Z]+")) {
            throw new NameNotValidException("Name contains invalid characters. Only alphabets are allowed.");
        }
    }

    // Display Student Information
    public void displayInfo() {
        System.out.println("Roll No: " + rollNo + ", Name: " + name + ", Age: " + age + ", Course: " + course);
    }
}

// Main Class to Test the Student Class
public class studentDatabase {
    public static void main(String[] args) {
        // Student 1: Valid data
        try {
            Student student1 = new Student(101, "Arun", 18, "Computer Science");
            student1.displayInfo();
        } catch (AgeNotWithinRangeException | NameNotValidException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        // Student 2: Invalid age
        try {
            Student student2 = new Student(102, "Priya", 22, "Mathematics"); // Age outside range
            student2.displayInfo();
        } catch (AgeNotWithinRangeException | NameNotValidException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        // Student 3: Invalid name with numbers
        try {
            Student student3 = new Student(103, "Dharun123", 19, "Physics"); // Invalid characters in name
            student3.displayInfo();
        } catch (AgeNotWithinRangeException | NameNotValidException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        // Student 4: Valid data
        try {
            Student student4 = new Student(104, "Kavi", 19, "Biology");
            student4.displayInfo();
        } catch (AgeNotWithinRangeException | NameNotValidException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
