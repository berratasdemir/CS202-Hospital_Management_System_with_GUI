package ozu_cs202_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomAssignment {
    private int roomAssignmentID;
    private boolean availability;

    public RoomAssignment(int roomAssignmentID, boolean availability) {
        this.roomAssignmentID = roomAssignmentID;
        this.availability = availability;
    }

    public RoomAssignment(Appointment appointment, Room room, Doctor doctor, Nurse nurse) {
    }

    public int getRoomAssignmentID() {
        return roomAssignmentID;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setRoomAssignmentID(int roomAssignmentID) {
        this.roomAssignmentID = roomAssignmentID;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void saveToDatabase(Connection connection) {
        String sql = "INSERT INTO RoomAssignment (RoomAssignmentID, Availability) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, this.roomAssignmentID);
            preparedStatement.setBoolean(2, this.availability);

            preparedStatement.executeUpdate();
            System.out.println("Room assignment details added to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateInDatabase(Connection connection) {
        String sql = "UPDATE RoomAssignment SET Availability = ? WHERE RoomAssignmentID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, this.availability);
            preparedStatement.setInt(2, this.roomAssignmentID);

            preparedStatement.executeUpdate();
            System.out.println("Room assignment details updated in the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     public static RoomAssignment getByIdFromDatabase(Connection connection, int roomAssignmentID) {
        String sql = "SELECT * FROM RoomAssignment WHERE RoomAssignmentID = ?";
        RoomAssignment roomAssignment = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, roomAssignmentID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    roomAssignment = extractRoomAssignmentFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomAssignment;
    }


    public static List<RoomAssignment> getAllFromDatabase(Connection connection) {
        String sql = "SELECT * FROM RoomAssignment";
        List<RoomAssignment> roomAssignments = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                RoomAssignment roomAssignment = extractRoomAssignmentFromResultSet(resultSet);
                roomAssignments.add(roomAssignment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomAssignments;
    }


    private static RoomAssignment extractRoomAssignmentFromResultSet(ResultSet resultSet) throws SQLException {
        int roomAssignmentID = resultSet.getInt("RoomAssignmentID");
        boolean availability = resultSet.getBoolean("Availability");

        return new RoomAssignment(roomAssignmentID, availability);
    }
}

