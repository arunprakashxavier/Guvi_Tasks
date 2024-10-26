/*  1.2). Create class Product (pid, price, quantity) with parameterized constructor.
Create a main function in different class (say XYZ) and perform following task:

a. Accept five product information from user and store in an array

b. Find Pid of the product with the highest price.

c. Create method (with array of product's object as argument) in XYZ class to
calculate and return the total amount spent on all products. (amount spent on
single product-price of product * quantity of product   */

import java.util.Scanner;
class Product{
    // Properties
    private int pid;
    private double price;
    private int quantity;

    //Parameterized Constructor
    public Product(int pid, double price, int quantity){
        this.pid = pid;
        this.price = price;
        this.quantity = quantity;
    }

    //Getter Methods
    public int getPid(){
        return pid;
    }

    public double getPrice(){
        return price;
    }

    public int getQuantity(){
        return quantity;
    }
}

class XYZ{
    //Methods to find the PID of the product with highest price
    public static int highPaidProduct(Product[] products){
        int highPriceIndex = 0;
        for(int i = 1; i < products.length; i++){
            if(products[i].getPrice() > products[highPriceIndex].getPrice()){
                highPriceIndex = i;
            }
        }
        return products[highPriceIndex].getPid();
    }

    //Methods to calculate the total amount spent ona all products
    public static double calculateTotalAmount(Product[] products){
        double totalAmount = 0;
        for(Product product : products){
            totalAmount += product.getPrice() * product.getQuantity();
        }
        return totalAmount;
    }
    //Main Method
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Product[] products = new Product[5];
        //Getting product details from the user
        for(int i = 0; i < products.length; i++)
        {
            System.out.println("Enter details for the product " + (i + 1) + ":");
            System.out.print("Product ID: ");
            int pid = scanner.nextInt();
            System.out.print("Product Price: ");
            double price = scanner.nextDouble();
            System.out.print("Product Quantity: ");
            int quantity = scanner.nextInt();
            products[i] = new Product(pid, price, quantity);
        }
        //Finding the PID of the pruduct with the highest price
        int highestPaidProduct = highPaidProduct(products);
        System.out.println(" ");
        System.out.println("Highest Paid Product: " + highestPaidProduct);

        //Calculating the total amount spent on all products
        double totalAmount = calculateTotalAmount(products);
        System.out.println("Total Amount: " + totalAmount);
        scanner.close();
    }
}
