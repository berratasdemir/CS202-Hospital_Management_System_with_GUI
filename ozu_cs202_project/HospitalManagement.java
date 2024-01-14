package ozu_cs202_project;

import ozu_cs202_project.DoctorSchedule;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.SQLException;

        public class HospitalManagement {


    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/cs202project";
        String user = "root";
        String password = "B89.e637";



        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Connected to the database!");
                DoctorSchedule doctorSchedule = new DoctorSchedule(connection);

            } else {
                System.out.println("Failed to make a connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
