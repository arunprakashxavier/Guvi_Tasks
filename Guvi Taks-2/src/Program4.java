/* 1.4) Define a base class Person with attributes name and age.

Create a subclass Employee that inherits from Person and adds attributes like employeeID and salary.

Use the super keyword to initialize the Person attributes in the Employee constructor.  */
// Base class
class personX {
    // Attributes of Person
    protected String name;
    protected int age;

    // Constructor to initialize Person attributes
    public personX(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Method to display Person information
    public void displayPersonInfo() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }
}

// Subclass Employee that inherits from Person
class Employee extends personX {
    // Attributes of Employee
    private int employeeID;
    private double salary;

    // Constructor to initialize Employee attributes using super for Person attributes
    public Employee(String name, int age, int employeeID, double salary) {
        super(name, age); // Calling the constructor of the Person class
        this.employeeID = employeeID;
        this.salary = salary;
    }

    // Method to display Employee information
    public void displayEmployeeInfo() {
        // Displaying Person information
        displayPersonInfo();
        // Displaying Employee-specific information
        System.out.println("Employee ID: " + employeeID);
        System.out.println("Salary: " + salary);
    }
}

// Main class to test the implementation
public class Program4 {
    public static void main(String[] args) {
        // Creating an Employee object
        Employee employee = new Employee("Priya Arun", 23, 2629, 50000.0);

        // Displaying Employee information
        employee.displayEmployeeInfo();
    }
}
