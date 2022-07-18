package controller;

import database.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class OrderCrudController {
    public String getLastOrderId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("SELECT orderId FROM `Order` ORDER BY orderId DESC LIMIT 1");
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    public boolean placeOrder(Order order){
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO `Order` VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1,order.getOrderId());
            stm.setString(2,order.getCustomerId());
            stm.setString(3,order.getOrderDate());
            stm.setString(4,order.getOrderTime());
            stm.setString(5,order.getOrderType());
            stm.setDouble(6,order.getSubTotal());
            stm.setDouble(7,order.getDeliveryCharges());
            stm.setDouble(8,order.getGrandTotal());
            stm.setString(9,order.getOrderState());

            if(stm.executeUpdate()>0){
                if(saveOrderDetails(order.getOrderId(),order.getOrderDetails())){
                    connection.commit();
                    return true;
                }else{
                    connection.rollback();
                    return false;
                }
            }else{
                connection.rollback();
                return false;
            }


        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            try{
                connection.setAutoCommit(true);
            }catch (SQLException e){

            }
        }
        return false;
    }

    private boolean saveOrderDetails(String orderId, ArrayList<OrderDetail> orderDetails) throws SQLException, ClassNotFoundException {
        for(OrderDetail detail : orderDetails){
            String sql = "INSERT INTO OrderDetail VALUES (?,?,?,?,?,?)";
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            stm.setString(1,orderId);
            stm.setString(2,detail.getFoodId());
            stm.setString(3,detail.getFoodType());
            stm.setString(4,detail.getFoodDescription());
            stm.setDouble(5,detail.getUnitPrice());
            stm.setInt(6,detail.getQty());
            if(stm.executeUpdate()>0){
                if (!updateQty(detail.getFoodId(), detail.getQty())) {
                    return false;
                }
            }else{
                return false;
            }
        }
        return true;
    }

    public boolean updateQty(String foodId, int qty)  {
        try {
            String sql = "UPDATE Item SET qtyOnHand=qtyOnHand-? WHERE foodId=?";
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            stm.setInt(1, qty);
            stm.setString(2, foodId);
            return stm.executeUpdate() > 0;
        }catch (SQLException | ClassNotFoundException e){
            //System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean orderIsPaid(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE `Order` SET orderState=? WHERE orderId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,"Paid");
        stm.setString(2,orderId);
        return stm.executeUpdate()>0;
    }

    public Order getOrderDetail(String orderId,String orderType) throws SQLException, ClassNotFoundException {
        String sql = "SELECT o.orderId,c.customerId,c.name,o.subTotal,o.deliveryCharges,o.grandTotal,o.orderState FROM `Order` o INNER JOIN Customer c ON o.customerId=c.customerId WHERE orderType=? AND orderId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,orderType);
        stm.setString(2,orderId);
        ResultSet result = stm.executeQuery();
        if(result.next()){
            ArrayList<OrderDetail> arrayList = orderedItems(orderId);
            return new Order(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4),
                    result.getDouble(5),
                    result.getDouble(6),
                    result.getString(7),
                    arrayList
            );
        }else{
            return null;
        }
    }

    private ArrayList<OrderDetail> orderedItems(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM OrderDetail WHERE orderId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ArrayList<OrderDetail> arrayList = new ArrayList<>();
        stm.setString(1,orderId);
        ResultSet resultSet = stm.executeQuery();
        while(resultSet.next()){
            arrayList.add(new OrderDetail(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getInt(6)
            ));
        }
        return arrayList;
    }

    public boolean orderIsNotPaid(String orderId,String orderType) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM `Order` WHERE orderId=? AND orderType=? AND orderState=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,orderId);
        stm.setString(2,orderType);
        stm.setString(3,"Non-Paid");

        ResultSet resultSet = stm.executeQuery();
        if(resultSet.next()){
            return true;
        }
        return false;
    }

    public boolean updateOrder(Order order){
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            String sql = "UPDATE `Order` SET customerId=?,orderType=?,subTotal=?,deliveryCharges=?,grandTotal=?,orderState=?,orderId=? WHERE orderId=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1,order.getCustomerId());
            stm.setString(2,order.getOrderType());
            stm.setDouble(3,order.getSubTotal());
            stm.setDouble(4,order.getDeliveryCharges());
            stm.setDouble(5,order.getGrandTotal());
            stm.setString(6,order.getOrderState());
            stm.setString(7,order.getOrderId());
            stm.setString(8,order.getOrderId());

            if(stm.executeUpdate()>0){
                if(deleteOrderDetails(order.getOrderId())){
                    if(saveOrderDetails(order.getOrderId(),order.getOrderDetails())){
                        connection.commit();
                        return true;
                    }else{
                        connection.rollback();
                        return false;
                    }
                }else{
                    connection.rollback();
                    return false;
                }
            }else{
                connection.rollback();
                return false;
            }


        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            try{
                connection.setAutoCommit(true);
            }catch (SQLException e){

            }
        }
        return false;
    }

    private boolean deleteOrderDetails(String orderId) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> detailArrayList = orderedItems(orderId);
        String sql = "DELETE FROM OrderDetail WHERE orderId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,orderId);
        for(OrderDetail detail : detailArrayList){
            if(!updateQtyPlus(detail.getFoodId(),detail.getQty())){
                return false;
            }
        }
        return stm.executeUpdate()>0;
    }

    public boolean updateQtyPlus(String foodId,int qty){
        try {
            String sql = "UPDATE Item SET qtyOnHand=qtyOnHand+? WHERE foodId=?";
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            stm.setInt(1, qty);
            stm.setString(2, foodId);
            return stm.executeUpdate() > 0;
        }catch (SQLException | ClassNotFoundException e){
            //System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteOrder(String orderId) throws SQLException, ClassNotFoundException {
        try{
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.INFORMATION, "Are you sure ?", ButtonType.YES, ButtonType.NO).showAndWait();

            if(buttonType.get().equals(ButtonType.YES)){
                String sql = "DELETE FROM `Order` WHERE orderId=?";
                PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
                stm.setString(1,orderId);
                    ArrayList<OrderDetail> arrayList = orderedItems(orderId);
                        for(OrderDetail detail : arrayList){
                            if(!updateQtyPlus(detail.getFoodId(),detail.getQty())){
                                return false;
                            }
                        }
                        return stm.executeUpdate()>0;

                } else{
                return false;
            }
        }catch (SQLException | ClassNotFoundException e){

        }
        return false;
    }

    /*public boolean deleteItems(String orderId, ArrayList<CartTM> list) throws SQLException, ClassNotFoundException {
        for(CartTM tm : list){
            String sql = "DELETE FROM OrderDetail WHERE orderId=? ";
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        }
    }*/

    public ArrayList<DateAndTotal> getOrderGrandTotals () throws SQLException, ClassNotFoundException {
        String sql = "SELECT orderDate,grandTotal FROM `order`";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ArrayList<DateAndTotal> list = new ArrayList<>();
        ResultSet result = stm.executeQuery();
        while (result.next()){
            list.add(new DateAndTotal(
                    result.getString(1),
                    result.getDouble(2)
            ));
        }
        return list;
    }

    public double getOrdersIncome(String day) throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(grandTotal) FROM `Order` WHERE orderDate=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,day);
        ResultSet result = stm.executeQuery();
        double ordersIncome = 0;
        while(result.next()){
            ordersIncome = result.getDouble(1);
        }
        return ordersIncome;
    }

    public double getOrdersIncome(String year, String month) throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(grandTotal) FROM `Order` WHERE YEAR(orderDate)=? AND MONTHNAME(orderDate)=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,year);
        stm.setString(2,month);
        ResultSet result = stm.executeQuery();
        double orderIncome = 0;
        while(result.next()){
            orderIncome = result.getDouble(1);
        }
        return orderIncome;
    }

    public double getAnullayOrdersIncome(int year) throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(grandTotal) FROM `Order` WHERE YEAR(orderDate)=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setInt(1,year);
        ResultSet result = stm.executeQuery();
        double orderIncome = 0;
        while(result.next()){
            orderIncome = result.getDouble(1);
        }
        return orderIncome;
    }

    public int totalOrderCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(orderId) FROM `Order`";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        int totalOrdersCount = 0;
        ResultSet result = stm.executeQuery();
        while(result.next()){
            totalOrdersCount+=result.getInt(1);
        }
        return totalOrdersCount;
    }

    public int paidOrdersCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(orderId) FROM `Order` WHERE orderState='Paid'";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        int paidOrdersCount = 0;
        ResultSet result = stm.executeQuery();
        while(result.next()){
            paidOrdersCount+=result.getInt(1);
        }
        return paidOrdersCount;
    }

    public int pendingDeliveries() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(orderId) FROM `Order` WHERE orderType='Delivery' AND orderState='Non-Paid'";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        int pendingDeliCount = 0;
        ResultSet result = stm.executeQuery();
        while(result.next()){
            pendingDeliCount+=result.getInt(1);
        }
        return pendingDeliCount;
    }
}
