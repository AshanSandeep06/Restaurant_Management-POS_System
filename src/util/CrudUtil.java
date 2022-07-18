package util;

import database.DBConnection;
import netscape.security.UserTarget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {

    public static <T> T execute(String sql, Object... params) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            stm.setObject((i + 1), params[i]);
        }
        if (sql.startsWith("SELECT")) {
            return (T) stm.executeQuery();
        } else {
            return ((T) (Boolean) (stm.executeUpdate() > 0));
            // return wena boolean value eka primitive data type
            // ekk nisa eka Boolean Object type ekakata cast karaganna ona... itapsse eka T kynne object type ekak nisa T kyna type ekata cast karagnnawa  Boolean Object
            // type eka...

            // Boolean -> Type inference (Boxing)
        }
    }

}
