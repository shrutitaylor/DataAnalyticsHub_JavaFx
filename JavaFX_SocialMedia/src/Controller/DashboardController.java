package Controller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Model.ModelMine;
import Model.Post;
import Model.User;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import Dao.PostLikesComparator;
import Dao.PostSharesComparator;
import Exception.EmptyFileException;
import Exception.InvalidNumberException;

public class DashboardController {

	private ModelMine model;
	private Stage stage;
	private Stage parentStage;
	private User currentUser;
	
	@FXML 
	private Label welcomeLabel;	
	
	@FXML
	private MenuItem viewProfile; // Corresponds to the Menu item "viewProfile" in HomeView.fxml
	@FXML
	private MenuItem updateProfile; // // Corresponds to the Menu item "updateProfile" in HomeView.fxml
	@FXML
	private MenuItem myPostsPage;
	@FXML
	private MenuItem logout;
	@FXML
	private MenuItem visualisation;
	@FXML
	private ListView<String> pageListView;
	
	@FXML
	private Button addPostButton;
	@FXML
	private Button searchPostButton;
	@FXML
	private TextField postIdTextField;
	@FXML
	private Button sortByLikesButton;
	@FXML
	private Button sortBySharesButton;
	@FXML
	private TextField numOfShares;
	@FXML
	private TextField numOfPosts;
	@FXML
	private Label invalidDetails;
	@FXML
	private Button refreshButton;
	@FXML
	private Button importCsv;
	
	List<Post> posts;
	private HashMap<Integer, Post> records ;
	
	 // Create a BooleanProperty to control menu visibility
    SimpleBooleanProperty menuVisible = new SimpleBooleanProperty(false);
	
