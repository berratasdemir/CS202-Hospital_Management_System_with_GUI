package ozu_cs202_project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerGUI extends JFrame {

    public ManagerGUI() {
        initializeComponents();
    }

    private void initializeComponents() {
        setTitle("ozu_cs202_project.Manager GUI");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton viewPatientStatsButton = new JButton("View ozu_cs202_project.Patient Statistics");
        JButton viewAppointmentRoomStatsButton = new JButton("View ozu_cs202_project.Appointment ozu_cs202_project.Room Statistics");
        JButton viewNurseAssignmentRatiosButton = new JButton("View ozu_cs202_project.Nurse Assignment Ratios");
        JButton viewMostBookedStatsButton = new JButton("View Most Booked ozu_cs202_project.Room and ozu_cs202_project.Doctor Statistics");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(viewPatientStatsButton);
        add(viewAppointmentRoomStatsButton);
        add(viewNurseAssignmentRatiosButton);
        add(viewMostBookedStatsButton);

        viewPatientStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to display patient statistics
                // manager.viewPatientStatistics();
                JOptionPane.showMessageDialog(null, "View ozu_cs202_project.Patient Statistics clicked");
            }
        });

        viewAppointmentRoomStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to display appointment room statistics
                // manager.viewAppointmentRoomStatistics();
                JOptionPane.showMessageDialog(null, "View ozu_cs202_project.Appointment ozu_cs202_project.Room Statistics clicked");
            }
        });

        viewNurseAssignmentRatiosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to display nurse assignment ratios
                // manager.viewNurseAssignmentRatios();
                JOptionPane.showMessageDialog(null, "View ozu_cs202_project.Nurse Assignment Ratios clicked");
            }
        });

        viewMostBookedStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to display most booked room and doctor statistics
                // manager.viewMostBookedRoomAndDoctorStatistics();
                JOptionPane.showMessageDialog(null, "View Most Booked ozu_cs202_project.Room and ozu_cs202_project.Doctor Statistics clicked");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ManagerGUI managerGUI = new ManagerGUI();
                managerGUI.setVisible(true);
            }
        });
    }
}

