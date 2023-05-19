/**
* User Controller Test Class.
* @author  Silvia Rose Simon
* @version 1.0 
*/
package com.sample.assignment.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.util.Arrays;
import java.util.List;

import com.sample.assignment.model.Status;
import com.sample.assignment.model.User;
import com.sample.assignment.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    
    private final String URL = "/user/";
    
    //For testing add user
    @Test
    public void testAddUser() throws Exception {
    	User userStub = new User("user","user", Status.Deactivated);
    	when(userService.createUser(any(User.class))).thenReturn(true);
     
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL).with(httpBasic("admin","admin"))
    							  .contentType(MediaType.APPLICATION_JSON_UTF8)
    							  .accept(MediaType.APPLICATION_JSON_UTF8)
    							  .content(objectToJson(userStub))).andReturn();
     
    	int status = result.getResponse().getStatus();
    	assertEquals("Incorrect Response Status", HttpStatus.CREATED.value(), status);
     
    	verify(userService).createUser(any(User.class));
     
    	User resultUser = jsonToObject(result.getResponse().getContentAsString(),
    										User.class);
    	assertNotNull(resultUser);
    	assertEquals("user", resultUser.getUsername());
     
    }
    
    //For testing add user - duplicate username
    @Test
    public void testAddUserDuplicate() throws Exception {
    	User userStub = new User("user","user",Status.Deactivated);
    	when(userService.createUser(any(User.class))).thenReturn(true);
     
    	MvcResult user1 = mockMvc.perform(MockMvcRequestBuilders.post(URL).with(httpBasic("admin","admin"))
    							  .contentType(MediaType.APPLICATION_JSON_UTF8)
    							  .accept(MediaType.APPLICATION_JSON_UTF8)
    							  .content(objectToJson(userStub))).andReturn();
    	
    	MvcResult users1 = mockMvc.perform(MockMvcRequestBuilders.get(URL).with(httpBasic("admin","admin"))
				  .contentType(MediaType.APPLICATION_JSON_UTF8)
				  .accept(MediaType.APPLICATION_JSON_UTF8)
				  .content(objectToJson(userStub))).andReturn();
    	
    	TypeToken<List<User>> token = new TypeToken<List<User>>() {
		};
		@SuppressWarnings("unchecked")
		List<User> usrListResult1 = jsonToList(users1.getResponse().getContentAsString(), token);
    	
    	assertEquals(" User List", usrListResult1.size(), 1);
     
    	MvcResult user2 = mockMvc.perform(MockMvcRequestBuilders.post(URL).with(httpBasic("admin","admin"))
				  .contentType(MediaType.APPLICATION_JSON_UTF8)
				  .accept(MediaType.APPLICATION_JSON_UTF8)
				  .content(objectToJson(userStub))).andReturn();
    	
    	MvcResult users2 = mockMvc.perform(MockMvcRequestBuilders.get(URL).with(httpBasic("admin","admin"))
				  .contentType(MediaType.APPLICATION_JSON_UTF8)
				  .accept(MediaType.APPLICATION_JSON_UTF8)
				  .content(objectToJson(userStub))).andReturn();
  	
		@SuppressWarnings("unchecked")
		List<User> usrListResult2 = jsonToList(users2.getResponse().getContentAsString(), token);
	  	
	  	assertEquals(" User List", usrListResult2.size(), 1);
     
    }
    
    //For testing get all users
    @Test
    public void testListAllUsers() throws Exception {   
        User user = new User("user","user",Status.Deactivated);
        List<User> allUsers = Arrays.asList(user);
        when(userService.getAllUsers()).thenReturn(allUsers);
     
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL).with(httpBasic("admin","admin"))
    							  .contentType(MediaType.APPLICATION_JSON_UTF8)
    							  .accept(MediaType.APPLICATION_JSON_UTF8)
    							  .content(objectToJson(user))).andReturn();
     
    	int status = result.getResponse().getStatus();
    	assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
     
    	verify(userService).getAllUsers();
     
    	TypeToken<List<User>> token = new TypeToken<List<User>>() {
		};
		@SuppressWarnings("unchecked")
		List<User> usrListResult = jsonToList(result.getResponse().getContentAsString(), token);

		assertNotNull("Users not found", usrListResult);
		assertEquals("Incorrect User List", allUsers.size(), usrListResult.size());
    }

    //For testing get user by ID
	@Test
	public void testGetUser() throws Exception {

		User userStub = new User("user","user",Status.Deactivated);
		when(userService.getUserById(any(Long.class))).thenReturn(userStub);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "{id}", new Long(1))
						.accept(MediaType.APPLICATION_JSON_UTF8)
						.with(httpBasic("admin","admin")))
						.andReturn();

		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		verify(userService).getUserById(any(Long.class));

		User resultUser = jsonToObject(result.getResponse().getContentAsString(), User.class);
		
		assertNotNull(resultUser);
		assertEquals("user", resultUser.getUsername());
	}
	
	//for testing update user
	@Test
    public void testUpdateUser() throws Exception {
     
    	User userStub = new User("user","user",Status.Activated);
    	when(userService.updateUser(any(Long.class),any(User.class))).thenReturn(true);
     
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(URL + "{id}", new Long(1)).with(httpBasic("admin","admin"))
    							  .contentType(MediaType.APPLICATION_JSON_UTF8)
    							  .accept(MediaType.APPLICATION_JSON_UTF8)
    							  .content(objectToJson(userStub))).andReturn();
     
    	int status = result.getResponse().getStatus();
    	assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
     
    	verify(userService).updateUser(any(Long.class),any(User.class));
     
    	User resultUser = jsonToObject(result.getResponse().getContentAsString(),
    										User.class);
    	assertNotNull(resultUser);
    	assertEquals(Status.Activated, resultUser.getStatus());
     
    }
	
	//for testing delete user
	@Test
    public void testDeleteUser() throws Exception {
     
    	User userStub = new User("user","user",Status.Deactivated);
    	when(userService.deleteUser(any(Long.class))).thenReturn(true);
     
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(URL + "{id}", new Long(1)).with(httpBasic("admin","admin"))
    							  .contentType(MediaType.APPLICATION_JSON_UTF8)
    							  .accept(MediaType.APPLICATION_JSON_UTF8)
    							  .content(objectToJson(userStub))).andReturn();
     
    	int status = result.getResponse().getStatus();
    	assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
     
    	verify(userService).deleteUser(any(Long.class));
     
    	User resultUser = jsonToObject(result.getResponse().getContentAsString(),
    										User.class);
    	assertEquals(null, resultUser);
     
    }

	//Util method for converting json to object
	public static <T> T jsonToObject(String json, Class<T> classOf) {
		Gson gson = new Gson();
		return gson.fromJson(json, classOf);
	}
	
	//Util method for converting object to json
	public static String objectToJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
    
	//Util method for converting json to list
    public static List jsonToList(String json, TypeToken token) {
		Gson gson = new Gson();
		return gson.fromJson(json, token.getType());
	}
	
}
