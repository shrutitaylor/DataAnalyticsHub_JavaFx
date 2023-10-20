package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import Exception.InvalidNumberException;
import Model.Model;
import Model.Post;
import Model.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
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
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;


public class PageController {
	
	private Model model;
	private Stage stage;
	private Stage parentStage;
	private User currentUser;
//	@FXML
//	private ListView<String> pageListView;
//	
	@FXML
	private Label invalidDetails;
	
//	String[] food = {"pizza","sushi","ramen"};
//	
//	String currentFood;
	// Create a TableView
	public static TableView<Post> table_info_app;
	public static ObservableList<Post> data_table;
	@FXML
	private TableView<Post> table_info;
	@FXML
	private TableColumn<Post, String> column_id,column_content;
	@FXML
	private TableColumn<Post, Integer> column_likes,column_shares;
	@FXML
	private TableColumn<Post, Button> col_update;
	
	@FXML
	private Button dashboard;
	
	
	
	public PageController(Stage parentStage, Model model) {
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
        
      //when clicking the back button - leads to dashboard
		dashboard.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/dashboard.fxml"));
				DashboardController dashboardController = new DashboardController(stage, model);
			
				loader.setController(dashboardController);
				VBox root = loader.load();
				root.getStylesheets().add("styles.css");
				dashboardController.showStage(root);
				stage.close();
				
				} catch (IOException e) {
					System.out.println(e.getMessage());
					//invalidDetails.setText(e.getMessage());
				}
		});
        
    }
	
    private void initializeCols() {
        // Post.java => id, content, likes, shares;

        column_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        column_content.setCellValueFactory(new PropertyValueFactory<>("content"));
       // column_likes.setCellValueFactory(new PropertyValueFactory<>("likes"));
        column_likes.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getLikes()).asObject());
        column_shares.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getShares()).asObject());
        col_update.setCellValueFactory(new PropertyValueFactory<>(""));
        editableCols();

    }

    private void editableCols() {
    	   try {
    	column_content.setCellFactory(TextFieldTableCell.forTableColumn());
    	column_content.setOnEditCommit(event -> 
    	{
    		Post post = event.getTableView().getItems().get(event.getTablePosition().getRow());
    		post.setContent(event.getNewValue());
    		System.out.println("Updating content: " + post.getContent());
    	});      	
    	
    	column_likes.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
        column_likes.setOnEditCommit(event -> {
        	Post post = event.getTableView().getItems().get(event.getTablePosition().getRow());
        	try {    
        		int num =checkValidNumber(event.getNewValue().toString());   		
    			if(num!=-1) {
    				post.setLikes(num);
    				System.out.println("Updating likes: " + post.getLikes());
        	}
        	}catch(Exception e) {
        	invalidDetails.setText("Input is not a valid number - likes");
        }
        });  	
    	
        
        column_shares.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
        column_shares.setOnEditCommit(event -> {
        	Post post = event.getTableView().getItems().get(event.getTablePosition().getRow());
        	try {    
        		int num =checkValidNumber(event.getNewValue().toString());   		
    			if(num!=-1) {
    				post.setShares(num);
    				System.out.println("Updating shares: " + post.getShares());
        	}
        	}catch(Exception e) {
        	invalidDetails.setText("Input is not a valid number - shares");
        }
        }); 
        
//        column_shares.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
//        column_shares.setOnEditCommit(e -> {
//        	int num =checkValidNumber(e.getNewValue().toString());
//        if(num!=-1) {
//        e.getTableView().getItems().get(e.getTablePosition().getRow()).setLikes(num);
//        	}
//        else {
//        	invalidDetails.setText("Input is not a valid number - Shares");
//        }
//        });
        
        col_update.setCellFactory(param -> new TableCell<>() {
            final Button updateButton = new Button("Update");

            {
                updateButton.setOnAction(event -> {
                    Post post = getTableView().getItems().get(getIndex());
                    // Perform update action for the post here
                    try {
						model.getPostsDao().updatePost(post);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						invalidDetails.setText("Cannot update Invalid details");
					}
                    System.out.println("Updating: " + post.getData());
                    invalidDetails.setText("Updated post: "+post.getId());
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });
        
        table_info.setEditable(true);
    	   }catch(Exception e) {
           	invalidDetails.setText("Input is not a valid number - shares");
           }
    }

    private void loadData() {
    	
        data_table = FXCollections.observableArrayList();

        List<Post> posts;
		try {
			posts = model.getPostsDao().getPosts(this.currentUser.getUsername());
		 
        for (Post p : posts) {
        	//p.setUpdate(new Button("update"));
        	//p.update();
            data_table.add(p);
        }
		}catch (SQLException e) {
			
			e.printStackTrace();
		}

        table_info.setItems(data_table);
    	
    }
		
    public int checkValidNumber(String givenNumber)  {	
		
        try {
        	int num = Integer.parseInt(givenNumber);
            if(num <= 0) {
            	
            	throw new InvalidNumberException();
            }else{
            	return num;
            }
                       
        } catch (InputMismatchException e) {
            //System.err.println("Input is not a valid number ");
            return -1;
            
        }catch (InvalidNumberException i) {
        	//System.err.println("Input is not a valid number, Valid number is greater than 0 and an integer");
        	return -1;
            
		}catch(NumberFormatException e) {
			//System.err.println("Input is not a valid number, Valid number is greater than 0 and an integer");
			return -1;
		}
		
	}
		


	public void showStage(Pane root) {
		
		Scene scene = new Scene(root,600,400);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("MyPosts Page");
		stage.show();
	}
	public static class CustomIntegerStringConverter extends IntegerStringConverter {
	    private final IntegerStringConverter converter = new IntegerStringConverter();
	    
	    @Override
	    public String toString(Integer object) {
	        try {
	            return converter.toString(object);
	        } catch (NumberFormatException e) {
	            showAlert(e);
	        	//invalidDetails.setText("Input is not a valid number - likes");
	        	
	        	return null;
	        }
	        
	    }

	    @Override
	    public Integer fromString(String string) {
	        try {
	            return converter.fromString(string);
	        } catch (NumberFormatException e) {
	        	showAlert(e);
	        	return null;
	        }
	        
	    }
	   
	    
	} private static void showAlert(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        DialogPane dialogPane = alert.getDialogPane();     
 
        dialogPane.getStyleClass().add("myDialog");
        dialogPane.setHeader(null);
        alert.show();
    }
}
