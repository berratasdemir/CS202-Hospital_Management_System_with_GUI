import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class HospitalManagement {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/cs202project";
        String user = "root";
        String password = "B89.e637";



        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Connected to the database!");

                // Instantiate UserDao
                User userObject = new User(connection);

                // Example: Adding a new user
                User newUser = new User(0, "newuser@example.com", "John", "Doe", "password123", "regular");
                userObject.addUser(newUser);

                // Example: Retrieving a user by email
                User retrievedUser = userObject.getUserByEmail("newuser@example.com");
                if (retrievedUser != null) {
                    System.out.println("Retrieved User: " + retrievedUser.getFirstName() + " " + retrievedUser.getLastName());

                    // Example: Updating user information
                    retrievedUser.setFirstName("UpdatedFirstName");
                    retrievedUser.setLastName("UpdatedLastName");
                    retrievedUser.setPassword("newPassword");
                    userObject.updateUser(retrievedUser);

                    // Example: Retrieving all users after update
                    List<User> updatedUserList = userObject.getAllUsers();
                    System.out.println("All Users after Update:");
                    for (User u : updatedUserList) {
                        System.out.println(u.getFirstName() + " " + u.getLastName());
                    }
                } else {
                    System.out.println("User not found.");
                }

                //Example: Deleting a user
                userObject.deleteUser(retrievedUser.getUserID());

            } else {
                System.out.println("Failed to make a connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Methods for handling user registration and login
    // Methods for each user type (Patient, Manager, Nurse, Doctor) to perform specific actions
    // Methods for appointment management
    // Methods for room management
    // Methods for statistics generation
}
