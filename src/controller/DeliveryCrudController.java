package controller;

import database.DBConnection;
import model.Delivery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveryCrudController {
    public boolean addDelivery(String orderId,String employeeId,String employeeName) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Delivery VALUES (?,?,?)");
        stm.setString(1,orderId);
        stm.setString(2,employeeId);
        stm.setString(3,employeeName);
        return stm.executeUpdate()>0;
    }

    public Delivery getDeliveryDetails(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Delivery WHERE orderId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,orderId);
        ResultSet resultSet = stm.executeQuery();
        if(resultSet.next()){
            return new Delivery(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }else{
            return null;
        }
    }

    public boolean updateDelivery(String orderId,String riderId,String riderName) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Delivery SET orderId=?,riderId=?,riderName=? WHERE orderId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,orderId);
        stm.setString(2,riderId);
        stm.setString(3,riderName);
        stm.setString(4,orderId);
        return stm.executeUpdate()>0;
    }
}
