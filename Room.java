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
}

