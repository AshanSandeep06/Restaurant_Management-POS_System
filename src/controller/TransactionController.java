package controller;

import database.DBConnection;
import model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionController {
    public boolean commitsTheTransaction(Transaction transaction) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Transaction VALUES (?,?,?,?,?,?,?)";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,transaction.getTransactionId());
        stm.setString(2,transaction.getCustomerId());
        stm.setString(3,transaction.getOrderId());
        stm.setString(4,transaction.getCashierId());
        stm.setDouble(5,transaction.getTotalAmount());
        stm.setDouble(6,transaction.getPaidAmount());
        stm.setDouble(7,transaction.getBalanceAmount());
        return stm.executeUpdate()>0;
    }

    public String getLastTransactionId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("SELECT transactionId FROM Transaction ORDER BY transactionId DESC LIMIT 1");
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }
}
