package Controller;
import java.io.IOException;
import java.sql.SQLException;

import Dao.UserDaoImpl;
import Model.ModelMine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class SignupController {
	
	@FXML
	private Button login;
	
	@FXML 
	private TextField usernameTextField;
	@FXML 
	private TextField passwordTextField;
	@FXML 
	private TextField firstnameTextField;
	@FXML 
	private TextField lastnameTextField;
	
	@FXML 
	private Label invalidDetails;
	@FXML
	private Button createUserbutton;
	
	
	protected
	String successMessage = String.format("-fx-text-fill: GREEN;");
	String errorMessage = String.format("-fx-text-fill: RED;");
	String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
	String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");	
	UserDaoImpl userImp = new UserDaoImpl();
	String regex = "^[a-zA-Z]+$";	
	
	private ModelMine model;
	private Stage stage;
	private Stage parentStage;
	
	boolean valid;
		
		
	  public SignupController(Stage parentStage, ModelMine model) {
			this.stage = new Stage();
			this.parentStage = parentStage;
			this.model = model;
		}

		@FXML
		public void initialize() {
			createUserbutton.setOnAction(event -> {
		try {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		String firstname = firstnameTextField.getText();
		String lastname = lastnameTextField.getText();
		
		valid = this.checkValid( username,  password, firstname,  lastname) ;
		
		if(valid) {
			//User createNewUser(String firstname, String lastname, String username, String password)
			
			firstname = firstname.substring(0, 1).toUpperCase() + firstname.substring(1);
			lastname = lastname.substring(0, 1).toUpperCase() + lastname.substring(1);
			
			System.out.println(username+" "+ password +" "+firstname+" "+lastname);
			model.getUserDao().createNewUser( firstname,  lastname,  username,  password);
		}
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			invalidDetails.setText("User already Exists, Try to log in.");
			invalidDetails.setTextFill(Color.RED);
		}
	});
			
			login.setOnAction(event ->{
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/login.fxml")); 
					
					LoginController loginController = new LoginController(stage, model);
					loader.setController(loginController);
									
					GridPane root = loader.load();
					root.getStylesheets().add("styles.css");
					loginController.showStage(root);
					
					//stage.close()	;				
					
			} catch (IOException | RuntimeException e) {
				System.out.println(e.getMessage());
				Scene scene = new Scene(new Label(e.getMessage()), 200, 100);
				stage.setTitle("Error");
				stage.setScene(scene);
				stage.show();
			}
			});
	
	}
	
	public boolean checkValid(String username, String password,String firstname, String lastname) {
				
		if (username.isBlank() || password.isBlank()|| firstname.isBlank()|| lastname.isBlank()) {
			  invalidDetails.setStyle(errorMessage);

			// When the username and password are blank
			if (username.isBlank() || password.isBlank()|| firstname.isBlank()|| lastname.isBlank()) {
			    invalidDetails.setText("The Login fields are required!");
			    usernameTextField.setStyle(errorStyle);
			    passwordTextField.setStyle(errorStyle);
			    firstnameTextField.setStyle(errorStyle);
			    lastnameTextField.setStyle(errorStyle);
			    

			} else
				if (firstname.isBlank() ) {
					firstnameTextField.setStyle(errorStyle);
				    invalidDetails.setText("First Name is required!");
				    passwordTextField.setStyle(successStyle);
				    usernameTextField.setStyle(successStyle);
				    lastnameTextField.setStyle(successStyle);
				    
				          
				} else
					if (lastname.isBlank()) {
						lastnameTextField.setStyle(errorStyle);
					    invalidDetails.setText("Last Name is required!");
					    passwordTextField.setStyle(successStyle);
					    usernameTextField.setStyle(successStyle);
					    firstnameTextField.setStyle(successStyle);
					    
					          
					} else
				// When only the username is blank
			if (username.isBlank()) {
			    usernameTextField.setStyle(errorStyle);
			    invalidDetails.setText("The Username or Email is required!");
			    passwordTextField.setStyle(successStyle);
			    lastnameTextField.setStyle(successStyle);
			    firstnameTextField.setStyle(successStyle);
			    
			          
			} else // When only the password is blank
			    if (password.isBlank()) {
			    	passwordTextField.setStyle(errorStyle);
			        invalidDetails.setText("The Password is required!");
			        usernameTextField.setStyle(successStyle);
				    lastnameTextField.setStyle(successStyle);
				    firstnameTextField.setStyle(successStyle);
			        
			        
			    }
			} else // Check if password is less than four characters, if so display error message
			  		     
				if (!firstname.matches(regex)) {
					invalidDetails.setText("First name should only have alphabets");
				    invalidDetails.setStyle(errorMessage);
				    firstnameTextField.setStyle(errorStyle);
				    passwordTextField.setStyle(successStyle);
				    usernameTextField.setStyle(successStyle);
				    lastnameTextField.setStyle(successStyle);
				}
				else
				  if (!lastname.matches(regex)) {
						invalidDetails.setText("Last name should only have alphabets");
					    invalidDetails.setStyle(errorMessage);
					    lastnameTextField.setStyle(errorStyle);
					    passwordTextField.setStyle(successStyle);
					    usernameTextField.setStyle(successStyle);
					    firstnameTextField.setStyle(successStyle);
				  }else
		      if (password.length() < 4) {
		      invalidDetails.setText("The Password can't be less than 4 characters!");
		      invalidDetails.setStyle(errorMessage);
		      passwordTextField.setStyle(errorStyle);
		      usernameTextField.setStyle(successStyle);
			    lastnameTextField.setStyle(successStyle);
			    firstnameTextField.setStyle(successStyle);
		      			      
		      }
			// If all login details are entered as required then display success message
			else {
			    invalidDetails.setText("Created New User!");
			    invalidDetails.setStyle(successMessage);
			    usernameTextField.setStyle(successStyle);
			    passwordTextField.setStyle(successStyle);
			    firstnameTextField.setStyle(successStyle);
			    lastnameTextField.setStyle(successStyle);
			    
			    return true;
			    
			    
			}return false;
		
	}
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Sign Up Page");
		stage.show();
	}

}
