package Controller;

import java.io.IOException;

import Model.ModelMine;
import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ProfileController {
	
	private ModelMine model;
	private Stage stage;
	
	@FXML
	private Button dashboard;
	@FXML
	private Button editProfile;
	@FXML
	private Label usernameLabel;
	@FXML
	private Label firstnameLabel;
	@FXML
	private Label lastnameLabel;
	@FXML
	private Label passwordLabel;
	
	private User currentUser;
	
	public ProfileController(Stage stage, ModelMine model) {
		this.stage = stage;
		this.model = model;
		this.currentUser = model.getCurrentUser(); // setting current user
	}
	
	@FXML
	public void initialize() {		
		//set all profile data
		usernameLabel.setText(currentUser.getUsername());
		firstnameLabel.setText(currentUser.getFirstname());
		lastnameLabel.setText(currentUser.getLastname());
		passwordLabel.setText(currentUser.getFirstname()); //wat to do with password

		//when clicking the back button - leads to dashboard
		dashboard.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/dashboard.fxml"));
				DashboardController dashboardController = new DashboardController(stage, model);
			
				loader.setController(dashboardController);
				VBox root = loader.load();
				dashboardController.showStage(root);
				//stage.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
					//invalidDetails.setText(e.getMessage());
				}
		});
		
		//when clicking edit profile button
		editProfile.setOnAction(event -> {
			
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/updateProfile.fxml"));
				UpdateProfileController updateProfileController = new UpdateProfileController(stage, model);
			
				loader.setController(updateProfileController);
				VBox root = loader.load();
				updateProfileController.showStage(root);
				//stage.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
					//invalidDetails.setText(e.getMessage());
				}
		});
		
		
		
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root,600,400);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Profile Page");
		stage.show();
	}

}
