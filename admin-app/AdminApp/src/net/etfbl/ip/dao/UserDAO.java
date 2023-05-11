package net.etfbl.ip.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.etfbl.ip.dto.User;

public class UserDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_ALL = "SELECT * FROM app_user";
	private static final String SQL_INSERT = "INSERT INTO app_user (city, lastname, mail, name, password_hash, username, app_user_role, enabled, locked) VALUES (?,?,?,?,?,?,?,?,?)";
	private static final String SQL_DELETE = "DELETE FROM app_user WHERE id=?";
	private static final String SQL_UPDATE = "UPDATE app_user SET city = ?, lastname = ?, mail = ?, name = ?, password_hash = ?, username = ?, app_user_role = ?, enabled = ?, locked = ? WHERE id = ?";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM app_user WHERE id = ?";
	private static final String SQL_SELECT_BY_USERNAME = "SELECT * FROM app_user WHERE username=?";
	private static final String SQL_IS_USERNAME_USED = "SELECT * FROM app_user WHERE username = ?";
	private static final String SQL_IS_MAIL_USED = "SELECT * FROM app_user WHERE mail = ?";
	
	public static boolean insert(User user) {
		boolean result = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object values[] = { user.getCity(), user.getLastName(), user.getEmail(), user.getFirstName(), user.getPassword(),
				user.getUsername(), user.getRole(), user.getEnabled(), user.isLocked()};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			pstmt.executeUpdate();
			generatedKeys = pstmt.getGeneratedKeys();
			if(pstmt.getUpdateCount()>0) {
				result = true;
			}
			if (generatedKeys.next()) {
				user.setId(generatedKeys.getLong(1));
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}
	
	public static boolean delete(long userId) {
	    boolean result = false;
	    Connection connection = null;
	    Object values[] = { userId };
	    try {
	    	ProductDAO.deleteByBuyerId(userId);
	    	ProductDAO.deleteBySellerId(userId);
	    	CommentDAO.deleteBySenderId(userId);
	    	ConfirmationTokenDAO.deleteByUserId(userId);
	        connection = connectionPool.checkOut();
	        PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_DELETE, false, values);
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

	public static boolean update(User user) {
	    boolean result = false;
	    Connection connection = null;
	    Object values[] = { user.getCity(), user.getLastName(), user.getEmail(), user.getFirstName(), user.getPassword(),
	            user.getUsername(), user.getRole(), user.getEnabled(), user.isLocked(), user.getId() };
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

	public static List<User> getAllUsers() {
	    List<User> userList = new ArrayList<User>();
	    Connection connection = null;
	    try {
	        connection = connectionPool.checkOut();
	        PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ALL, false);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            User user = new User(rs.getLong("id"), rs.getString("name"), rs.getString("lastname"), rs.getString("username"),
	                    rs.getString("mail"), rs.getString("password_hash"), UserRole.valueOf(rs.getString("app_user_role")),
	                    rs.getString("city"), rs.getBoolean("enabled"), rs.getBoolean("locked"));
	            userList.add(user);
	        }
	        rs.close();
	        pstmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        connectionPool.checkIn(connection);
	    }
	    return userList;
	}
	
	public static User getUserById(long id) {
	    User user = null;
	    Connection connection = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Object values[] = { id };
	    try {
	        connection = connectionPool.checkOut();
	        pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_BY_ID, false, values);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            user = new User();
	            user.setId(rs.getLong("id"));
	            user.setFirstName(rs.getString("name"));
	            user.setLastName(rs.getString("lastname"));
	            user.setEmail(rs.getString("mail"));
	            user.setPassword(rs.getString("password_hash"));
	            user.setUsername(rs.getString("username"));
	            user.setRole(UserRole.valueOf(rs.getString("app_user_role")));
	            user.setEnabled(rs.getBoolean("enabled"));
	            user.setLocked(rs.getBoolean("locked"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        //DAOUtil.close(connection, pstmt, rs);
	        connectionPool.checkIn(connection);
	    }
	    return user;
	}
	
	public static User selectByUsernameAndPassword(String username, String password){
		User user = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {username};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_BY_USERNAME, false, values);
			rs = pstmt.executeQuery();
			if (rs.next()){
				if(password.equals(rs.getString("password_hash"))) {
				user = new User(rs.getLong("id"), rs.getString("name"), rs.getString("lastname"), rs.getString("username"),
						rs.getString("mail"), rs.getString("password_hash"), UserRole.valueOf(rs.getString("app_user_role")), rs.getString("city"),
						rs.getBoolean("enabled"), rs.getBoolean("locked"));
				}
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return user;
	}
	
	public static boolean isUserNameUsed(String username) {
		boolean result = true;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {username};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_IS_USERNAME_USED, false, values);
			rs = pstmt.executeQuery();
			if (rs.next()){
				result = false;
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}
	
	public static boolean isEmailUsed(String email) {
		boolean result = true;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {email};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_IS_MAIL_USED, false, values);
			rs = pstmt.executeQuery();
			if (rs.next()){
				result = false;
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}
	
}
