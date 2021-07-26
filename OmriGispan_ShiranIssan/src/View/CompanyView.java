package View;

import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.text.NumberFormatter;

import Model.Department;
import Model.EfficiencyType;
import Model.Employee;
import Model.Role;
import helpingmethods.CustomDialog;
import helpingmethods.LimitedTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import listeners.GuiEventsListener;


public class CompanyView implements AbstractCompanyView {
	//----MVC----//
	private ArrayList<GuiEventsListener> allListeners = new ArrayList<GuiEventsListener>();
	
	//Titled Panes//
	private TitledPane addDepartmentTitledPane; 		//Add department
	private TitledPane addRoleToDepartmentTitledPane; 	//Add role
	private TitledPane addEmployeeToRoleTitledPane; 	//Add employee
	private TitledPane showDetailsTitledPane; 			//Show company details
	private TitledPane changeDepartmentPrefSyncPane; 	//Change pref&sync for departments
	private TitledPane changeRolePrefSyncPane;			//Change pref&sync for roles
	private TitledPane showTheResultPane;				//See the profit or the loss for company departments employees


	//Departments ComboBox
	private ComboBox<String> depsComboAddRole = new ComboBox<>(); 			//Departments ComboBox - Add role title pane
	private ComboBox<String> depsComboEmployees = new ComboBox<>(); 		//Departments ComboBox - Add employee title pane
	private ComboBox<String> rolesComboEmployee = new ComboBox<>();			//Roles ComboBox - Add employee title pane
	private ComboBox<String> depsComboSyncPrefDeps = new ComboBox<>(); 		//Departments ComboBox - Change sync&pref departments title pane
	private ComboBox<String> depsComboSyncPrefRoles = new ComboBox<>(); 	//Departments ComboBox - Change sync&pref roles title pane
	private ComboBox<String> rolesComboSyncPrefRoles = new ComboBox<>(); 	//Roles ComboBox - Change sync&pref roles title pane
	
	//ArrayList for efficiency table
	private ArrayList<String> departmentsNames = new ArrayList<>();
	private ArrayList<String> employeesNames = new ArrayList<>();

	//Roles ArrayList<ComboBox string> - Contains ComboBox roles divided by department index
	private ArrayList<ComboBox<String>> arrayOfRolesByIndex = new ArrayList<ComboBox<String>>(); //Roles ArrayList - add employee title pane

	//Selectors index
	private int indexDepAddRole = -1;		 //Department index - Add role title pane
	private int indexDepAddEmployee = -1;	 //Department index - Add Employee title pane
	private int indexDepSyncPrefDeps = -1;	 //Department index - Change sync&pref departments title pane
	private int indexDepSyncPrefRoles = -1;	 //Department index - Change sync&pref roles title pane
	private int indexRoleSyncPrefRoles = -1; //Role index - Change sync&pref roles title pane

	private String showCompanyDetails = "";
	private ScrollPane scrollDetailsMsg;
	private ScrollPane scrollForTitledPanes;
	
	private ArrayList<Double> efficiencyDepartments = new ArrayList<Double>();
	private ArrayList<Double> efficiencyEmployees = new ArrayList<Double>();
	private Label lblEffCompany = new Label("");
	private String toLoadData = "";
	
	//Company name//
	private String companyName = "";

