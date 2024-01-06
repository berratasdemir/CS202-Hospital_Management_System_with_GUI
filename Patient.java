public class Patient extends User {
    private int patientID;
    private String medicalHistory;

    public Patient(int userID, String email, String firstName, String lastName, String password, String userType,
                   int patientID, String medicalHistory) {
        super(userID, email, firstName, lastName, password, userType);
        this.patientID = patientID;
        this.medicalHistory = medicalHistory;
    }

    public int getPatientID() {
        return patientID;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
}

