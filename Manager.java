public class Manager extends User {
    private int managerID;

    public Manager(int userID, String email, String firstName, String lastName, String password, String userType,
                   int managerID) {
        super(userID, email, firstName, lastName, password, userType);
        this.managerID = managerID;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }
}

