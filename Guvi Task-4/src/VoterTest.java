/*Create a class Voter(voterld, name, age) with parameterized constructor. The parameterized
constructor should throw a checked exception if age is less than 18. The message of exception
is "invalid age for voter" */


// Custom Exception for Invalid Voter Age
class InvalidVoterAgeException extends Exception {
    public InvalidVoterAgeException(String message) {
        super(message);
    }
}

// Voter Class
class Voter {
    private int voterId;
    private String name;
    private int age;

    // Parameterized Constructor
    public Voter(int voterId, String name, int age) throws InvalidVoterAgeException {
        if (age < 18) {
            throw new InvalidVoterAgeException("Invalid age for voter");
        }
        this.voterId = voterId;
        this.name = name;
        this.age = age;
    }

    // Method to Display Voter Information
    public void displayInfo() {
        System.out.println("Voter ID: " + voterId + ", Name: " + name + ", Age: " + age);
    }
}

// Main Class to Test the Voter Class
public class VoterTest {
    public static void main(String[] args) {
        try {
            // Create a voter with valid age
            Voter voter1 = new Voter(101, "Arun", 25);
            voter1.displayInfo();

            // Create a voter with invalid age
            Voter voter2 = new Voter(102, "Priya", 16); // This will throw an exception

        } catch (InvalidVoterAgeException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
