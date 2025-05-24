//Program to find a number is positive or negative


import java.util.Scanner; //Used to import the Scanner class, so that i can get i/p from the user.
public class PositiveOrNegative
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a number : "); // Asking the use to enter a number so that code can find positive or negative
        int num = scanner.nextInt();
        if (num>0) /*If the condition is evaluated to true then the number is positive
                     If the condition is false else if block gets executed and if the condition is evaluated to
                     true then number is negative. If it is evaluated to false then the number is zero */
        {
            System.out.println(num+ " is a positive number");
        }
        else if(num<0)
        {
            System.out.println(num+ " is a negative number");
        }
        else
        {
            System.out.println(" The number is  zero ");
        }
        scanner.close(); //Used to close the Scanner.
    }

}
