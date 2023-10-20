package Dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import Model.Post;
import Model.User;

public interface PostsDao {
	
	void setup() throws SQLException;
	
	List<Post> getPosts(String username) throws SQLException;
	List<Post> getAllPosts() throws SQLException;
	
	Post getPostById(int postId) throws SQLException;
	Post createNewPost(int id, String content, String author, int likes, int shares, String datetime)
			throws SQLException, ParseException;	
	Post updatePost(Post p) throws SQLException;

}
