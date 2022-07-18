package controller;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Employee;
import model.EmployeeTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeController {
    public boolean addEmployee(Employee employee) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO Employee VALUES (?,?,?,?,?,?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, employee.getEmployeeID());
            stm.setString(2, employee.getEmployeeName());
            stm.setString(3, employee.getEmployeeAddress());
            stm.setString(4, employee.getEmployeeNIC());
            stm.setString(5, employee.getEmployeeContactNumber());
            stm.setString(6, employee.getJobRole());
            stm.setInt(7, employee.getWorkingHours());

            if (stm.executeUpdate() > 0) {
                if (employee.getJobRole().equals("Cashier")) {
                    if (addLoginData(employee.getEmployeeID(), employee.getUserName(), employee.getPassword(), employee.getJobRole())) {
                        connection.commit();
                        return true;
                    } else {
                        connection.rollback();
                        return false;
                    }
                } else if (employee.getJobRole().equals("Rider")) {
                    if (addDriverData(employee.getEmployeeID(), employee.getDrivingLicenseNumber(), employee.getBikeNo())) {
                        connection.commit();
                        return true;
                    } else {
                        connection.rollback();
                        return false;
                    }
                } else {
                    connection.commit();
                    return true;
                }
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {

        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    public boolean updateEmployee(Employee e) throws SQLException, ClassNotFoundException{
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            String sql = "UPDATE Employee SET employeeId=?,employeeName=?,address=?,employeeNic=?,contact=?,jobRole=?,workingHours=? WHERE employeeId=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, e.getEmployeeID());
            stm.setString(2, e.getEmployeeName());
            stm.setString(3, e.getEmployeeAddress());
            stm.setString(4, e.getEmployeeNIC());
            stm.setString(5, e.getEmployeeContactNumber());
            stm.setString(6, e.getJobRole());
            stm.setInt(7, e.getWorkingHours());
            stm.setString(8,e.getEmployeeID());

            if (stm.executeUpdate() > 0) {
                if (e.getJobRole().equals("Cashier")) {
                    if (updateLoginData(e.getEmployeeID(), e.getUserName(), e.getPassword(), e.getJobRole())) {
                        connection.commit();
                        return true;
                    } else {
                        connection.rollback();
                        return false;
                    }
                } else if (e.getJobRole().equals("Rider")) {
                    if (updateDriverData(e.getEmployeeID(), e.getDrivingLicenseNumber(), e.getBikeNo())) {
                        connection.commit();
                        return true;
                    } else {
                        connection.rollback();
                        return false;
                    }
                } else {
                    connection.commit();
                    return true;
                }
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException | ClassNotFoundException exception) {

        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    public boolean deleteEmployee(String employeeId, String jobRole){
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            String sql = "DELETE FROM Employee WHERE employeeId=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, employeeId);


            if (stm.executeUpdate() > 0) {
                if (jobRole.equals("Cashier")) {

                    connection.commit();
                    return true;

                } else if (jobRole.equals("Rider")) {

                    connection.commit();
                    return true;

                } else {
                    connection.commit();
                    return true;
                }
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException | ClassNotFoundException exception) {

        }
        finally {
            try{
                connection.setAutoCommit(true);
            }catch (SQLException e){

            }
        }
        return false;
    }

    private boolean addDriverData(String employeeID, String drivingLicenseNumber, String bikeNo) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Driver VALUES (?,?,?)";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1, bikeNo);
        stm.setString(2, drivingLicenseNumber);
        stm.setString(3, employeeID);
        return stm.executeUpdate() > 0;
    }

    private boolean addLoginData(String employeeID, String userName, String password, String jobRole) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Login VALUES (?,?,?,?)";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1, password);
        stm.setString(2, userName);
        stm.setString(3, employeeID);
        stm.setString(4, jobRole);
        return stm.executeUpdate() > 0;
    }

    private boolean updateLoginData(String employeeID, String userName, String password, String jobRole) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Login SET password=?,userName=?,employeeId=?,jobRole=? WHERE employeeId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1, password);
        stm.setString(2, userName);
        stm.setString(3, employeeID);
        stm.setString(4, jobRole);
        stm.setString(5, employeeID);
        return stm.executeUpdate() > 0;
    }

    private boolean updateDriverData(String employeeID, String drivingLicenseNumber, String bikeNo) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Driver SET driverBikeNumber=?,driverLisceneNo=?,employeeId=? WHERE employeeId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1, bikeNo);
        stm.setString(2, drivingLicenseNumber);
        stm.setString(3, employeeID);
        stm.setString(4, employeeID);
        return stm.executeUpdate() > 0;
    }

    private boolean deleteLoginData(String employeeId, String jobRole) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Login WHERE employeeId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1, employeeId);
        return stm.executeUpdate() > 0;
    }

    private boolean deleteDriverData(String employeeId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Driver WHERE employeeId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1, employeeId);
        return stm.executeUpdate() > 0;
    }

    public static String getLastEmployeeId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("SELECT employeeId FROM Employee ORDER BY employeeId DESC LIMIT 1");
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    public ArrayList<EmployeeTM> loadAllEmployees() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Employee";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        ArrayList<EmployeeTM> tmList = new ArrayList<>();
        while (result.next()){
            tmList.add(new EmployeeTM(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getInt(7)
            ));
        }
        return tmList;
    }

    public Employee getCashiers(EmployeeTM tm) throws SQLException, ClassNotFoundException {
        String sql = "SELECT e.employeeId,e.employeeName,e.address,e.employeeNic,e.contact,e.jobRole,e.workingHours,l.userName,l.password FROM Employee e INNER JOIN Login l ON e.employeeId=l.employeeId WHERE e.employeeId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,tm.getEmployeeID());
        ResultSet result = stm.executeQuery();
        if(result.next()){
            return new Employee(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getInt(7),
                    result.getString(8),
                    result.getString(9),
                    0.00
            );
        }
        return null;
    }

    public Employee getRiders(EmployeeTM tm) throws SQLException, ClassNotFoundException {
        String sql = "SELECT e.employeeId,e.employeeName,e.address,e.employeeNic,e.contact,e.jobRole,e.workingHours,d.driverBikeNumber,d.driverLisceneNo FROM Employee e INNER JOIN Driver d ON e.employeeId=d.employeeId WHERE e.employeeId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,tm.getEmployeeID());
        ResultSet result = stm.executeQuery();
        if(result.next()){
            return new Employee(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getInt(7),
                    result.getString(8),
                    result.getString(9),
                    true
            );
        }
        return null;
    }

    public Employee getOthers(EmployeeTM tm) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Employee WHERE employeeId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,tm.getEmployeeID());
        ResultSet result = stm.executeQuery();
        if(result.next()){
            return new Employee(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getInt(7),
                    true
            );
        }
        return null;
    }

    public ObservableList<Employee> getDrivers() throws SQLException, ClassNotFoundException {
        String sql = "SELECT e.employeeId ,e.employeeName FROM Employee e INNER JOIN Driver d ON e.employeeId=d.employeeId";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        ObservableList<Employee> obList = FXCollections.observableArrayList();
        while (result.next()){
            obList.add(new Employee(
                    result.getString(1),
                    result.getString(2)
            ));
        }
        return obList;
    }

    public String getCashierId(String employeeName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT employeeId FROM Employee WHERE employeeName=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,employeeName);
        ResultSet result = stm.executeQuery();
        if(result.next()){
            return result.getString(1);
        }
        return null;
    }

    public Employee getEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Employee WHERE employeeId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,employeeId);
        ResultSet result = stm.executeQuery();
        if(result.next()){
            return new Employee(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getInt(7),
                    true
            );
        }else{
            return null;
        }
    }
}
