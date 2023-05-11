package net.etfbl.ip.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.etfbl.ip.dto.Attribute;

public class AttributeDAO {
	
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_DELETE_BY_CATEGORY_ID = "DELETE FROM attribute WHERE category_id=?";
    private static final String SQL_INSERT = "INSERT INTO attribute (name, category_id) VALUES (?, ?)";
    private static final String SQL_DELETE = "DELETE FROM attribute WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE attribute SET name = ?, category_id = ? WHERE id = ?";
	
	public static ArrayList<Attribute> selectByCategoryId(long categoryId) {
	    ArrayList<Attribute> retVal = new ArrayList<Attribute>();
	    Connection connection = null;
	    ResultSet rs = null;
	    Object values[] = { categoryId };
	    try {
	        connection = connectionPool.checkOut();
	        PreparedStatement pstmt = DAOUtil.prepareStatement(connection, "SELECT * FROM attribute WHERE category_id=?", false, values);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            retVal.add(new Attribute(rs.getLong("id"), rs.getString("name"), rs.getLong("category_id")));
	        }
	        pstmt.close();
	    } catch (SQLException exp) {
	        exp.printStackTrace();
	    } finally {
	        connectionPool.checkIn(connection);
	    }
	    return retVal;
	}

	 public static boolean deleteByCategoryId(long categoryId) {
	        boolean result = false;
	        Connection connection = null;
	        Object values[] = { categoryId };
	        try {
	            connection = connectionPool.checkOut();
	            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_DELETE_BY_CATEGORY_ID, false, values);
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

	    public static boolean insert(Attribute attribute) {
	        boolean result = false;
	        Connection connection = null;
	        ResultSet generatedKeys = null;
	        Object values[] = { attribute.getName(), attribute.getCategoryId() };
	        try {
	            connection = connectionPool.checkOut();
	            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
	            pstmt.executeUpdate();
	            generatedKeys = pstmt.getGeneratedKeys();
	            if (pstmt.getUpdateCount() > 0) {
	                result = true;
	            }
	            if (generatedKeys.next())
	                attribute.setId(generatedKeys.getLong(1));
	            pstmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            connectionPool.checkIn(connection);
	        }
	        return result;
	    }

	    public static void delete(Attribute attribute) throws SQLException {
	        Connection connection = null;
	        PreparedStatement pstmt = null;
	        try {
	        	ProductAttributeDAO.deleteByAttributeId(attribute.getId());
	            connection = connectionPool.checkOut();
	            pstmt = connection.prepareStatement(SQL_DELETE);
	            pstmt.setLong(1, attribute.getId());
	            pstmt.executeUpdate();
	        } finally {
	            if (pstmt != null) {
	                pstmt.close();
	            }
	            connectionPool.checkIn(connection);
	        }
	    }
	    
	    public static boolean update(Attribute attribute) {
	        boolean result = false;
	        Connection connection = null;
	        Object values[] = { attribute.getName(), attribute.getId() };
	        try {
	            connection = connectionPool.checkOut();
	            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_UPDATE, false, values);
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
