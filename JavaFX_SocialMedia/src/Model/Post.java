package Model;

import java.util.*;
import java.util.Date;

import Controller.PageController;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

import java.io.*;
import java.text.SimpleDateFormat;

public class Post{
	
	private int id;
	private String content;
	private String author;
	private int likes;
	private int shares;
	private Date dateTime;
	
	 Button update;
	public void update() {
		 update.setOnAction(e -> {
	            ObservableList<Post> post = PageController.table_info_app.getSelectionModel().getSelectedItems();

	            for (Post p : post) {
	                if (p.getUpdate() == update) {
	                    System.out.println("id: " +p.getId());
	                    System.out.println("name: " + p.getAuthor());
	                    System.out.println("email: " + p.getLikes());
	                    System.out.println("notes: " + p.getShares());
	                }
	            }
	        });
	}
	 public Button getUpdate() {
	        return update;
	    }
	
	public void setUpdate(Button update) {
		this.update = update;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getShares() {
		return shares;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	
	
	public Post(int id, String content, String author, int likes, int shares, String datetime) {
		this.id = id;
		this.content = content;
		this.author = author;
		this.likes = likes;
		this.shares = shares;
		
		try {
		this.dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH).parse(datetime);
		}
		catch(Exception e){
		}	
				
	}
	
	public Post(List<String> record) {
		
		this.id = Integer.parseInt(record.get(0));
		this.content = record.get(1);
		this.author = record.get(2);
		this.likes = Integer.parseInt(record.get(3));
		this.shares = Integer.parseInt(record.get(4));
		
		try {
		this.dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH).parse(record.get(5));
		}
		catch(Exception e){
			
		}	
		
	}
	
	public void printData() {
		System.out.println("Post Id: "+this.getId()+"\n"+
				"Content: "+this.getContent()+"\n"+
				"Author: "+this.getAuthor()+"\n"+
				"Likes: "+this.getLikes()+"\n"+
				"Shares:"+this.getShares()+"\n"+
				"Date Posted: "+new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH).format( this.getDateTime()));

		
	}
	public String getData() {
		String currentpost = ("Post Id: "+this.getId()+"\n"+
				"Content: "+this.getContent()+"\n"+
				"Author: "+this.getAuthor()+"\n"+
				"Likes: "+this.getLikes()+"\n"+
				"Shares:"+this.getShares()+"\n"+
				"Date Posted: "+new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH).format( this.getDateTime()));
		return currentpost;
		
	}
}


