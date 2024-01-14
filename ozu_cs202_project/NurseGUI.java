package ozu_cs202_project;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class NurseGUI extends JFrame {

    private Nurse nurse;

    public NurseGUI(Nurse nurse) {
        this.nurse = nurse;

        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Nurse Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton viewRoomAvailabilityButton = new JButton("View Room Availability");
        JButton viewUpcomingAssignedRoomsButton = new JButton("View Upcoming Assigned Rooms");

        viewRoomAvailabilityButton.addActionListener(e -> onViewRoomAvailability());
        viewUpcomingAssignedRoomsButton.addActionListener(e -> onViewUpcomingAssignedRooms());

        panel.add(viewRoomAvailabilityButton);
        panel.add(viewUpcomingAssignedRoomsButton);

        add(panel);

        setVisible(true);
    }

    private void onViewRoomAvailability() {
        List<Room> availableRooms = nurse.viewRoomAvailability();

        System.out.println("Available Rooms:");
        for (Room room : availableRooms) {
            System.out.println(room);
        }
    }

    private void onViewUpcomingAssignedRooms() {
        List<Room> assignedRooms = nurse.viewUpcomingAssignedRooms();

        System.out.println("Upcoming Assigned Rooms:");
        for (Room room : assignedRooms) {
            System.out.println(room);
        }
    }

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/cs202project";
        String user = "root";
        String password = "B89.e637";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                Nurse nurse = new Nurse(
                        connection, 1, "nurse@email.com", "Nurse", "One", "nursepass", "Nurse",
                        1, "Emergency"
                );

                SwingUtilities.invokeLater(() -> new NurseGUI(nurse));
            } else {
                System.out.println("Failed to make a connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

