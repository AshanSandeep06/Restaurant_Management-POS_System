package controller;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Reservation;
import model.ReservationTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationCrudController {
    public String getLastReservationId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("SELECT reservationId FROM Reservation ORDER BY reservationId DESC LIMIT 1");
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    public boolean addReservation(Reservation reservation) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Reservation VALUES (?,?,?,?,?)";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,reservation.getReservationId());
        stm.setString(2,reservation.getCustomerId());
        stm.setString(3,reservation.getReservationDate());
        stm.setString(4,reservation.getReservationTime());
        stm.setInt(5,reservation.getParticipants());
        return stm.executeUpdate()>0;
    }

    public ObservableList<ReservationTM> getReservationDetails(String date) throws SQLException, ClassNotFoundException {
        String sql = "SELECT r.reservationId,c.name,c.contactNumber,r.reservationTime,r.totalParticipants FROM Reservation r INNER JOIN Customer c ON c.customerId=r.customerId WHERE reservationDate=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,date);
        ObservableList<ReservationTM> list = FXCollections.observableArrayList();
        ResultSet resultSet = stm.executeQuery();
        while (resultSet.next()){
            list.add(new ReservationTM(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getInt(5)));
        }
        return list;
    }

    public ObservableList<ReservationTM> getReservationDetailBasedOnCustomer(String customerId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT reservationId,reservationDate,reservationTime,totalParticipants FROM Reservation WHERE customerId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,customerId);
        ObservableList<ReservationTM> list = FXCollections.observableArrayList();
        ResultSet resultSet = stm.executeQuery();
        while(resultSet.next()) {
            list.add(new ReservationTM(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),Integer.valueOf(resultSet.getString(4))));
        }
        return list;
    }

    public boolean updateReservation(Reservation reservation) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Reservation SET reservationDate=?,reservationTime=?,totalParticipants=? WHERE reservationId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,reservation.getReservationDate());
        stm.setString(2,reservation.getReservationTime());
        stm.setInt(3,reservation.getParticipants());
        stm.setString(4,reservation.getReservationId());

        return stm.executeUpdate()>0;
    }

    public boolean deleteReservation(String reservationId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Reservation WHERE reservationId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,reservationId);
        return stm.executeUpdate()>0;
    }
}
