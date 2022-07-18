package controller;

import database.DBConnection;
import model.Attendance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeAttendanceCrudController {
    public String getLastAttendanceId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("SELECT attendanceId FROM Attendance ORDER BY attendanceId DESC LIMIT 1");
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    public ArrayList<Attendance> getAllAttendances() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Attendance";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        ArrayList<Attendance> list = new ArrayList<>();
        while (result.next()){
            list.add(new Attendance(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getDouble(6),
                    result.getString(7)
            ));
        }
        return list;
    }

    public boolean addAttendance(Attendance attendance) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Attendance VALUES (?,?,?,?,?,?,?)";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,attendance.getAttendanceId());
        stm.setString(2,attendance.getEmployeeId());
        stm.setString(3,attendance.getEmployeeName());
        stm.setString(4,attendance.getAttendDate());
        stm.setString(5,attendance.getWorkingType());
        stm.setDouble(6,attendance.getWorkingHours());
        stm.setString(7,attendance.getJobRole());
        return stm.executeUpdate()>0;
    }

    public boolean updateAttendance(Attendance attendance) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Attendance SET attendanceId=?,employeeId=?,employeeName=?,attendDate=?,workingType=?,workingHours=?,jobRole=? WHERE attendanceId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,attendance.getAttendanceId());
        stm.setString(2,attendance.getEmployeeId());
        stm.setString(3,attendance.getEmployeeName());
        stm.setString(4,attendance.getAttendDate());
        stm.setString(5,attendance.getWorkingType());
        stm.setDouble(6,attendance.getWorkingHours());
        stm.setString(7,attendance.getJobRole());
        stm.setString(8,attendance.getAttendanceId());
        return stm.executeUpdate()>0;
    }

    public boolean deleteAttendance(String attendanceId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Attendance WHERE attendanceId=?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setString(1,attendanceId);
        return stm.executeUpdate()>0;
    }
}
