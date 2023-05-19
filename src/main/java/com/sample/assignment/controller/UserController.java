/**
* Controller for REST API.
* @author  Silvia Rose Simon
* @version 1.0 
*/
package com.sample.assignment.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sample.assignment.model.User;
import com.sample.assignment.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	Logger logger = Logger.getLogger(UserController.class);

	//GET method for listing all users
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		logger.debug("listAllUsers:Start");
		List<User> users = userService.getAllUsers();
		logger.debug("No: of Users: "+users.size());
		if(users.isEmpty()){
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	//GET method for retrieving user with given ID.
	@RequestMapping(value="/user/{id}", method=RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable("id") long id) {
		logger.debug("getUser:Start");
		User user = userService.getUserById(id);
		if(user == null){
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
		else {
			logger.debug("User Found: "+user.getUsername());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}	
	
	//POST method for adding a user
	@RequestMapping(value="/user", method=RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user) {
		logger.debug("createUser:Start");
		boolean unique = userService.createUser(user);
		logger.debug("Unique User: "+unique);
		if (!unique) {
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }
		else {
			return new ResponseEntity<User>(user,HttpStatus.CREATED);
		}
	}
	
	//PUT method for updating a user with the given ID.
	@RequestMapping(value="/user/{id}", method=RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		logger.debug("updateUser:Start");
		boolean success = userService.updateUser(id,user);
		logger.debug("User Present: "+success);
		if (!success) {
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
		else {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}	
	
	//DELETE method for deleting user with the given ID.
	@RequestMapping(value="/user/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
		logger.debug("deleteUser:Start");
		boolean success = userService.deleteUser(id);
		logger.debug("User Present: "+success);
		if (!success) {
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
		else {
			return new ResponseEntity<User>(HttpStatus.OK);
		}
	}	

}
