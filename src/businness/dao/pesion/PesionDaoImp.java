package businness.dao.pesion;

import businness.config.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;


public class PesionDaoImp implements PesionDao {
    public static String currentUsername = null;
    @Override
    public int login(String username, String password) {
        int loginResult = -1;

        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL login(?, ?, ?)}")) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.registerOutParameter(3, Types.INTEGER);

            stmt.execute();

            loginResult = stmt.getInt(3);
            if (loginResult == 1) {
                currentUsername = username;
            } else {
                currentUsername = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loginResult;
    }


    @Override
    public void logout() {
        if (currentUsername != null) {
            currentUsername = null;
        }
    }
}
