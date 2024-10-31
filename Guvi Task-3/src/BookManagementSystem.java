class Book {
    private int bookID;
    private String bookTitle;
    private String author;
    private boolean isAvailable;

    //Constructor to initialize the book attributes
    public Book(int bookID, String bookTitle, String author, boolean isAvailable){
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    // getter and setter methods for book attributes
    public int getBookID()
    {
        return bookID;
    }
    public String getBookTitle() {
        return bookTitle;
    }
    public String getAuthor(){
        return author;
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public void setAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }
    @Override
    public String toString(){
        return "Book ID: " + bookID + ", Title: " + bookTitle + ", Author: " + author + ", Available: " + isAvailable;
    }
}

// class library to manage books in the library
class Library{
    private Book[] books;
    private int count;
    public Library(int capacity){
        books = new Book[capacity];
        count = 0;
    }

    //this method is to add a book to the library
    public void addBook(Book book){
        if (count < books.length) {
            books[count] = book;
            count++;
            System.out.println("Book added successfully.");
        }
        else{
            System.out.println("Library is full, cannot add more books.");
            }
        }

    // This method is remove a book from the library using the bookID
    public void removeBook(int bookID) {
            for(int i = 0; i < count; i++){
                if(books[i].getBookID() == bookID){
                    books[i] = books[count - 1];
                    books[count - 1] = null;
                    count--;
                    System.out.println("Book removed successfully.");
                    return;
                }
            }
            System.out.println("Book not found.");
        }

        // this method is to search a book bit its ID
    public Book searchBook(int bookID){
        for (int i = 0; i < count; i++){
            if ( books[i].getBookID() == bookID){
                return books[i];
            }
        }
        return null;
    }

    //Method to display all books in the library
    public void displayBooks(){
        if (count == 0){
            System.out.println(" No book available in the library.");
        }
        else{
            for (int i = 0; i < count; i++){
                System.out.println(books[i]);
            }
        }
    }
}

// Main Class with menu-driven interface
public class BookManagementSystem {
    public static void main(String[] args) {
        Library library = new Library(10); // Creating a library with a capacity of 10
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1.Add Book");
            System.out.println("2.Remove Book");
            System.out.println("3.Search Book");
            System.out.println("4.Display All Books");
            System.out.println("5.Exit");
            System.out.println("Choose an option: ");

            int choice = scanner.nextInt();
            switch(choice) {
                case 1:
                    System.out.println("Enter Book ID: ");
                    int bookID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter Title: ");
                    String title = scanner.nextLine();
                    System.out.println("Enter Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Is Available (True/False): ");
                    boolean isAvailable = scanner.nextBoolean();
                    Book book = new Book(bookID, title, author, isAvailable);
                    library.addBook(book);
                    break;

                case 2:
                    System.out.print("Enter Book ID to search: ");
                    int removeID = scanner.nextInt();
                    library.removeBook(removeID);
                    break;

                case 3:
                    System.out.print("Enter Book ID to search: ");
                    int searchID = scanner.nextInt();
                    Book foundBook = library.searchBook(searchID);
                    if (foundBook != null){
                        System.out.println("Book Found; " + foundBook);
                    } else{
                        System.out.println("Book not found");
                    }
                    break;
                case 4:
                    library.displayBooks();
                    break;
                case 5:
                    System.out.println("Existing system. Goodbye;)");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again");

            }
        }
    }
}