	public DashboardController(Stage parentStage, ModelMine model) {
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
		menuVisible = new SimpleBooleanProperty(true);
		welcomeLabel.setText("Welcome, "+ this.currentUser.getFirstname()+" "+this.currentUser.getLastname());

		 //sets the posts on the dashboard
		pageListView.setFocusTraversable( false );
		List<Post> postList;
		List<String>  postString = new ArrayList<String>();
		//usernames.add(currentFood);
		pageListView.getItems().clear();
		try {
			postList = model.getPostsDao().getAllPosts();
			
			for (Post p : postList) {
				postString.add(p.getData());
//				System.out.println("--"); 
//				System.out.println(p.getAuthor()); 
//				System.out.println(p.getId()); 
			}
			
			pageListView.getItems().addAll(postString);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		
		//View the profile button on click
		viewProfile.setOnAction(event -> {
			try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/profile.fxml"));
			ProfileController profileController = new ProfileController(stage, model);
			
			loader.setController(profileController);
			VBox root = loader.load();
			root.getStylesheets().add("styles.css");
			profileController.showStage(root);
			//stage.close();
		
			
			} catch (IOException e) {
				System.out.println(e.getMessage());
				Scene scene = new Scene(new Label(e.getMessage()), 200, 100);
				stage.setTitle("Error");
				stage.setScene(scene);
				stage.show();;
			}
			
		});
		
		//edit the profile on click
		updateProfile.setOnAction(event -> {
			try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/updateProfile.fxml"));
			UpdateProfileController updateProfileController = new UpdateProfileController(stage, model);
			
			loader.setController(updateProfileController);
			VBox root = loader.load();
			root.getStylesheets().add("styles.css");
			updateProfileController.showStage(root);
			//stage.close();
		
			
			} catch (IOException e) {
				System.out.println(e.getMessage());
				Scene scene = new Scene(new Label(e.getMessage()), 200, 100);
				stage.setTitle("Error");
				stage.setScene(scene);
				stage.show();;
			}

			
			
		});
		//edit the profile on click
				visualisation.setOnAction(event -> {
					try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/visualisation.fxml"));
					VisualController visualController = new VisualController(stage, model);
					
					loader.setController(visualController);
					VBox root = loader.load();
					root.getStylesheets().add("styles.css");
					visualController.showStage(root);
					//stage.close();
				
					
					} catch (IOException e) {
						System.out.println(e.getMessage());
						Scene scene = new Scene(new Label(e.getMessage()), 200, 100);
						stage.setTitle("Error");
						stage.setScene(scene);
						stage.show();;
					}

					
					
				});
		myPostsPage.setOnAction(event -> {
			 try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MyPosts.fxml"));
					PageController pageController = new PageController(stage, model);
					
					loader.setController(pageController);
					VBox root = loader.load();
					root.getStylesheets().add("styles.css");
					
					pageController.showStage(root);
					stage.close();

				}catch (IOException e) {
				System.out.println(e.getMessage());
				Scene scene = new Scene(new Label(e.getMessage()), 200, 100);
				stage.setTitle("Error");
				stage.setScene(scene);
				stage.show();
				e.printStackTrace();
			}

			
			
		});
		
		addPostButton.setOnAction(event -> {
			try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/AddPost.fxml"));
			AddPostController addPostController = new AddPostController(stage, model);
			
				loader.setController(addPostController);
			VBox root = loader.load();
			root.getStylesheets().add("styles.css");
			addPostController.showStage(root);
			stage.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			Scene scene = new Scene(new Label(e.getMessage()), 200, 100);
			stage.setTitle("Error");
			stage.setScene(scene);
			stage.show();;
		}
			
		});
		
		
		searchPostButton.setOnAction(event -> {
			
			if(postIdTextField.getText() != "") {
			Post post = getId( postIdTextField.getText() );
			postString.clear();
			if(post != null) {
				postString.add(post.getData());
				
				pageListView.getItems().clear();
				pageListView.getItems().addAll(postString);
				pageListView.refresh();
				System.out.println("yes");
				invalidDetails.setText("Post with post ID: " + postIdTextField.getText());
			}else {
				System.out.println("no");
//				postString.clear();
//				pageListView.getItems().clear();
//				postString.add("Wrong Post ID");
//				pageListView.getItems().addAll(postString);
//				pageListView.refresh();
				invalidDetails.setText("Wrong Post ID");
			}
			}else {
				postString.clear();
				pageListView.getItems().clear();
				try {
										
					for (Post p : model.getCurrentUser().getPosts()) {
						postString.add(p.getData());
					}
					
					pageListView.getItems().addAll(postString);
				} catch (Exception e) {
					e.printStackTrace();
					//System.out.println(e.getMessage());
				}
			}
			
		});
		
		sortByLikesButton.setOnAction(event -> {
			
			try {
				posts = model.getPostsDao().getAllPosts();
				Collections.sort(posts, new PostLikesComparator() );
				
				
				int postLen = posts.size();
				int n = checkValidNumber( numOfPosts.getText());
				
				if(n==-1) {
					invalidDetails.setText("Invalid number of Posts");
				}else {
				// if the given number is valid
				try {
					postString.clear();
					pageListView.getItems().clear();
					
					//if there are less posts than the given number
					if	(postLen < n)	{			
						for (Post p : posts) {
							postString.add(p.getData());
						}					
						pageListView.getItems().addAll(postString);
						invalidDetails.setText("Only "+ postLen +" posts exist in the collection. Showing all of them.");
					}
					// to get top n posts
					else {
						for(int i=0;i<n;i++) {
							postString.add(posts.get(i).getData());
						}
						pageListView.getItems().addAll(postString);
						invalidDetails.setText("The top-"+n+" posts with the most likes");
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
	
		});
		
		sortBySharesButton.setOnAction(event -> {
			
			try {
				posts = model.getPostsDao().getAllPosts();
				Collections.sort(posts, new PostSharesComparator() );
				
				
				int postLen = posts.size();
				int n = checkValidNumber( numOfShares.getText());
				
				if(n==-1) {
					invalidDetails.setText("Invalid number of Posts");
				}else {
				// if the given number is valid
				try {
					postString.clear();
					pageListView.getItems().clear();
					
					//if there are less posts than the given number
					if	(postLen < n)	{			
						for (Post p : posts) {
							postString.add(p.getData());
						}					
						pageListView.getItems().addAll(postString);
						invalidDetails.setText("Only "+ postLen +" posts exist in the collection. Showing all of them.");
					}
					// to get top n posts
					else {
						for(int i=0;i<n;i++) {
							postString.add(posts.get(i).getData());
						}
						pageListView.getItems().addAll(postString);
						invalidDetails.setText("The top-"+n+" posts with the most shares");
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		});
		
		
		
		refreshButton.setOnAction(event -> {
			pageListView.getItems().clear();
			postString.clear();
			try {
								
				for (Post p : model.getPostsDao().getAllPosts()) {
					postString.add(p.getData());
//					System.out.println("--"); 
//					System.out.println(p.getAuthor()); 
//					System.out.println(p.getId()); 
				}
				
				pageListView.getItems().addAll(postString);
				invalidDetails.setText("Displaying All Posts");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		});
		
		logout.setOnAction(event -> {
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
		// Bind the visibility of the menu item to the BooleanProperty
		visualisation.visibleProperty().bind(menuVisible);

        // Add a listener to update menu visibility based on a variable
        menuVisible.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("Menu item is visible.");
            } else {
                System.out.println("Menu item is hidden.");
            }
        });
		
        // importing the csv -- this method reads and adds all posts
        importCsv.visibleProperty().bind(menuVisible);
        importCsv.setOnAction(event -> {
        	// Open a FileChooser window, allow me to choose an csv on my computer
			FileChooser fileChooser = new FileChooser();
					
			File selectedFile = fileChooser.showOpenDialog(new Stage()); 
	
			try {
				FileInputStream fileInputStream = new FileInputStream(selectedFile);
			readFile(selectedFile);
				
				
			}catch (FileNotFoundException e) {
			invalidDetails.setText("File not found!");
				e.printStackTrace();
			} 
	
			
        	System.out.println("read file");
        });
		
	}
	private Post getId(String id) {

		int postId;
		try {
			 postId = Integer.parseInt(id);
			 
			 Post p = model.getPostsDao().getPostById(postId);
			
			 if(p != null) {
				 System.out.println("yes post");
				 return p;
				 
			 }
			 
		}catch(NumberFormatException  e) {
			//e.printStackTrace();
			return null;
		}catch(SQLException e) {
			//e.printStackTrace();
			return null;
		}

		return null;
		
	} 
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 665, 428);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Dashboard");
		stage.show();
	}
	
	
		
//	private void loadData(String firstname,String lastname, String username,String password){
//	    data_table=FXCollections.observableArrayList();
//
//	    for(int x=1;x< 2;x++){
//
//	    /* Generates the data items in the table */
//	    data_table.add(new User( firstname, lastname,  username, password));
//	    }
//
//	    table_info.setItems(data_table);
//	}

//	@Override
//    public ListCell<User> call(ListView<User> param) {
//        return new ListCell<>(){
//            @Override
//            public void updateItem(User user, boolean empty) {
//                super.updateItem(user, empty);
//                if (empty || user == null) {
//                    setText(null);
//                } else {
//                    setText(user.getFirstname() + " " + user.getLastname());
//                }
//            }
//        };
//    }
	
	public int checkValidNumber(String givenNumber)  {	
				
        try {
        	int num = Integer.parseInt(givenNumber);
            if(num <= 0) {
            	
            	throw new InvalidNumberException();
            }else{
            	return num;
            }
                       
        } catch (InputMismatchException e) {
            System.err.println("Input is not a valid number ");
            return -1;
            
        }catch (InvalidNumberException i) {
        	System.err.println("Input is not a valid number, Valid number is greater than 0 and an integer");
        	return -1;
            
		}catch(NumberFormatException e) {
			return -1;
		}
		
	}
	
	public void readFile(File fileName) {
		Post p;	
		
		records = new HashMap<Integer, Post>();
		List<Integer> invalidPost = new ArrayList<Integer>();
		
		
		try {
			posts = model.getPostsDao().getAllPosts();
			
		if (fileName.length() == 0) {
	    	
	    	throw new EmptyFileException();
	    	
	    }else {
	    	
	    	try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
				br.readLine();
			    String line;
			    while ((line = br.readLine()) != null) {
			    String[] values = line.split(",");
			        
			    p = new Post(Arrays.asList(values)); // Post object
			    p.setAuthor(this.currentUser.getUsername());
			    try {
			    		model.getPostsDao().createNewPost(p.getId(),p.getContent(),p.getAuthor(),p.getLikes(),p.getShares(),Arrays.asList(values).get(5));
			    		
			    	}catch (SQLException e) {
			    		invalidPost.add(p.getId());
			    	}
			    }
	    	}
			 catch (NullPointerException e) {
				showAlert(e);
	        	System.err.println("File is null.");
	        	//return "File is null.";
	 		   // System.exit(1);
	        } catch (FileNotFoundException e) {
	        	showAlert(e);
	        	System.err.println("File not found.");
	        	//return "File not found.";
	 		    //System.exit(1);
	        } catch (IOException e) {
	        	showAlert(e);
	        	System.err.println("An error occurred while reading the file.");
	        	//return "An error occurred while reading the file.";
	        	//System.exit(1);
			} catch (ParseException e) {
				showAlert(e);
	        	System.err.println("An error occurred while reading the file.");
			}
	    	
	    }
		} catch (EmptyFileException e) {
			showAlert(e);
			System.err.println("File is empty.");
        	//return "File is empty.";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			showAlert(e);
			e.printStackTrace();
		} 
		
		if(!invalidPost.isEmpty()) {
	    	Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Error");
	        
	        alert.setHeaderText("Error adding these Posts:");
	        alert.setContentText(invalidPost.toString());
	        DialogPane dialogPane = alert.getDialogPane();     
	 
	        dialogPane.getStyleClass().add("myDialog");
	        dialogPane.setHeader(null);
	        alert.show();
	    }else {
	    	invalidDetails.setText("Posts Added!");
	    }
		
	}
	private static void showAlert(Exception e){
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
