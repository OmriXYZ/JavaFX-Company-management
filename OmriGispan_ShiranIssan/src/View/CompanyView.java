package View;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import Model.EfficiencyType;
import helpingmethods.CustomDialog;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import listeners.GuiEventsListener;

public class CompanyView implements AbstractCompanyView {

	//----MVC----//
	private ArrayList<GuiEventsListener> allListeners = new ArrayList<GuiEventsListener>();

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
	private int indexDepAddRole = -1;		 //Department index - Add role pane
	private int indexDepAddEmployee = -1;	 //Department index - Add Employee pane
	private int indexDepSyncPrefDeps = -1;	 //Department index - Change sync&pref departments pane
	private int indexDepSyncPrefRoles = -1;	 //Department index - Change sync&pref roles pane
	private int indexRoleSyncPrefRoles = -1; //Role index - Change sync&pref roles pane

	//toString Pane
	private String showCompanyDetails = "";
	private ScrollPane scrollDetailsMsg;
	
	//Efficiency ArrayList for departments and employee
	private ArrayList<Double> efficiencyDepartments = new ArrayList<Double>();
	private ArrayList<Double> efficiencyEmployees = new ArrayList<Double>();
	//total efficiency - using on showing the results
	private Label lblEffCompany = new Label("");
	//using for loading the data by string answer
	private String toLoadData = "";
	//Company name on top
	private Label lblCompanyName;
	//some string on top (using for disable it when loading data)
	private Label lblAcceptName;
	//textfield company name on top (using for disable it when loading data)
	private TextField fieldCompanyName;
	//some string on top (using for disable it when loading data)
	private Label lblMenu;
	//Divides categories into tabs
	private TabPane tabPane;
	//The main pane
	private GridPane gpRoot;
	//Graph pane (using on "changeRolePrefSyncBuildPane()")
	private GridPane graphPane;

	public CompanyView(Stage stage) throws Exception {
		
		//Define tab pane on top
		tabPane = new TabPane();
		tabPane.setDisable(true);
		tabPane.setPrefWidth(1200);
		
		//Define company name
		lblCompanyName = new Label("");
		lblCompanyName.setStyle("-fx-font-size: 18; -fx-text-fill: white");
		
		//Set name of window
		stage.setTitle("Company Menu");

		//Define the main pane
		gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
				
		//Save and Exit button
		Button btnSaveAndExit = new Button("Save and exit");
		btnSaveAndExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				for (GuiEventsListener l : allListeners) {
					l.saveDataFromUi();
				}
				stage.close();
			}
		}); //Event end
				
		//CustomDialog for loading recent data
		CustomDialog loadDataDialog = new CustomDialog();
		loadDataDialog.showAndWait();
		toLoadData = loadDataDialog.getResult();
		//CustomDialog-END
		
		lblMenu = new Label("Hello, type your company name: "); //Welcome MSG
		fieldCompanyName = new TextField(""); //text field set company name
		fieldCompanyName.setMaxWidth(120);
		lblAcceptName = new Label("(Press enter for accept company name)");
		fieldCompanyName.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ENTER && !fieldCompanyName.getText().isEmpty()) { //Accept company name when press ENTER
				setCompanyName(fieldCompanyName.getText());
				gpRoot.getChildren().remove(lblMenu);
				gpRoot.getChildren().remove(fieldCompanyName);
				gpRoot.getChildren().remove(lblAcceptName);
				tabPane.setDisable(false);
			}
		});
		
		//ContextMenu using for tabpane on top
		ContextMenu contextMenu = new ContextMenu();
		tabPane.setContextMenu(contextMenu);
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE); //So they can not close tabs
		
		//Define Adding all tabs (Build each tab happens on methods)
		Tab addDepTab = new Tab("Add department");
		addDepTab.setContent(addDepartmentBuildPane());
		Tab addRoleTab = new Tab("Add role");
		addRoleTab.setContent(addRoleBuildPane());
		Tab addEmployeeTab = new Tab("Add employee");
		addEmployeeTab.setContent(addEmployeeBuildPane());
		Tab showDetailsTab = new Tab("Show details");
		showDetailsTab.setContent(showDetailsTBuildPane());
		Tab changeDepTab = new Tab("Change departments options");
		changeDepTab.setContent(changeDepartmentPrefSyncBuildPane());
		Tab changeRoleTab = new Tab("Change roles options");
		changeRoleTab.setContent(changeRolePrefSyncBuildPane());
		Tab showEffTab = new Tab("Show profit or loss");
		showEffTab.setContent(showResultsBuildPane());
		
		//Adding all tabs to the tabpane
		tabPane.getTabs().add(addDepTab);
		tabPane.getTabs().add(addRoleTab);
		tabPane.getTabs().add(addEmployeeTab);
		tabPane.getTabs().add(showDetailsTab);
		tabPane.getTabs().add(changeDepTab);
		tabPane.getTabs().add(changeRoleTab);
		tabPane.getTabs().add(showEffTab);
		
		//Adding TabPane, company name label, button for save and exit
		gpRoot.add(lblCompanyName, 0, 0);
		gpRoot.add(lblMenu, 0, 1, 2, 1);
		gpRoot.add(fieldCompanyName, 0, 2, 1, 1);
		gpRoot.add(lblAcceptName, 1, 2);
		gpRoot.add(tabPane, 0, 3, 2, 2);
		gpRoot.add(btnSaveAndExit, 0, 8);
		gpRoot.setStyle("-fx-font: 14 arial;"); //Set bigger text
		
		//Set scene, size of the window, set new css for design, show the window
		stage.setScene(new Scene(gpRoot, 1150, 850));
		stage.getScene().getStylesheets().add(getClass().getResource("dark_theme.css").toString());
		stage.show();
	}
	
