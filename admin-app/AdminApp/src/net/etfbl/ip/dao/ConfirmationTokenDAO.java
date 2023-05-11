package net.etfbl.ip.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConfirmationTokenDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_DELETE_BY_APP_USER_ID = "DELETE FROM confirmation_token WHERE app_user_id=?";

	public static boolean deleteByUserId(long userId) {
        boolean result = false;
        Connection connection = null;
        Object values[] = { userId };
        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_DELETE_BY_APP_USER_ID, false, values);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return result;
    }
}
