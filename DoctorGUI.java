import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DoctorGUI extends JFrame {

    private Doctor doctor;

    private JButton upcomingAppointmentsButton;
    private JButton pastAppointmentsButton;
    private JButton specifyUnavailabilityButton;
    private JButton listRoomAvailabilityButton;

    public DoctorGUI(Doctor doctor) {
        super("Doctor Screen");
        this.doctor = doctor;

        initializeComponents();
    }

    private void initializeComponents() {
        upcomingAppointmentsButton = new JButton("Upcoming Appointments");
        pastAppointmentsButton = new JButton("Past Appointments");
        specifyUnavailabilityButton = new JButton("Specify Unavailability");
        listRoomAvailabilityButton = new JButton("List Room Availability");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(upcomingAppointmentsButton);
        panel.add(pastAppointmentsButton);
        panel.add(specifyUnavailabilityButton);
        panel.add(listRoomAvailabilityButton);

        getContentPane().add(panel);

        upcomingAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAppointments(doctor.getUpcomingAppointments(), "Upcoming Appointments");
            }
        });

        pastAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAppointments(doctor.getPastAppointments(), "Past Appointments");
            }
        });

        specifyUnavailabilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle specifying unavailability here
                // You might open a new window or dialog for this functionality
                // For simplicity, we will just print a message here
                JOptionPane.showMessageDialog(DoctorGUI.this, "Specify Unavailability");
            }
        });

        listRoomAvailabilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRooms(doctor.listRoomAvailability());
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setVisible(true);
    }

    private void showAppointments(List<Appointment> appointments, String title) {
        StringBuilder message = new StringBuilder();
        message.append(title).append(":\n");

        if (appointments.isEmpty()) {
            message.append("No appointments found.");
        } else {
            for (Appointment appointment : appointments) {
                message.append(appointment).append("\n");
            }
        }

        JOptionPane.showMessageDialog(this, message.toString(), title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void showRooms(List<Room> rooms) {
        StringBuilder message = new StringBuilder();
        message.append("Available Rooms:\n");

        if (rooms.isEmpty()) {
            message.append("No available rooms found.");
        } else {
            for (Room room : rooms) {
                message.append(room).append("\n");
            }
        }

        JOptionPane.showMessageDialog(this, message.toString(), "Available Rooms", JOptionPane.INFORMATION_MESSAGE);
    }



}

