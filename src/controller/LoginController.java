package controller;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Login;
import model.Password;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    public Login getLogin(String userName, String password) throws SQLException, ClassNotFoundException {
        String sql = "select e.employeeName,l.jobRole from login l left join employee e on e.employeeId=l.employeeId where l.userName=? and l.password=?;";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,userName);
        stm.setString(2,password);
        ResultSet resultSet = stm.executeQuery();
        if(resultSet.next()){
            return new Login(resultSet.getString(1),resultSet.getString(2));
        }else{
            return null;
        }
    }

    public Password getLogin(String userName, String password,String jobRole) throws SQLException, ClassNotFoundException {
        String sql=null;
        if(jobRole.equals("Admin")){
            sql = "SELECT userName,password FROM Login WHERE userName=? AND password=? AND jobRole=?";
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            stm.setString(1,userName);
            stm.setString(2,password);
            stm.setString(3,jobRole);
            ResultSet result = stm.executeQuery();
            if(result.next()){
                return new Password(result.getString(1),result.getString(2));
            }
            return null;
        }else{
            sql = "SELECT e.employeeId,e.employeeName,l.userName,l.password FROM Employee e INNER JOIN Login l ON e.employeeId=l.employeeId WHERE l.userName=? AND l.password=?";
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            stm.setString(1,userName);
            stm.setString(2,password);
            ResultSet result = stm.executeQuery();
            if(result.next()){
                return new Password(result.getString(1),result.getString(2),result.getString(3),result.getString(4));
            }
            return null;
        }
    }

    public ObservableList<Login> getUserNames() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Login");
        ResultSet resultSet = stm.executeQuery();
        ObservableList<Login> obList = FXCollections.observableArrayList();
        while(resultSet.next()){
            obList.add(new Login(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return obList;
    }

    public boolean changePassword(String userName, String password) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Login SET password=? WHERE userName=?");
        stm.setString(1,password);
        stm.setString(2,userName);
        return stm.executeUpdate()>0;
    }
}
