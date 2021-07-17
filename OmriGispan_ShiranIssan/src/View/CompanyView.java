package View;

import java.util.ArrayList;

import Model.Department;
import Model.Role;
import helpingmethods.LimitedTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import listeners.GuiEventsListener;


public class CompanyView implements AbstractCompanyView {
	//----MVC----//
	private ArrayList<GuiEventsListener> allListeners = new ArrayList<GuiEventsListener>();
	
	//Titled Panes//
	private TitledPane addCompanyTitle;
	private TitledPane addDepartmentTitledPane;
	private TitledPane addRoleToDepartmentTitledPane;
	private TitledPane addEmployeeToRoleTitledPane;

	//Departments Arrays 
	private ComboBox<String> departmentsCombo = new ComboBox<>();
	private ArrayList<Department> departmentsList = new ArrayList<>();

	public CompanyView(Stage stage) throws Exception {
		
		//Set View Settings//
		stage.setTitle("Company Menu");
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		Label lblMenu = new Label("Please select an option:");
		
		//----PANES----//:
		
		//Add Department Pane//
		GridPane departmentPane = addDepartmentBuildPane();
		addDepartmentTitledPane = new TitledPane("Create department", departmentPane);
		//Add RoleToDepartment Pane//
		GridPane rolePane = addRoleBuildPane();
		addRoleToDepartmentTitledPane = new TitledPane("Create Role to Department", rolePane);
		
		//Add nodes to main gridpane
		gpRoot.add(lblMenu, 0, 0);
		gpRoot.add(addDepartmentTitledPane, 0, 0);
		gpRoot.add(addRoleToDepartmentTitledPane, 0, 1);

		
		
		stage.setScene(new Scene(gpRoot, 520, 750));
		stage.show();
	}
	
//----Making The Grid Panes----//
	private GridPane addDepartmentBuildPane() {
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		
		//create nodes
		Label lblDepartmentname = new Label("Enter Department's name: ");
		TextField nameOfDepartment = new TextField();
		CheckBox isSync = new CheckBox("Employees synchronized by hours");
		CheckBox canChangePref = new CheckBox("Can change the prefernces");
		Button btnAddDepartment = new Button("Add Department");

		btnAddDepartment.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {

			}
		});
		
		gpRoot.add(lblDepartmentname, 0, 0);
		gpRoot.add(nameOfDepartment, 1, 0);
		gpRoot.add(isSync, 0, 1);
		gpRoot.add(canChangePref, 0, 2);
		gpRoot.add(btnAddDepartment, 0, 3);


		
		return gpRoot;
	}
	
	private GridPane addRoleBuildPane() {
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		
		//create nodes
		Label lblRolename = new Label("Enter Role's name:");
		TextField nameOfRole = new TextField();
		CheckBox isSync = new CheckBox("Employees synchronized by hours");
		CheckBox canChangePref = new CheckBox("Can change the prefernces");
		Button btnAddRole = new Button("Add Role to Department");
		Label lblRoleList = new Label("Choose Department to assign the role:");
		Label lblWorkingHours = new Label("Enter working hours:");
		LimitedTextField startHour = new LimitedTextField();
		startHour.setMaxLength(2);
		startHour.setMaxWidth(30);
		LimitedTextField endHour = new LimitedTextField();
		endHour.setMaxLength(2);
		endHour.setMaxWidth(30);
		
		btnAddRole.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				
			}
		});
		gpRoot.add(lblRolename, 0, 0);
		gpRoot.add(lblWorkingHours, 0, 1);
		gpRoot.add(startHour, 1, 1);
		gpRoot.add(endHour, 2, 1);
		gpRoot.add(nameOfRole, 1, 0);
		gpRoot.add(isSync, 0, 2);
		gpRoot.add(canChangePref, 0, 3);
		gpRoot.add(lblRoleList, 0, 4);
		gpRoot.add(departmentsCombo, 1, 4);
		gpRoot.add(btnAddRole, 0, 5);


		
		return gpRoot;
	}

	
	//----MVC----//
	@Override
	public void registerListener(GuiEventsListener listener) {
		allListeners.add(listener);
	}

	@Override
	public void setCompanyNameToGui(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDepartmentToGui(Department department) {
		departmentsList.add(department);
		departmentsCombo.getItems().add(department.getName());
		
	}

	@Override
	public void addRoleToGui(String name, boolean mustEmployeeSync, boolean canChangeWorkHours, int indexDepartment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEmployeeToGui(int iD, String name, Role role, Department department, int begHour, int endHour,
			String pref) {
		// TODO Auto-generated method stub
		
	}

}
