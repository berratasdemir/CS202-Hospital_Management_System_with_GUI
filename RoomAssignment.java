public class RoomAssignment {
    private int roomAssignmentID;
    private boolean availability;

    public RoomAssignment(int roomAssignmentID, boolean availability) {
        this.roomAssignmentID = roomAssignmentID;
        this.availability = availability;
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
}