	public CompanyView(Stage stage) throws Exception {
		
		//Set View Settings//
		stage.setTitle("Company Menu");
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		//Save and Exit button
		Button btnSaveAndExit = new Button("Save and exit");
		btnSaveAndExit.setStyle("-fx-font: 16 arial;-fx-text-fill: black;");
		btnSaveAndExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				for (GuiEventsListener l : allListeners) {
					l.saveDataFromUi();
				}
				stage.close();
			}
		}); //Event end
		
		//Close title panes button
		Button btnExpandedOff = new Button("Close everything");
		btnExpandedOff.setStyle("-fx-font: 10 arial;-fx-text-fill: red;");
		btnExpandedOff.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				addDepartmentTitledPane.setExpanded(false);
				addRoleToDepartmentTitledPane.setExpanded(false);
				showDetailsTitledPane.setExpanded(false);
				addEmployeeToRoleTitledPane.setExpanded(false);
				changeDepartmentPrefSyncPane.setExpanded(false);
				changeRolePrefSyncPane.setExpanded(false);
				showTheResultPane.setExpanded(false);

			}
		}); //Event end
		
		//Scroll those titlepanes if necessary
		scrollForTitledPanes = new ScrollPane(gpRoot);
		
		//CustomDialog for loading recent data
		CustomDialog loadDataDialog = new CustomDialog();
		loadDataDialog.showAndWait();
		toLoadData = loadDataDialog.getResult();
		//CustomDialog-END
		
		Label lblMenu = new Label("Hello " + companyName + ", Please select an option:"); //Welcome MSG
		
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
		addEmployeeToRoleTitledPane.setExpanded(false);

		//Change department pref and sync Pane//
		GridPane changeDepartmentPrefSync = changeDepartmentPrefSyncBuildPane();
		changeDepartmentPrefSyncPane = new TitledPane("Change Departments preference requirement and sync workers", changeDepartmentPrefSync);
		changeDepartmentPrefSyncPane.setExpanded(false);
		
		//Change role pref and sync Pane//
		GridPane changeRolePrefSync = changeRolePrefSyncBuildPane();
		changeRolePrefSyncPane = new TitledPane("Change Roles preference requirement and sync workers", changeRolePrefSync);
		changeRolePrefSyncPane.setExpanded(false);
		
		//Change role pref and sync Pane//
		GridPane showResultsPane = showResultsBuildPane();
		showTheResultPane = new TitledPane("Show profit or loss for company, departments or employees", showResultsPane);
		showTheResultPane.setExpanded(false);

		//Add nodes and TitlePanes to main gridpane
		gpRoot.add(lblMenu, 0, 0);
		gpRoot.add(addDepartmentTitledPane, 0, 1);
		gpRoot.add(addRoleToDepartmentTitledPane, 0, 2);
		gpRoot.add(addEmployeeToRoleTitledPane, 0, 3);
		gpRoot.add(showDetailsTitledPane, 0, 4);
		gpRoot.add(changeDepartmentPrefSyncPane, 0, 5);
		gpRoot.add(changeRolePrefSyncPane, 0, 6);
		gpRoot.add(showTheResultPane, 0, 7);
		gpRoot.add(btnSaveAndExit, 0, 8);
		gpRoot.add(btnExpandedOff, 1, 1);

		stage.setScene(new Scene(scrollForTitledPanes, 730, 850));
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
		Button btnAddDepartment = new Button("Add Department");
		
		//Event for addDepartment button
		btnAddDepartment.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				for (GuiEventsListener l : allListeners) {
					if (!nameOfDepartment.getText().isEmpty()) {
						l.addDepartmentFromGui(nameOfDepartment.getText());
					} else
						dialog("Do not leave empty field please");	
				}
			}
		}); //Event end
		
		gpRoot.add(lblDepartmentname, 0, 0);
		gpRoot.add(nameOfDepartment, 1, 0);
		gpRoot.add(btnAddDepartment, 0, 1);


		
		return gpRoot;
	}
	
	private GridPane addRoleBuildPane() {
		
		//Making the gridpane
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		
		//create nodes
		Label lblRolename = new Label("Enter Role's name:");
		TextField nameOfRole = new TextField();
		Button btnAddRole = new Button("Add Role to Department");
		Label lblRoleList = new Label("Choose Department to assign the role:");
		
		//Button add role event
		btnAddRole.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				indexDepAddRole = depsComboAddRole.getSelectionModel().getSelectedIndex(); //Index of selected department
				for (GuiEventsListener l : allListeners) {
					if (!nameOfRole.getText().isEmpty() && indexDepAddRole != -1 ) {
						l.addRoleFromGui(nameOfRole.getText(), indexDepAddRole);
					} else
						dialog("Do not leave empty field please");
				}
			}
		});

		gpRoot.add(lblRolename, 0, 0);
		gpRoot.add(nameOfRole, 1, 0);
		gpRoot.add(lblRoleList, 0, 1);
		gpRoot.add(depsComboAddRole, 0, 2);
		gpRoot.add(btnAddRole, 0, 3);

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
				
		GridPane gridPaneForPrefHours = new GridPane();
		gridPaneForPrefHours.setPadding(new Insets(2));
		gridPaneForPrefHours.setHgap(2);
		gridPaneForPrefHours.setVgap(2);
		Label lblPreferredhours = new Label("Preferred hours:");
		
		Label lblEmployeeName = new Label("Enter employee's name:");
		TextField nameOfEmployee = new TextField();
		Label lblHoursOnMonth = new Label("How many hours the employee worked a month?");
		TextField hoursMonthField = new TextField();
		Label lblPayPerHour = new Label("Hourly pay:");
		TextField hourlyPayField = new TextField();

		
		//Preference Hours
		Spinner<Integer> begHour = new Spinner<>(0, 24, 8);
		begHour.setMaxWidth(65);
		Spinner<Integer> endHour = new Spinner<>(0, 23, 17);
		endHour.setMaxWidth(65);
		endHour.setDisable(true);
		
		gridPaneForPrefHours.add(begHour, 1, 0);
		gridPaneForPrefHours.add(endHour, 2, 0);
		gridPaneForPrefHours.add(lblPreferredhours, 0, 0);

		begHour.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
	        if (!"".equals(newValue)) {
	        	if (begHour.getValue() >= 15) {
		            endHour.getValueFactory().setValue(begHour.getValue()-15);
				} else
		            endHour.getValueFactory().setValue(begHour.getValue() + 9);
	        } 
	    });

		
		Label lblEmployeeType = new Label("Choose your employee's salary type:");
		RadioButton c1 = new RadioButton("By hour");
		c1.setUserData("HOUR");
		RadioButton c2 = new RadioButton("Base salary");
		c2.setUserData("BASE");
		RadioButton c3 = new RadioButton("Base salary + bonus");
		c3.setUserData("BASEBONUS");
		
		ToggleGroup radioButtonSalaryType = new ToggleGroup();
		c1.setToggleGroup(radioButtonSalaryType);
		c2.setToggleGroup(radioButtonSalaryType);
		c3.setToggleGroup(radioButtonSalaryType);
		c1.setSelected(true); //Prevent unselected error
		
		radioButtonSalaryType.selectedToggleProperty().addListener((obserableValue, old_toggle, new_toggle) -> {
		    if (radioButtonSalaryType.getSelectedToggle() != c1) {
		    	lblHoursOnMonth.setDisable(true);
		    	hoursMonthField.setDisable(true);
		    	hoursMonthField.clear();
		    } else {
		    	lblHoursOnMonth.setDisable(false);
		    	hoursMonthField.setDisable(false);
		    }
		});
		
		Label lblEmployeePref = new Label("Choose your employee's prefernce:");
		RadioButton c4 = new RadioButton("Start working early");
		c4.setUserData("EARLIER");
		RadioButton c5 = new RadioButton("Start working late");
		c5.setUserData("LATER");
		RadioButton c6 = new RadioButton("Stay on basic (8 - 17)");
		c6.setUserData("STAYBASIC");
		RadioButton c7 = new RadioButton("Work from home");
		c7.setUserData("WORKHOME");
		ToggleGroup radioButtonPref = new ToggleGroup();
		c4.setToggleGroup(radioButtonPref);
		c5.setToggleGroup(radioButtonPref);
		c6.setToggleGroup(radioButtonPref);
		c7.setToggleGroup(radioButtonPref);
		c4.setSelected(true); //Prevent unselected error
		
		radioButtonPref.selectedToggleProperty().addListener((obserableValue, old_toggle, new_toggle) -> {
			if (radioButtonPref.getSelectedToggle() == c4) {
				begHour.getValueFactory().setValue(7);
			} else if (radioButtonPref.getSelectedToggle() == c5) {
				begHour.getValueFactory().setValue(9);
			} else if (radioButtonPref.getSelectedToggle() == c6) {
				begHour.getValueFactory().setValue(8);
			} else if (radioButtonPref.getSelectedToggle() == c7) {

			}
		});
		
		Tooltip msg = new Tooltip("Can't start working between 15 to 00");
		begHour.setTooltip(msg);

		
		begHour.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
			if (!"".equals(newValue)) {
				if (begHour.getValue() < 16 && radioButtonPref.getSelectedToggle() == c7) {
					c7.setSelected(true);
				} else if (begHour.getValue() < 8 && begHour.getValue() > -1) {
					c4.setSelected(true);
				} else if (begHour.getValue() > 8 && begHour.getValue() < 16) {
					c5.setSelected(true);
				} else if (begHour.getValue() > 15) {
					begHour.getValueFactory().setValue(0);
				} else if (begHour.getValue() == 8) {
					c6.setSelected(true);
				}
				
					
			}
		});
		
		Label lblDepartmentChoose = new Label("Choose Department:");
		Label lblRoleChoose = new Label("Choose Role to assign:");
		
		Button btnCreateEmployee = new Button("Create Employee");
		
		//----Action event for COMBOBOX----//
		depsComboEmployees.setOnAction((event) -> {
			int indexDepAddEmployee = depsComboEmployees.getSelectionModel().getSelectedIndex(); //Department Index
			rolesComboEmployee.getItems().setAll(arrayOfRolesByIndex.get(indexDepAddEmployee).getItems());
		});
		
		//----Action event for Button----//
		btnCreateEmployee.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				int indexRoleAddEmployee = rolesComboEmployee.getSelectionModel().getSelectedIndex(); //Role Index
				int indexDepAddEmployee = depsComboEmployees.getSelectionModel().getSelectedIndex(); //Department Index

				boolean emptyComboBoxFields = depsComboEmployees.getSelectionModel().isEmpty() || rolesComboEmployee.getSelectionModel().isEmpty();
				if (nameOfEmployee.getText().isEmpty() || emptyComboBoxFields) {
					dialog("Do not leave empty fields please");
				} else {
					for (GuiEventsListener l : allListeners) {
						try {
							if (radioButtonSalaryType.getSelectedToggle() == c1) {
								if (!hoursMonthField.getText().isEmpty() && !hourlyPayField.getText().isEmpty()) {
									l.addEmployeeFromGui(nameOfEmployee.getText(), indexRoleAddEmployee, indexDepAddEmployee, begHour.getValue(), radioButtonPref.getSelectedToggle().getUserData().toString(), radioButtonSalaryType.getSelectedToggle().getUserData().toString(), Integer.parseInt(hoursMonthField.getText()), Integer.parseInt(hourlyPayField.getText()));
								} else 	
									dialog("Do not leave empty fields please");

							} else
								l.addEmployeeFromGui(nameOfEmployee.getText(), indexRoleAddEmployee, indexDepAddEmployee, begHour.getValue(), radioButtonPref.getSelectedToggle().getUserData().toString(), radioButtonSalaryType.getSelectedToggle().getUserData().toString(), 160, Integer.parseInt(hourlyPayField.getText()));
						} catch (NumberFormatException e) {
							dialog("Can't use letters for hours in month, ONLY number");
						} catch (Exception e) {
							dialog(e.getMessage());
						}
					}

				}
			}
		});

		
		gpRoot.add(lblEmployeeName, 0, 0);
		gpRoot.add(nameOfEmployee, 1, 0);
		gpRoot.add(lblPayPerHour, 0, 1);
		gpRoot.add(hourlyPayField, 1, 1);
		gpRoot.add(lblEmployeeType, 0, 2);
		gpRoot.add(c1, 0, 3);
		gpRoot.add(lblHoursOnMonth, 0, 4);
		gpRoot.add(hoursMonthField, 1, 4);
		gpRoot.add(c2, 0, 5);
		gpRoot.add(c3, 0, 6);
		gpRoot.add(lblDepartmentChoose, 0, 7);
		gpRoot.add(depsComboEmployees, 0, 8);
		gpRoot.add(lblRoleChoose, 0, 9);
	    gpRoot.add(rolesComboEmployee, 0, 10);
	    gpRoot.add(lblEmployeePref, 0, 11);
		gpRoot.add(c4, 0, 12);
		gpRoot.add(c5, 0, 13);
		gpRoot.add(gridPaneForPrefHours, 1, 13);
		gpRoot.add(c6, 0, 14);
		gpRoot.add(c7, 0, 15);
	    gpRoot.add(btnCreateEmployee, 0, 16);

		return gpRoot;
	}

	private GridPane changeDepartmentPrefSyncBuildPane() {
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		
		//create nodes
		Label lblDepartmentChoise = new Label("Choose department from the list:");
		CheckBox isSync = new CheckBox("Employees synchronized by hours");
		CheckBox canChangePref = new CheckBox("Can change the prefernces");
		Label lblDescription = new Label("* If you select Employee synchronization by hours, it forces all roles to be at the time you set");
		Label lblDescription2 = new Label("* If you unselect Can change the prefernce, employees under all roles, can not change their preference");
		lblDescription.setTextFill(Color.RED);
		lblDescription2.setTextFill(Color.RED);
		
		GridPane gridPaneForPrefHours = new GridPane();
		gridPaneForPrefHours.setPadding(new Insets(2));
		gridPaneForPrefHours.setHgap(2);
		gridPaneForPrefHours.setVgap(2);
		Label lblPreferredhours = new Label("Synchronized hours:");

		// Preference Hours
		Spinner<Integer> syncHour = new Spinner<>(0, 23, 8);
		syncHour.setMaxWidth(65);
		Spinner<Integer> endHour = new Spinner<>(0, 23, 17);
		endHour.setMaxWidth(65);
		endHour.setDisable(true);

		gridPaneForPrefHours.add(syncHour, 1, 0);
		gridPaneForPrefHours.add(endHour, 2, 0);
		gridPaneForPrefHours.add(lblPreferredhours, 0, 0);

		syncHour.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
			if (!"".equals(newValue)) {
				if (syncHour.getValue() > 15 && syncHour.getValue() < 25) {
					syncHour.getValueFactory().setValue(0);
				}
				if (syncHour.getValue() >= 15) {
					endHour.getValueFactory().setValue(syncHour.getValue() - 15);
				} else
					endHour.getValueFactory().setValue(syncHour.getValue() + 9);

				for (GuiEventsListener l : allListeners) {
					l.setSyncDepartmentFromGui(indexDepSyncPrefDeps, isSync.isSelected(), syncHour.getValue(), endHour.getValue());
				}
			}
		});

		
		isSync.setDisable(true);
		canChangePref.setDisable(true);
		syncHour.setDisable(true);


		depsComboSyncPrefDeps.setOnAction((event) -> {
		    indexDepSyncPrefDeps = depsComboSyncPrefDeps.getSelectionModel().getSelectedIndex();
			isSync.setDisable(false);
			canChangePref.setDisable(false);
			for (GuiEventsListener l : allListeners) {
				boolean isDepartmentSync = l.getSyncDepartmentFromGui(indexDepSyncPrefDeps);
				boolean isDepartmentPref = l.getPrefDepartmentFromGui(indexDepSyncPrefDeps);
				isSync.setSelected(isDepartmentSync);
				canChangePref.setSelected(isDepartmentPref);
			}
		});
		
		canChangePref.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  for (GuiEventsListener l : allListeners) {
			  			l.setPrefDepartmentFromGui(indexDepSyncPrefDeps, canChangePref.isSelected());
			  		}	
			      });
		isSync.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
					syncHour.setDisable(!isSync.isSelected());
					for (GuiEventsListener l : allListeners) {
						l.setSyncDepartmentFromGui(indexDepSyncPrefDeps, isSync.isSelected(), syncHour.getValue(), endHour.getValue());
					}
				});
		

		gpRoot.add(lblDepartmentChoise, 0, 0);
		gpRoot.add(depsComboSyncPrefDeps, 0, 1);
		gpRoot.add(isSync, 0, 2);
		gpRoot.add(gridPaneForPrefHours, 0, 3);
		gpRoot.add(canChangePref, 0, 4);
		gpRoot.add(lblDescription, 0, 5);
		gpRoot.add(lblDescription2, 0, 6);

		
		return gpRoot;
	}

	private GridPane changeRolePrefSyncBuildPane() {
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		
		Label lblDepartmentChoise = new Label("Choose department from the list and after choose a role:");
		CheckBox isSync = new CheckBox("Employees synchronized by hours");
		CheckBox canChangePref = new CheckBox("Can change the prefernces");
		
		GridPane gridPaneForPrefHours = new GridPane();
		gridPaneForPrefHours.setPadding(new Insets(2));
		gridPaneForPrefHours.setHgap(2);
		gridPaneForPrefHours.setVgap(2);
		Label lblPreferredhours = new Label("Synchronized hours:");

		// Preference Hours
		Spinner<Integer> syncHour = new Spinner<>(0, 23, 8);
		syncHour.setMaxWidth(65);
		Spinner<Integer> endHour = new Spinner<>(0, 23, 17);
		endHour.setMaxWidth(65);
		endHour.setDisable(true);

		gridPaneForPrefHours.add(syncHour, 1, 0);
		gridPaneForPrefHours.add(endHour, 2, 0);
		gridPaneForPrefHours.add(lblPreferredhours, 0, 0);

		syncHour.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
			if (!"".equals(newValue)) {
				if (syncHour.getValue() > 15 && syncHour.getValue() < 25) {
					syncHour.getValueFactory().setValue(0);
				}
				if (syncHour.getValue() >= 15) {
					endHour.getValueFactory().setValue(syncHour.getValue() - 15);
				} else
					endHour.getValueFactory().setValue(syncHour.getValue() + 9);
				for (GuiEventsListener l : allListeners) {
					l.setSyncRoleFromGui(indexDepSyncPrefRoles, indexRoleSyncPrefRoles, isSync.isSelected(), syncHour.getValue(), endHour.getValue());
				}
			}
		});
		

		
		//create nodes
		isSync.setDisable(true);
		canChangePref.setDisable(true);
		syncHour.setDisable(true);
		
		
		//----Action event for COMBOBOX----//
		depsComboSyncPrefRoles.setOnAction((event) -> {
			indexDepSyncPrefRoles = depsComboSyncPrefRoles.getSelectionModel().getSelectedIndex(); // Department Index
			rolesComboSyncPrefRoles.getItems().setAll(arrayOfRolesByIndex.get(indexDepSyncPrefRoles).getItems());
			indexRoleSyncPrefRoles = -1;
			rolesComboSyncPrefRoles.getSelectionModel().clearSelection();
			isSync.setDisable(true);
			canChangePref.setDisable(true);


		});

		depsComboSyncPrefRoles.setOnMouseClicked((event) -> {
			if (depsComboSyncPrefRoles.getItems().isEmpty() && indexDepSyncPrefRoles != -1 && indexRoleSyncPrefRoles != -1) { // Prevent errors
				rolesComboSyncPrefRoles.getItems().setAll(arrayOfRolesByIndex.get(indexDepSyncPrefRoles).getItems());
			}
		});

		rolesComboSyncPrefRoles.setOnAction((event) -> {
			indexRoleSyncPrefRoles = rolesComboSyncPrefRoles.getSelectionModel().getSelectedIndex();
			isSync.setDisable(false);
			canChangePref.setDisable(false);
			if (indexRoleSyncPrefRoles != -1) {

				for (GuiEventsListener l : allListeners) {
					boolean isRoleSync = l.getSyncRoleFromGui(indexDepSyncPrefRoles, indexRoleSyncPrefRoles);
					boolean isRolePref = l.getPrefRoleFromGui(indexDepSyncPrefRoles, indexRoleSyncPrefRoles);
					isSync.setSelected(isRoleSync);
					canChangePref.setSelected(isRolePref);
				}

			}
		});
		

		canChangePref.setOnAction((event) -> {
			for (GuiEventsListener l : allListeners) {
				l.setPrefRoleFromGui(indexDepSyncPrefRoles, indexRoleSyncPrefRoles, canChangePref.isSelected());
			}
		});
		
		isSync.setOnAction((event) -> {
			for (GuiEventsListener l : allListeners) {
				l.setSyncRoleFromGui(indexDepSyncPrefRoles, indexRoleSyncPrefRoles, isSync.isSelected(), syncHour.getValue(), endHour.getValue());
			}
		});

