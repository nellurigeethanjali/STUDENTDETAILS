package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    static final String URL = "jdbc:mysql://localhost:3306/jdbc";
    static final String USER = "root";
    static final String PASSWORD = "root";

    // Database Connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // INSERT
    public static void insertStudent(String name, int age,
                                     String gender, String aadhar) {

        String sql = "INSERT INTO student_details (Name, Age, Gender, Aadhar) VALUES (?, ?, ?, ?)";

        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, aadhar);

            ps.executeUpdate();
            System.out.println("Student inserted successfully!");

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.out.println("Aadhar already exists!");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

    // DISPLAY
    public static void displayStudents() {

        String sql = "SELECT * FROM student_details";

        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("Name"));
                System.out.println("Age: " + rs.getInt("Age"));
                System.out.println("Gender: " + rs.getString("Gender"));
                System.out.println("Aadhar: " + rs.getString("Aadhar"));
                System.out.println("--------------------");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // UPDATE
    public static void updateStudent(int id, int age) {

        String sql = "UPDATE student_details SET Age = ? WHERE id = ?";

        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, age);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            System.out.println(rows > 0
                    ? "Student updated successfully!"
                    : "Student ID not found.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // DELETE
    public static void deleteStudent(int id) {

        String sql = "DELETE FROM student_details WHERE id = ?";

        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            System.out.println(rows > 0
                    ? "Student deleted successfully!"
                    : "Student ID not found.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        // Insert student
        insertStudent("Ammu", 21, "Female", "3001");

        // Display students
        System.out.println("\nStudent Details:");
        displayStudents();

        // Uncomment when needed; use the correct student ID.
        // updateStudent(1, 22);
        // deleteStudent(1);
    }
}

