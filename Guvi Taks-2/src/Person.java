/*   Q1. write a java program on below questions[OOP's].

1.1) Create a class Person with properties (name and age) with following features.

a. Default age of person should be 18;

b. A person object can be initialized with name and age;

c. Method to display name and age of person               */


class Person {
    protected String name;
    protected int age;

    public Person(){    //here, i have used Default Constructor - sets default age to 18
        this.name = "Unknown";
        this.age = 18;
    }
    public Person(String name, int age){    //here i have used Parameterized Constructor - initailizes name and age
        this.name = name;
        this.age = age;
    }
    public void displayinfo(){  //Methods to display nam and age
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }

    //Main method to test the Person class
    public static void main(String[] args){
        //creting another Person object with default constructor
        Person person1 = new Person();
        person1.displayinfo();

        //creating a Person object with Parameterized constructor
        Person person2 = new Person("Priya",23);
        person2.displayinfo();

        //creating a Person object with Parameterized constructor
        Person person3 = new Person("Arun",25);
        person3.displayinfo();

    }
}
