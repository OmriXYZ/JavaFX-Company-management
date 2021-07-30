package Controller;

import java.util.ArrayList;

import Model.Company;
import View.AbstractCompanyView;
import listeners.GuiEventsListener;
import listeners.ModelEventsListener;

public class Controller implements ModelEventsListener, GuiEventsListener {
	private Company company; //Model
	private AbstractCompanyView view; //Gui
	
	public Controller(Company company, AbstractCompanyView view) {
		this.company = company;
		this.view = view;
		
		view.registerListener(this);
		company.registerListener(this);
		
	}

	@Override
	public void addDepartmentFromGui(String name) { //Send request to create department from gui to model.
		try {
			company.addDepartment(name);
		} catch (Exception e) {
			view.dialog(e.getMessage());
		}
	}

	@Override
	public void addRoleFromGui(String name, int indexDepartment) { //Send request to create role from gui to model.
		try {
			company.addRole(name, indexDepartment);
		} catch (Exception e) {
			view.dialog(e.getMessage());
		}
	}

	@Override
	public void addEmployeeFromGui(String name, int indexRole, int indexDepartment, int begHour, String pref, String typeOfWorker, int hoursMonth, int payPerHour) throws Exception { //Send request to create employee from gui to model.
		company.addEmployee(name, indexRole, indexDepartment, begHour, pref, typeOfWorker, hoursMonth, payPerHour, 0);
	}
	
	@Override
	public void addEmployeeBonusFromGui(String name, int indexRole, int indexDepartment, int begHour, String pref,
			String typeOfWorker, int hoursMonth, int payPerHour, int salesPerMonth) throws Exception {
		company.addEmployee(name, indexRole, indexDepartment, begHour, pref, typeOfWorker, hoursMonth, payPerHour, salesPerMonth);
		
	}

	@Override
	public void setedCompanyNameFromCompany(String name) { //Model send to gui that company name was seted.
		view.dialog(name + " has been set to be the company name");
		view.setCompanyNameToGui(name);
	}
	
	@Override
	public void setCompanyNameFromGui(String name) { //Set name of company
		company.setName(name);
	}

	@Override
	public void addedDepartmentFromCompany(String departmentName) { //Model send to gui that department created.
		view.addDepartmentToGui(departmentName);
	}

	@Override
	public void addedRoleFromCompany(String roleName, int departmentIndex) { //Model send to gui that role created.
		view.addRoleToGui(roleName, departmentIndex);
	}

	@Override
	public void addedEmployeeFromCompany(String name) { //Model send to gui that employee created.
		view.addEmployeeToGui(name);
	}

	@Override
	public void sendCompanyDetails(String toString) { //Model send to gui details string.
		view.addCompanyDetailsToGui(toString);
	}

	@Override
	public void setPrefDepartmentFromGui(int indexDepartment, boolean b) { //Sends a request to change the ability to change preference for department
		company.setPrefDepartmentFromGui(indexDepartment, b);
	}

	@Override
	public void setSyncDepartmentFromGui(int indexDepartment, boolean b, int syncHour, int endHour) { //Sends a request to change synchronization for department
		company.setSyncDepartmentFromGui(indexDepartment, b, syncHour, endHour);
	}

	@Override
	public void setPrefRoleFromGui(int indexDepartment, int indexRole, boolean b) { //Sends a request to change the ability to change preference for role
		try {
			company.setPrefRoleFromGui(indexDepartment, indexRole, b);
		} catch (Exception e) {
			view.dialog(e.getMessage());
		}
	}

	@Override
	public void setSyncRoleFromGui(int indexDepartment, int indexRole, boolean b, int syncHour, int endHour) { //Sends a request to change synchronization for role
		try {
			company.setSyncRoleFromGui(indexDepartment, indexRole, b, syncHour, endHour);
		} catch (Exception e) {
			view.dialog(e.getMessage());
		}
	}

	@Override
	public boolean getPrefDepartmentFromGui(int indexDepartment) { //Get the value of "can change preference" for department
		return company.getPrefDepartmentFromGui(indexDepartment);
	}

	@Override
	public boolean getSyncDepartmentFromGui(int indexDepartment) { //Get the value of "synchronization" for department
		return company.getSyncDepartmentFromGui(indexDepartment);
	}

	@Override
	public boolean getPrefRoleFromGui(int indexDepartment, int indexRole) { //Get the value of "can change preference" for role
		return company.getPrefRoleFromGui(indexDepartment, indexRole);
	}

	@Override
	public boolean getSyncRoleFromGui(int indexDepartment, int indexRole) { //Get the value of "synchronization" for role
		return company.getSyncRoleFromGui(indexDepartment, indexRole);
	}

	@Override
	public void calculateEfficiencyFromGui() { //Sends a request to calculate efficiency from gui to model
		try {
			company.calcTotalEfficiency();
		} catch (Exception e) {
			view.dialog(e.getMessage());
		}
	}

	@Override
	public void sendTotalEfficiency(Double totalEfficiency) { //Sends the value of total efficiency from model to gui
		view.sendTotalEfficiencyToGui(totalEfficiency);
	}

	@Override
	public void sendDepartmentsEfficiency(ArrayList<Double> departmentsEfficiency) { //Sends departments efficiency from model to gui
		view.sendDepartmentsEfficiencyToGui(departmentsEfficiency);
	}

	@Override
	public void sendEmployeesEfficiency(ArrayList<Double> employeesEfficiency) { //Sends employees efficiency from model to gui
		view.sendEmployeesEfficiencyToGui(employeesEfficiency);
	}

	@Override
	public void saveDataFromUi() { //Sends a request to save data from gui to model
		try {
			company.saveData();
		} catch (Exception e) {
			view.dialog(e.getMessage());
		}
		
	}

	@Override
	public void sendDataMsg(String dataMsg) { //Sends data message succefull or not, from model to gui
		view.dialog(dataMsg);
	}

	@Override
	public void loadDataFromUi() { //Sends a request to load data from gui to model
		try {
			company.loadData();
		} catch (Exception e) {
			view.dialog(e.getMessage());
		}
		
	}


	
}