//----Making The Grid Panes----//
	private GridPane addDepartmentBuildPane() { //Add department grid pane
		
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
	
	private GridPane addRoleBuildPane() { //Add role grid pane
		
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
					if (!nameOfRole.getText().isEmpty() && indexDepAddRole != -1 ) { //Check that textbox is empty and no department selected
						l.addRoleFromGui(nameOfRole.getText(), indexDepAddRole);
					} else
						dialog("Do not leave empty field please");
				}
			}
		});

		gpRoot.add(lblRolename, 0, 0);
		gpRoot.add(nameOfRole, 1, 0);
		gpRoot.add(lblRoleList, 0, 1, 2, 1);
		gpRoot.add(depsComboAddRole, 0, 2);
		gpRoot.add(btnAddRole, 0, 3, 2, 1);

		return gpRoot;
	}

	private GridPane showDetailsTBuildPane() { //toString of company grid pane

		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);

		// create nodes
		scrollDetailsMsg = new ScrollPane();
		scrollDetailsMsg.setPrefSize(700, 700);

		Label lblCompanyDetailsMsg = new Label("Company Details:");

		gpRoot.add(lblCompanyDetailsMsg, 0, 0);
		gpRoot.add(scrollDetailsMsg, 0, 1);

		return gpRoot;
	}
	
	private GridPane addEmployeeBuildPane() { //Add employee grid pane

		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
				
		GridPane gridPaneForPrefHours = new GridPane();
		gridPaneForPrefHours.setPadding(new Insets(2));
		gridPaneForPrefHours.setHgap(2);
		gridPaneForPrefHours.setVgap(2);
		
		//Define nodes
		Label lblPreferredhours = new Label("Preferred hours:");
		Label lblEmployeeName = new Label("Enter employee's name:");
		TextField nameOfEmployee = new TextField();
		Label lblHoursOnMonth = new Label("How many hours the employee worked a month?");
		TextField hoursMonthField = new TextField();
		Label lblPayPerHour = new Label("Hourly pay:");
		TextField hourlyPayField = new TextField();
		TextField bonusSalesField = new TextField();
		Label lblSales = new Label("Sales per month:");
		lblSales.setDisable(true);
		bonusSalesField.setDisable(true);

		//Preference Hours using spinner class
		Spinner<Integer> begHour = new Spinner<>(0, 24, 7);
		begHour.setMaxWidth(65);
		Spinner<Integer> endHour = new Spinner<>(0, 23, 16);
		endHour.setMaxWidth(65);
		endHour.setDisable(true);
		
		gridPaneForPrefHours.add(begHour, 1, 0);
		gridPaneForPrefHours.add(endHour, 2, 0);
		gridPaneForPrefHours.add(lblPreferredhours, 0, 0);
		
		//Define radiobuttons
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
		
		//Event for salary type radio button
		radioButtonSalaryType.selectedToggleProperty().addListener((obserableValue, old_toggle, new_toggle) -> {
		    if (radioButtonSalaryType.getSelectedToggle() == c1) {
		    	lblHoursOnMonth.setDisable(false);
		    	hoursMonthField.setDisable(false);
				lblSales.setDisable(true);
				bonusSalesField.setDisable(true);
		    } else if (radioButtonSalaryType.getSelectedToggle() == c2) {
				lblSales.setDisable(true);
				bonusSalesField.setDisable(true);
		    	lblHoursOnMonth.setDisable(true);
		    	hoursMonthField.setDisable(true);
		    	hoursMonthField.clear();
		    } else if (radioButtonSalaryType.getSelectedToggle() == c3) {
				lblSales.setDisable(false);
				bonusSalesField.setDisable(false);
		    	lblHoursOnMonth.setDisable(true);
		    	hoursMonthField.setDisable(true);
		    	hoursMonthField.clear();
		    }
		});
		
		//Define radiobuttons
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
		
		//Event for preference radio button
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
		
		//Event for spinner (time change)
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
		
		//Event for spinner limit start working at 15 (time change)
		begHour.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
	        if (!"".equals(newValue)) {
	        	if (begHour.getValue() >= 15) {
		            endHour.getValueFactory().setValue(begHour.getValue()-15);
				} else
		            endHour.getValueFactory().setValue(begHour.getValue() + 9);
	        } 
	    });
		
		Label lblDepartmentChoose = new Label("Choose Department:");
		Label lblRoleChoose = new Label("Choose Role to assign:");
		Button btnCreateEmployee = new Button("Create Employee");
		
		//----Action event for COMBOBOX----//
		depsComboEmployees.setOnAction((event) -> {
			indexDepAddEmployee = depsComboEmployees.getSelectionModel().getSelectedIndex(); //Department Index
			rolesComboEmployee.getItems().setAll(arrayOfRolesByIndex.get(indexDepAddEmployee).getItems());
		});
		
		//----Action event for create employee Button----//
		btnCreateEmployee.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				int indexRoleAddEmployee = rolesComboEmployee.getSelectionModel().getSelectedIndex(); //Role Index
				int indexDepAddEmployee = depsComboEmployees.getSelectionModel().getSelectedIndex(); //Department Index

				boolean emptyComboBoxFields = depsComboEmployees.getSelectionModel().isEmpty() || rolesComboEmployee.getSelectionModel().isEmpty();
				if (nameOfEmployee.getText().isEmpty() || emptyComboBoxFields || hourlyPayField.getText().isEmpty()) {
					dialog("Do not leave empty fields please");
				} else {
					for (GuiEventsListener l : allListeners) {
						try {
							if (radioButtonSalaryType.getSelectedToggle() == c1) {
								if (!hoursMonthField.getText().isEmpty()) {
									l.addEmployeeFromGui(nameOfEmployee.getText(), indexRoleAddEmployee, indexDepAddEmployee, begHour.getValue(), radioButtonPref.getSelectedToggle().getUserData().toString(), radioButtonSalaryType.getSelectedToggle().getUserData().toString(), Integer.parseInt(hoursMonthField.getText()), Integer.parseInt(hourlyPayField.getText()));
								} else 	
									dialog("Do not leave empty fields please");

							} else if (radioButtonSalaryType.getSelectedToggle() == c2) {
								l.addEmployeeFromGui(nameOfEmployee.getText(), indexRoleAddEmployee, indexDepAddEmployee, begHour.getValue(), radioButtonPref.getSelectedToggle().getUserData().toString(), radioButtonSalaryType.getSelectedToggle().getUserData().toString(), 0, Integer.parseInt(hourlyPayField.getText()));
							} else if (radioButtonSalaryType.getSelectedToggle() == c3 && !bonusSalesField.getText().isEmpty()) {
								l.addEmployeeBonusFromGui(nameOfEmployee.getText(), indexRoleAddEmployee, indexDepAddEmployee, begHour.getValue(), radioButtonPref.getSelectedToggle().getUserData().toString(), radioButtonSalaryType.getSelectedToggle().getUserData().toString(), 0, Integer.parseInt(hourlyPayField.getText()), Integer.parseInt(bonusSalesField.getText()));
							} else
								dialog("Do not leave empty fields please");
							} catch (NumberFormatException e) {
							dialog("Can't use letters, Only Numbers");
						} catch (Exception e) {
							dialog(e.getMessage());
						}
					}

				}
			}
		});
		
		//Adding nodes to gridpane
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
		gpRoot.add(bonusSalesField, 0, 8);
		bonusSalesField.setMaxWidth(70);
		gpRoot.add(lblSales, 0, 7);
		gpRoot.add(lblDepartmentChoose, 0, 9);
		gpRoot.add(depsComboEmployees, 0, 10);
		gpRoot.add(lblRoleChoose, 0, 11);
	    gpRoot.add(rolesComboEmployee, 0, 12);
	    gpRoot.add(lblEmployeePref, 0, 13);
		gpRoot.add(c4, 0, 14);
		gpRoot.add(c5, 0, 15);
		gpRoot.add(gridPaneForPrefHours, 1, 15);
		gpRoot.add(c6, 0, 16);
		gpRoot.add(c7, 0, 17);
	    gpRoot.add(btnCreateEmployee, 0, 18);

		return gpRoot;
	}

	private GridPane changeDepartmentPrefSyncBuildPane() { //Change options for department grid pane
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		
		//create nodes
		Label lblDepartmentChoise = new Label("Choose department from the list:");
		CheckBox isSync = new CheckBox("Employees synchronized by hours");
		CheckBox canChangePref = new CheckBox("Can change the prefernces");
		Label lblDescription = new Label("If you select Employee synchronization by hours, it forces all roles to be at the time you set");
		Label lblDescription2 = new Label("If you unselect Can change the prefernce, employees under all roles, can not change their preference");
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

		//Event for spinner (time change)
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
		
		String depSyncTrue = "The department forces all roles and employees to be synchronized at the defined time";
		String depSyncFalse = "The department doesn’t force the roles and employees to be synchronized, and the option is given to the role requires";
		String depPrefTrue = "The department doesn’t allow roles and employees to change preference";
		String depPrefFalse = "The department allows roles to decide whether to change the preference for employees";

		//Event happend when user change department from combobox
		depsComboSyncPrefDeps.setOnAction((event) -> {
		    indexDepSyncPrefDeps = depsComboSyncPrefDeps.getSelectionModel().getSelectedIndex();
			isSync.setDisable(false);
			canChangePref.setDisable(false);
			for (GuiEventsListener l : allListeners) {
				boolean isDepartmentSync = l.getSyncDepartmentFromGui(indexDepSyncPrefDeps); //get isMustSync value from company
				boolean isDepartmentPref = l.getPrefDepartmentFromGui(indexDepSyncPrefDeps); //get canChangePref value from company
				isSync.setSelected(isDepartmentSync); //set checkbox value isMustSync value from company
				canChangePref.setSelected(isDepartmentPref); //set checkbox value canChangePref value from company
				if (isDepartmentSync) {
					lblDescription.setText(depSyncTrue);
					lblDescription.setStyle("-fx-font-size: 16; -fx-text-fill: red");
				} else {
					lblDescription.setText(depSyncFalse);
					lblDescription.setStyle("-fx-font-size: 16; -fx-text-fill: green");

				}
				if (isDepartmentPref) {
					lblDescription2.setStyle("-fx-font-size: 16; -fx-text-fill: red");
					lblDescription2.setText(depPrefFalse);
				} else {
					lblDescription2.setStyle("-fx-font-size: 16; -fx-text-fill: green");
					lblDescription2.setText(depPrefTrue);
				}
			}
		});
		
		//Event happend when user change the value of checkbox canChangePref
		canChangePref.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
						if (!new_val) {
							lblDescription2.setStyle("-fx-font-size: 16; -fx-text-fill: green");
							lblDescription2.setText(depPrefTrue);
						} else {
							lblDescription2.setStyle("-fx-font-size: 16; -fx-text-fill: red");
							lblDescription2.setText(depPrefFalse);
						}
					for (GuiEventsListener l : allListeners) {
						l.setPrefDepartmentFromGui(indexDepSyncPrefDeps, canChangePref.isSelected());
					}
			      });
		
		//Event happend when user change the value of checkbox isSync
		isSync.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			if (new_val) {
				lblDescription.setStyle("-fx-font-size: 16; -fx-text-fill: red");
				lblDescription.setText(depSyncTrue);
			} else {
				lblDescription.setStyle("-fx-font-size: 16; -fx-text-fill: green");
				lblDescription.setText(depSyncFalse);
				syncHour.getValueFactory().setValue(8);
				endHour.getValueFactory().setValue(17);
			}
			syncHour.setDisable(!isSync.isSelected());
			for (GuiEventsListener l : allListeners) {
				l.setSyncDepartmentFromGui(indexDepSyncPrefDeps, isSync.isSelected(), syncHour.getValue(),
						endHour.getValue());
			}
		});

		gpRoot.add(lblDepartmentChoise, 0, 0);
		gpRoot.add(depsComboSyncPrefDeps, 0, 1);
		gpRoot.add(isSync, 0, 2);
		gpRoot.add(gridPaneForPrefHours, 0, 3);
		gpRoot.add(canChangePref, 0, 4);
		gpRoot.add(lblDescription, 1, 2);
		gpRoot.add(lblDescription2, 1, 4);

		return gpRoot;
	}

	private GridPane changeRolePrefSyncBuildPane() { //Change options for role grid pane
		
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

		//Event for spinner (time change)
		syncHour.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
			if (!"".equals(newValue)) {
				if (syncHour.getValue() > 15 && syncHour.getValue() < 25) {
					syncHour.getValueFactory().setValue(0);
				}
				if (syncHour.getValue() >= 15) {
					endHour.getValueFactory().setValue(syncHour.getValue() - 15);
				} else
					endHour.getValueFactory().setValue(syncHour.getValue() + 9);
			}
		});
		
		//Event for spinner (time change) - send values to company
		syncHour.setOnMouseClicked((event) -> {
			for (GuiEventsListener l : allListeners) {
				l.setSyncRoleFromGui(indexDepSyncPrefRoles, indexRoleSyncPrefRoles, isSync.isSelected(), syncHour.getValue(), endHour.getValue());
			}
		});
		
		isSync.setDisable(true);
		canChangePref.setDisable(true);
		syncHour.setDisable(true);
		
		Button btnUpdateGraph = new Button("Update graph");
		
		//Event for update graph button
		btnUpdateGraph.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				if (indexRoleSyncPrefRoles != -1) {
					updateGraphPane();
				}
			}
			
		});
		
		//Event for change selection on roles combobox
		rolesComboSyncPrefRoles.setOnAction((event) -> {
			indexRoleSyncPrefRoles = rolesComboSyncPrefRoles.getSelectionModel().getSelectedIndex();
			isSync.setDisable(false);
			canChangePref.setDisable(false);
			for (GuiEventsListener l : allListeners) {
				if (indexRoleSyncPrefRoles != -1) {
					boolean isRoleSync = l.getSyncRoleFromGui(indexDepSyncPrefRoles, indexRoleSyncPrefRoles);
					boolean isRolePref = l.getPrefRoleFromGui(indexDepSyncPrefRoles, indexRoleSyncPrefRoles);
					isSync.setSelected(isRoleSync);
					canChangePref.setSelected(isRolePref);
					syncHour.setDisable(!isRoleSync);
					gpRoot.getChildren().remove(graphPane);
				}
			}
			if (indexRoleSyncPrefRoles != -1) {
				graphPane = showGraph(gpRoot, depsComboSyncPrefRoles.getItems().get(indexDepSyncPrefRoles).toString(), rolesComboSyncPrefRoles.getItems().get(indexRoleSyncPrefRoles).toString());
			}

		});
		
		//----Action event for department COMBOBOX----//
		depsComboSyncPrefRoles.setOnAction((event) -> {
			indexDepSyncPrefRoles = depsComboSyncPrefRoles.getSelectionModel().getSelectedIndex(); // Department Index
			rolesComboSyncPrefRoles.getItems().setAll(arrayOfRolesByIndex.get(indexDepSyncPrefRoles).getItems());
			indexRoleSyncPrefRoles = -1;
			rolesComboSyncPrefRoles.getSelectionModel().clearSelection();
			isSync.setDisable(true);
			canChangePref.setDisable(true);
			gpRoot.getChildren().remove(graphPane);
		});

		//Event for change boolean values - send it to the company
		canChangePref.setOnAction((event) -> {
			for (GuiEventsListener l : allListeners) {
				l.setPrefRoleFromGui(indexDepSyncPrefRoles, indexRoleSyncPrefRoles, canChangePref.isSelected());
			}
		});
		
		//Event for change boolean values - send it to the company
		isSync.setOnAction((event) -> {
			syncHour.setDisable(!isSync.isSelected());
			for (GuiEventsListener l : allListeners) {
				l.setSyncRoleFromGui(indexDepSyncPrefRoles, indexRoleSyncPrefRoles, isSync.isSelected(), syncHour.getValue(), endHour.getValue());
			}
		});
		
		//Adding nodes to gridpane
		gpRoot.add(lblDepartmentChoise, 0, 0);
		gpRoot.add(depsComboSyncPrefRoles, 0, 1);
		gpRoot.add(rolesComboSyncPrefRoles, 0, 2);
		gpRoot.add(isSync, 0, 3);
		gpRoot.add(gridPaneForPrefHours, 0, 4);
		gpRoot.add(canChangePref, 0, 5);
		
		gpRoot.add(btnUpdateGraph, 5, 10);
		
		return gpRoot;
	}

	private GridPane showGraph(GridPane gp, String depName, String roleName) { //Using for showing graph on (changeRolePrefSyncBuildPane())
		GridPane grid = new GridPane();
		grid.setVgap(5);
		grid.setHgap(12);

		Label dName = new Label("Department: ");
		dName.setStyle("-fx-underline:true");
		Label rName = new Label("Role: ");
		rName.setStyle("-fx-underline:true");
		Label dep = new Label(depName);
		Label role = new Label(roleName);
		Label sync = new Label("Must\nsync");
		Label pref = new Label("Can change\npreference");
		Label sync2 = new Label("Must\nsync");
		Label pref2 = new Label("Can change\npreference");
		pref.setStyle("-fx-font: 12 arial;");
		sync.setStyle("-fx-font: 12 arial;");
		pref2.setStyle("-fx-font: 12 arial;");
		sync2.setStyle("-fx-font: 12 arial;");

		Line syncLine = new Line();
		syncLine.setStartY(0);
		syncLine.setEndY(130);
		syncLine.setStrokeWidth(7);
		//syncLine.setStyle("-fx-stroke: red");

		Line prefLine = new Line();
		prefLine.setStartY(0);
		prefLine.setEndY(130);
		prefLine.setStrokeWidth(7);
		
		Line syncLine2 = new Line();
		syncLine2.setStartY(0);
		syncLine2.setEndY(130);
		syncLine2.setStrokeWidth(7);

		Line prefLine2 = new Line();
		prefLine2.setStartY(0);
		prefLine2.setEndY(130);
		prefLine2.setStrokeWidth(7);

		int roleAdd = 10;
		//grid.setGridLinesVisible(true);
		grid.add(dep, 2, 0);
		grid.add(role, 2+5+roleAdd, 0);
		grid.add(syncLine, 1, 1);
		grid.add(prefLine, 3, 1);
		grid.add(syncLine2, 1+5+roleAdd, 1);
		grid.add(prefLine2, 3+5+roleAdd, 1);
		grid.add(sync, 1, 2);
		grid.add(pref, 3, 2);
		grid.add(sync2, 6+roleAdd, 2);
		grid.add(pref2, 6+roleAdd+2, 2);
		grid.add(dName, 0, 0);
		grid.add(rName, 5+roleAdd, 0);

		gp.add(grid, 5, 6);
		return grid;

	}
	
	private void updateGraphPane() { //Using for update graph on (changeRolePrefSyncBuildPane())
		// 2 - prefDepartmentLine
		// 3 - syncDepartmentLine
		// 4 - syncRoleLine
		// 5 - prefRoleLine
		boolean prefDep = false;
		boolean syncDep = false;
		boolean prefRole = false;
		boolean syncRole = false;
		
		for (GuiEventsListener l : allListeners) {
			prefDep = l.getPrefDepartmentFromGui(indexDepSyncPrefRoles);
			syncDep = l.getSyncDepartmentFromGui(indexDepSyncPrefRoles);
			prefRole = l.getPrefRoleFromGui(indexDepSyncPrefRoles, indexRoleSyncPrefRoles);
			syncRole = l.getSyncRoleFromGui(indexDepSyncPrefRoles, indexRoleSyncPrefRoles);
		}
		
		if (syncDep) {
			graphPane.getChildren().get(2).setStyle("-fx-stroke: red");
		} else
			graphPane.getChildren().get(2).setStyle("-fx-stroke: black");
		if (prefDep) {
			graphPane.getChildren().get(3).setStyle("-fx-stroke: red");
		} else
			graphPane.getChildren().get(3).setStyle("-fx-stroke: black");
		if (syncRole) {
			graphPane.getChildren().get(4).setStyle("-fx-stroke: red");
		} else
			graphPane.getChildren().get(4).setStyle("-fx-stroke: black");
		if (prefRole) {
			graphPane.getChildren().get(5).setStyle("-fx-stroke: red");
		} else
			graphPane.getChildren().get(5).setStyle("-fx-stroke: black");
	}

	@SuppressWarnings("unchecked")
	private GridPane showResultsBuildPane() { //Show efficiency tables and total efficiency
		
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
	public void registerListener(GuiEventsListener listener) { //MVC
		allListeners.add(listener);
	}

	@Override
	public void setCompanyNameToGui(String name) { //Set company labal from company to gui
		lblCompanyName.setText(name);
	}

	@Override
	public void addDepartmentToGui(String departmentName) { //Add department from company to gui
		depsComboAddRole.getItems().add(departmentName);
		dialog("Department " + "\"" + departmentName + "\"" + " was added to the company");
		depsComboEmployees.getItems().add(departmentName);
		depsComboSyncPrefDeps.getItems().add(departmentName);
		depsComboSyncPrefRoles.getItems().add(departmentName);
		arrayOfRolesByIndex.add(new ComboBox<String>());
		departmentsNames.add(departmentName);
	}

	@Override
	public void addRoleToGui(String roleName, int indexDepartment) { //Add role from company to gui

		dialog("Role " + "\"" + roleName + "\"" + " was added to the company");
		arrayOfRolesByIndex.get(indexDepartment).getItems().add(roleName);
		if (indexDepAddEmployee != -1) {
		    rolesComboEmployee.getItems().setAll(arrayOfRolesByIndex.get(indexDepAddEmployee).getItems());
		}
		if (indexDepSyncPrefRoles != -1) {
		    rolesComboSyncPrefRoles.getItems().setAll(arrayOfRolesByIndex.get(indexDepSyncPrefRoles).getItems());
		}
	}
	
	@Override
	public void addEmployeeToGui(String name) { //Add employee from company to gui
		dialog("Employee " + "\"" + name + "\"" + " was added to the company");
		employeesNames.add(name);
	}
	
	@Override
	public void addCompanyDetailsToGui(String toString) { //Add toString details from company to gui
		showCompanyDetails = toString;
		Label details = new Label(showCompanyDetails);
		details.setStyle("-fx-font-size: 1.3em;");
		scrollDetailsMsg.setContent(details);
	}
	
	@Override
	public void sendTotalEfficiencyToGui(Double totalEfficiency) { //send total efficiency value from company to gui
		lblEffCompany.setText("" + totalEfficiency);
	}

	@Override
	public void sendDepartmentsEfficiencyToGui(ArrayList<Double> departmentsEfficiency) { //Send department's efficiencies from company to gui
		efficiencyDepartments.clear();
		efficiencyDepartments.addAll(departmentsEfficiency);
	}

	@Override
	public void sendEmployeesEfficiencyToGui(ArrayList<Double> employeesEfficiency) { //Send employee's efficiencies from company to gui
		efficiencyEmployees.clear();
		efficiencyEmployees.addAll(employeesEfficiency);
	}
	
	public void dialog(String msg) { //Show dialog with get a string
		JOptionPane.showMessageDialog(null, msg);
	}
	
	public ObservableList<EfficiencyType> getEmployeesEfficiencyList() { //Get the efficiency employee information to insert to the table
		ArrayList<EfficiencyType> listOfEmployees = new ArrayList<>();
		for (int i = 0; i < employeesNames.size(); i++) {
			listOfEmployees.add(new EfficiencyType(employeesNames.get(i), efficiencyEmployees.get(i)));
		}
		ObservableList<EfficiencyType> list = FXCollections.observableArrayList();
		list.addAll(listOfEmployees);
		return list;
	}

	public ObservableList<EfficiencyType> getDepartmentsEfficiencyList() { //Get the efficiency department information to insert to the table
		ArrayList<EfficiencyType> listOfDepartments = new ArrayList<>();
		for (int i = 0; i < departmentsNames.size(); i++) {
			listOfDepartments.add(new EfficiencyType(departmentsNames.get(i), efficiencyDepartments.get(i)));
		}
		ObservableList<EfficiencyType> list = FXCollections.observableArrayList();
		list.addAll(listOfDepartments);
		return list;
	}

	@Override
	public void loadData() { //Send request to Model to load the data
		if (toLoadData == "yes") {
			for (GuiEventsListener l : allListeners) {
				l.loadDataFromUi();
			}
		}
		//Check if company name was loaded -> remove welcome msg
		if (!lblCompanyName.getText().isEmpty()) {
			gpRoot.getChildren().remove(lblMenu);
			gpRoot.getChildren().remove(fieldCompanyName);
			gpRoot.getChildren().remove(lblAcceptName);
			tabPane.setDisable(false);
		}
	}

	public void setCompanyName(String companyName) { //Send request to set company name
		for (GuiEventsListener l : allListeners) {
			l.setCompanyNameFromGui(companyName);
		}
	}

}
