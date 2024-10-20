//Program to reverse a given number using loops


import java.util.Scanner; //Used to import the scanner class, so that i can get the i/p from the user
public class reverseNumbers {
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a number: ");
        int number = scanner.nextInt(); //Used to get i/p from the user.
        int reverse = 0;
        while(number != 0)
        {
         int digit = number % 10; /* Used to get the last digit of the number */
         reverse = reverse * 10 + digit; /* Since reverse=0 at first (0*10+3)=3. now reverse=3 */
         number /= 10; /* used to remove the last digit in the number. (123 /= 10) = 12. now the value of number is 12 */
        } /* The loop gets executed until the condition is evaluated to false */
        System.out.println("The reversed number is: " + reverse); //Finally the reversed number gets printed
        scanner.close(); //Used to close the scanner
    }
}
