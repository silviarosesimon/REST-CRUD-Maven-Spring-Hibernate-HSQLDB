/**
* User Service Test Class.
* @author  Silvia Rose Simon
* @version 1.0 
*/
package com.sample.assignment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.sample.assignment.dao.UserDao;
import com.sample.assignment.model.Status;
import com.sample.assignment.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {
 
    @TestConfiguration
    static class UserServiceTestImplContextConfiguration {
  
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }
 
    @Autowired
    private UserService userService;
 
    @MockBean
    private UserDao userDao;
    
    //Add User
    @Test
    public void testAddUser() {
    	User user = new User("admin","admin", Status.Activated);
        boolean created = userService.createUser(user);
        
        verify(userDao).addUser(any(User.class));
    
        assertThat(created).isEqualTo(true);
    }
    
    //Get User By Id
    @Test
    public void testGetUserById() {
    	User user = new User("admin","admin",Status.Activated);
         
        when(userDao.findById(user.getId()))
           .thenReturn(user);
         
        long id = 1;
        User found = userService.getUserById(id);
        
        verify(userDao).findById(any(Long.class));
        
        if(found != null) {
        	assertThat(found.getUsername()).isEqualTo("admin");
        }
        else {
        	assertThat("User Not Present");
        }
    }
    
    //find users
    @Test
    public void testFindUsers() {
    	User user = new User("user","user",Status.Deactivated);
        List<User> allUsers = Arrays.asList(user);
        when(userDao.findAllUsers()).thenReturn(allUsers);
        
        List<User> users = userService.getAllUsers();
        
        verify(userDao).findAllUsers();
        
        assertNotNull("Users not found", users);
		assertEquals("Incorrect User List", allUsers.size(), users.size());
		
     }
    
    //update user
    @Test
    public void testUpdateUser() {
    	long id = 1;    
    	User userStub = userService.getUserById(id);
    	boolean updated = false;
    	if(userStub != null) {
    		userStub.setStatus(Status.Activated);
    		updated = userService.updateUser(id,userStub);
        
    		assertThat(updated).isEqualTo(true);
    	}
    	else {
    		assertThat(updated).isEqualTo(false);
    	}
     }
    
   //delete user
    @Test
    public void testDeleteUser() {
        long id = 1;
        User userStub = userService.getUserById(id);
        boolean deleted = false;
    	if(userStub != null) {
    		deleted = userService.deleteUser(id);
   
    		assertThat(deleted).isEqualTo(true);
    	}
    	else {
    		assertThat(deleted).isEqualTo(false);
    	}
     }
 
    
}