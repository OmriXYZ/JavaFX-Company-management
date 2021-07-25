package Controller;

import java.util.ArrayList;

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
	public void addDepartmentFromGui(String name) {
		try {
			company.addDepartment(name);
		} catch (Exception e) {
			view.dialog(e.getMessage());
		}
	}

	@Override
	public void addRoleFromGui(String name, int indexDepartment) {
		try {
			company.addRole(name, indexDepartment);
		} catch (Exception e) {
			view.dialog(e.getMessage());
		}
	}

	@Override
	public void addEmployeeFromGui(String name, int indexRole, int indexDepartment, int begHour, String pref, String typeOfWorker, int hoursMonth) throws Exception {
		company.addEmployee(name, indexRole, indexDepartment, begHour, pref, typeOfWorker, hoursMonth);
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
	public void addedEmployeeFromCompany(String name) {
		view.addEmployeeToGui(name);
	}

	@Override
	public void sendCompanyDetails(String toString) {
		view.addCompanyDetailsToGui(toString);
	}

	
	@Override
	public void setPrefDepartmentFromGui(int indexDepartment, boolean b) {
		company.setPrefDepartmentFromGui(indexDepartment, b);
	}

	@Override
	public void setSyncDepartmentFromGui(int indexDepartment, boolean b, int syncHour, int endHour) {
		company.setSyncDepartmentFromGui(indexDepartment, b, syncHour, endHour);
		
	}

	@Override
	public void setPrefRoleFromGui(int indexDepartment, int indexRole, boolean b) {
		company.setPrefRoleFromGui(indexDepartment, indexRole, b);
	}

	@Override
	public void setSyncRoleFromGui(int indexDepartment, int indexRole, boolean b, int syncHour, int endHour) {
		company.setSyncRoleFromGui(indexDepartment, indexRole, b, syncHour, endHour);
		
	}

	@Override
	public boolean getPrefDepartmentFromGui(int indexDepartment) {
		return company.getPrefDepartmentFromGui(indexDepartment);
	}

	@Override
	public boolean getSyncDepartmentFromGui(int indexDepartment) {
		return company.getSyncDepartmentFromGui(indexDepartment);
	}

	@Override
	public boolean getPrefRoleFromGui(int indexDepartment, int indexRole) {
		return company.getPrefRoleFromGui(indexDepartment, indexRole);
	}

	@Override
	public boolean getSyncRoleFromGui(int indexDepartment, int indexRole) {
		return company.getSyncRoleFromGui(indexDepartment, indexRole);
	}

	@Override
	public void calculateEfficiencyFromGui() {
		company.calcTotalEfficiency();
	}

	@Override
	public void sendTotalEfficiency(Double totalEfficiency) {
		view.sendTotalEfficiencyToGui(totalEfficiency);
	}

	@Override
	public void sendDepartmentsEfficiency(ArrayList<Double> departmentsEfficiency) {
		view.sendDepartmentsEfficiencyToGui(departmentsEfficiency);
	}

	@Override
	public void sendEmployeesEfficiency(ArrayList<Double> employeesEfficiency) {
		view.sendEmployeesEfficiencyToGui(employeesEfficiency);
	}

	@Override
	public void saveDataFromUi() {
		try {
			company.saveData();
		} catch (Exception e) {
			view.dialog(e.getMessage());
		}
		
	}

	@Override
	public void sendDataMsg(String dataMsg) {
		view.dialog(dataMsg);
	}

	@Override
	public void loadDataFromUi() {
		try {
			company.loadData();
		} catch (Exception e) {
			view.dialog(e.getMessage());
		}
		
	}
	
}
