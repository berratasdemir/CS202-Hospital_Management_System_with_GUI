package ozu_cs202_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DoctorGUI extends JFrame {

    private Doctor doctor;
    private User doc_user;

    private JButton signInButton;

    public DoctorGUI() {
        super("Doctor Screen");
        initializeComponents();
    }

    public DoctorGUI(Doctor doctor) {
        super("Doctor Screen");
        this.doctor = doctor;
        initializeComponents();
    }

    public DoctorGUI(Doctor doctor, User doc_user) {
        super("Doctor Screen");
        this.doctor = doctor;
        this.doc_user = doc_user;
        initializeComponents();
    }

    private void initializeComponents() {
        signInButton = new JButton("Sign In");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Center the sign-in button
        panel.add(Box.createVerticalGlue());
        panel.add(signInButton);
        panel.add(Box.createVerticalGlue());

        getContentPane().add(panel);

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSignInScreen();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void openSignInScreen() {
        JFrame signInFrame = new JFrame("Sign In");
        signInFrame.setLayout(new FlowLayout());

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);

        JButton signInScreenButton = new JButton("Sign In");

        signInScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (signIn(username, password)) {
                    JOptionPane.showMessageDialog(signInFrame, "Sign In successful! Opening Doctor's Screen.");
                    signInFrame.dispose();
                    showDoctorScreen();
                } else {
                    JOptionPane.showMessageDialog(signInFrame, "Invalid username or password. Please try again.");
                }
            }
        });

        signInFrame.add(usernameLabel);
        signInFrame.add(usernameField);
        signInFrame.add(passwordLabel);
        signInFrame.add(passwordField);
        signInFrame.add(signInScreenButton);

        signInFrame.setSize(300, 150);
        signInFrame.setLocationRelativeTo(null); // Center the frame on the screen
        signInFrame.setVisible(true);
    }

    private boolean signIn(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/cs202project";
        String user = "root";
        String dbPassword = "B89.e637";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
            if (connection != null) {
                String sql = "SELECT * FROM User WHERE email = ? AND password = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);

                    ResultSet resultSet = preparedStatement.executeQuery();
                    return resultSet.next();
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    private void showDoctorScreen() {
        if (doctor != null) {
            JFrame doctorScreen = new JFrame("Doctor Information");
            doctorScreen.setSize(300, 200);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(4, 1));

            JLabel nameLabel = new JLabel("Name: " + doc_user.getFirstName() + " " + doc_user.getLastName());
            JLabel emailLabel = new JLabel("Email: " + doc_user.getEmail());
            JLabel expertiseLabel = new JLabel("Expertise: " + doctor.getExpertise());

            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doctorScreen.dispose();
                }
            });

            panel.add(nameLabel);
            panel.add(emailLabel);
            panel.add(expertiseLabel);
            panel.add(closeButton);

            doctorScreen.add(panel);
            doctorScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            doctorScreen.setLocationRelativeTo(null); // Center the frame on the screen
            doctorScreen.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Doctor information not available.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DoctorGUI();
            }
        });
    }
}
