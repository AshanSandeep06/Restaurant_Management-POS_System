package controller;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Discount;
import model.DiscountTM;
import model.Food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountController {
    public boolean addDiscount(Discount discount){
        Connection connection=null;
        try{
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO Discount VALUES (?,?,?,?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, discount.getDiscountId());
            stm.setDouble(2,discount.getDiscountPrice());
            stm.setString(3, discount.getFoodId());
            stm.setString(4, discount.getStartDate());
            stm.setString(5, discount.getCloseDate());

            if (stm.executeUpdate() > 0) {
                if (calculateUnitPrice(discount.getFoodId(), discount.getDiscountPrice())) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }

        }catch (SQLException | ClassNotFoundException e){

        }
        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    public boolean updateDiscount(Discount discount){
        Connection connection=null;
        try{
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            String sql = "UPDATE Discount SET discountId=?,discountPrice=?,foodId=?,startDate=?,closeDate=? WHERE discountId=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, discount.getDiscountId());
            stm.setDouble(2,discount.getDiscountPrice());
            stm.setString(3, discount.getFoodId());
            stm.setString(4, discount.getStartDate());
            stm.setString(5, discount.getCloseDate());
            stm.setString(6,discount.getDiscountId());

            if (stm.executeUpdate() > 0) {
                if (calculateUnitPrice(discount.getFoodId(), discount.getCalculatedDisPrice())) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }

        }catch (SQLException | ClassNotFoundException e){

        }

        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    public boolean deleteDiscount(String discountId, String foodId, double discountPrice){
        Connection connection=null;
        try{

            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            String sql = "DELETE FROM Discount WHERE discountId=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, discountId);

            if (stm.executeUpdate() > 0) {
                if (deletedDiscountAndAddUnitPrice(foodId,discountPrice)) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }

        }catch (SQLException | ClassNotFoundException e){

        }

        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    public boolean deletedDiscountAndAddUnitPrice(String foodId, double discountPrice) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Item SET unitPrice=unitPrice+? WHERE foodId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setDouble(1,discountPrice);
        stm.setString(2,foodId);
        return stm.executeUpdate()>0;
    }

    public boolean calculateUnitPrice(String foodId, double unitPrice) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Item SET unitPrice=unitPrice-? WHERE foodId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setDouble(1,unitPrice);
        stm.setString(2,foodId);
        return stm.executeUpdate()>0;
    }

    public String getDiscountId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT discountId FROM Discount ORDER BY discountId DESC LIMIT 1";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        if (result.next()) {
            return result.getString(1);
        }
        return null;
    }

    public ObservableList<DiscountTM> getDiscountData() throws SQLException, ClassNotFoundException {
        //String sql = "SELECT * FROM Discount";
        String sql = "SELECT d.discountId,d.discountPrice,f.description,d.startDate,d.closeDate FROM Discount d INNER JOIN Item f ON d.foodId=f.foodId";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        ObservableList<DiscountTM> obList = FXCollections.observableArrayList();
        while (result.next()){
            obList.add(new DiscountTM(
                    result.getString(1),
                    result.getDouble(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5)
            ));
        }
        return obList;
    }

    public Food getFoodId(String foodName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Item WHERE description=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,foodName);
        ResultSet result = stm.executeQuery();
        if(result.next()){
            return new Food(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4),
                    result.getInt(5)
            );
        }
        return null;
    }

    public double getDiscountPrice(String discountId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT discountPrice FROM Discount WHERE discountId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,discountId);
        ResultSet resultSet = stm.executeQuery();
        if(resultSet.next()){
            return resultSet.getDouble(1);
        }
        return 0.00;
    }
}
