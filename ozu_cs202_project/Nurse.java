package ozu_cs202_project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;


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
        String password = "B89.e637";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Connected to the database!");

                Nurse nurse = new Nurse(
                        connection, 7, "farni2@creativecommons.org", "Kalli", "Beauvais", "yJ6%ttezw#", "Nurse",
                        7, "Pediatrics" // ozu_cs202_project.Nurse-specific attributes
                );

                List<Room> assignedRooms = nurse.viewUpcomingAssignedRooms();

                System.out.println("Upcoming Assigned Rooms for Nurse:");
                for (Room room : assignedRooms) {
                    System.out.println("Room ID: " + room.getRoomID() + ", Room Type: " + room.getRoomType());
                }

            } else {
                System.out.println("Failed to make a connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNurseDetailsToDB() {
        String url = "jdbc:mysql://localhost:3306/cs202project";
        String user = "root";
        String password = "B89.e637";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                String sql = "INSERT INTO Nurse (NurseID, department, UserID) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, this.nurseID);
                    preparedStatement.setString(2, this.department);
                    preparedStatement.setInt(3, this.getUserID());
                    preparedStatement.executeUpdate();
                    System.out.println("Nurse details added to the database!");
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

    public List<Room> viewRoomAvailability() {
        List<Room> availableRooms = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs202project", "root", "B89.e637")) {
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

        }
        return availableRooms;
    }

    public List<Room> viewUpcomingAssignedRooms() {
        List<Room> assignedRooms = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs202project", "root", "B89.e637")) {
            String sql = "SELECT Room.RoomID, Room.RoomType, Room.Availability " +
                    "FROM Room " +
                    "JOIN RoomAssignment ON Room.RoomID = RoomAssignment.RoomID " +
                    "JOIN Nurse ON Nurse.NurseID = RoomAssignment.NurseID " +
                    "JOIN Appointment ON RoomAssignment.AppointmentID = Appointment.AppointmentID " +
                    "WHERE RoomAssignment.Availability = 1 AND RoomAssignment.NurseID = ? " +
                    "AND Appointment.DateTime > ?"; // Parameterized query

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, this.nurseID);

                LocalDateTime currentDateTime = LocalDateTime.now();

                Timestamp timestamp = Timestamp.valueOf(currentDateTime);

                stmt.setTimestamp(2, timestamp);

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

        }
        return assignedRooms;
    }
}
