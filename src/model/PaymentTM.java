package model;

public class PaymentTM {
    private String paymentId ;
    private String employeeId;
    private  String employeeName;
    private String paymentDate;
    private String post;
    private double workingHours;
    private double totalSalary;

    public PaymentTM() {

    }

    public PaymentTM(String paymentId, String employeeId, String employeeName, String paymentDate, String post, double workingHours, double totalSalary) {
        this.paymentId = paymentId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.paymentDate = paymentDate;
        this.post = post;
        this.workingHours = workingHours;
        this.totalSalary = totalSalary;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
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

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(double workingHours) {
        this.workingHours = workingHours;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(double totalSalary) {
        this.totalSalary = totalSalary;
    }
}
