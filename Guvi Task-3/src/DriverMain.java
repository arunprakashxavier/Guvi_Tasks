/*  2. Create Interface Taxable with members sales Tax=7% and income Tax-10.5%. create abstract method calcTax().
a. Create class Employee(empId,name, salary) and implement Taxable to calculate mcome Tax on yearly salary.
b. Create class Product(pid,price,quantity) and implement Taxable to calculate sales Tax on unit price of product.
C. Create class for main method(Say DriverMain), accept employee information and a product information from user and print income tax and sales tax respectively */

import java.util.Scanner;

// Define the interface Taxable
interface Taxable {
    double SALES_TAX = 0.07; // 7% sales tax
    double INCOME_TAX = 0.105; // 10.5% income tax

    double calcTax(); // Abstract method for calculating tax
}

// Employee class implementing Taxable to calculate income tax on yearly salary
class Employee implements Taxable {
    private int empId;
    private String name;
    private double salary;

    public Employee(int empId, String name, double salary) {
        this.empId = empId;
        this.name = name;
        this.salary = salary;
    }

    @Override
    public double calcTax() {
        return salary * INCOME_TAX;
    }

    public String toString() {
        return "Employee ID: " + empId + ", Name: " + name + ", Yearly Salary: $" + salary;
    }
}

// Product class implementing Taxable to calculate sales tax on unit price
class Product implements Taxable {
    private int pid;
    private double price;
    private int quantity;

    public Product(int pid, double price, int quantity) {
        this.pid = pid;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public double calcTax() {
        return price * SALES_TAX;
    }

    public String toString() {
        return "Product ID: " + pid + ", Price: $" + price + ", Quantity: " + quantity;
    }
}

// Main class to accept user input and print tax calculations
public class DriverMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Accept employee information
        System.out.print("Enter Employee ID: ");
        int empId = scanner.nextInt();
        System.out.print("Enter Employee Name: ");
        String name = scanner.next();
        System.out.print("Enter Employee Salary: ");
        double salary = scanner.nextDouble();
        
        // Create Employee object and calculate income tax
        Employee employee = new Employee(empId, name, salary);
        double incomeTax = employee.calcTax();
        
        // Accept product information
        System.out.print("Enter Product ID: ");
        int pid = scanner.nextInt();
        System.out.print("Enter Product Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Product Quantity: ");
        int quantity = scanner.nextInt();
        
        // Create Product object and calculate sales tax
        Product product = new Product(pid, price, quantity);
        double salesTax = product.calcTax();
        
        // Display results
        System.out.println("\n" + employee);
        System.out.printf("Income Tax on Yearly Salary: $%.2f%n", incomeTax);
        System.out.println("\n" + product);
        System.out.printf("Sales Tax on Unit Price: $%.2f%n", salesTax);
        
        scanner.close();
    }
}
