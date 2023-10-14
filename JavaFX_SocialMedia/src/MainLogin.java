import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import Controller.LoginController;
import Controller.LoginController2;
import Controller.ProfileController;
import Model.ModelMine;
import Model.Post;
import Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainLogin extends Application{

	private ModelMine model;

	@Override
	public void init() {
		model = new ModelMine();
	}

	@Override
	public void start(Stage primaryStage) {
		try {
				model.setup();	
//				User u = new User("asdf","asdf","Shruti","avi");
//				model.setCurrentUser(u);
//				try {
//					List<Post> postList = model.getPostsDao().getPosts(u.getUsername());
//					model.getCurrentUser().setPosts(postList);
//					for( Post p:model.getCurrentUser().getPosts()) 
//					{	System.out.println(p.getData());}
//					
//				} catch (SQLException e) {
//					
//					e.printStackTrace();
//				}
		
				//Load FXML file
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/login.fxml")); 
				
				LoginController loginController = new LoginController(primaryStage, model);
				loader.setController(loginController);
								
				GridPane root = loader.load();
				loginController.showStage(root);
				
							
				
				
		} catch (IOException | SQLException | RuntimeException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			Scene scene = new Scene(new Label(e.getMessage()), 200, 100);
			primaryStage.setTitle("Error");
			primaryStage.setScene(scene);
			primaryStage.show();
		}
;		
	}
	public static void main(String[] args) {
		Application.launch(args);

	}
}
