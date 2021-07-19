package Controller;

import Model.Company;
import Model.Department;
import Model.Role;
import View.AbstractCompanyView;
import listeners.GuiEventsListener;
import listeners.ModelEventsListener;

public class Controller implements ModelEventsListener, GuiEventsListener {
	private Company company;
	private AbstractCompanyView view;
	
	public Controller(Company company, AbstractCompanyView view) {
		this.company = company;
		this.view = view;
		
		view.registerListener(this);
		company.registerListener(this);
	}

	@Override
	public void setCompanyNameFromGui(String name) {
		company.setName(name);
	}

	@Override
	public void addDepartmentFromGui(String name, boolean mustEmployeeSync, boolean canChangePreferences) {
		company.addDepartment(name, mustEmployeeSync, canChangePreferences);
	}

	@Override
	public void addRoleFromGui(String name, boolean mustEmployeeSync, boolean canChangePreferences, int indexDepartment) {
		company.addRole(name, mustEmployeeSync, canChangePreferences, indexDepartment);
	}

	@Override
	public void addEmployeeFromGui(String name, int indexRole, int indexDepartment, int begHour, String pref) throws Exception {
		company.addEmployee(name, indexRole, indexDepartment, begHour, pref);
	}

	@Override
	public void setedCompanyNameFromCompany(String name) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addedDepartmentFromCompany(String departmentName) {
		view.addDepartmentToGui(departmentName);
	}

	@Override
	public void addedRoleFromCompany(String roleName, int departmentIndex) {
		view.addRoleToGui(roleName, departmentIndex);
	}

	@Override
	public void addedEmployeeFromCompany(String name, Role role, Department department, int begHour, String pref) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendCompanyDetails(String toString) {
		view.addCompanyDetailsToGui(toString);
	}
	
}
