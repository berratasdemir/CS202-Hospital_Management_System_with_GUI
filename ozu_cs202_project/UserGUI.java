package ozu_cs202_project;

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

        // Initialize ozu_cs202_project.User class with the connection
        user = new User(connection);

        setTitle("ozu_cs202_project.User Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createComponents();

        // Place the components on the frame
        setLayout(new GridLayout(7, 2));
        add(new JLabel("ozu_cs202_project.User ID:"));
        add(userIDField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("First Name:"));
        add(firstNameField);
        add(new JLabel("Last Name:"));
        add(lastNameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("ozu_cs202_project.User Type:"));
        add(userTypeField);

        JButton addUserButton = new JButton("Add ozu_cs202_project.User");
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

        // Open a new window based on the user type
        if ("doctor".equalsIgnoreCase(userType)) {
            openDoctorScreen(newUser);
        } else if ("patient".equalsIgnoreCase(userType)) {
            openPatientScreen(newUser);
        } else if ("nurse".equalsIgnoreCase(userType)) {
            openNurseScreen(newUser);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid user type!");
        }

        // Optionally, you can display a success message or update the UI
        JOptionPane.showMessageDialog(this, "ozu_cs202_project.User added successfully!");

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

    private void openDoctorScreen(User newUser) {
        JFrame doctorFrame = new JFrame("ozu_cs202_project.Doctor Information");
        doctorFrame.setSize(400, 300);
        doctorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel doctorPanel = new JPanel();
        doctorPanel.setLayout(new GridLayout(5, 2));

        JTextField doctorIDField = new JTextField();
        JTextField expertiseField = new JTextField();

        doctorPanel.add(new JLabel("ozu_cs202_project.Doctor ID:"));
        doctorPanel.add(doctorIDField);
        doctorPanel.add(new JLabel("Expertise:"));
        doctorPanel.add(expertiseField);

        JButton saveButton = new JButton("Save ozu_cs202_project.Doctor Information");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int doctorID = Integer.parseInt(doctorIDField.getText());
                String expertise = expertiseField.getText();

                // Create a new ozu_cs202_project.Doctor instance with the additional information
                Doctor newDoctor = new Doctor(connection, newUser.getUserID(), newUser.getEmail(),
                        newUser.getFirstName(), newUser.getLastName(), newUser.getPassword(),
                        newUser.getUserType(), doctorID, expertise);

                // Add the user and doctor details to the database
                newDoctor.addUser(newDoctor);
                newDoctor.addDoctorDetailsToDB();

                // Optionally, you can display a success message or update the UI
                JOptionPane.showMessageDialog(doctorFrame, "ozu_cs202_project.Doctor information saved successfully!");

                // Close the ozu_cs202_project.Doctor screen
                doctorFrame.dispose();
            }
        });

        doctorPanel.add(saveButton);

        doctorFrame.add(doctorPanel);
        doctorFrame.setVisible(true);
    }

    private void openPatientScreen(User newUser) {
        JFrame patientFrame = new JFrame("ozu_cs202_project.Patient Information");
        patientFrame.setSize(400, 300);
        patientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel patientPanel = new JPanel();
        patientPanel.setLayout(new GridLayout(5, 2));

        JTextField patientIDField = new JTextField();
        JTextField medicalHistoryField = new JTextField();

        patientPanel.add(new JLabel("ozu_cs202_project.Patient ID:"));
        patientPanel.add(patientIDField);
        patientPanel.add(new JLabel("Medical History:"));
        patientPanel.add(medicalHistoryField);

        JButton saveButton = new JButton("Save ozu_cs202_project.Patient Information");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int patientID = Integer.parseInt(patientIDField.getText());
                String medicalHistory = medicalHistoryField.getText();

                // Create a new ozu_cs202_project.Patient instance with the additional information
                Patient newPatient = new Patient(newUser.getUserID(), newUser.getEmail(),
                        newUser.getFirstName(), newUser.getLastName(), newUser.getPassword(),
                        newUser.getUserType(), patientID, medicalHistory);

                // Add the user details to the database
                newPatient.addUser(newPatient);

                // Optionally, you can display a success message or update the UI
                JOptionPane.showMessageDialog(patientFrame, "ozu_cs202_project.Patient information saved successfully!");

                // Close the ozu_cs202_project.Patient screen
                patientFrame.dispose();
            }
        });

        patientPanel.add(saveButton);

        patientFrame.add(patientPanel);
        patientFrame.setVisible(true);
    }

    private void openNurseScreen(User newUser) {
        JFrame nurseFrame = new JFrame("ozu_cs202_project.Nurse Information");
        nurseFrame.setSize(400, 300);
        nurseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel nursePanel = new JPanel();
        nursePanel.setLayout(new GridLayout(5, 2));

        JTextField nurseIDField = new JTextField();
        JTextField departmentField = new JTextField();

        nursePanel.add(new JLabel("ozu_cs202_project.Nurse ID:"));
        nursePanel.add(nurseIDField);
        nursePanel.add(new JLabel("Department:"));
        nursePanel.add(departmentField);

        JButton saveButton = new JButton("Save ozu_cs202_project.Nurse Information");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int nurseID = Integer.parseInt(nurseIDField.getText());
                String department = departmentField.getText();

                // Create a new ozu_cs202_project.Nurse instance with the additional information
                Nurse newNurse = new Nurse(connection, newUser.getUserID(), newUser.getEmail(),
                        newUser.getFirstName(), newUser.getLastName(), newUser.getPassword(),
                        newUser.getUserType(), nurseID, department);

                // Add the user and nurse details to the database
                newNurse.addUser(newNurse);
                newNurse.addNurseDetailsToDB();

                // Optionally, you can display a success message or update the UI
                JOptionPane.showMessageDialog(nurseFrame, "ozu_cs202_project.Nurse information saved successfully!");

                // Close the ozu_cs202_project.Nurse screen
                nurseFrame.dispose();
            }
        });

        nursePanel.add(saveButton);

        nurseFrame.add(nursePanel);
        nurseFrame.setVisible(true);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserGUI();
            }
        });
    }
}
