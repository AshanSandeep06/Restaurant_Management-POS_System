package model;

public class Password {
    private String employeeId;
    private String employeeName;
    private String userName;
    private String password;

    public Password() {

    }

    public Password(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Password(String employeeId, String employeeName, String userName, String password) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.userName = userName;
        this.password = password;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Password{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