//		isSync.selectedProperty()
//				.addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
//					syncHour.setDisable(!isSync.isSelected());
//					for (GuiEventsListener l : allListeners) {
//						if (l.getSyncDepartmentFromGui(indexDepSyncPrefRoles)) {
//							dialog("The choice does not matter because department force all roles to be synchronized");
//						} else
//							
//					}
//				});
		
		
		gpRoot.add(lblDepartmentChoise, 0, 0);
		gpRoot.add(depsComboSyncPrefRoles, 0, 1);
		gpRoot.add(rolesComboSyncPrefRoles, 0, 2);
		gpRoot.add(isSync, 0, 3);
		gpRoot.add(gridPaneForPrefHours, 1, 3);
		gpRoot.add(canChangePref, 0, 4);

		
		return gpRoot;
	}

	private GridPane showResultsBuildPane() {
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		
		//create nodes
		Label lblCompany = new Label("The total profit/loss of the company is:");
		lblCompany.setStyle("-fx-font: 14 arial;");
		Button btnCalculateEfficiency = new Button("Calculate efficiency");
		
		//Table titles
		Text empTitle = new Text("Employee table");
		empTitle.setStyle("-fx-font: 24 arial;");
		Text depTitle = new Text("Departments table");
		depTitle.setStyle("-fx-font: 24 arial;");


		//Build the employee table
		TableView<EfficiencyType> employeesTable = new TableView<EfficiencyType>();
		employeesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		//Name col
		TableColumn<EfficiencyType, String> nameCol = new TableColumn<>("Name");
		nameCol.setMinWidth(50);
		nameCol.setCellValueFactory(new PropertyValueFactory<EfficiencyType, String>("name"));
		//Efficiency col
		TableColumn<EfficiencyType, Double> effCol = new TableColumn<>("Efficiency");
		effCol.setMinWidth(50);
		effCol.setCellValueFactory(new PropertyValueFactory<EfficiencyType, Double>("efficiency"));
		employeesTable.getColumns().addAll(nameCol, effCol);
		
		//Build the department table
		TableView<EfficiencyType> departmentsTable = new TableView<EfficiencyType>();
		departmentsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		//Name col
		TableColumn<EfficiencyType, String> nameDepCol = new TableColumn<>("Name");
		nameDepCol.setMinWidth(50);
		nameDepCol.setCellValueFactory(new PropertyValueFactory<EfficiencyType, String>("name"));
		//Efficiency col
		TableColumn<EfficiencyType, Double> effDepCol = new TableColumn<>("Efficiency");
		effDepCol.setMinWidth(50);
		effDepCol.setCellValueFactory(new PropertyValueFactory<EfficiencyType, Double>("efficiency"));
		departmentsTable.getColumns().addAll(nameDepCol, effDepCol);
		
		
		btnCalculateEfficiency.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent action) {
				for (GuiEventsListener l : allListeners) {
					l.calculateEfficiencyFromGui();
					employeesTable.setItems(getEmployeesEfficiencyList());
					departmentsTable.setItems(getDepartmentsEfficiencyList());
				}
			}
		});

		gpRoot.add(btnCalculateEfficiency, 0, 0);
		gpRoot.add(employeesTable, 1, 2);
		gpRoot.add(empTitle, 1, 1);
		gpRoot.add(departmentsTable, 0, 2);
		gpRoot.add(depTitle, 0, 1);
		gpRoot.add(lblCompany, 0, 3);
		gpRoot.add(lblEffCompany, 1, 3);
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
		depsComboAddRole.getItems().add(departmentName);
		dialog("Department " + "\"" + departmentName + "\"" + " was added to the company");
		depsComboEmployees.getItems().add(departmentName);
		depsComboSyncPrefDeps.getItems().add(departmentName);
		depsComboSyncPrefRoles.getItems().add(departmentName);
		arrayOfRolesByIndex.add(new ComboBox<String>());
		departmentsNames.add(departmentName);
	}

	@Override
	public void addRoleToGui(String roleName, int indexDepartment) {
		dialog("Role " + "\"" + roleName + "\"" + " was added to the company");
		arrayOfRolesByIndex.get(indexDepartment).getItems().add(roleName);
		if (indexDepAddEmployee != -1) { //Prevent errors
		    rolesComboEmployee.getItems().setAll(arrayOfRolesByIndex.get(indexDepAddEmployee).getItems());
		}
		if (indexDepSyncPrefRoles != -1) { //Prevent errors
		    rolesComboSyncPrefRoles.getItems().setAll(arrayOfRolesByIndex.get(indexDepSyncPrefRoles).getItems());
		}

	}

	@Override
	public void addEmployeeToGui(String name) {
		dialog("Employee " + "\"" + name + "\"" + " was added to the company");
		employeesNames.add(name);
	}
	
	@Override
	public void addCompanyDetailsToGui(String toString) {
		showCompanyDetails = toString;
		Label details = new Label(showCompanyDetails);
		scrollDetailsMsg.setContent(details);
	}
	
	@Override
	public void sendTotalEfficiencyToGui(Double totalEfficiency) {
		lblEffCompany.setText("" + totalEfficiency);
	}

	@Override
	public void sendDepartmentsEfficiencyToGui(ArrayList<Double> departmentsEfficiency) {
		efficiencyDepartments.clear();
		efficiencyDepartments.addAll(departmentsEfficiency);
	}

	@Override
	public void sendEmployeesEfficiencyToGui(ArrayList<Double> employeesEfficiency) {
		efficiencyEmployees.clear();
		efficiencyEmployees.addAll(employeesEfficiency);
	}
	
	//----Helping Methods----//
	public void dialog(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}
	
	public ObservableList<EfficiencyType> getEmployeesEfficiencyList() {
		ArrayList<EfficiencyType> listOfEmployees = new ArrayList<>();
		for (int i = 0; i < employeesNames.size(); i++) {
			listOfEmployees.add(new EfficiencyType(employeesNames.get(i), efficiencyEmployees.get(i)));
		}
		ObservableList<EfficiencyType> list = FXCollections.observableArrayList();
		list.addAll(listOfEmployees);
		return list;
	}
	
	public ObservableList<EfficiencyType> getDepartmentsEfficiencyList() {
		ArrayList<EfficiencyType> listOfDepartments = new ArrayList<>();
		for (int i = 0; i < departmentsNames.size(); i++) {
			listOfDepartments.add(new EfficiencyType(departmentsNames.get(i), efficiencyDepartments.get(i)));
		}
		ObservableList<EfficiencyType> list = FXCollections.observableArrayList();
		list.addAll(listOfDepartments);
		return list;
	}

	@Override
	public void loadData() {
		if (toLoadData == "yes") {
			for (GuiEventsListener l : allListeners) {
				l.loadDataFromUi();
			}
		}
		
	}
	public void setCompanyName() {
		for (GuiEventsListener l : allListeners) {
			l.setCompanyNameFromGui(companyName);
		}
	}






}
