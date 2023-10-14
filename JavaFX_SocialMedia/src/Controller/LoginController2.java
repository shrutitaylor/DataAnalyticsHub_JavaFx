package Controller;
import java.io.IOException;
import java.security.GeneralSecurityException;

import Model.ModelMine;
import Model.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class LoginController2 {

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
	
	public LoginController2(Stage stage, ModelMine model) {
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
				    model.setCurrentUser(currentUser);

				    try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
						ProfileController profileController = new ProfileController(stage, model);
						
						loader.setController(profileController);
						VBox root = loader.load();
						profileController.showStage(root);
						
						} catch (IOException e) {
							System.out.println(e.getMessage());
							//invalidDetails.setText(e.getMessage());
						}
					}
				   
				    
				    
				}
				    
				}
		catch(Exception e){
			invalidDetails.setText("bkjbk");
		}
		
	
	});
		signUpButton.setOnAction(event -> {
		
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signUp.fxml"));
            
            SignupController signupController =  new SignupController(stage, model);
			loader.setController(signupController);

			GridPane root = loader.load();
			signupController.showStage(root);
			
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	
	});
	}
	
	public User authenticate(String username, String password) {
		try {
			//System.out.println(s.encryptPassword(passwordTextField.getText()));
			//s.encryptPassword(passwordTextField.getText());
			//User u = db.getUsername(username, password);	
			User u = model.getUserDao().getUser(username, password);
			if (u != null) {
				System.out.println(u.getPassword());
				return u;
			}
			
		} catch (Exception e) {	e.printStackTrace(); }		
			
		return null;
	    
	}
	
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root,600,400);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Login Page");
		stage.show();
	}
	
	

}
