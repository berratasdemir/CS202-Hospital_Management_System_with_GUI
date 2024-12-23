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

        user = new User(connection);

        setTitle("User Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createComponents();

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

        if ("doctor".equalsIgnoreCase(userType)) {
            openDoctorScreen(newUser);
        } else if ("patient".equalsIgnoreCase(userType)) {
            openPatientScreen(newUser);
        } else if ("nurse".equalsIgnoreCase(userType)) {
            openNurseScreen(newUser);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid user type!");
        }

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

    private void openDoctorScreen(User newUser) {
        JFrame doctorFrame = new JFrame("Doctor Information");
        doctorFrame.setSize(400, 300);
        doctorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel doctorPanel = new JPanel();
        doctorPanel.setLayout(new GridLayout(5, 2));

        JTextField doctorIDField = new JTextField();
        JTextField expertiseField = new JTextField();

        doctorPanel.add(new JLabel("Doctor ID: (needs to be the same with the userID added)"));
        doctorPanel.add(doctorIDField);
        doctorPanel.add(new JLabel("Expertise:"));
        doctorPanel.add(expertiseField);

        JButton saveButton = new JButton("Save Doctor Information");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int doctorID = Integer.parseInt(doctorIDField.getText());
                String expertise = expertiseField.getText();

                Doctor newDoctor = new Doctor(connection, newUser.getUserID(), newUser.getEmail(),
                        newUser.getFirstName(), newUser.getLastName(), newUser.getPassword(),
                        newUser.getUserType(), doctorID, expertise);


                newDoctor.addUser(newDoctor);
                newDoctor.addDoctorDetailsToDB();


                JOptionPane.showMessageDialog(doctorFrame, "Doctor information saved successfully!");


                doctorFrame.dispose();
            }
        });

        doctorPanel.add(saveButton);

        doctorFrame.add(doctorPanel);
        doctorFrame.setVisible(true);
    }

    private void openPatientScreen(User newUser) {
        JFrame patientFrame = new JFrame("Patient Information");
        patientFrame.setSize(400, 300);
        patientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel patientPanel = new JPanel();
        patientPanel.setLayout(new GridLayout(5, 2));

        JTextField patientIDField = new JTextField();
        JTextField medicalHistoryField = new JTextField();

        patientPanel.add(new JLabel("Patient ID: (needs to be the same with the userID added)"));
        patientPanel.add(patientIDField);
        patientPanel.add(new JLabel("Medical History:"));
        patientPanel.add(medicalHistoryField);

        JButton saveButton = new JButton("Save Patient Information");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int patientID = Integer.parseInt(patientIDField.getText());
                String medicalHistory = medicalHistoryField.getText();

                // Create a new ozu_cs202_project.Patient instance with the additional information
                Patient newPatient = new Patient(newUser.getUserID(), newUser.getEmail(),
                        newUser.getFirstName(), newUser.getLastName(), newUser.getPassword(),
                        newUser.getUserType(), patientID, medicalHistory);

                newPatient.addUser(newPatient);

                JOptionPane.showMessageDialog(patientFrame, "Patient information saved successfully!");

                patientFrame.dispose();
            }
        });

        patientPanel.add(saveButton);

        patientFrame.add(patientPanel);
        patientFrame.setVisible(true);
    }

    private void openNurseScreen(User newUser) {
        JFrame nurseFrame = new JFrame("Nurse Information");
        nurseFrame.setSize(400, 300);
        nurseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel nursePanel = new JPanel();
        nursePanel.setLayout(new GridLayout(5, 2));

        JTextField nurseIDField = new JTextField();
        JTextField departmentField = new JTextField();

        nursePanel.add(new JLabel("Nurse ID: (needs to be the same with the userID added)"));
        nursePanel.add(nurseIDField);
        nursePanel.add(new JLabel("Department:"));
        nursePanel.add(departmentField);

        JButton saveButton = new JButton("Save Nurse Information");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int nurseID = Integer.parseInt(nurseIDField.getText());
                String department = departmentField.getText();

                Nurse newNurse = new Nurse(connection, newUser.getUserID(), newUser.getEmail(),
                        newUser.getFirstName(), newUser.getLastName(), newUser.getPassword(),
                        newUser.getUserType(), nurseID, department);

                newNurse.addUser(newNurse);
                newNurse.addNurseDetailsToDB();

                JOptionPane.showMessageDialog(nurseFrame, "Nurse information saved successfully!");

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
