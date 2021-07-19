package View;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import Model.Department;
import Model.Employee;
import Model.Role;
import helpingmethods.CustomDialog;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import listeners.GuiEventsListener;


public class CompanyView implements AbstractCompanyView {
	//----MVC----//
	private ArrayList<GuiEventsListener> allListeners = new ArrayList<GuiEventsListener>();
	
	//Titled Panes//
	private TitledPane addDepartmentTitledPane;
	private TitledPane addRoleToDepartmentTitledPane;
	private TitledPane addEmployeeToRoleTitledPane;
	private TitledPane showDetailsTitledPane;
	
	//Departments Arrays
	private ComboBox<String> departmentsCombo = new ComboBox<>();
	private ComboBox<String> departmentsComboForEmployees = new ComboBox<>();
	private ArrayList<ComboBox<String>> arrayOfrolesByIndex = new ArrayList<ComboBox<String>>();
	private ComboBox<String> rolesByIndex = new ComboBox<String>();


	private int selectedIndex = -1;
	private int selectedIndexForEmployees = -1;
	private int selectedIndexForRoles = 0;
	private String showCompanyDetails = "";
	private ScrollPane scrollDetailsMsg;
	private ScrollPane scrollForTitledPanes;

	public CompanyView(Stage stage) throws Exception {
		
		//Set View Settings//
		stage.setTitle("Company Menu");
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		Label lblMenu = new Label("Please select an option:");
		scrollForTitledPanes = new ScrollPane(gpRoot);
		
		CustomDialog setCompanyNameDialog = new CustomDialog();
		while (setCompanyNameDialog.getResult() == null || setCompanyNameDialog.getResult() == "") {
			setCompanyNameDialog.showAndWait();
		}
		for (GuiEventsListener l : allListeners) {
			l.setCompanyNameFromGui(setCompanyNameDialog.getResult());
		}
		
		
		
		//----PANES----//:
		
		//Add Department Pane//
		GridPane departmentPane = addDepartmentBuildPane();
		addDepartmentTitledPane = new TitledPane("Create department", departmentPane);
		addDepartmentTitledPane.setExpanded(false);

		
		//Add RoleToDepartment Pane//
		GridPane rolePane = addRoleBuildPane();
		addRoleToDepartmentTitledPane = new TitledPane("Create Role to Department", rolePane);
		addRoleToDepartmentTitledPane.setExpanded(false);

		
		//Add Show Details Pane//
		GridPane detailsPane = showDetailsTBuildPane();
		showDetailsTitledPane = new TitledPane("Show Company Details", detailsPane);
		showDetailsTitledPane.setExpanded(false);

		//Add EmployeeToRole Pane//
		GridPane employeePane = addEmployeeBuildPane();
		addEmployeeToRoleTitledPane = new TitledPane("Add Employee to Department and assign a role", employeePane);
		showDetailsTitledPane.setExpanded(false);

		//Add nodes to main gridpane (All the titled panes)
		gpRoot.add(lblMenu, 0, 0);
		gpRoot.add(addDepartmentTitledPane, 0, 1);
		gpRoot.add(addRoleToDepartmentTitledPane, 0, 2);
		gpRoot.add(addEmployeeToRoleTitledPane, 0, 3);
		gpRoot.add(showDetailsTitledPane, 0, 4);



		stage.setScene(new Scene(scrollForTitledPanes, 520, 750));
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
		
		btnAddDepartment.setOnAction(new EventHandler<ActionEvent>() { //Event for addDepartment button
			@Override
			public void handle(ActionEvent action) {
				for (GuiEventsListener l : allListeners) {
					if (!nameOfDepartment.getText().isEmpty()) {
						l.addDepartmentFromGui(nameOfDepartment.getText(), isSync.isSelected(), canChangePref.isSelected());
					} else
						dialog("Do not leave empty field please");
						
				}
			}
		}); //Event end
		
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
		//Check index of selected department
		departmentsCombo.setOnAction((event) -> {
		    selectedIndex = departmentsCombo.getSelectionModel().getSelectedIndex();
		});
		
		btnAddRole.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				for (GuiEventsListener l : allListeners) {
					if (!nameOfRole.getText().isEmpty() && selectedIndex != -1 ) {
						l.addRoleFromGui(nameOfRole.getText(), isSync.isSelected(), canChangePref.isSelected(), selectedIndex);
					} else
						dialog("Do not leave empty field please");
						
				}
			}
		});

		gpRoot.add(lblRolename, 0, 0);
		gpRoot.add(nameOfRole, 1, 0);
		gpRoot.add(isSync, 0, 2);
		gpRoot.add(canChangePref, 0, 3);
		gpRoot.add(lblRoleList, 0, 4);
		gpRoot.add(departmentsCombo, 1, 4);
		gpRoot.add(btnAddRole, 0, 5);

		return gpRoot;
	}

	private GridPane showDetailsTBuildPane() {

		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);

		// create nodes
		scrollDetailsMsg = new ScrollPane();

		Label lblCompanyDetailsMsg = new Label("Company Details:");

		gpRoot.add(lblCompanyDetailsMsg, 0, 0);
		gpRoot.add(scrollDetailsMsg, 0, 1);

		return gpRoot;
	}
	
	private GridPane addEmployeeBuildPane() {

		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		
		Label lblEmployeeName = new Label("Enter employee's name:");
		TextField nameOfEmployee = new TextField();
		Label lblEmployeeType = new Label("Choose your employee's salary type:");
		RadioButton c1 = new RadioButton("By hour");
		c1.setUserData("Hour");
		RadioButton c2 = new RadioButton("Base salary");
		c2.setUserData("Base");
		RadioButton c3 = new RadioButton("Base salary + bonus");
		c3.setUserData("BaseBonus");
		
		ToggleGroup radioButtonSalaryType = new ToggleGroup();
		c1.setToggleGroup(radioButtonSalaryType);
		c2.setToggleGroup(radioButtonSalaryType);
		c3.setToggleGroup(radioButtonSalaryType);
		c1.setSelected(true); //Prevent unselected error

		
		Label lblEmployeePref = new Label("Choose your employee's prefernce:");
		RadioButton c4 = new RadioButton("Start working early");
		c4.setUserData("Earlier");
		RadioButton c5 = new RadioButton("Start working late");
		c5.setUserData("Later");
		RadioButton c6 = new RadioButton("Stay on basic (8 - 17)");
		c6.setUserData("StayBasic");
		RadioButton c7 = new RadioButton("Work from home");
		c7.setUserData("WorkHome");
		ToggleGroup radioButtonPref = new ToggleGroup();
		c4.setToggleGroup(radioButtonPref);
		c5.setToggleGroup(radioButtonPref);
		c6.setToggleGroup(radioButtonPref);
		c7.setToggleGroup(radioButtonPref);
		c4.setSelected(true); //Prevent unselected error

		Label lblDepartmentChoose = new Label("Choose Department:");
		Label lblRoleChoose = new Label("Choose Role to assign:");
		
		Button btnCreateEmployee = new Button("Create Employee");
		
		//----Action event for COMBOBOX----//
		departmentsComboForEmployees.setOnAction((event) -> {
			selectedIndexForEmployees = departmentsComboForEmployees.getSelectionModel().getSelectedIndex(); //Department Index
			rolesByIndex.getItems().setAll(arrayOfrolesByIndex.get(selectedIndexForEmployees).getItems());
		});
		
		departmentsComboForEmployees.setOnMouseClicked((event) -> {
			if (!departmentsComboForEmployees.getItems().isEmpty()) { //Prevent errors
			    rolesByIndex.getItems().setAll(arrayOfrolesByIndex.get(selectedIndexForEmployees).getItems());
			}
		});
		
		rolesByIndex.setOnAction((event) -> {
		    selectedIndexForRoles = rolesByIndex.getSelectionModel().getSelectedIndex(); //Role Index
		});	
		
		//----Action event for Button----//
		btnCreateEmployee.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				boolean emptyComboBoxFields = departmentsComboForEmployees.getSelectionModel().isEmpty() || rolesByIndex.getSelectionModel().isEmpty();
				if (nameOfEmployee.getText().isEmpty() || emptyComboBoxFields) {
					JOptionPane.showMessageDialog(null, "Do not leave empty fields please");
				} else {
					for (GuiEventsListener l : allListeners) {
						try {
							System.out.println(radioButtonPref.getSelectedToggle().getUserData().toString());
							l.addEmployeeFromGui(nameOfEmployee.getText(), selectedIndexForRoles, selectedIndexForEmployees, 4, radioButtonPref.getSelectedToggle().getUserData().toString());
						} catch (Exception e) {
							dialog(e.getMessage());
						}
					}

				}
			}
		});

		
		gpRoot.add(lblEmployeeName, 0, 0);
		gpRoot.add(nameOfEmployee, 1, 0);
		gpRoot.add(lblEmployeeType, 0, 1);
		gpRoot.add(c1, 0, 2);
		gpRoot.add(c2, 0, 3);
		gpRoot.add(c3, 0, 4);
		gpRoot.add(lblDepartmentChoose, 0, 5);
		gpRoot.add(departmentsComboForEmployees, 0, 6);
		gpRoot.add(lblRoleChoose, 0, 7);
	    gpRoot.add(rolesByIndex, 0, 8);
	    gpRoot.add(lblEmployeePref, 0, 9);
		gpRoot.add(c4, 0, 10);
		gpRoot.add(c5, 0, 11);
		gpRoot.add(c6, 0, 12);
		gpRoot.add(c7, 0, 13);

	    gpRoot.add(btnCreateEmployee, 0, 14);


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
	public void addDepartmentToGui(String departmentName) {
		departmentsCombo.getItems().add(departmentName);
		dialog("Department " + "\"" + departmentName + "\"" + " was added to the company");
		departmentsComboForEmployees.getItems().add(departmentName);
		arrayOfrolesByIndex.add(new ComboBox<String>());
	}

	@Override
	public void addRoleToGui(String roleName, int indexDepartment) {
		arrayOfrolesByIndex.get(indexDepartment).getItems().add(roleName);
		if (selectedIndexForEmployees != -1) { //Prevent errors
		    rolesByIndex.getItems().setAll(arrayOfrolesByIndex.get(selectedIndexForEmployees).getItems());
		}
	}

	@Override
	public void addEmployeeToGui(int iD, String name, Role role, Department department, int begHour, int endHour,
			String pref) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addCompanyDetailsToGui(String toString) {
		showCompanyDetails = toString;
		Label details = new Label(showCompanyDetails);
		scrollDetailsMsg.setContent(details);
	}
	
	//----Helping Methods----//
	public void dialog(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}



}
