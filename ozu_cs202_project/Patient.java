package ozu_cs202_project;

import ozu_cs202_project.Appointment;
import ozu_cs202_project.Doctor;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
    private int patientID;
    private String medical_history;

    public Patient(int userID, String email, String firstName, String lastName, String password, String userType,
                   int patientID, String medical_history) {
        super(userID, email, firstName, lastName, password, userType);
        this.patientID = patientID;
        this.medical_history = medical_history;

    }

    public int getPatientID() {
        return patientID;
    }

    public String getMedicalHistory() {
        return medical_history;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medical_history = medicalHistory;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }


    public void addPatientDetailsToDB() {
        String url = "jdbc:mysql://localhost:3306/cs202project";
        String user = "root";
        String password = "B89.e637";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                String sql = "INSERT INTO Patient (PatientID, medicalHistory, UserID) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, this.patientID);
                    preparedStatement.setString(2, this.medical_history);
                    preparedStatement.setInt(3, this.getUserID());

                    preparedStatement.executeUpdate();
                    System.out.println("Patient details added to the database!");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Failed to make a connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Doctor> searchDoctorsByExpertise(String expertise) {
        List<Doctor> doctors = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs202project", "root", "B89.e637");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Doctor WHERE expertise = ?")) {
            stmt.setString(1, expertise);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int doctorID = rs.getInt("DoctorID");
                expertise = rs.getString("expertise");
                int userID = rs.getInt("UserID");
                String email = rs.getString("Email");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String password = rs.getString("Password");
                String userType = rs.getString("UserType");
                Doctor doctor = new Doctor(conn, userID, email, firstName, lastName, password, userType, doctorID, expertise);
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }
    public List<Appointment> filterAppointmentsByDoctorExpertise(String expertise) {
        List<Appointment> filteredAppointments = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs202project", "root", "B89.e637");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Appointment " +
                     "JOIN Doctor ON Appointment.DoctorID = Doctor.DoctorID " +
                     "WHERE Doctor.expertise = ?")) {
            stmt.setString(1, expertise);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("AppointmentID");
                Timestamp timestamp = rs.getTimestamp("DateTime");
                LocalDateTime dateTime = timestamp.toLocalDateTime();
                String status = rs.getString("Status");
                int doctorID = rs.getInt("DoctorID");
                Appointment appointment = new Appointment(appointmentID, dateTime, status);
                appointment.setDoctorID(doctorID);

                filteredAppointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredAppointments;
    }

    public List<Doctor> searchDoctorsByAvailableHoursWithinRange(int startDay, int endDay, int startHour, int endHour) {
        List<Doctor> doctors = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs202project", "root", "B89.e637");
             PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT Doctor.DoctorID, Doctor.expertise, Doctor.UserID " +
                     "FROM Doctor " +
                     "JOIN DoctorSchedule ON Doctor.DoctorID = DoctorSchedule.DoctorID " +
                     "WHERE DoctorSchedule.day BETWEEN ? AND ? AND DoctorSchedule.start_hour >= ? AND DoctorSchedule.end_hour <= ?")) {
            stmt.setInt(1, startDay);
            stmt.setInt(2, endDay);
            stmt.setInt(3, startHour);
            stmt.setInt(4, endHour);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int doctorID = rs.getInt("DoctorID");
                String expertise = rs.getString("expertise");
                int userID = rs.getInt("UserID");
                String email = rs.getString("Email");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String password = rs.getString("Password");
                String userType = rs.getString("UserType");

                Doctor doctor = new Doctor(conn, userID, email, firstName, lastName, password, userType, doctorID, expertise);
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    public void bookAppointment(int appointmentID) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs202project", "root", "B89.e637");
             PreparedStatement stmt = conn.prepareStatement("UPDATE Appointment SET Status = 'Booked' WHERE AppointmentID = ?")) {
            stmt.setInt(1, appointmentID);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Appointment booked successfully!");
            } else {
                System.out.println("Failed to book appointment. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelAppointment(int appointmentID) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs202project", "root", "B89.e637");
             PreparedStatement stmt = conn.prepareStatement("SELECT DateTime FROM Appointment WHERE AppointmentID = ?")) {
            stmt.setInt(1, appointmentID);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Timestamp appointmentDateTime = rs.getTimestamp("DateTime");
                long timeDifferenceMillis = appointmentDateTime.getTime() - System.currentTimeMillis();
                long hoursDifference = timeDifferenceMillis / (60 * 60 * 1000);

                if (hoursDifference > 24) {
                    try (PreparedStatement cancelStmt = conn.prepareStatement("UPDATE Appointment SET Status = 'Cancelled' WHERE AppointmentID = ?")) {
                        cancelStmt.setInt(1, appointmentID);
                        int cancelRowsAffected = cancelStmt.executeUpdate();

                        if (cancelRowsAffected > 0) {
                            System.out.println("Appointment cancelled successfully!");
                        } else {
                            System.out.println("Failed to cancel appointment. Please try again.");
                        }
                    }
                } else {

                    System.out.println("Cannot cancel appointment. Less than 24 hours left.");
                }
            } else {
                System.out.println("Appointment not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
