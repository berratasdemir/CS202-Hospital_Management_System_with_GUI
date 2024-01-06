import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Nurse extends User {
    private int nurseID;
    private String department;

    public Nurse(Connection connection, int userID, String email, String firstName, String lastName, String password, String userType,
                 int nurseID, String department) {
        super(userID, email, firstName, lastName, password, userType);
        this.connection = connection;
        this.nurseID = nurseID;
        this.department = department;
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/cs202project";
        String user = "root";
        String password = "your_password"; // Change this to your actual database password

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Connected to the database!");

                // Creating a Nurse instance for testing
                Nurse nurse = new Nurse(
                        connection, 10, "nurse@example.com", "Jane", "Doe", "nurse_password", "Nurse",
                        10, "Cardiology" // Nurse-specific attributes
                );

                // Adding the user details using the inherited addUser method
                nurse.addUser(nurse);

                // Testing viewUpcomingAssignedRooms method
                List<Room> assignedRooms = nurse.viewUpcomingAssignedRooms();

                System.out.println("Upcoming Assigned Rooms for Nurse:");
                for (Room room : assignedRooms) {
                    System.out.println("Room ID: " + room.getRoomID() + ", Room Type: " + room.getRoomType());
                }

                // Other methods of Nurse can be tested similarly

            } else {
                System.out.println("Failed to make a connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getNurseID() {
        return nurseID;
    }

    public String getDepartment() {
        return department;
    }

    public void setNurseID(int nurseID) {
        this.nurseID = nurseID;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // Method to view room availability without patient information
    public List<Room> viewRoomAvailability() {
        List<Room> availableRooms = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs202project", "root", "Ccs2002pwxyz")) {
            String sql = "SELECT * FROM Room WHERE Availability = 1";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int roomId = rs.getInt("RoomID");
                    String roomType = rs.getString("RoomType");
                    boolean availability = rs.getBoolean("Availability");

                    Room room = new Room(roomId, roomType, availability);
                    availableRooms.add(room);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
        return availableRooms;
    }

    // Method to view upcoming assigned rooms without patient information
    public List<Room> viewUpcomingAssignedRooms() {
        List<Room> assignedRooms = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs202project", "root", "Ccs2002pwxyz")) {
            String sql = "SELECT Room.RoomID, Room.RoomType, Room.Availability " +
                    "FROM Room " +
                    "JOIN RoomAssignment ON Room.RoomID = RoomAssignment.RoomID " +
                    "JOIN Nurse ON Nurse.NurseID = RoomAssignment.NurseID " +
                    "WHERE RoomAssignment.Availability = 1 AND RoomAssignment.NurseID = ? " +
                    "AND Appointment.DateTime > NOW()";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, this.nurseID); // Assuming nurseID is accessible within the Nurse object

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int roomId = rs.getInt("RoomID");
                    String roomType = rs.getString("RoomType");
                    boolean availability = rs.getBoolean("Availability");

                    Room room = new Room(roomId, roomType, availability);
                    assignedRooms.add(room);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
        return assignedRooms;
    }
}
