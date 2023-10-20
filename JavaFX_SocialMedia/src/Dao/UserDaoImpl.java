package Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import Model.User;

public class UserDaoImpl implements UserDao{

	private final String TABLE_NAME = "Users";
	List<User> userList = new ArrayList<>();
	
	
	@Override
	public void setup() throws SQLException {
		try (Connection connection = DatabaseConnection.getConnection();
				Statement stmt = connection.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (username VARCHAR(10) NOT NULL,"
					+ "password VARCHAR(8) NOT NULL," +"firstname TEXT    NOT NULL,"+" lastname  TEXT  NOT NULL,"+"vip TEXT, "+ "PRIMARY KEY (username))";
			stmt.executeUpdate(sql);
		} 
	}

	@Override
	public User createNewUser(String firstname, String lastname, String username, String password) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?)";
		// insert into users values(username, password,firstname,lastname);
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, firstname);
			stmt.setString(4, lastname);
			

			stmt.executeUpdate();
			return new User(firstname, lastname,username, password);
		} 
	}

	@Override
	public User getUser(String username, String password) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
		try (Connection connection = DatabaseConnection.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					// User(String firstname,String lastname, String username,String password) 
					User user = new User(rs.getString("firstname"),rs.getString("lastname"),rs.getString("username"),rs.getString("password"));
					user.setVip(rs.getString("vip"));
					return user;
				}
				return null;
			} 
		}
	}
	@Override
	public User getvipUser(String username, String password) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
		try (Connection connection = DatabaseConnection.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					// User(String firstname,String lastname, String username,String password) 
					User user = new User(rs.getString("firstname"),rs.getString("lastname"),rs.getString("username"),rs.getString("password"));
					user.setVip(rs.getString("vip"));
					return user;
				}
				return null;
			} 
		}
	}
	
	@Override
	public User updateUser(String firstname, String lastname, String username, String password) throws SQLException {
		
		String sql = "UPDATE " + TABLE_NAME + " SET firstname = ? ,lastname = ? , password = ? WHERE username = ?";
		try (Connection connection = DatabaseConnection.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, firstname);
			stmt.setString(2, lastname);
			stmt.setString(3, password);
			stmt.setString(4, username);
			
			stmt.executeUpdate();

			//after executing update
            User u = this.getUser(username, password);
            return u;
		}
	}
	
	@Override
	public List<User> getAllUsers() throws SQLException {
		
		String sql = "SELECT * FROM " + TABLE_NAME + ";";
		User user;
		try (Connection connection = DatabaseConnection.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					// User(String firstname,String lastname, String username,String password) 
					user = new User(rs.getString("firstname"),rs.getString("lastname"),rs.getString("username"),rs.getString("password"));
					userList.add(user);
					System.out.println(rs.getString("firstname"));
					
				}
				return userList;
			}
		}
	}
	
	

}
