/* Q5.Use Collection Classes to store Integers. Create some methods for following functionalities.

        a. Include functions for pushing elements onto the stack.

        b. popping elements from the stack.

        c. checking if the stack is empty.*/


import java.util.Stack;
import java.util.EmptyStackException;

public class StackOperations {

    private Stack<Integer> stack;

    // Constructor to initialize the stack
    public StackOperations() {
        stack = new Stack<>();
    }

    // Method to push elements onto the stack
    public void pushElement(int element) {
        stack.push(element);
        System.out.println(element + " has been pushed onto the stack.");
    }

    // Method to pop elements from the stack
    public void popElement() {
        try {
            int poppedElement = stack.pop();
            System.out.println(poppedElement + " has been popped from the stack.");
        } catch (EmptyStackException e) {
            System.out.println("The stack is empty. No element to pop.");
        }
    }

    // Method to check if the stack is empty
    public boolean isStackEmpty() {
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        StackOperations stackOps = new StackOperations();

        // Pushing elements onto the stack
        stackOps.pushElement(10);
        stackOps.pushElement(20);
        stackOps.pushElement(30);

        // Checking if the stack is empty
        System.out.println("Is the stack empty? " + stackOps.isStackEmpty());

        // Popping elements from the stack
        stackOps.popElement();
        stackOps.popElement();
        stackOps.popElement();

        // Trying to pop from an empty stack
        stackOps.popElement();

        // Checking if the stack is empty after popping all elements
        System.out.println("Is the stack empty? " + stackOps.isStackEmpty());
    }
}
