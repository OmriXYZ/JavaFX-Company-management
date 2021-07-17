package View;

import Controller.ControllerUi;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LoadingScreen implements AbstractLoadingView {
	private Scene loadingScene;
	private BorderPane pane;
	private Label loadMSG;
	private Button bt1, bt2;
	
	public LoadingScreen(Stage stage) {
		loadMSG = new Label("Do you want to load the data from the last save?");
		loadMSG.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
		loadMSG.setPadding(new Insets(20));
		bt1 = new Button("Yes");
		bt1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				loadData();
			}
		});
		
		bt2 = new Button("No");
		bt2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				
			}
		});

		pane = new BorderPane();
		pane.setPadding(new Insets(35));
		pane.setTop(loadMSG);
		
		pane.setAlignment(loadMSG, Pos.CENTER);

		HBox hbox = new HBox(50, bt1, bt2);
		hbox.setAlignment(Pos.CENTER);
		
		pane.setCenter(hbox);

		loadingScene = new Scene(pane, 600, 200);
		stage.setTitle("Load Data");
		stage.setScene(loadingScene);
		stage.setResizable(false);
		stage.show();

	}
	
	public BorderPane getLoadingScreen() {
		return pane;
	}

	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveData() {
		// TODO Auto-generated method stub
		
	}



}
