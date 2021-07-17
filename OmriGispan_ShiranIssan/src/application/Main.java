package application;
	
import Controller.Controller;
import Model.Company;
import View.AbstractCompanyView;
import View.CompanyView;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Company company = new Company("");
		AbstractCompanyView theView1 = new CompanyView(primaryStage);
		Controller controller1 = new Controller(company, theView1);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
