/*Program that takes the purchase amount as i/p and calculate the final
payable amount after applying the discount
    1.If the purchase amount is less than 500, no discount is applied
    2.If the purchase amount is between 500 and 1000, a 10% discount is applied
    3.If the purchase amount is greater than 1000, 20% discount is applied. */


import java.util.Scanner; //Used to import scanner class, so that I can get i/p from the user.
public class discountCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the purchase amount: "); //Asking the purchased amount from the user
        double purchaseAmount = scanner.nextDouble(); //Storing the value of from the user in purchaseAmount
        double finalTotal;

        /* If the condition is evaluated to true there will be no discount applied
           If th condition is evaluated to false else if block gets executed. */
        if (purchaseAmount < 500)
        {
            finalTotal = purchaseAmount;
        }
        /* If the condition is evaluated to true a discount of 10% is applied
           If the conditoin is evaluated to false else block gets executed and a 20% discount is applied */
        else if (purchaseAmount >=500 && purchaseAmount <= 1000)
        {
            finalTotal = purchaseAmount * 0.90;
        }
        else
        {
            finalTotal = purchaseAmount * 0.80;
        }
        System.out.println("Your final payable amount is : "+finalTotal);
        System.out.println("THANK YOU FOR SHOPPING");
        scanner.close();
    }

}
