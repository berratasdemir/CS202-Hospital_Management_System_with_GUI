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

        JButton viewPatientStatsButton = new JButton("View Patient Statistics");
        JButton viewAppointmentRoomStatsButton = new JButton("View Appointment Room Statistics");
        JButton viewNurseAssignmentRatiosButton = new JButton("View Nurse Assignment Ratios");
        JButton viewMostBookedStatsButton = new JButton("View Most Booked Room and Doctor Statistics");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(viewPatientStatsButton);
        add(viewAppointmentRoomStatsButton);
        add(viewNurseAssignmentRatiosButton);
        add(viewMostBookedStatsButton);

        viewPatientStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(null, "View Patient Statistics clicked");
            }
        });

        viewAppointmentRoomStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(null, "View Appointment Room Statistics clicked");
            }
        });

        viewNurseAssignmentRatiosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(null, "View Nurse Assignment Ratios clicked");
            }
        });

        viewMostBookedStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(null, "View Most Booked Room and Doctor Statistics clicked");
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

