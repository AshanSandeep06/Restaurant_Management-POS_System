package controller;

import database.DBConnection;
import model.Customer;
import model.CustomerTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerCrudController {
    public boolean addCustomer(Customer c1) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Customer VALUES (?,?,?,?)";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,c1.getCustomerId());
        stm.setString(2,c1.getName());
        stm.setString(3,c1.getAddress());
        stm.setString(4,c1.getContactNumber());
        return stm.executeUpdate()>0;
    }

    public Customer getCustomer(String customerId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Customer WHERE customerId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,customerId);
        ResultSet result = stm.executeQuery();
        if(result.next()){
            return new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return null;
    }

    public ArrayList<Customer> getAllCustomers() throws SQLException, ClassNotFoundException{
        String sql = "SELECT * FROM Customer";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        ArrayList<Customer> customerList = new ArrayList<>();
        while(result.next()){
            customerList.add(new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            ));
        }
        return customerList;
    }

    public boolean updateCustomer(Customer c1) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Customer SET customerId=?,name=?,address=?,contactNumber=? WHERE customerId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,c1.getCustomerId());
        stm.setString(2,c1.getName());
        stm.setString(3,c1.getAddress());
        stm.setString(4,c1.getContactNumber());
        stm.setString(5,c1.getCustomerId());
        return stm.executeUpdate()>0;
    }

    public boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Customer WHERE customerId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,customerId);
        return stm.executeUpdate()>0;
    }

    public Customer getCustomerWithMobileNumber(String mobileNumber) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Customer WHERE contactNumber=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,mobileNumber);
        ResultSet result = stm.executeQuery();
        if(result.next()){
            return new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return null;
    }

    public String getLastCustomerId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT customerId FROM Customer ORDER BY customerId DESC LIMIT 1";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = stm.executeQuery();
        if(resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public boolean customerIsExists(String number) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT contactNumber FROM Customer WHERE contactNumber=?");
        stm.setString(1,number);
        ResultSet resultSet = stm.executeQuery();
        if(resultSet.next()){
            return true;
        }
        return false;
    }

    public Customer getCustomerWithMobileNumberIsExist(String mobileNumber) throws SQLException, ClassNotFoundException {
        String sql = "SELECT c.customerId,c.name,c.address,c.contactNumber FROM Customer c INNER JOIN Reservation r ON c.customerId=r.customerId WHERE contactNumber=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,mobileNumber);
        ResultSet result = stm.executeQuery();
        if(result.next()){
            return new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return null;
    }

    public ArrayList<Customer> getTopThreeCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> list = new ArrayList<>();
        String sql = "SELECT o.customerId,c.name FROM `Order` o INNER JOIN Customer c ON o.customerId=c.customerId GROUP BY customerId ORDER BY COUNT(o.customerId) DESC LIMIT 3";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        while(result.next()){
            list.add(new Customer(
                        result.getString(1),
                        result.getString(2)
            ));
        }
        return list;
    }
}
