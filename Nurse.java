public class Nurse extends User {
    private int nurseID;
    private String department;

    public Nurse(int userID, String email, String firstName, String lastName, String password, String userType,
                 int nurseID, String department) {
        super(userID, email, firstName, lastName, password, userType);
        this.nurseID = nurseID;
        this.department = department;
    }

    public int getNurseID() {
        return nurseID;
    }

    public String getDepartment() {
        return department;
    }

    public void setNurseID(int nurseID) {
        this.nurseID = nurseID;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}

