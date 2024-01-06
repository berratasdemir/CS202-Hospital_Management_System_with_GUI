import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserGUI extends JFrame {
    private User user;
    private Connection connection;

    private JTextField userIDField;
    private JTextField emailField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField passwordField;
    private JTextField userTypeField;

    public UserGUI() {
        connection = establishConnection();

        // Initialize User class with the connection
        user = new User(connection);

        setTitle("User Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createComponents();

        // Place the components on the frame
        setLayout(new GridLayout(7, 2));
        add(new JLabel("User ID:"));
        add(userIDField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("First Name:"));
        add(firstNameField);
        add(new JLabel("Last Name:"));
        add(lastNameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("User Type:"));
        add(userTypeField);

        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
        add(addUserButton);

        setVisible(true);
    }

    private void createComponents() {
        userIDField = new JTextField();
        emailField = new JTextField();
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        passwordField = new JTextField();
        userTypeField = new JTextField();
    }

    private void addUser() {
        int userID = Integer.parseInt(userIDField.getText());
        String email = emailField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password = passwordField.getText();
        String userType = userTypeField.getText();

        User newUser = new User(userID, email, firstName, lastName, password, userType);
        user.addUser(newUser);

        // Optionally, you can display a success message or update the UI
        JOptionPane.showMessageDialog(this, "User added successfully!");

        // Clear the input fields after adding the user
        clearFields();
    }

    private void clearFields() {
        userIDField.setText("");
        emailField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        passwordField.setText("");
        userTypeField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserGUI();
            }
        });
    }
    private Connection establishConnection() {
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/cs202project";
        String user = "root";
        String password = "B89.e637";

        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make a connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

}

