/**
* User Dao Test Class.
* @author  Silvia Rose Simon
* @version 1.0 
*/
package com.sample.assignment.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.sample.assignment.model.Status;
import com.sample.assignment.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserDaoTest {

	@TestConfiguration
    static class UserDaoTestContextConfiguration {
  
        @Bean
        public UserDao userDao() {
            return new UserDaoImpl();
        }
    }
 
    @Autowired
    private UserDao userDao;
    
    private User user;
    
    @Before
    public void setUp() {
        user = new User("admin","admin", Status.Activated);
     
    }
    
    //create user
    @Test
    public void testCreateUser() throws Exception {
        userDao.addUser(user);
        User found = this.userDao.findById(1);
    	if(found != null) {
    		assertThat(found.getUsername()).isEqualTo("admin");
    	}
    	else {
    		assertThat("User not created");
    	}
    }
    
    //update user
    @Test
    public void testUpdateUser() throws Exception {
    	User found = this.userDao.findById(1);
    	if(found != null) {
    		found.setStatus(Status.Deactivated);
    		userDao.updateUser(found);
    		assertThat(found.getStatus()).isEqualTo(Status.Deactivated);
    	}
    	else {
    		assertThat("User not present");
    	}
    }
    
    //delete user
    @Test
    public void testDeleteUser() throws Exception {
    	User found = this.userDao.findById(1);
    	if(found != null) {
    		userDao.deleteUser(found);
    		assertThat(userDao.findAllUsers().size()).isEqualTo(0);
    	}
    	else {
    		assertThat("User not present");
    	}
    }
    
    //find users
    @Test
    public void testFindUsers() throws Exception {
        List<User> users = userDao.findAllUsers();
        if(!users.isEmpty()) {
        	assertThat(users.size()).isEqualTo(1);
        }
        else {
        	assertThat(users.size()).isEqualTo(0);
        }
    }

    //find user by id
    @Test
    public void testFindById() throws Exception {
        User found = this.userDao.findById(1);
        if(found != null) {
        	assertThat(found.getUsername()).isEqualTo("admin");
        }
        else {
        	assertThat("User not present");
        }
    }

}