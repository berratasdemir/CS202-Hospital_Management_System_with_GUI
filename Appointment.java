import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Appointment {
    private int appointmentID;
    private LocalDateTime dateTime;
    private String status;
    private int doctorID;

    private Connection connection;

    public Appointment(int appointmentID, LocalDateTime dateTime, String status) {
        this.appointmentID = appointmentID;
        this.dateTime = dateTime;
        this.status = status;
    }

    public Appointment(Connection connection) {
        this.connection = connection;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getStatus() {
        return status;
    }

    public int getAppointmentID() {
        return appointmentID;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setAppointmentID(int appointmentID) {
        this.appointmentID=appointmentID;
    }
    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    // Method to add a new appointment
    public void addAppointment(Appointment appointment) {
        String sql = "INSERT INTO Appointment (AppointmentID, DateTime, Status) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, appointment.getAppointmentID());
            preparedStatement.setObject(2, appointment.getDateTime());
            preparedStatement.setString(3, appointment.getStatus());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    // Method to retrieve an appointment by ID
    public Appointment getAppointmentByID(int appointmentID) {
        String sql = "SELECT * FROM Appointment WHERE AppointmentID = ?";
        Appointment appointment = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, appointmentID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    appointment = extractAppointmentFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return appointment;
    }

    // Method to retrieve all appointments
    public List<Appointment> getAllAppointments() {
        String sql = "SELECT * FROM Appointment";
        List<Appointment> appointmentList = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Appointment appointment = extractAppointmentFromResultSet(resultSet);
                appointmentList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return appointmentList;
    }

    // Method to update appointment information
    public void updateAppointment(Appointment appointment) {
        String sql = "UPDATE Appointment SET DateTime=?, Status=? WHERE AppointmentID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, appointment.getDateTime());
            preparedStatement.setString(2, appointment.getStatus());
            preparedStatement.setInt(3, appointment.getAppointmentID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }



    // Method to delete an appointment by ID
    public void deleteAppointment(int appointmentID) {
        String sql = "DELETE FROM Appointment WHERE AppointmentID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, appointmentID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    // Private method to extract appointment information from ResultSet
    private Appointment extractAppointmentFromResultSet(ResultSet resultSet) throws SQLException {
        return new Appointment(
                resultSet.getInt("AppointmentID"),
                resultSet.getObject("DateTime", LocalDateTime.class),
                resultSet.getString("Status")
        );
    }




    // Getter and Setter methods (similar to your existing code)
}
