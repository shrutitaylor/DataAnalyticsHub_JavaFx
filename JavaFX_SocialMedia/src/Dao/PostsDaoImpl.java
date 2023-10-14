package Dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Model.Post;
import Model.User;



public class PostsDaoImpl implements PostsDao{
	private final String TABLE_NAME = "Posts";
	
	
	
	@Override
	public void setup() throws SQLException {
		try (Connection connection = DatabaseConnection.getConnection();
				Statement stmt = connection.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INT NOT NULL," + " content TEXT NOT NULL,"+" author TEXT NOT NULL," +
					" likes INT ," + " shares INT ," +"dateTime Date NOT NULL," + "PRIMARY KEY (id))";
					
			stmt.executeUpdate(sql);
		} 
	}
	
	@Override
	public List<Post> getPosts(String username) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE author = ? ";
		List<Post> postList = new ArrayList<>();
		try (Connection connection = DatabaseConnection.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
						
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					//System.out.println(rs.getInt("id"));
					//Post(int id, String content, String author, int likes, int shares, String datetime)
					Post p = new Post(rs.getInt("id"),rs.getString("content"),rs.getString("author"),
							rs.getInt("likes"),rs.getInt("shares"),rs.getString("dateTime"));
					postList.add(p);
					//System.out.println(p.getAuthor());		
					
				}
				
			} return postList;
		}
	}
	@Override
	public Post getPostById(int postId) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ? ";
		try (Connection connection = DatabaseConnection.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setInt(1, postId);
						
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					//System.out.println(rs.getInt("id"));
					//Post(int id, String content, String author, int likes, int shares, String datetime)
					Post p = new Post(rs.getInt("id"),rs.getString("content"),rs.getString("author"),
							rs.getInt("likes"),rs.getInt("shares"),rs.getString("dateTime"));
					
					//System.out.println(p.getAuthor());		
					return p;
				}
				
			} return null;
		}
	}
	@Override
	public List<Post> getAllPosts() throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + ";";
		List<Post> postList = new ArrayList<>();
		try (Connection connection = DatabaseConnection.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			
						
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					//System.out.println(rs.getInt("id"));
					//Post(int id, String content, String author, int likes, int shares, String datetime)
					Post p = new Post(rs.getInt("id"),rs.getString("content"),rs.getString("author"),
							rs.getInt("likes"),rs.getInt("shares"),rs.getString("dateTime"));
					postList.add(p);
					//System.out.println(p.getAuthor());		
					
				}
				
			} return postList;
		}
	}
	
	@Override
	public Post createNewPost(int id, String content, String author, int likes, int shares, String datetime) throws SQLException, ParseException {
		
//		Date d =  new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH).parse(datetime);
//		java.sql.Date sqlDate = new java.sql.Date(d.getTime());
		
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ? ,?, ?)";
		// insert into posts values(username, password,firstname,lastname);
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setInt(1, id);
			stmt.setString(2, content);
			stmt.setString(3, author);
			stmt.setInt(4, likes);
			stmt.setInt(5, shares);
			stmt.setString(6, datetime);
			
			
			stmt.executeUpdate();
			
			return new Post(id,  content,  author,  likes,  shares,  datetime);
		}
	}

}
