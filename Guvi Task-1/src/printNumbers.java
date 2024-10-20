//Program to print numbers from 10 to 50 using while loop


public class printNumbers {
    public static void main(String[] args) {
       int num = 10; // Declaring a variable called 'num' with a value 0f 10.
       System.out.println("Program to print numbers from 10 to 50 using while loop");
       while (num<=50) //Loop continues as long as the number is less than 51
        {
            System.out.println(num); // Prints the current value of the present iteration
            num++; // incrementing num by '1' for the next iteration
        }
       }
} // If you forget to increment 'num' in line 9, the loop would get executed for infinite number of times.