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
//		company.addDepartment("נגרייה");
//		company.addRole("נגר", 0);
//		company.setPrefRoleFromGui(0, 0, true);
//		company.setPrefDepartmentFromGui(0, true);
//		company.addEmployee("עמרם", 0, 0, 13, "LATER", "HOUR", 120);
		theView1.loadData();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
