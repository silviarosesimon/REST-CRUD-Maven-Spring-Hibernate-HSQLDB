/**
* User Service Interface.
* @author  Silvia Rose Simon
* @version 1.0 
*/
package com.sample.assignment.service;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.sample.assignment.model.User;

public interface UserService {

	//For securing service layer
	@Secured("ROLE_ADMIN")
	public boolean createUser(User user);
	
	@Secured("ROLE_ADMIN")
	public List<User> getAllUsers();
	
	@Secured("ROLE_ADMIN")
	public User getUserById(long id);
	
	@Secured("ROLE_ADMIN")
	public boolean updateUser(long id,User user);
	
	@Secured("ROLE_ADMIN")
	public boolean deleteUser(long id);
	
	@Secured("ROLE_ADMIN")
	public boolean isNull(String param);

}
