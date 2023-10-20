package Dao;


import java.sql.SQLException;
import java.util.List;

import Model.User;



/**
 * A data access object (DAO) is a pattern that provides an abstract interface 
 * to a database or other persistence mechanism. 
 * the DAO maps application calls to the persistence layer and provides some specific data operations 
 * without exposing details of the database. 
 */
public interface UserDao {
	void setup() throws SQLException;
	User getUser(String username, String password) throws SQLException;
	User createNewUser(String firstname, String lastname, String username, String password) throws SQLException;
	User updateUser(String firstname, String lastname, String username, String password) throws SQLException ;
	List<User> getAllUsers() throws SQLException;
	User getvipUser(String username, String password) throws SQLException;
}
