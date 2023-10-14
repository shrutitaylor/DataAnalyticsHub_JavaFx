package Model;
import java.sql.SQLException;

import Dao.PostsDao;
import Dao.PostsDaoImpl;
import Dao.UserDao;
import Dao.UserDaoImpl;


public class ModelMine {

	private UserDao userDao;
	private User currentUser; 
	private PostsDao postsDao;
	private PostsDaoImpl postsDaoImpl;
	
	public ModelMine() {
		userDao = new UserDaoImpl();
		postsDao = new PostsDaoImpl();
		
	}
	
	public void setup() throws SQLException {
		userDao.setup();
		postsDao.setup();
	}
	
	public UserDao getUserDao() {
		return userDao;
	}
	public PostsDao getPostsDao() {
		return postsDao;
	}
	
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	public void setCurrentUser(User user) {
		currentUser = user;
	}
	
	
	
}
