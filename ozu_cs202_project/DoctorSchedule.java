package ozu_cs202_project;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DoctorSchedule {
    private int scheduleID;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String type;
    private int doctorID;
    private Connection connection;

    // Constructor that accepts a Connection instance
    public DoctorSchedule(Connection connection) {
        this.connection = connection;
    }


    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/cs202project";
        String user = "root";
        String password = "Ccs2002pwxyz";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Connected to the database!");

                DoctorSchedule doctorSchedule = new DoctorSchedule(connection);

                // Replace 10 with an existing ozu_cs202_project.Doctor ID for testing
                int doctorID = 10;
                List<DoctorSchedule> doctorSchedules = doctorSchedule.getDoctorSchedules(doctorID);

                // Displaying retrieved doctor schedules
                System.out.println("ozu_cs202_project.Doctor Schedules for ozu_cs202_project.Doctor ID " + doctorID + ":");
                for (DoctorSchedule schedule : doctorSchedules) {
                    int scheduleID = schedule.getScheduleID();
                    LocalDateTime startDate = schedule.getStartDate();
                    LocalDateTime endDate = schedule.getEndDate();
                    String type = schedule.getType();

                    System.out.println("Schedule ID: " + scheduleID +
                            ", Start Date: " + startDate +
                            ", End Date: " + endDate +
                            ", Type: " + type);
                }
            } else {
                System.out.println("Failed to make a connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public DoctorSchedule(int scheduleID, LocalDateTime startDate, LocalDateTime endDate, String type, int doctorID) {
        this.scheduleID = scheduleID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.doctorID = doctorID;
    }

    public void addScheduleToDB(Connection connection) {
        String sql = "INSERT INTO DoctorSchedule (schedule_id, start_date, end_date, type, DoctorID) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, this.scheduleID);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(this.startDate));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(this.endDate));
            preparedStatement.setString(4, this.type);
            preparedStatement.setInt(5, this.doctorID);

            preparedStatement.executeUpdate();
            System.out.println("Schedule added to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as required
        }
    }

    public void removeScheduleFromDB(Connection connection, int scheduleID) {
        String sql = "DELETE FROM DoctorSchedule WHERE schedule_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, scheduleID);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Schedule removed from the database!");
            } else {
                System.out.println("Schedule not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as required
        }
    }

    public void updateScheduleInDB(int scheduleID, LocalDateTime startDate, LocalDateTime endDate, String type, int doctorID) throws SQLException {
        String sql = "UPDATE DoctorSchedule SET start_date = ?, end_date = ?, type = ? WHERE schedule_id = ? AND DoctorID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(startDate));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(endDate));
            preparedStatement.setString(3, type);
            preparedStatement.setInt(4, scheduleID);
            preparedStatement.setInt(5, doctorID);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Schedule updated in the database!");
            } else {
                System.out.println("Schedule with ID " + scheduleID + " not found for ozu_cs202_project.Doctor ID " + doctorID);
            }
        } catch (SQLException e) {
            throw new SQLException("Error updating schedule in the database", e);
        }
    }

    public List<DoctorSchedule> getDoctorSchedules(int doctorID) throws SQLException {
        List<DoctorSchedule> doctorSchedules = new ArrayList<>();
        String sql = "SELECT schedule_id, start_date, end_date, type FROM DoctorSchedule WHERE DoctorID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, doctorID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int scheduleID = resultSet.getInt("schedule_id");
                LocalDateTime startDate = resultSet.getTimestamp("start_date").toLocalDateTime();
                LocalDateTime endDate = resultSet.getTimestamp("end_date").toLocalDateTime();
                String type = resultSet.getString("type");
                DoctorSchedule schedule = new DoctorSchedule(connection);
                schedule.setScheduleID(scheduleID);
                schedule.setStartDate(startDate);
                schedule.setEndDate(endDate);
                schedule.setType(type);
                doctorSchedules.add(schedule);
            }
        } catch (SQLException e) {
            throw new SQLException("Error getting doctor schedules from the database", e);
        }
        return doctorSchedules;
    }

    public List<DoctorSchedule> listAvailableSchedules(String availabilityStatus) throws SQLException {
        List<DoctorSchedule> availableSchedules = new ArrayList<>();
        String sql = "SELECT schedule_id, start_date, end_date, type FROM DoctorSchedule WHERE type = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, availabilityStatus);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int scheduleID = resultSet.getInt("schedule_id");
                LocalDateTime startDate = resultSet.getTimestamp("start_date").toLocalDateTime();
                LocalDateTime endDate = resultSet.getTimestamp("end_date").toLocalDateTime();
                String type = resultSet.getString("type");
                DoctorSchedule schedule = new DoctorSchedule(connection);
                schedule.setScheduleID(scheduleID);
                schedule.setStartDate(startDate);
                schedule.setEndDate(endDate);
                schedule.setType(type);
                availableSchedules.add(schedule);
            }
        } catch (SQLException e) {
            throw new SQLException("Error listing available schedules from the database", e);
        }
        return availableSchedules;
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

