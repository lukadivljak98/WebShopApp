package net.etfbl.ip.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductAttributeDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_DELETE_BY_ATTRIBUTE_ID = "DELETE FROM product_attribute WHERE attribute_id=?";
    private static final String SQL_DELETE_BY_PRODUCT_ID = "DELETE FROM product_attribute WHERE product_id=?";

	public static boolean deleteByAttributeId(long attributeId) {
        boolean result = false;
        Connection connection = null;
        Object values[] = { attributeId };
        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_DELETE_BY_ATTRIBUTE_ID, false, values);
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
