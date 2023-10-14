package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import Model.ModelMine;
import Model.Post;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PageController {
	
	private ModelMine model;
	private Stage stage;
	private Stage parentStage;
	private User currentUser;
//	@FXML
//	private ListView<String> pageListView;
//	
//	@FXML
//	private Label myLabel;
	
//	String[] food = {"pizza","sushi","ramen"};
//	
//	String currentFood;
	// Create a TableView
	public static TableView<Post> table_info_app;
	public static ObservableList<Post> data_table;
	@FXML
	private TableView<Post> table_info;
	@FXML
	private TableColumn<Post, String> column_id,column_name,column_email,column_notes;
	@FXML
	private TableColumn<Post, Button> col_update;
	
	public PageController(Stage parentStage, ModelMine model) {
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
		
//		pageListView.setFocusTraversable( false );
//		List<Post> postList;
//		List<String>  usernames = new ArrayList<String>();;
//		//usernames.add(currentFood);
//		try {
//			postList = model.getCurrentUser().getPosts();
//			
//			for (Post p : postList) {
//				usernames.add(p.getData());
//				System.out.println(p.getAuthor()); 
//			}
//			
//			pageListView.getItems().addAll(usernames);
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//		}
		
		table_info_app = table_info;

        initializeCols();
        loadData();
    }

    private void initializeCols() {
        // User.java => id, name, email, notes;

        column_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        column_name.setCellValueFactory(new PropertyValueFactory<>("author"));
        column_email.setCellValueFactory(new PropertyValueFactory<>("likes"));
        column_notes.setCellValueFactory(new PropertyValueFactory<>("shares"));
        col_update.setCellValueFactory(new PropertyValueFactory<>("update"));
        editableCols();
    }

    private void editableCols() {
//        column_id.setCellFactory(TextFieldTableCell.forTableColumn());
//        column_id.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setId(Integer.parseInt(e.getNewValue())));

        column_name.setCellFactory(TextFieldTableCell.forTableColumn());
        column_name.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setAuthor(e.getNewValue()));
       
//        column_email.setCellFactory(TextFieldTableCell.forTableColumn());
//        column_email.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setLikes(Integer.parseInt(e.getNewValue())));
//
//        column_notes.setCellFactory(TextFieldTableCell.forTableColumn());
//        column_notes.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setShares(Integer.parseInt(e.getNewValue())));

        table_info.setEditable(true);
    }

    private void loadData() {
        data_table = FXCollections.observableArrayList();

        List<Post> posts;
		try {
			posts = model.getPostsDao().getPosts(this.currentUser.getUsername());
		 
        for (Post p : posts) {
        	p.setUpdate(new Button("update"));
        	p.update();
            data_table.add(p);
        }
		}catch (SQLException e) {
			
			e.printStackTrace();
		}

        table_info.setItems(data_table);
    }
		
    
		


	public void showStage(Pane root) {
		
		Scene scene = new Scene(root,600,400);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("MyPosts Page");
		stage.show();
	}
}
