package Model;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	
	private List<Post> postList = new ArrayList<>(); //Each User has a list of posts
	private String vip; //If the user is a Vip User 
	
	public User(String firstname,String lastname, String username,String password) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
	}
	
	public String getVip() {
		return vip;
	}
	public void setVip(String vip) {
		this.vip = vip;
	}
	
	
	public List<Post> getPosts() {
		return postList;
	}
	public void setPosts(List<Post> postList) {
		this.postList = postList;
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
