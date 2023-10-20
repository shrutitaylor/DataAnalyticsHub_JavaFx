package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import Dao.PostSharesComparator;
import Model.ModelMine;
import Model.Post;
import Model.User;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VisualController {

	@FXML
    private PieChart pieChart;
	@FXML
	private Button dashboard;

	private User currentUser;
	private ModelMine model;
	private Stage stage;
	
	public VisualController(Stage stage, ModelMine model) {
		this.stage = stage;
		this.model = model;
		this.currentUser = model.getCurrentUser(); // setting current user
	}

	@FXML
	public void initialize() {
		
				int[] sharesList = getSharesList();
				ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("low", sharesList[0]),
                        new PieChart.Data("mid", sharesList[1]),
                        new PieChart.Data("high", sharesList[2])
                       );


        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " amount: ", data.pieValueProperty()
                        )
                )
        );

        pieChart.getData().addAll(pieChartData);
        
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
	
	public int[] getSharesList() {
		
		int low =0;
		int mid =0;
		int high =0;
		
		try {
			List<Post> posts = model.getPostsDao().getAllPosts();
			Collections.sort(posts, new PostSharesComparator() );
			
			for(Post p: posts) {
				if(p.getShares() >=0 && p.getShares() <=99) {
					low+=1;
				}
				if(p.getShares() >=100 && p.getShares() <=999) {
					mid+=1;
				}
				if(p.getShares() >=1000 ) {
					high+=1;
				}
			}
			int[] display = {low,mid,high};
			return display;
			
		}catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root,600,00);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Profile Page");
		stage.show();
	}

}
