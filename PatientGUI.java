import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PatientGUI extends JFrame {

    private Patient patient;

    private JButton searchDoctorsButton;
    private JButton filterAppointmentsButton;
    private JButton searchDoctorsByAvailabilityButton;
    private JButton bookAppointmentButton;
    private JButton cancelAppointmentButton;

    public PatientGUI(Patient patient) {
        super("Patient Screen");
        this.patient = patient;

        initializeComponents();
    }

    private void initializeComponents() {
        searchDoctorsButton = new JButton("Search Doctors by Expertise");
        filterAppointmentsButton = new JButton("Filter Appointments by Doctor Expertise");
        searchDoctorsByAvailabilityButton = new JButton("Search Doctors by Availability");
        bookAppointmentButton = new JButton("Book Appointment");
        cancelAppointmentButton = new JButton("Cancel Appointment");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(searchDoctorsButton);
        panel.add(filterAppointmentsButton);
        panel.add(searchDoctorsByAvailabilityButton);
        panel.add(bookAppointmentButton);
        panel.add(cancelAppointmentButton);

        getContentPane().add(panel);

        searchDoctorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expertise = JOptionPane.showInputDialog(PatientGUI.this, "Enter doctor expertise:");
                List<Doctor> doctors = patient.searchDoctorsByExpertise(expertise);
                showDoctors(doctors);
            }
        });

        filterAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expertise = JOptionPane.showInputDialog(PatientGUI.this, "Enter doctor expertise:");
                List<Appointment> filteredAppointments = patient.filterAppointmentsByDoctorExpertise(expertise);
                showAppointments(filteredAppointments, "Filtered Appointments");
            }
        });

        searchDoctorsByAvailabilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int startDay = Integer.parseInt(JOptionPane.showInputDialog(PatientGUI.this, "Enter start day:"));
                int endDay = Integer.parseInt(JOptionPane.showInputDialog(PatientGUI.this, "Enter end day:"));
                int startHour = Integer.parseInt(JOptionPane.showInputDialog(PatientGUI.this, "Enter start hour:"));
                int endHour = Integer.parseInt(JOptionPane.showInputDialog(PatientGUI.this, "Enter end hour:"));
                List<Doctor> doctors = patient.searchDoctorsByAvailableHoursWithinRange(startDay, endDay, startHour, endHour);
                showDoctors(doctors);
            }
        });

        bookAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int appointmentID = Integer.parseInt(JOptionPane.showInputDialog(PatientGUI.this, "Enter appointment ID to book:"));
                patient.bookAppointment(appointmentID);
            }
        });

        cancelAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int appointmentID = Integer.parseInt(JOptionPane.showInputDialog(PatientGUI.this, "Enter appointment ID to cancel:"));
                patient.cancelAppointment(appointmentID);
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setVisible(true);
    }

    private void showDoctors(List<Doctor> doctors) {
        StringBuilder message = new StringBuilder();
        message.append("Doctors:\n");

        if (doctors.isEmpty()) {
            message.append("No doctors found.");
        } else {
            for (Doctor doctor : doctors) {
                message.append(doctor).append("\n");
            }
        }

        JOptionPane.showMessageDialog(this, message.toString(), "Doctors List", JOptionPane.INFORMATION_MESSAGE);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Example usage
                Patient patient = new Patient(
                        123, // Replace with the actual user ID
                        "patient@example.com", // Replace with the actual email
                        "Jane", // Replace with the actual first name
                        "Doe", // Replace with the actual last name
                        "patient_password", // Replace with the actual password
                        "Patient", // Assuming the user type is Patient
                        789, // Replace with the actual patient ID
                        "Heart condition" // Replace with the actual medical history
                );

                // Pass the Patient object to the PatientGUI constructor
                PatientGUI patientGUI = new PatientGUI(patient);
                patientGUI.setVisible(true);
            }
        });
    }
}

