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
		// TODO Auto-generated method stub
		
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
	public void addEmployeeFromGui(int iD, String name, int indexRole, int indexDepartment, int begHour, int endHour,
			String pref) {
		company.addEmployee(iD, name, indexRole, indexDepartment, begHour, endHour, pref);
	}

	@Override
	public void setedCompanyNameFromCompany(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addedDepartmentFromCompany(String name, boolean mustEmployeeSync, boolean canChangePreferences) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addedRoleFromCompany(Role role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addedEmployeeFromCompany(int iD, String name, String nameRole, String nameDepartment, int begHour,
			int endHour, String pref) {
		// TODO Auto-generated method stub
		
	}
	
}
