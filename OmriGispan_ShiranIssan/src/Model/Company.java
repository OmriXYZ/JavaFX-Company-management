package Model;

import java.util.ArrayList;

import listeners.ModelEventsListener;

public class Company {
	private String name;
	private ArrayList<Department> departments;
	private ArrayList<Employee> employees;
	private double totalEfficiency;
	
	//MVC
	private ArrayList<ModelEventsListener> listeners;
	
	public Company(String name) {
		this.name = name;
		this.departments = new ArrayList<>();
		this.employees = new ArrayList<>();
		totalEfficiency = 0;
		
		//MVC
		listeners = new ArrayList<ModelEventsListener>();
	}
	
	public void addDepartment(String name, boolean mustEmployeeSync, boolean canChangePreferences) {
		Department dep = new Department(name, mustEmployeeSync, canChangePreferences);
		departments.add(dep);
		fireAddedDepartment(name);
	}
	
	public void addRole(String name, boolean mustEmployeeSync, boolean canChangePreferences, int indexDepartment) {
		Role role = new Role(name, mustEmployeeSync, canChangePreferences);
		departments.get(indexDepartment).addRoleToDepartment(role);
		fireAddedRole(name, indexDepartment);
	}
	
	public void addEmployee(String name,int indexRole, int indexDepartment, int begHour, String pref) throws Exception {		
		Department dep = departments.get(indexDepartment); //pointer for specific department
		Role role = dep.getRole(indexRole); //pointer for specific role into department
		Employee emp = new Employee(name, role, dep, begHour, pref); //making employee
		employees.add(emp);
		role.addEmployeeToRole(emp); //add employee to role under department
		fireAddedEmployee(name, role, dep, begHour, pref);
	}

	public void setName(String name) {
		this.name = name;
		
	}
	
	public String toString() {
		String str = "List of Departments:\n\n";
		for (Department department : departments) {
			str += department.toString() + "\n";
		}
		return str;
	}


	public void calcTotalEfficiency() {
		for (int i = 0; i < departments.size(); i++) {
			totalEfficiency += departments.get(i).getTotalEfficiency();
		}
	}
	
	//----MVC----//
	private void fireAddedEmployee(String name,Role nameRole, Department nameDepartment, int begHour, String pref) {
		for (ModelEventsListener l : listeners) {
			l.addedEmployeeFromCompany(name, nameRole, nameDepartment, begHour, pref);
			l.sendCompanyDetails(toString());
		}
	}
	
	private void fireAddedDepartment(String departmentName) {
		for (ModelEventsListener l : listeners) {
			l.addedDepartmentFromCompany(departmentName);
			l.sendCompanyDetails(toString());
		}
	}
	
	private void fireAddedRole(String roleName, int departmentIndex) {
		for (ModelEventsListener l : listeners) {
			l.addedRoleFromCompany(roleName, departmentIndex);
			l.sendCompanyDetails(toString());
		}
	}
	
	public void registerListener(ModelEventsListener listener) {
		listeners.add(listener);
	}

	
	
	
}
