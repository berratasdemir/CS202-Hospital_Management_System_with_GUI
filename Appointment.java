import java.time.LocalDateTime;

public class Appointment {
    private int appointmentID;
    private LocalDateTime dateTime;
    private String status;

    public Appointment(int appointmentID, LocalDateTime dateTime, String status) {
        this.appointmentID = appointmentID;
        this.dateTime = dateTime;
        this.status = status;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

