package controller;

import database.DBConnection;
import model.DateAndTotal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FinanceController {
    /*public boolean setIncome_Expense_NetIncome() {
        try{
            ArrayList<DateAndTotal> incomeList = new OrderCrudController().getOrderGrandTotals();
            ArrayList<DateAndTotal> expenseList = new PaymentCrudController().getTotalSalaries();

            for(DateAndTotal tm : incomeList){
                String sql = "INSERT INTO Finance VALUES (?,?,?,?)";
                PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
                stm.setString(1,tm.getOrderDate());
                stm.setDouble(2,tm.getGrandTotal());
                stm.setDouble(3,0.00);
                stm.setDouble(4,0.00);
                stm.executeUpdate();
            }

            for(DateAndTotal tm : expenseList){
                String sql = "INSERT INTO Finance VALUES (?,?,?,?)";
                PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
                stm.setString(1,tm.getOrderDate());
                stm.setDouble(2,0.00);
                stm.setDouble(3,tm.getGrandTotal());
                stm.setDouble(4,0.00);
                stm.executeUpdate();
            }
            return true;


        }catch (SQLException | ClassNotFoundException e){
            return false;
        }
    }*/

    public void setExpenses(String day, double expense) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO `Expense` VALUES (?,?)");
        stm.setString(1,day);
        stm.setDouble(2,expense);
        stm.executeUpdate();
    }

    public double getExpenses(String day) throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(expenseValue) FROM `Expense` WHERE date=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,day);
        double expenses = 0;
        ResultSet result = stm.executeQuery();
        while (result.next()){
            expenses = result.getDouble(1);
        }
        return expenses;
    }

    public double getExpenses(String year,String month) throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(expenseValue) FROM `Expense` WHERE YEAR(date)=? && MONTHNAME(date)=?";
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

    public double getAnnuallyExpenses(int year) throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(expenseValue) FROM `Expense` WHERE YEAR(date)=?";
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
