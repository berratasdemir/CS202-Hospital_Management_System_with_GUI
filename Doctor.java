import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class Doctor extends User {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/cs202project";
        String user = "root";
        String password = "B89.e637";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Connected to the database!");

                // Creating a Doctor instance for testing
                Doctor doctor = new Doctor(
                        connection, 16, "proberra34@email.com", "ccc", "cccc", "cc", "Doctor",
                        16, "Moktor" // Doctor-specific attributes
                );


                // Adding the user details using the inherited addUser method
                doctor.addUser(doctor);

                // Adding doctor-specific details to the database
                doctor.addDoctorDetailsToDB();


                // Testing listRoomAvailability method
                List<Room> availableRooms = doctor.listRoomAvailability();

                System.out.println("Available Rooms:");
                for (Room room : availableRooms) {
                    System.out.println(room); // This will call the overridden toString() method
                }

                // Assuming other methods are tested in a similar manner
                // Testing assignRoomToAppointment, specifyUnavailability, getUpcomingAppointments, getPastAppointments methods
            } else {
                System.out.println("Failed to make a connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDoctor(){

    }
    private int doctorID;
    private String expertise;

    public Doctor(Connection connection, int userID, String email, String firstName, String lastName, String password, String userType,
                  int doctorID, String expertise) {
        super(userID, email, firstName, lastName, password, userType);
        this.connection = connection; // Initialize connection in Doctor class
        this.doctorID = doctorID;
        this.expertise = expertise;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public void addDoctorDetailsToDB() {
        String url = "jdbc:mysql://localhost:3306/cs202project";
        String user = "root";
        String password = "B89.e637"; // Replace with your database password

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                String sql = "INSERT INTO Doctor (DoctorID, expertise, UserID) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, this.doctorID);
                    preparedStatement.setString(2, this.expertise);
                    preparedStatement.setInt(3, this.getUserID()); // Assuming getUserID() method exists in the User class

                    preparedStatement.executeUpdate();
                    System.out.println("Doctor details added to the database!");
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle the exception appropriately
                }
            } else {
                System.out.println("Failed to make a connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public List<Room> listRoomAvailability() {
        List<Room> availableRooms = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs202project", "root", "B89.e637");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Room WHERE Availability = 1")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int roomId = rs.getInt("RoomID");
                String roomType = rs.getString("RoomType");
                boolean availability = rs.getBoolean("Availability");

                Room room = new Room(roomId, roomType, availability);
                availableRooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as required
        }
        return availableRooms;
    }

    // Method to assign a room to an appointment
    public void assignRoomToAppointment(Appointment appointment, Room room, Nurse nurse) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs202project", "root", "B89.e637");
             PreparedStatement stmt = conn.prepareStatement("UPDATE Appointment SET RoomID = ?, Status = ? WHERE AppointmentID = ?")) {
            stmt.setInt(1, room.getRoomID());
            stmt.setString(2, "Scheduled");
            stmt.setInt(3, appointment.getAppointmentID());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Create room assignment record
                RoomAssignment roomAssignment = new RoomAssignment(appointment, room, this, nurse);
                roomAssignment.saveToDatabase(); // Assuming a method to save RoomAssignment to the database
            } else {
                // Appointment update failed, handle accordingly
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as required
        }
    }

    // Method to specify unavailability
    public void specifyUnavailability(Date startDate, Date endDate) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs202project", "root", "B89.e637");
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO DoctorSchedule (start_date, end_date, type, DoctorID) VALUES (?, ?, ?, ?)")) {
            stmt.setTimestamp(1, new Timestamp(startDate.getTime())); // Use getTime() to get milliseconds from Date
            stmt.setTimestamp(2, new Timestamp(endDate.getTime()));
            stmt.setString(3, "Unavailability");
            stmt.setInt(4, this.doctorID);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected <= 0) {
                // Insertion failed, handle accordingly
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as required
        }
    }

    // Method to get upcoming appointments
    public List<Appointment> getUpcomingAppointments() {
        List<Appointment> upcomingAppointments = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs202project", "root", "B89.e637");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Appointment WHERE DoctorID = ? AND DateTime > NOW()")) {
            stmt.setInt(1, this.doctorID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("AppointmentID");
                Timestamp dateTime = rs.getTimestamp("DateTime");
                String status = rs.getString("Status");
                // Retrieve other appointment details

                Appointment appointment = new Appointment(appointmentID, dateTime.toLocalDateTime(), status); // Create Appointment object with details
                upcomingAppointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as required
        }
        return upcomingAppointments;
    }

    // Method to get past appointments
    public List<Appointment> getPastAppointments() {
        List<Appointment> pastAppointments = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs202project", "root", "B89.e637");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Appointment WHERE DoctorID = ? AND DateTime < NOW()")) {
            stmt.setInt(1, this.doctorID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("AppointmentID");
                Timestamp dateTime = rs.getTimestamp("DateTime");
                String status = rs.getString("Status");
                // Retrieve other appointment details

                Appointment appointment = new Appointment(appointmentID, dateTime.toLocalDateTime(), status); // Create Appointment object with details
                pastAppointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as required
        }
        return pastAppointments;
    }

}

