package net.etfbl.ip.beans;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.etfbl.ip.AppUser;
import net.etfbl.ip.UserRole;
import net.etfbl.ip.dao.ConnectionPool;
import net.etfbl.ip.dao.DAOUtil;

public class AppUserBean {
	
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_BY_USERNAME = "SELECT * FROM app_user WHERE username=?";
	
	public AppUser getUserById(Long userId) {
		AppUser retVal = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {userId };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, "SELECT * FROM app_user WHERE id = ?", false, values);
			rs = pstmt.executeQuery();
			if (rs.next()) {
	        	Long id = rs.getLong("id");
	            String firstName = rs.getString("name");
	            String lastName = rs.getString("lastname");
	            String userName = rs.getString("username");
	            String email = rs.getString("mail");
	            String password = rs.getString("password_hash");
	            UserRole role = UserRole.valueOf(rs.getString("app_user_role"));
	            return new AppUser(id, userName, lastName, firstName, email, password, role);
	        }
			pstmt.close();
	        rs.close();
		}catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
	public AppUser loginUser(String username, String password) {
		AppUser user = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {username};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_BY_USERNAME, false, values);
			rs = pstmt.executeQuery();
			if (rs.next()){
				String passwordHash = getHash(password);
				if(passwordHash.equals(rs.getString("password_hash")) && rs.getString("app_user_role").equals("OPERATER")) {
				user = new AppUser(rs.getLong("id"), rs.getString("username"), rs.getString("lastname"), rs.getString("name"),
						rs.getString("mail"), rs.getString("password_hash"), UserRole.valueOf(rs.getString("app_user_role")));
				}
			}
			pstmt.close();
	        rs.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return user;
	}
	
	public String getHash(String input) {
		 StringBuilder hexString = new StringBuilder();
		 try {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
	        hexString = new StringBuilder();
	        for (byte b : hash) {
	            String hex = Integer.toHexString(0xff & b);
	            if (hex.length() == 1) {
	                hexString.append('0');
	            }
	            hexString.append(hex);
	        }
	        
		 }catch(NoSuchAlgorithmException e) {
			 
		 }
		 return hexString.toString();
		 }
}
