//Program to find the smallest along three numbers


import java.util.Scanner; //Used to import scanner class, so that I can get i/p from the user.
public class smallestNumber
{
public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter the first number: "); //Asking the user for the first number.
        int num1 = scanner.nextInt(); //Getting the first number from the user.
        System.out.println("Enter the second number"); //Asking the user for the second number
        int num2 = scanner.nextInt(); //Getting the second number from the user.
        System.out.println("Enter the third number"); //Asking the user for the third number.
        int num3 = scanner.nextInt(); //Getting the third number from the user.
        int smallestNum = num1; //Storing the value of num1 in smallestNum initially.
        /*If the condition is evaluated to true then the value of num2 gets stored in smallestNum
         If the condition is evaluated to false the control gets transferred to the next if condition*/
        if (num2<smallestNum)
        {
            smallestNum = num2;
        }
        //If the condition is evaluated to true then the value of num3 gets stored in smallestNum
        //If the condition is evaluated to false smallestNum remains the smallest which hs the value of num1
        if (num3<smallestNum)
        {
            smallestNum = num3;
        }
        System.out.println("The smallest number is : "+smallestNum); //Prints the smallest number among the three
        scanner.close(); //Used to close the scanner
    }
}
