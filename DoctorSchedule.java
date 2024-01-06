import java.time.LocalDateTime;

public class DoctorSchedule {
    private int scheduleID;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String type;

    public DoctorSchedule(int scheduleID, LocalDateTime startDate, LocalDateTime endDate, String type) {
        this.scheduleID = scheduleID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getType() {
        return type;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setType(String type) {
        this.type = type;
    }
}

