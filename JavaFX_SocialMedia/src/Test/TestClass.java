package Test;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import Dao.UserDaoImpl;
import Model.User;

public class TestClass {
	ArrayList<String> validAwards = new ArrayList<>();
	
	@Test
	void testValidCreateUser() {
		//Test Case 1: Check the function with valid inputs
		UserDaoImpl userDao = new UserDaoImpl();
        User u = null;
		//Test Case 1_Test Data 1		
		//If successful, this method returns user
              
				try {
					u = userDao.createNewUser("first", "last", "user", "pwd123");
					assertNotNull(u);
				} catch (SQLException e) {
					//e.printStackTrace();}		
				}
				
		//Test Case 1_Test Data 2	
		//If successful, this method returns user
		       
		       try {
					u = userDao.createNewUser("first1", "last", "user", "pwd"); //password needs to be valid
					
				} catch (SQLException e) {assertNull(u);}		

	}
	@Test
	void testValidCreateUser1() {
		//Test Case 1: Check the function with valid inputs
		UserDaoImpl userDao = new UserDaoImpl();
        User u;
		
				
		//Test Case 1_Test Data 2	
		//If successful, this method returns user
		       
		       try {
					u = userDao.createNewUser("first1", "last", "user1", "pwd"); //password needs to be valid
					assertNull(u);
				} catch (SQLException e) {e.printStackTrace();}		

	}
	
	
	
	

}
