package Test;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import Controller.SignupController;
import Dao.PostsDaoImpl;
import Dao.UserDaoImpl;
import Model.Model;
import Model.Post;
import Model.User;
import javafx.stage.Stage;

public class TestClass {
	ArrayList<String> validAwards = new ArrayList<>();
	
	
	
	@Test
	void testValidCreateUser() { //Test Case 1: Check the function with valid inputs
		
		UserDaoImpl userDao = new UserDaoImpl();
        User u = null;
		//Test Case 1_Test Data 1		
		//If successful, this method returns user
              
				try {
					u = userDao.createNewUser("first", "last", "user1", "pwd123");
					assertNotNull(u);
				} catch (SQLException e) {}
	

	}
	@Test
	void testUpdateUser() { //Test Case 1: Check the updateUser function with valid inputs
		
		UserDaoImpl userDao = new UserDaoImpl();
        User u = null;
		//Test Case 2_Test Data 1		
		//If successful, this method returns user
              
				try {
					u = userDao.updateUser("first", "lastnew", "user1", "pwd123");
					assertNotNull(u);
				} catch (SQLException e) {}
				
		//Test Case 2_Test Data 2	
		//If successful, this method returns null				
				try {
					u = userDao.updateUser("first", "lastnew", "use", "pwd123"); //wrong username
					assertNull(u);
				} catch (SQLException e) {}
	

	}
	@Test
	void testUpdatePost() { //Test Case 1: Check the updatePost function with valid inputs
		
		PostsDaoImpl postDao = new PostsDaoImpl();
		
		//Test Case 3_Test Data 1		
		//If successful, this method returns post
              
				try {
					Post p = new Post(20582,"Come and meet us at Building 14 of RMIT.","SD2C45",10,24,"12/05/2023 10:10");
					Post updatedPost = postDao.updatePost(p);
					assertNotNull(updatedPost);
				} catch (SQLException e) {}
		
		//Test Case 3_Test Data 2		
		//If successful, this method returns null
		              
				try {
					//Wrong username
					Post p = new Post(20111,"Come and meet us at Building 14 of RMIT.","SD2C45",10,24,"12/05/2023 10:10");
					Post updatedPost = postDao.updatePost(p);
					assertNull(updatedPost);
				} catch (SQLException e) {}				
				
		
	}
	
	
	
	
	
	

}
