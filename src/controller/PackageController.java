package controller;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Food;
import model.Package;
import model.PackageDetail;
import util.CrudUtil;

import javax.print.DocFlavor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PackageController {
    public String getPackageId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT packageId FROM Package ORDER BY packageId DESC LIMIT 1";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        if (result.next()) {
            return result.getString(1);
        }
        return null;
    }

    public ObservableList<Food> getFoods() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Item";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        ObservableList<Food> obList = FXCollections.observableArrayList();
        while (result.next()) {
            obList.add(new Food(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4),
                    result.getInt(5)
            ));
        }
        return obList;
    }

    public boolean addPackage(Package pack) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO Package VALUES (?,?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, pack.getPackageId());
            stm.setString(2, pack.getPackageName());
            stm.setDouble(3, pack.getPackagePrice());

            if (stm.executeUpdate() > 0) {
                if (savePackageDetails(pack.getPackageId(), pack.getPackageDetails())) {
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

        } catch (SQLException | ClassNotFoundException e) {
            //e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {

            }
        }
        return false;
    }

    public boolean savePackageDetails(String packageId, ArrayList<PackageDetail> arrayList) throws SQLException, ClassNotFoundException {
        String foodType = null;
        for (PackageDetail detail : arrayList) {
            if (detail.getFoodType().equals("Meal")) {
                foodType = "Meal";
            } else if (detail.getFoodType().equals("Pizza")) {
                foodType = "Pizza";
            } else if (detail.getFoodType().equals("Burger")) {
                foodType = "Burger";
            } else {
                foodType = "Drink";
            }

            String sql = "INSERT INTO PackageDetail VALUES (?,?,?,?,?)";
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            stm.setString(1, packageId);
            stm.setString(2, detail.getFoodId());
            stm.setString(3, detail.getFoodType());
            stm.setInt(4, detail.getQty());
            stm.setDouble(5, detail.getUnitPrice());
            if (stm.executeUpdate() > 0) {

                /*2022-03-16 edited*/
                /*if (!updateQty(detail.getFoodId(), detail.getQty())) {
                    return false;
                }*/
            } else {
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

    public ObservableList<Package> loadPackageCode() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Package");
        ResultSet resultSet = stm.executeQuery();
        ObservableList<Package> obList = FXCollections.observableArrayList();
        while (resultSet.next()){
            obList.add(new Package(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            ));
        }
        return obList;
    }

    public ObservableList<String> loadPackageName() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT packageName FROM Package");
        ResultSet resultSet = stm.executeQuery();
        ObservableList<String> obList = FXCollections.observableArrayList();
        while (resultSet.next()){
           obList.add(resultSet.getString(1));
        }
        return obList;
    }

    public ArrayList<PackageDetail> getPackageDetails(String packageId) throws SQLException, ClassNotFoundException {
        ArrayList<PackageDetail> arrayList = new ArrayList<>();
        String sql = "SELECT foodId,foodType,qty,unitPrice FROM PackageDetail WHERE packageId=?";
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setString(1,packageId);
        ResultSet result = statement.executeQuery();
        while (result.next()){
            arrayList.add(new PackageDetail(
                    result.getString(1),
                    result.getString(2),
                    result.getInt(3),
                    result.getDouble(4)
            ));
        }
        return arrayList;
    }

    public boolean deletePackageDetail(String packageId,String foodId, int qty) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM PackageDetail WHERE foodId=? AND packageId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,foodId);
        stm.setString(2,packageId);
        if(stm.executeUpdate()>0){
            /*if(updateQuantity(foodId,qty)){
                return true;
            }*/

            // 2022-03-16 edit
            return true;
        }
        return false;
    }

    public boolean updateQuantity(String foodId, int qty) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Item SET qtyOnHand=qtyOnHand+? WHERE foodId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setInt(1,qty);
        stm.setString(2,foodId);
        return stm.executeUpdate()>0;
    }

    public boolean updatePackage(Package pack) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Package SET packageId=?,packageName=?,packagePrice=? WHERE packageId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,pack.getPackageId());
        stm.setString(2,pack.getPackageName());
        stm.setDouble(3,pack.getPackagePrice());
        stm.setString(4,pack.getPackageId());
        return stm.executeUpdate()>0;
    }

    public boolean deletePackage(Package pack) throws SQLException, ClassNotFoundException {
        Connection connection=null;
        try{

            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            String sql = "DELETE FROM Package WHERE packageId=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1,pack.getPackageId());

            if (stm.executeUpdate() > 0) {
                /*2022-03-16 edited*/ //till
                connection.commit();
                return true;
                /*if (updateItemQty(pack.getPackageId(), pack.getPackageDetails())) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }*/

            } else {
                connection.rollback();
                return false;
            }

        }catch (SQLException | ClassNotFoundException e){

        }
        finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    public boolean updateItemQty(String packageId, ArrayList<PackageDetail> details) throws SQLException, ClassNotFoundException {
        for(PackageDetail detail : details){
            String sql = "UPDATE Item SET qtyOnHand=qtyOnHand+? WHERE foodId=?";
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            stm.setInt(1,detail.getQty());
            stm.setString(2,detail.getFoodId());
            if(!(stm.executeUpdate()>0)){
                return false;
            }
        }
        return true;
    }
}
