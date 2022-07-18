package controller;

import database.DBConnection;
import model.Payment;
import net.sf.jasperreports.components.table.DesignBaseCell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeSalaryCrudController {
    public String getLastPaymentId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("SELECT paymentId FROM Payment ORDER BY paymentId DESC LIMIT 1");
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    public ArrayList<Payment> getAllPayments() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Payment WHERE MONTHNAME(paymentDate)=MONTHNAME(CURRENT_DATE )";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        ArrayList<Payment> list = new ArrayList<>();
        while (result.next()){
            list.add(new Payment(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getDouble(6),
                    result.getDouble(7)
            ));
        }
        return list;
    }

    public double getAllPays(String day) throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(totalSalary) FROM Payment WHERE paymentDate=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,day);
        double expenses = 0;
        ResultSet result = stm.executeQuery();
        while (result.next()){
            expenses = result.getDouble(1);
        }
        return expenses;
    }

    public double getAllPays(String year,String month) throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(totalSalary) FROM Payment WHERE YEAR(paymentDate)=? && MONTHNAME(paymentDate)=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,year);
        stm.setString(2,month);
        double expenses = 0;
        ResultSet result = stm.executeQuery();
        while (result.next()){
            expenses = result.getDouble(1);
        }
        return expenses;
    }

    public double getAnuallySalaryPayments(int year) throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(totalSalary) FROM Payment WHERE YEAR(paymentDate)=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setInt(1,year);
        double expenses = 0;
        ResultSet result = stm.executeQuery();
        while (result.next()){
            expenses = result.getDouble(1);
        }
        return expenses;
    }
}
