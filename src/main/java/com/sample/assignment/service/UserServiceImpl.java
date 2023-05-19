/**
* User Service Implementation.
* @author  Silvia Rose Simon
* @version 1.0 
*/
package com.sample.assignment.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.assignment.dao.UserDao;
import com.sample.assignment.model.User;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	Logger logger = Logger.getLogger(UserServiceImpl.class);

	//for creating user
	@Override
	public boolean createUser(User user) {
		logger.debug("createUser:Start");
		boolean unique;
		User currentUser = userDao.findByUserName(user.getUsername());
		logger.debug(currentUser);
		if((currentUser != null) && (currentUser.getUsername().equals(user.getUsername()))) {
			unique = false;
			logger.debug("createUser:Unique User: "+unique);
		}
		else {
			unique = true;
			logger.debug("createUser:Adding User: "+user.getUsername());
			userDao.addUser(user);
		}
		return unique;
	}
	
	//listing all users
	@Override
	public List<User> getAllUsers() {
		logger.debug("getAllUsers:Start");
		List<User> users = userDao.findAllUsers();
		logger.debug("getAllUsers:No: of Users: "+users.size());
		return users;
	}
	
	//get user by id
	@Override
	public User getUserById(long id) {
		logger.debug("getUserById:Start");
		User user = userDao.findById(id);
		if(user != null) {
			logger.debug("getUserById:User Found: "+user.getUsername());
		}
		else {
			logger.debug("User Not Found");
		}
		return user;
	}
	
	//update user
	@Override
	public boolean updateUser(long id,User user) {
		 logger.debug("updateUser:Start");
		 boolean success = true;
		 User currentUser = userDao.findById(id);
		 if(currentUser != null) {
			 if(!isNull(user.getUsername())){
				 currentUser.setUsername(user.getUsername());
			 }
			 if(!isNull(user.getPassword())){
				 currentUser.setPassword(user.getPassword());
			 }
			 if(!isNull(user.getStatus().toString())){
				 currentUser.setStatus(user.getStatus());
			 }
			 userDao.updateUser(currentUser);
		 }
		 else {
			 success = false;
		 }
		 logger.debug("updateUser:User Present: "+success);
		 return success;
	}
	
	//Delete User
	@Override
	public boolean deleteUser(long id) {
		logger.debug("deleteUser:Start");
		User user = userDao.findById(id);
		boolean exists = false;
		if(user != null) {
			exists = true;
			userDao.deleteUser(user);
		}
		logger.debug("deleteUser:User Present: "+exists);
		return exists;
	}
	
	//Check for null values
	@Override
	public boolean isNull(String param) {
		logger.debug("isNull:Start");
		if(param == null) {
			return true;
		}
		else {
			return false;
		}
	}
}
