package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import Model.Model;
import Model.Post;
import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddPostController {
	

	@FXML
	private TextField postidTextField;
	@FXML
	private TextField contentTextField;
	@FXML
	private Label authorLabel;
	@FXML
	private TextField likesTextField;
	@FXML
	private TextField sharesTextField;

	@FXML
	private Label invalidDetails;
	@FXML
	private Button createPostbutton;
	
	@FXML
	private Button dashboard;
	
	private Model model;
	private Stage stage;
	private Stage parentStage;
	private User currentUser;

	public AddPostController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.setCurrentUser(model.getCurrentUser());
	}
	
	public void setCurrentUser(User user) {
		this.currentUser = user;
	}
	
	@FXML
	public void initialize() {
		String author = model.getCurrentUser().getUsername();
		authorLabel.setText(author);
		LocalDateTime now = LocalDateTime.now();
        
        // Format the date and time as a string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedDateTime = now.format(formatter);
        
		createPostbutton.setOnAction(event ->{
			
			
			int id = this.getId(postidTextField.getText()) ;	
			String content =contentTextField.getText();
						
			int likes = this.getLikes(likesTextField.getText());
			int shares = this.getShares(sharesTextField.getText());
			
			String datetime = formattedDateTime;
			
			if(id !=-1 && content != "" && likes !=-1 && shares != -1 && datetime != null && author !="") {
				Post p = new Post(id,  content,  author,  likes,  shares,  datetime); //post object
				try {
					model.getPostsDao().createNewPost(id, content, author, likes, shares, datetime);
					//System.out.println("The post has been added to the collection! ");
					
					try {
						List<Post> postList = model.getPostsDao().getPosts(author);
						
						model.getCurrentUser().setPosts(postList);
						for (Post p1: postList) {
							System.out.print(p1.getId()+"- \t");
						}
						
						System.out.println("The post has been added to the collection! ");
						invalidDetails.setText("The post has been added to the collection!");
						callDashboard( stage,  model);
						
					} catch (SQLException e) {
						invalidDetails.setText("Post ID exists");
						e.printStackTrace();
					}
					
					
				} catch (SQLException e) {
					invalidDetails.setText("Post ID exists");
					e.printStackTrace();
				} catch (ParseException e) {
					invalidDetails.setText("Invalid Details");
					e.printStackTrace();
				}
				
			}
			
		});
		//when clicking the back button - leads to dashboard
				dashboard.setOnAction(event -> {
					callDashboard( stage,  model);
				});
		
	}

	
	private int getLikes(String like) {
		//Method to check if the likes are valid
		int likes;
		try {
			 likes = Integer.parseInt(like);
			 return likes;
			 
		}catch(NumberFormatException  e) {
			invalidDetails.setText("Likes should be a Number");
		}

		return -1;
	}
	private int getShares(String share) {
		//Method to check if the shares are valid
		int shares;
		try {
			shares = Integer.parseInt(share);
			 return shares;
			 
		}catch(NumberFormatException  e) {
			invalidDetails.setText("Shares should be a Number");
		}

		return -1;
	}

	private int getId(String id) {
		//Method to check ID validity
		int postId;
		try {
			 postId = Integer.parseInt(id);
			 
			 Post p = model.getPostsDao().getPostById(postId);
			
			 if(p == null) {
				 return postId;
			 }
			 
		}catch(NumberFormatException  e) {
			invalidDetails.setText("Post Id should be a Number");
		}catch(SQLException e) {
			invalidDetails.setText("Post Id already exists");	
		}

		return -1;
		
	} 
	
	public void callDashboard(Stage stage, Model model) {
		//Method to call the dashboard
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/dashboard.fxml"));
			DashboardController dashboardController = new DashboardController(stage, model);
			
			loader.setController(dashboardController);
			VBox root = loader.load();
			root.getStylesheets().add("styles.css");
			dashboardController.showStage(root);
			stage.close();

		}catch (IOException e) {
			System.out.println(e.getMessage());
			invalidDetails.setText(e.getMessage());
			 e.printStackTrace();
		}
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Add Post");
		stage.show();
	}
	

}

