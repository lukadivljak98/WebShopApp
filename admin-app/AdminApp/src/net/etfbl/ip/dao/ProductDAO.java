package net.etfbl.ip.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.etfbl.ip.dto.User;

public class ProductDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_DELETE_BY_SELLER_ID = "DELETE FROM product WHERE seller_id=?";
    private static final String SQL_DELETE_BY_BUYER_ID = "DELETE FROM product WHERE buyer_id=?";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM product WHERE id = ?";

	public static boolean deleteBySellerId(long sellerId) {
        boolean result = false;
        Connection connection = null;
        Object values[] = { sellerId };
        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_DELETE_BY_SELLER_ID, false, values);
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
	
	public static boolean deleteByBuyerId(long buyerId) {
        boolean result = false;
        Connection connection = null;
        Object values[] = { buyerId };
        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_DELETE_BY_BUYER_ID, false, values);
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
