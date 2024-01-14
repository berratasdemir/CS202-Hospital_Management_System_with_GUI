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
        JButton doctorButton = new JButton("Login as Doctor");
        JButton patientButton = new JButton("Login as Patient");
        JButton nurseButton = new JButton("Login as Nurse");
        JButton signUpButton = new JButton("Sign Up"); // New button for Sign Up

        // Set layout
        setLayout(new GridLayout(5, 1)); // Increase the row count to accommodate the new button

        // Add buttons to the frame
        add(adminButton);
        add(doctorButton);
        add(patientButton);
        add(nurseButton);
        add(signUpButton); // Add the new Sign Up button

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

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSignUpScreen(); // Handle the Sign Up button
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300); // Set your preferred size
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void openSignUpScreen() {
        UserGUI userGUI = new UserGUI();
        userGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                userGUI.setVisible(true);
            }
        });
    }

    private void openAdminLoginScreen() {
        ManagerGUI managerGUI = new ManagerGUI();
        managerGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                managerGUI.setVisible(true);
            }
        });
    }

    private void openDoctorLoginScreen() {
        DoctorGUI doctorGUI = new DoctorGUI();
        doctorGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                doctorGUI.setVisible(true);
            }
        });
    }

    private void openPatientLoginScreen() {
        PatientGUI patientGUI = new PatientGUI(null);
        patientGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                patientGUI.setVisible(true);
            }
        });
    }

    private void openNurseLoginScreen() {
        NurseGUI nurseGUI = new NurseGUI();
        nurseGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                nurseGUI.setVisible(true);
            }
        });
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
