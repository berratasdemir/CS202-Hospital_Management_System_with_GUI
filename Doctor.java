public class Doctor extends User {
    private int doctorID;
    private String expertise;

    public Doctor(int userID, String email, String firstName, String lastName, String password, String userType,
                  int doctorID, String expertise) {
        super(userID, email, firstName, lastName, password, userType);
        this.doctorID = doctorID;
        this.expertise = expertise;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }
}

