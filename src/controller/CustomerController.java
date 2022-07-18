package controller;

import database.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerController {
    public String getCustomerNumberOnOrderID(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT c.contactNumber FROM Customer c INNER JOIN `Order` o ON c.customerId=o.customerId WHERE o.orderId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,orderId);
        ResultSet resultSet = stm.executeQuery();
        if(resultSet.next()){
            return resultSet.getString(1);
        }else{
            return null;
        }
    }
}
