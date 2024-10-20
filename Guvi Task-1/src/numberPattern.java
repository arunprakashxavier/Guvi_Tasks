/* Java program to print bellowed pattern ->i and j and k=>5
   55555
   54444
   54333
   54322
   54321   */

public class numberPattern {
    public static void main(String[] args)
    {
        int rows = 5; // Set number of rows
        int cols = 5; // Set number of columns
        int i, j, k;

        // Loop through each row
        for (i = 1; i <= rows; i++) {
            // Loop 1: Print decreasing numbers
            for (j = cols; j > cols - i; j--) {
                System.out.print(j); // Print the current j value
            }

            // Loop 2: Print the repeated number
            for (k = 1; k <= cols - i; k++) {
                System.out.print(rows - i + 1); // Print the repeated value
            }

            System.out.println(); // Move to the next line after each row
        }
    }
}

/*  EXECUTION
    1.The outer loop controls the number of rows
    2.The first inner loop prints decreasing number based on the value of n
    3.The second inner loop appends the repeated number depending on the row index
    4.After constructing each row, it prints a new line so that next row can be printed in new line
*/