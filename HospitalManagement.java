import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class HospitalManagement {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/cs202project";
        String user = "root";
        String password = "B89.e637";



        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Connected to the database!");



                Patient patient = new Patient(
                        5, "patient@email.com", "John", "Doe", "password", "Patient",
                        123, "Previous medical history"
                );

                // Test addPatientDetailsToDB method
                patient.addPatientDetailsToDB();

                // Test searchDoctorsByExpertise method
                List<Doctor> doctorsByExpertise = patient.searchDoctorsByExpertise("Cardiology");
                System.out.println("Doctors with Cardiology expertise:");
                for (Doctor doctor : doctorsByExpertise) {
                    System.out.println(doctor);
                }

                // Test filterAppointmentsByDoctorExpertise method
                List<Appointment> filteredAppointments = patient.filterAppointmentsByDoctorExpertise("Cardiology");
                System.out.println("Appointments with doctors having Cardiology expertise:");
                for (Appointment appointment : filteredAppointments) {
                    System.out.println(appointment);
                }

                // Test searchDoctorsByAvailableHoursOnDay method
                /*List<Doctor> doctorsByAvailableHours = patient.searchDoctorsByAvailableHoursOnDay(1, 9, 17);
                System.out.println("Doctors available on Monday between 9 AM and 5 PM:");
                for (Doctor doctor : doctorsByAvailableHours) {
                    System.out.println(doctor);
                }*/

                // Test searchDoctorsByAvailableHoursWithinRange method
                List<Doctor> doctorsByAvailableHoursRange = patient.searchDoctorsByAvailableHoursWithinRange(1, 5, 9, 17);
                System.out.println("Doctors available between Monday to Friday, 9 AM to 5 PM:");
                for (Doctor doctor : doctorsByAvailableHoursRange) {
                    System.out.println(doctor);
                }

                // Test bookAppointment method
                // Assuming there is an appointment with ID 456 that the patient wants to book
                patient.bookAppointment(456);

                // Test cancelAppointment method
                // Assuming there is an appointment with ID 789 that the patient wants to cancel
                //patient.cancelAppointment(789);


            } else {
                System.out.println("Failed to make a connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Methods for handling user registration and login
    // Methods for each user type (Patient, Manager, Nurse, Doctor) to perform specific actions
    // Methods for appointment management
    // Methods for room management
    // Methods for statistics generation
}
