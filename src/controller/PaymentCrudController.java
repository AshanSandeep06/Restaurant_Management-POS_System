package controller;

import database.DBConnection;
import model.DateAndTotal;
import model.Payment;
import model.Reservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentCrudController {
    public ArrayList<Double> getWorkedHours(String employeeId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT workingHours FROM Attendance WHERE employeeId=? AND MONTHNAME(attendDate)=MONTHNAME(CURRENT_DATE());";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,employeeId);
        ResultSet result = stm.executeQuery();
        ArrayList<Double> list= new ArrayList<>();
        while(result.next()){
            list.add(result.getDouble(1));
        }
        return list;
    }

    public boolean commitPayment(Payment payment) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Payment VALUES (?,?,?,?,?,?,?)";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,payment.getPaymentId());
        stm.setString(2,payment.getEmployeeId());
        stm.setString(3,payment.getEmployeeName());
        stm.setString(4,payment.getPaymentDate());
        stm.setString(5,payment.getPost());
        stm.setDouble(6,payment.getWorkingHours());
        stm.setDouble(7,payment.getTotalSalary());
        return stm.executeUpdate()>0;
    }

    public ArrayList<DateAndTotal> getTotalSalaries() throws SQLException, ClassNotFoundException {
        ArrayList<DateAndTotal> list = new ArrayList<>();
        String sql = "SELECT paymentDate,totalSalary FROM Payment";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        while (result.next()){
            list.add(new DateAndTotal(
                    result.getString(1),
                    result.getDouble(2)
            ));
        }
        return list;
    }
}
