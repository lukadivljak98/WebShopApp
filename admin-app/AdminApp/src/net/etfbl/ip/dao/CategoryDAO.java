package net.etfbl.ip.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.etfbl.ip.dto.Attribute;
import net.etfbl.ip.dto.Category;

public class CategoryDAO {

	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_ALL = "SELECT * FROM category";
	private static final String SQL_INSERT = "INSERT INTO category (type) VALUES (?)";
	
	public static boolean insert(Category category) {
		boolean result = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object values[] = { category.getType() };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			pstmt.executeUpdate();
			generatedKeys = pstmt.getGeneratedKeys();
			if(pstmt.getUpdateCount()>0) {
				result = true;
			}
			if (generatedKeys.next()) {
				category.setId(generatedKeys.getLong(1));
				for (Attribute attribute : category.getAttributes()) {
	                attribute.setCategoryId(category.getId());
	                AttributeDAO.insert(attribute);
	            }
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}

	public static ArrayList<Category> selectAll() {
		ArrayList<Category> retVal = new ArrayList<Category>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ALL, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				//retVal.add(new Category(rs.getLong("id"), rs.getString("type")));
				Category category = new Category(rs.getLong("id"), rs.getString("type"));
	            ArrayList<Attribute> attributes = AttributeDAO.selectByCategoryId(category.getId());
	            category.setAttributes(attributes);
	            retVal.add(category);
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static boolean delete(Category category) {
	    boolean result = false;
	    Connection connection = null;
	    Object values[] = { category.getId() };
	    try {
	        connection = connectionPool.checkOut();
	        AttributeDAO.deleteByCategoryId(category.getId());
	        PreparedStatement pstmt = DAOUtil.prepareStatement(connection, "DELETE FROM category WHERE id=?", false, values);
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

	public static boolean update(Category category) {
	    boolean result = false;
	    Connection connection = null;
	    Object values[] = { category.getType(), category.getId() };
	    try {
	        connection = connectionPool.checkOut();
	        PreparedStatement pstmt = DAOUtil.prepareStatement(connection, "UPDATE category SET type=? WHERE id=?", false, values);
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            result = true;
//	            AttributeDAO.deleteByCategoryId(category.getId());
//	            for (Attribute attribute : category.getAttributes()) {
//	                attribute.setCategoryId(category.getId());
//	                AttributeDAO.insert(attribute);
//	            }
	        }
	        pstmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        connectionPool.checkIn(connection);
	    }
	    return result;
	}

	public static Category getById(long id) {
	    Category category = null;
	    Connection connection = null;
	    ResultSet rs = null;
	    Object values[] = { id };
	    try {
	        connection = connectionPool.checkOut();
	        PreparedStatement pstmt = DAOUtil.prepareStatement(connection, "SELECT * FROM category WHERE id=?", false, values);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            category = new Category(rs.getLong("id"), rs.getString("type"));
	            category.setAttributes(AttributeDAO.selectByCategoryId(id));
	        }
	        pstmt.close();
	    } catch (SQLException exp) {
	        exp.printStackTrace();
	    } finally {
	        connectionPool.checkIn(connection);
	    }
	    return category;
	}


}
