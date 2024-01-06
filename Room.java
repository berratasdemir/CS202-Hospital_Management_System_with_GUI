import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private int roomID;
    private String roomType;
    private boolean availability;

    public Room(int roomID, String roomType, boolean availability) {
        this.roomID = roomID;
        this.roomType = roomType;
        this.availability = availability;
    }


    public int getRoomID() {
        return roomID;
    }

    public String getRoomType() {
        return roomType;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Room ID: " + roomID + ", Room Type: " + roomType + ", Availability: " + (availability ? "Available" : "Not Available");
    }


    // Method to save room details to the database
    public void saveToDatabase(Connection connection) {
        String sql = "INSERT INTO Room (RoomID, RoomType, Availability) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, this.roomID);
            preparedStatement.setString(2, this.roomType);
            preparedStatement.setBoolean(3, this.availability);

            preparedStatement.executeUpdate();
            System.out.println("Room details added to the database!");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    // Method to update room details in the database
    public void updateInDatabase(Connection connection) {
        String sql = "UPDATE Room SET RoomType=?, Availability=? WHERE RoomID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, this.roomType);
            preparedStatement.setBoolean(2, this.availability);
            preparedStatement.setInt(3, this.roomID);

            preparedStatement.executeUpdate();
            System.out.println("Room details updated in the database!");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    // Method to delete room from the database by RoomID
    public void deleteFromDatabase(Connection connection) {
        String sql = "DELETE FROM Room WHERE RoomID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, this.roomID);

            preparedStatement.executeUpdate();
            System.out.println("Room deleted from the database!");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    // Method to get a list of all rooms from the database
    public static List<Room> getAllRooms(Connection connection) {
        String sql = "SELECT * FROM Room";
        List<Room> roomList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int roomID = resultSet.getInt("RoomID");
                String roomType = resultSet.getString("RoomType");
                boolean availability = resultSet.getBoolean("Availability");

                Room room = new Room(roomID, roomType, availability);
                roomList.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return roomList;
    }

    // Method to get a list of available rooms from the database
    public static List<Room> getAvailableRooms(Connection connection) {
        String sql = "SELECT * FROM Room WHERE Availability = true";
        List<Room> availableRooms = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int roomID = resultSet.getInt("RoomID");
                String roomType = resultSet.getString("RoomType");
                boolean availability = resultSet.getBoolean("Availability");

                Room room = new Room(roomID, roomType, availability);
                availableRooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return availableRooms;
    }
}
