package Controller;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.ModelMine;
import Model.Post;
import Model.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class LoginController {

	@FXML 
	private TextField usernameTextField;
	@FXML 
	private TextField passwordTextField;
	
	@FXML 
	private Label outputLabel;
	
	@FXML 
	private Label invalidDetails;
	@FXML
	private Button loginButton;
	@FXML
	private Button signUpButton;

	
	protected
	String successMessage = String.format("-fx-text-fill: GREEN;");
	String errorMessage = String.format("-fx-text-fill: RED;");
	String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
	String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");	
	
	
	
	private ModelMine model;
	private Stage stage;
	
	
	public LoginController(Stage stage, ModelMine model) {
		this.stage = stage;
		this.model = model;
	}
	
	@FXML
	public void initialize() {	
		
		
		
		loginButton.setOnAction(event -> {
		User currentUser;
		try {
			if (usernameTextField.getText().isBlank() || passwordTextField.getText().isBlank()) {
				  invalidDetails.setStyle(errorMessage);

				// When the username and password are blank
				if (usernameTextField.getText().isBlank() || passwordTextField.getText().isBlank()) {
				    invalidDetails.setText("The Login fields are required!");
				    usernameTextField.setStyle(errorStyle);
				    passwordTextField.setStyle(errorStyle);
				    

				} else // When only the username is blank
				if (usernameTextField.getText().isBlank()) {
				    usernameTextField.setStyle(errorStyle);
				    invalidDetails.setText("The Username or Email is required!");
				    passwordTextField.setStyle(successStyle);
				    
				          
				} else // When only the password is blank
				    if (passwordTextField.getText().isBlank()) {
				    	passwordTextField.setStyle(errorStyle);
				        invalidDetails.setText("The Password is required!");
				        usernameTextField.setStyle(successStyle);
				        
				        
				    }
				} else // Check if password is less than four characters, if so display error message
				  if (passwordTextField.getText().length() < 4) {
				      invalidDetails.setText("The Password can't be less than 4 characters!");
				      invalidDetails.setStyle(errorMessage);
				      passwordTextField.setStyle(errorStyle);
				      
				      
				  }
				// If all login details are entered as required then display success message
				else {
					currentUser = this.authenticate(usernameTextField.getText().toString(),passwordTextField.getText().toString());
					//System.out.println("1");
					if(currentUser == null) {
						invalidDetails.setText("Wrong username or password");
					    invalidDetails.setStyle(errorMessage);
					    usernameTextField.setStyle(errorStyle);
					    passwordTextField.setStyle(errorStyle);
					}
					
					else {
				    invalidDetails.setText("Login Successful!");
				    invalidDetails.setStyle(successMessage);
				    usernameTextField.setStyle(successStyle);
				    passwordTextField.setStyle(successStyle);
				    
				    // set current uer after log in 
				    model.setCurrentUser(currentUser); 
				    //set posts of current user after login 
				    this.setUserPosts(currentUser);
				   // System.out.println("2");
					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/dashboard.fxml"));
						DashboardController dashboardController = new DashboardController(stage, model);
						
						loader.setController(dashboardController);
						
						VBox root = loader.load();
						root.getStylesheets().add("styles.css");
						dashboardController.showStage(root);
						stage.close();
						
						showAlert();

					}catch (IOException e) {
						System.out.println(e.getMessage());
						invalidDetails.setText(e.getMessage());
						 e.printStackTrace();
					}
//				    try {
//						FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Page.fxml"));
//						PageController pageController = new PageController(stage, model);
//						
//						loader.setController(pageController);
//						VBox root = loader.load();
//
//						pageController.showStage(root);
//						stage.close();
//
//					}catch (IOException e) {
//						System.out.println(e.getMessage());
//						invalidDetails.setText(e.getMessage());
//						 e.printStackTrace();
//					}
				  }				    
				}
			}
		catch(Exception e){
			invalidDetails.setText(e.getMessage());
		}
		
	
	});
		signUpButton.setOnAction(event -> {
		
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/signUp.fxml"));
            
            SignupController signupController =  new SignupController(stage, model);
			loader.setController(signupController);

			GridPane root = loader.load();
			root.getStylesheets().add("styles.css");
			signupController.showStage(root);
			stage.close();
			
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	
	});
		

		
	}
	
	public User authenticate(String username, String password) {
		try {
			//System.out.println(s.encryptPassword(passwordTextField.getText()));
			//s.encryptPassword(passwordTextField.getText());
			User u = model.getUserDao().getUser(username, password);		
			if (u != null) {
				System.out.println(u.getPassword());
				return u;
			}
			
		} catch (Exception e) {	e.printStackTrace(); }		
			
		return null;
	    
	}
	
	
	
	public void setUserPosts(User user) {
		try {
			List<Post> postList = model.getPostsDao().getPosts(user.getUsername());
			model.getCurrentUser().setPosts(postList);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root,600,400);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Login Page");
		stage.show();
	}
	
	private void showAlert(){
		// a custom confirmation dialog
        Alert dialog = new Alert(AlertType.CONFIRMATION);
        dialog.setTitle("Confirmation Dialog");
        dialog.setHeaderText("Would you like to subscribe to the application for a monthly fee of $0?");
        //dialog.setContentText("");

        // Create custom buttons
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        dialog.getButtonTypes().setAll(yesButton, noButton);

        // Add buttons to the dialog
        Button yesButtonNode = (Button) dialog.getDialogPane().lookupButton(yesButton);
        Button noButtonNode = (Button) dialog.getDialogPane().lookupButton(noButton);

        yesButtonNode.setOnAction(event -> {
            dialog.close(); // Close the confirmation dialog
            showLoginAlert(); // Show the "need to login" alert
        });

        noButtonNode.setOnAction(event -> {
            dialog.close(); // Close the confirmation dialog
        });

        // Show the confirmation dialog
        dialog.show();

    
}
	private void showLoginAlert() {
        // Create an alert for "need to login"
        Alert loginAlert = new Alert(AlertType.INFORMATION);
        loginAlert.setTitle("Information");
        loginAlert.setHeaderText(null);
        loginAlert.setContentText("Please log out and log in again to access VIP functionalities.");
        loginAlert.show();
    }
	
	

}
