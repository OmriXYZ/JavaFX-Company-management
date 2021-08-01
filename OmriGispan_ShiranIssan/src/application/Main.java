package application;
	
import Controller.Controller;
import Model.Company;
import View.AbstractCompanyView;
import View.CompanyView;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Company company = new Company("");
		AbstractCompanyView theView1 = new CompanyView(primaryStage);
		Controller controller = new Controller(company, theView1); //Do actions between model and gui
		theView1.loadData(); //Open dialog, ask the user about loading recent data
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
