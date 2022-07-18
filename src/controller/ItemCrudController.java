package controller;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.*;
import model.Package;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ItemCrudController {

    public <T> ArrayList<T> getFoodCodes(String foodType) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Item WHERE foodType=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1, foodType);
        ResultSet result = stm.executeQuery();
        ArrayList<T> itemCodeArrayList = new ArrayList<>();
        while (result.next()) {
            if (foodType.equals("Meal")) {
                itemCodeArrayList.add((T) new Meal(result.getString(1), result.getString(2), result.getString(3), result.getDouble(4), result.getInt(5)));
            } else if (foodType.equals("Pizza")) {
                itemCodeArrayList.add((T) new Pizza(result.getString(1), result.getString(2), result.getString(3), result.getDouble(4), result.getInt(5)));
            } else if (foodType.equals("Burger")) {
                itemCodeArrayList.add((T) new Burger(result.getString(1), result.getString(2), result.getString(3), result.getDouble(4), result.getInt(5)));
            } else {
                itemCodeArrayList.add((T) new Beverage(result.getString(1), result.getString(2), result.getString(3), result.getDouble(4), result.getInt(5)));
            }
        }
        return itemCodeArrayList;
    }

    public <T> T getFoodItem(String foodId, String foodType) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Item WHERE foodId=? AND foodType=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1, foodId);
        stm.setString(2, foodType);
        ResultSet result = stm.executeQuery();
        if (result.next()) {
            if (foodType.equals("Meal")) {
                return (T) new Meal(result.getString(1), result.getString(2), result.getString(3), result.getDouble(4), result.getInt(5));
            } else if (foodType.equals("Pizza")) {
                return (T) new Pizza(result.getString(1), result.getString(2), result.getString(3), result.getDouble(4), result.getInt(5));
            } else if (foodType.equals("Burger")) {
                return (T) new Burger(result.getString(1), result.getString(2), result.getString(3), result.getDouble(4), result.getInt(5));
            } else {
                return (T) new Beverage(result.getString(1), result.getString(2), result.getString(3), result.getDouble(4), result.getInt(5));
            }
        }
        return null;
    }

    public String getFoodDescription(String foodId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT description FROM Item WHERE foodId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,foodId);
        ResultSet result = stm.executeQuery();
        if(result.next()){
            return result.getString(1);
        }
        return null;
    }

    public boolean addMeal(Meal meal) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Item VALUES (?,?,?,?,?)";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1, meal.getFoodId());
        stm.setString(2, meal.getFoodType());
        stm.setString(3, meal.getDescription());
        stm.setDouble(4, meal.getUnitPrice());
        stm.setInt(5, meal.getQtyOnHand());
        return stm.executeUpdate() > 0;
    }

    public boolean updateMeal(Meal meal, String mealId) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Item SET foodId=?,foodType=?,description=?,unitPrice=?,qtyOnHand=? WHERE foodId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,meal.getFoodId());
        stm.setString(2,meal.getFoodType());
        stm.setString(3,meal.getDescription());
        stm.setDouble(4,meal.getUnitPrice());
        stm.setInt(5,meal.getQtyOnHand());
        stm.setString(6,mealId);
        return stm.executeUpdate()>0;
    }

    public boolean deleteMeal(String mealId) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            return DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Item WHERE foodId='"+mealId+"'").executeUpdate()>0;
        }
        return false;
    }

    public boolean addPizza(Pizza pizza) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Item VALUES (?,?,?,?,?)";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1, pizza.getFoodId());
        stm.setString(2, pizza.getFoodType());
        stm.setString(3, pizza.getDescription());
        stm.setDouble(4, pizza.getUnitPrice());
        stm.setInt(5, pizza.getQtyOnHand());
        return stm.executeUpdate() > 0;
    }

    public boolean updatePizza(Pizza pizza, String mealId) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Item SET foodId=?,foodType=?,description=?,unitPrice=?,qtyOnHand=? WHERE foodId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,pizza.getFoodId());
        stm.setString(2,pizza.getFoodType());
        stm.setString(3,pizza.getDescription());
        stm.setDouble(4,pizza.getUnitPrice());
        stm.setInt(5,pizza.getQtyOnHand());
        stm.setString(6,mealId);
        return stm.executeUpdate()>0;
    }

    public boolean deletePizza(String mealId) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            return DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Item WHERE foodId='"+mealId+"'").executeUpdate()>0;
        }
        return false;
    }

    public boolean addBurger(Burger burger) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Item VALUES (?,?,?,?,?)";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1, burger.getFoodId());
        stm.setString(2, burger.getFoodType());
        stm.setString(3, burger.getDescription());
        stm.setDouble(4, burger.getUnitPrice());
        stm.setInt(5, burger.getQtyOnHand());
        return stm.executeUpdate() > 0;
    }

    public boolean updateBurger(Burger burger, String mealId) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Item SET foodId=?,foodType=?,description=?,unitPrice=?,qtyOnHand=? WHERE foodId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,burger.getFoodId());
        stm.setString(2,burger.getFoodType());
        stm.setString(3,burger.getDescription());
        stm.setDouble(4,burger.getUnitPrice());
        stm.setInt(5,burger.getQtyOnHand());
        stm.setString(6,mealId);
        return stm.executeUpdate()>0;
    }

    public boolean deleteBurger(String mealId) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            return DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Item WHERE foodId='"+mealId+"'").executeUpdate()>0;
        }
        return false;
    }

    public boolean addBeverage(Beverage beverage) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Item VALUES (?,?,?,?,?)";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1, beverage.getFoodId());
        stm.setString(2, beverage.getFoodType());
        stm.setString(3, beverage.getDescription());
        stm.setDouble(4, beverage.getUnitPrice());
        stm.setInt(5, beverage.getQtyOnHand());
        return stm.executeUpdate() > 0;
    }

    public boolean updateBeverage(Beverage beverage, String mealId) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Item SET foodId=?,foodType=?,description=?,unitPrice=?,qtyOnHand=? WHERE foodId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,beverage.getFoodId());
        stm.setString(2,beverage.getFoodType());
        stm.setString(3,beverage.getDescription());
        stm.setDouble(4,beverage.getUnitPrice());
        stm.setInt(5,beverage.getQtyOnHand());
        stm.setString(6,mealId);
        return stm.executeUpdate()>0;
    }

    public boolean deleteBeverage(String mealId) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            return DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Item WHERE foodId='"+mealId+"'").executeUpdate()>0;
        }
        return false;
    }

    public String getLastFoodId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT foodId FROM Item ORDER BY foodId DESC LIMIT 1";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        if(result.next()){
            return result.getString(1);
        }
        return null;
    }

    public ObservableList<Food> getMeals(String foodType) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Item WHERE foodType=?";  //FORMAT(unitPrice,2)
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,foodType);
        ObservableList<Food> obList = FXCollections.observableArrayList();
        ResultSet resultSet = stm.executeQuery();
        while (resultSet.next()){
            obList.add(new Food(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            ));
        }
        return obList;
    }

    public ObservableList<Food> getPizza(String foodType) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Item WHERE foodType=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,foodType);
        ObservableList<Food> obList = FXCollections.observableArrayList();
        ResultSet resultSet = stm.executeQuery();
        while (resultSet.next()){
            obList.add(new Food(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            ));
        }
        return obList;
    }

    public ObservableList<Food> getBurgers(String foodType) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Item WHERE foodType=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,foodType);
        ObservableList<Food> obList = FXCollections.observableArrayList();
        ResultSet resultSet = stm.executeQuery();
        while (resultSet.next()){
            obList.add(new Food(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            ));
        }
        return obList;
    }

    public ObservableList<Food> getDrinks(String foodType) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Item WHERE foodType=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,foodType);
        ObservableList<Food> obList = FXCollections.observableArrayList();
        ResultSet resultSet = stm.executeQuery();
        while (resultSet.next()){
            obList.add(new Food(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            ));
        }
        return obList;
    }

    public ObservableList<Food> getPackages() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Package";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ObservableList<Food> obList = FXCollections.observableArrayList();
        ResultSet resultSet = stm.executeQuery();
        while (resultSet.next()){
            obList.add(new Food(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            ));
        }
        return obList;
    }

    public ArrayList<PackageDetailTM> getPackageItems(String packageId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT d.foodId,i.description,d.unitPrice,d.qty FROM PackageDetail d INNER JOIN Item i ON d.foodId=i.foodId WHERE packageId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,packageId);
        ResultSet result = stm.executeQuery();
        ArrayList<PackageDetailTM> arrayList = new ArrayList<>();
        while (result.next()){
            arrayList.add(new PackageDetailTM(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getInt(4),
                    0.00
           ));
        }
        return arrayList;
    }

    public ArrayList<PackageDetail> getPackageItems(String packageId,String s) throws SQLException, ClassNotFoundException {
        String sql = "SELECT foodId,foodType,qty,unitPrice FROM PackageDetail WHERE packageId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,packageId);
        ResultSet result = stm.executeQuery();
        ArrayList<PackageDetail> arrayList = new ArrayList<>();
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

    public double getDiscountedPrice(String foodId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT discountPrice FROM Discount WHERE foodId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,foodId);
        ResultSet result = stm.executeQuery();
        double totalDiscountedPrice=0.00;
        while(result.next()){
            totalDiscountedPrice+=result.getDouble(1);
        }
        return totalDiscountedPrice;
    }

    public String getFoodType(String foodId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT foodType FROM Item WHERE foodId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,foodId);
        ResultSet result = stm.executeQuery();
        if(result.next()){
            return result.getString(1);
        }
        return null;
    }

    public ObservableList<Food> getMeals(String foodType,String s) throws SQLException, ClassNotFoundException {
        String sql = "SELECT i.foodId,i.foodType,i.description,i.unitPrice,i.qtyOnHand, SUM(d.discountPrice) FROM Item i LEFT JOIN Discount d ON i.foodId=d.foodId WHERE foodType=? GROUP BY i.foodId;";  //FORMAT(unitPrice,2)
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,foodType);
        ObservableList<Food> obList = FXCollections.observableArrayList();
        ResultSet resultSet = stm.executeQuery();
        while (resultSet.next()){
            obList.add(new Food(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5),
                    resultSet.getDouble(6)
            ));
        }
        return obList;
    }

    public ObservableList<Food> getPizza(String foodType,String s) throws SQLException, ClassNotFoundException {
        String sql = "SELECT i.foodId,i.foodType,i.description,i.unitPrice,i.qtyOnHand, SUM(d.discountPrice) FROM Item i LEFT JOIN Discount d ON i.foodId=d.foodId WHERE foodType=? GROUP BY i.foodId;";  //FORMAT(unitPrice,2)
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,foodType);
        ObservableList<Food> obList = FXCollections.observableArrayList();
        ResultSet resultSet = stm.executeQuery();
        while (resultSet.next()){
            obList.add(new Food(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5),
                    resultSet.getDouble(6)
            ));
        }
        return obList;
    }

    public ObservableList<Food> getBurgers(String foodType,String s) throws SQLException, ClassNotFoundException {
        String sql = "SELECT i.foodId,i.foodType,i.description,i.unitPrice,i.qtyOnHand, SUM(d.discountPrice) FROM Item i LEFT JOIN Discount d ON i.foodId=d.foodId WHERE foodType=? GROUP BY i.foodId;";  //FORMAT(unitPrice,2)
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,foodType);
        ObservableList<Food> obList = FXCollections.observableArrayList();
        ResultSet resultSet = stm.executeQuery();
        while (resultSet.next()){
            obList.add(new Food(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5),
                    resultSet.getDouble(6)
            ));
        }
        return obList;
    }

    public ObservableList<Food> getDrinks(String foodType,String s) throws SQLException, ClassNotFoundException {
        String sql = "SELECT i.foodId,i.foodType,i.description,i.unitPrice,i.qtyOnHand, SUM(d.discountPrice) FROM Item i LEFT JOIN Discount d ON i.foodId=d.foodId WHERE foodType=? GROUP BY i.foodId;";  //FORMAT(unitPrice,2)
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,foodType);
        ObservableList<Food> obList = FXCollections.observableArrayList();
        ResultSet resultSet = stm.executeQuery();
        while (resultSet.next()){
            obList.add(new Food(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5),
                    resultSet.getDouble(6)
            ));
        }
        return obList;
    }

    public ArrayList<PackageDetail> getPackage(String packageId) throws SQLException, ClassNotFoundException {
        String sql ="SELECT * FROM PackageDetail WHERE packageId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,packageId);
        ResultSet resultSet = stm.executeQuery();
        ArrayList<PackageDetail> arrayList = new ArrayList<>();
        while(resultSet.next()){
            arrayList.add(new PackageDetail(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5)
            ));
        }
        return arrayList;
    }

    public ObservableList<CartTM> setMostMovableItems() throws SQLException, ClassNotFoundException {
        String sql = "select foodId,description,unitPrice, SUM(qty) from orderdetail group by foodId order by SUM(qty) desc limit 5";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ObservableList<CartTM> list = FXCollections.observableArrayList();
        ResultSet result = stm.executeQuery();
        while(result.next()){
            list.add(new CartTM(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getInt(4)
            ));
        }
        return list;
    }

    public ObservableList<CartTM> setLeastMovableItems() throws SQLException, ClassNotFoundException {
        String sql = "select foodId,description,unitPrice, SUM(qty) from orderdetail group by foodId order by SUM(qty) asc limit 5";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ObservableList<CartTM> list = FXCollections.observableArrayList();
        ResultSet result = stm.executeQuery();
        while(result.next()){
            list.add(new CartTM(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getInt(4)
            ));
        }
        return list;
    }

    public int getTotalFoodItems() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(foodId) FROM Item";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        int foodItemsCount = 0;
        ResultSet result = stm.executeQuery();
        while(result.next()){
            foodItemsCount+=result.getInt(1);
        }
        return foodItemsCount;
    }

    public int getQtyOfFoodId(String foodId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT qtyOnHand FROM Item WHERE foodId=?");
        stm.setString(1,foodId);
        ResultSet resultSet = stm.executeQuery();
        if(resultSet.next()){
            return resultSet.getInt(1);
        }else{
            return 0;
        }
    }
}
