package ozu_cs202_project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SignInGUI extends JFrame {

    private User user;

    private JTextField usernameField;
    private JPasswordField passwordField;

    public SignInGUI(User user) {
        super("Sign In");
        this.user = user;

        initializeComponents();
    }

    private void initializeComponents() {
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton signInButton = new JButton("Sign In");
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignIn();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(signInButton);

        getContentPane().add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setVisible(true);
    }

    private void handleSignIn() {
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        if (user.signIn(username, password)) {
            String userType = user.getUserType();
            openScreenBasedOnUserType(userType);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        passwordField.setText("");
    }

    private void openScreenBasedOnUserType(String userType) {
        JFrame frame;

        switch (userType) {
            case "ozu_cs202_project.Nurse":
                frame = new NurseGUI((Nurse) user);
                break;
            case "ozu_cs202_project.Doctor":
                frame = new DoctorGUI((Doctor) user);
                break;
            case "ozu_cs202_project.Manager":
                frame = new ManagerGUI();
                break;
            case "ozu_cs202_project.Patient":
                frame = new PatientGUI((Patient) user);
                break;
            default:
                frame = new JFrame("Unknown User Type");
                break;
        }

        // Set up the frame and show the corresponding screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);

        this.dispose();
    }

    public static void main(String[] args) {
        // Example usage
        Connection connection = establishDatabaseConnection(); // Implement this method to create your database connection
        User user = new User(connection);
        SignInGUI signInGUI = new SignInGUI(user);
    }

    private static Connection establishDatabaseConnection() {
        String url = "jdbc:mysql://localhost:3306/cs202project";
        String user = "root";
        String password = "B89.e637";

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
