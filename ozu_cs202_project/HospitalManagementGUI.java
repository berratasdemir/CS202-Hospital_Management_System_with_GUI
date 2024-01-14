package ozu_cs202_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HospitalManagementGUI extends JFrame {

    private DoctorGUI doctor;
    public HospitalManagementGUI() {
        super("Hospital Management System");

        // Create buttons
        JButton adminButton = new JButton("Login as Admin");
        JButton doctorButton = new JButton("Login as ozu_cs202_project.Doctor");
        JButton patientButton = new JButton("Login as ozu_cs202_project.Patient");
        JButton nurseButton = new JButton("Login as ozu_cs202_project.Nurse");

        // Set layout
        setLayout(new GridLayout(4, 1));

        // Add buttons to the frame
        add(adminButton);
        add(doctorButton);
        add(patientButton);
        add(nurseButton);

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAdminLoginScreen();
            }
        });

        doctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDoctorLoginScreen();
            }
        });

        patientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPatientLoginScreen();
            }
        });

        nurseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNurseLoginScreen();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300); // Set your preferred size
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void openAdminLoginScreen() {
        // Implement logic to open the Admin login screen
        // Example: new AdminLoginGUI();
    }

    private void openDoctorLoginScreen() {
        // Assuming you have the necessary information to create a ozu_cs202_project.Doctor instance

    }


    private void openPatientLoginScreen() {
        // Implement logic to open the ozu_cs202_project.Patient login screen
        // Example: new PatientLoginGUI();
    }

    private void openNurseLoginScreen() {
        // Implement logic to open the ozu_cs202_project.Nurse login screen
        // Example: new NurseLoginGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HospitalManagementGUI();
            }
        });
    }
}
