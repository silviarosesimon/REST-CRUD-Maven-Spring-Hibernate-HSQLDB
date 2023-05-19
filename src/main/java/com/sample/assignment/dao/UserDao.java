/**
* User Dao Interface.
* @author  Silvia Rose Simon
* @version 1.0 
*/
package com.sample.assignment.dao;

import java.util.List;

import com.sample.assignment.model.User;

public interface UserDao {

	public void addUser(User user);
	
	public List<User> findAllUsers();
	
	public User findById(long id);
	
	public void updateUser(User user);
	
	public void deleteUser(User user);
	
	public User findByUserName(String userName);

}
