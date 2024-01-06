import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Manager extends User {
    private int managerID;

    public Manager(int userID, String email, String firstName, String lastName, String password, String userType,
                   int managerID) {
        super(userID, email, firstName, lastName, password, userType);
        this.managerID = managerID;
    }


    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/cs202project";
        String user = "root";
        String password = "Ccs2002pwxyz";

        Manager manager = new Manager(20, "manager@email.com", "Can", "Sevgican", "pw", "Manager", 20);


        Doctor doctor = new Doctor(
                getConnection(), 22, "proberra34@email.com", "ccc", "cccc", "cc", "Doctor",
                22, "Moktor" // Doctor-specific attributes
        );

        // Adding doctor to the database using Manager's method
        manager.addDoctorToDB(doctor);

        // Testing viewPatientStatistics method
        manager.viewPatientStatistics();

        // Testing viewAppointmentRoomStatistics method
        manager.viewAppointmentRoomStatistics();

        // Testing viewNurseAssignmentRatios method
        manager.viewNurseAssignmentRatios();

        // Testing viewMostBookedRoomAndDoctorStatistics method
        manager.viewMostBookedRoomAndDoctorStatistics();
    }


    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public void addDoctorToDB(Doctor doctor) {
        doctor.addUser(doctor);
        doctor.addDoctorDetailsToDB();
    }

    public void addNurseToDB(Nurse nurse) {
        String insertNurseQuery = "INSERT INTO Nurse (NurseID, department, UserID) VALUES (?, ?, ?)";

        try (Connection connection = getConnection(); // Implement your connection logic here
             PreparedStatement preparedStatement = connection.prepareStatement(insertNurseQuery)) {

            preparedStatement.setInt(1, nurse.getNurseID());
            preparedStatement.setString(2, nurse.getDepartment());
            preparedStatement.setInt(3, nurse.getUserID());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Nurse added successfully!");
            } else {
                System.out.println("Failed to add nurse.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    public void addPatientToDB(Patient patient) {
        String sql = "INSERT INTO Patient (PatientID, medical_history, UserID) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, patient.getPatientID());
            preparedStatement.setString(2, patient.getMedicalHistory());
            preparedStatement.setInt(3, patient.getUserID());

            preparedStatement.executeUpdate();
            System.out.println("Patient details added to the database!");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public void viewPatientStatistics() {
        String sql = "SELECT COUNT(*), department FROM Patient " +
                "INNER JOIN User ON Patient.UserID = User.UserID " +
                "INNER JOIN Nurse ON User.UserID = Nurse.UserID " +
                "GROUP BY department";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int patientCount = resultSet.getInt(1);
                String department = resultSet.getString(2);
                System.out.println("Department: " + department + ", Patient Count: " + patientCount);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public void viewAppointmentRoomStatistics() {
        String sql = "SELECT Room.RoomID, COUNT(Appointment.RoomID) as AppointmentCount " +
                "FROM Room LEFT JOIN Appointment ON Room.RoomID = Appointment.RoomID " +
                "GROUP BY Room.RoomID";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int roomID = resultSet.getInt("RoomID");
                int appointmentCount = resultSet.getInt("AppointmentCount");
                System.out.println("Room ID: " + roomID + ", Appointment Count: " + appointmentCount);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public void viewNurseAssignmentRatios() {
        String sql = "SELECT Nurse.department, COUNT(RoomAssignment.NurseID) AS AssignmentCount " +
                "FROM Nurse LEFT JOIN RoomAssignment ON Nurse.NurseID = RoomAssignment.NurseID " +
                "GROUP BY Nurse.department";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String department = resultSet.getString("department");
                int assignmentCount = resultSet.getInt("AssignmentCount");
                System.out.println("Department: " + department + ", Assignment Count: " + assignmentCount);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public void viewMostBookedRoomAndDoctorStatistics() {
        String sql = "SELECT DoctorID, COUNT(*) as RoomBookingCount " +
                "FROM Appointment WHERE RoomID IS NOT NULL " +
                "GROUP BY DoctorID ORDER BY RoomBookingCount DESC LIMIT 1";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int doctorID = resultSet.getInt("DoctorID");
                int roomBookingCount = resultSet.getInt("RoomBookingCount");
                System.out.println("Doctor ID: " + doctorID + ", Room Booking Count: " + roomBookingCount);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }



    // Implement getConnection() method to get database connection
    // You can use your preferred method for creating a database connection
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/cs202project"; // Update with your database URL
        String user = "root"; // Replace with your database username
        String password = "Ccs2002pwxyz"; // Replace with your database password

        return DriverManager.getConnection(url, user, password);
    }
}

