package model;

public class Login {
    private String password;
    private String userName;
    private String employeeId;
    private String jobRole;
    private String employeeName;

    public Login() {

    }

    public Login(String password, String userName, String employeeId, String jobRole) {
        this.password = password;
        this.userName = userName;
        this.employeeId = employeeId;
        this.jobRole = jobRole;
    }

    public Login(String employeeName, String jobRole) {
        this.employeeName = employeeName;
        this.jobRole = jobRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    @Override
    public String toString() {
        return userName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
