package net.etfbl.ip;

import java.io.Serializable;
import java.sql.SQLException;

import net.etfbl.ip.beans.AppUserBean;

public class Message implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String text;
	private Long appUserId;
	private boolean isRead;
	private AppUser appUser = new AppUser();
	
	public Message() {}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public Message(Long id, String text, Long appUserId, boolean isRead) throws SQLException {
		super();
		this.id = id;
		this.text = text;
		this.appUserId = appUserId;
		this.isRead = isRead;
		setAppUser(appUserId);
	}
	
	public void setAppUser(Long id) throws SQLException {
		AppUserBean appUserBean = new AppUserBean();
		this.appUser = appUserBean.getUserById(id);
	}
	
	public AppUser getAppUser() {
		return this.appUser;
	}

	public Long getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(Long appUserId) {
		this.appUserId = appUserId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
