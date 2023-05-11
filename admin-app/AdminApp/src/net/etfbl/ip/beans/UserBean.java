package net.etfbl.ip.beans;

import java.util.List;

import net.etfbl.ip.dao.UserDAO;
import net.etfbl.ip.dao.UserRole;
import net.etfbl.ip.dto.User;

public class UserBean {

private static final long serialVersionUID = 1L;
private User user = new User();
private boolean isLoggedIn = false;
	
	public boolean add(User category) {
		return UserDAO.insert(category);
	}
	
	public List<User> getAll(){
		return UserDAO.getAllUsers();
	}
	
	public User getById(long id) {
		return UserDAO.getUserById(id);
	}
	
	public boolean delete(User user) {
		return UserDAO.delete(user.getId());
	}
	
	public boolean update(User user) {
		return UserDAO.update(user);
	}
	
	public boolean login(String username, String password) {
		if ((user = UserDAO.selectByUsernameAndPassword(username, password)) != null) {
			if(user.getRole() == UserRole.ADMIN && !user.isLocked()) {
				isLoggedIn = true;
				return true;
			}
		}
		return false;
	}
	
	public void logout() {
		user = new User();
		isLoggedIn = false;
	}
	
	public boolean isUserNameAllowed(String username) {
		return UserDAO.isUserNameUsed(username);
	}
	
	public boolean isEmailAllowed(String email) {
		return UserDAO.isEmailUsed(email);
	}
}
