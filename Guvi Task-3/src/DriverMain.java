// Interface Taxable
interface Taxable{
    double salesTax = 0.07;
    double incomeTax = 0.105;
    double calcTax();
}

class Employee implements Taxable{
    private int empId;
    private String name;
    private double salary;

    public Employee(int empId, String name, double salary){
        this.empId = empId;
        this.name = name;
        this.salary = salary;
    }

    // Calculating Income tax based on annual income
    @Override
    public double calcTax() {
        return salary * incomeTax;
    }

    @Override
    public String toString(){
        return "Employee ID: " + empId + ", Name: " + name + ", Salary: " + salary;
    }
}

// Product class implementing taxable interface
class Product implements Taxable{
    private int pid;
    private double price;
    private int quantity;

    public Product(int pid, double price, int quantity) {
        this.pid = pid;
        this.price = price;
        this.quantity = quantity;
    }

    //calculating sales tax based on Unit Price
    @Override
    public double calcTax()
    {
        return price * salesTax;

    }

    @Override
    public String toString(){
        return "Product ID: " + pid + ", Price: $" + price + ", Quantity: " + quantity;
    }
}

// Main class with main method
public class DriverMain {
    public static void main (String[] args){
        // Accepte employee information
        Employee employee = new Employee(1,"Priya Arun",26000);
        System.out.println(employee);
        System.out.println("Income Tax: $" + employee.calcTax());

        // Accept product information
        Product product = new Product(69, 500, 7);
        System.out.println(product);
        System.out.println("Sales Tax on unit Price: $" + product.calcTax());

    }
}