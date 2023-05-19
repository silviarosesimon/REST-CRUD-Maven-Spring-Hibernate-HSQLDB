/**
* User Model Class.
* @author  Silvia Rose Simon
* @version 1.0 
*/
package com.sample.assignment.model;

public class User {
	
	private long id;
	private String username;
	private String password;
	private Status status;
	
	public User() {
		
	}
	public User(String username,String password, Status status) {
		this.username = username;
		this.password = password;
		this.status = status;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Status getStatus() {
	    return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
}
