import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class guviJdbc {
    public static void main(String[] args) {
        // Database URL, username, and password
        String url = "jdbc:mysql://localhost:3306/employee_jdbc";
        String user = "root";
        String password = "Im@arrun2629";

        // Insert query
        String insertQuery = "INSERT INTO employee (empcode, empname, empage, esalary) VALUES (?, ?, ?, ?)";

        // Employee data
        Object[][] empData = {
                {101, "Jenny", 25, 10000.0},
                {102, "Jacky", 30, 20000.0},
                {103, "Joe", 20, 40000.0},
                {104, "John", 40, 80000.0},
                {105, "Shameer", 25, 90000.0}
        };

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            // Iterate through the data and insert into the database
            for (Object[] emp : empData) {
                preparedStatement.setInt(1, (int) emp[0]);       // empcode
                preparedStatement.setString(2, (String) emp[1]); // empname
                preparedStatement.setInt(3, (int) emp[2]);       // empage
                preparedStatement.setDouble(4, (double) emp[3]); // esalary

                preparedStatement.executeUpdate(); // Execute the insert query
            }

            System.out.println("Data inserted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
