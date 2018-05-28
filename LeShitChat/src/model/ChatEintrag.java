package model;

import java.io.Serializable;
import java.util.Date;

public class ChatEintrag implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String message;
	private Date dateSend;
	private Date dateRecieved;
	
	public ChatEintrag(String username, String message) {
		this.username = username;
		this.message = message;
		
		this.dateSend = new Date();
	}
	
	@Override
	public String toString() {
		return dateSend + " - " + username + ": " + message;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDateSend() {
		return dateSend;
	}
	public void setDateSend(Date dateSend) {
		this.dateSend = dateSend;
	}
	public Date getDateRecieved() {
		return dateRecieved;
	}
	public void setDateRecieved(Date dateRecieved) {
		this.dateRecieved = dateRecieved;
	}
}
