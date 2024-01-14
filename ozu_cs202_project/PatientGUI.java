package ozu_cs202_project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PatientGUI extends JFrame {

    private Patient patient;
    private User patient_user;

    private JButton signInButton;

    public PatientGUI() {
        super("Patient Screen");
        initializeComponents();
    }

    public PatientGUI(Patient patient) {
        super("Patient Screen");
        this.patient = patient;
        initializeComponents();
    }

    public PatientGUI(Patient patient, User patient_user) {
        super("Patient Screen");
        this.patient = patient;
        this.patient_user = patient_user;
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
                    JOptionPane.showMessageDialog(signInFrame, "Sign In successful! Opening Patient's Screen.");
                    signInFrame.dispose();
                    showPatientScreen();
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

    private void showPatientScreen() {
        if (patient != null) {
            JFrame patientScreen = new JFrame("Patient Information");
            patientScreen.setSize(300, 200);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(4, 1));

            JLabel nameLabel = new JLabel("Name: " + patient_user.getFirstName() + " " + patient_user.getLastName());
            JLabel emailLabel = new JLabel("Email: " + patient_user.getEmail());
            JLabel medicalHistoryLabel = new JLabel("Medical History: " + patient.getMedicalHistory());

            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    patientScreen.dispose();
                }
            });

            panel.add(nameLabel);
            panel.add(emailLabel);
            panel.add(medicalHistoryLabel);
            panel.add(closeButton);

            patientScreen.add(panel);
            patientScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            patientScreen.setLocationRelativeTo(null); // Center the frame on the screen
            patientScreen.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Patient information not available.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PatientGUI();
            }
        });
    }
}
