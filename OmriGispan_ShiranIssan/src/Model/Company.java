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
		fireAddedDepartment(name, mustEmployeeSync, canChangePreferences);
	}
	
	public void addRole(String name, boolean mustEmployeeSync, boolean canChangePreferences, int indexDepartment) {
		Role role = new Role(name, mustEmployeeSync, canChangePreferences);
		departments.get(indexDepartment).addRoleToDepartment(role);
		fireAddedRole(role);
	}
	
	public void addEmployee(int ID, String name,int indexRole, int indexDepartment, int begHour, int endHour, String pref) {		
		Department dep = departments.get(indexDepartment); //pointer for specific department
		Role role = dep.getRole(indexRole); //pointer for specific role into department
		Employee emp = new Employee(ID, name, role, dep, begHour, endHour, pref); //making employee
		employees.add(emp);
		role.addEmployeeToRole(emp); //add employee to role under department
		fireAddedEmployee(ID, name, role.getName(), dep.getName(), begHour, endHour, pref);
	}



	public void calcTotalEfficiency() {
		for (int i = 0; i < departments.size(); i++) {
			totalEfficiency += departments.get(i).getTotalEfficiency();
		}
	}
	
	//----MVC----//
	private void fireAddedEmployee(int ID, String name,String nameRole, String nameDepartment, int begHour, int endHour, String pref) {
		for (ModelEventsListener l : listeners) {
			l.addedEmployeeFromCompany(ID, name, nameRole, nameDepartment, begHour, endHour, pref);
		}
	}
	
	private void fireAddedDepartment(String name, boolean mustEmployeeSync, boolean canChangePreferences) {
		for (ModelEventsListener l : listeners) {
			l.addedDepartmentFromCompany(name, mustEmployeeSync, canChangePreferences);
		}
	}
	
	private void fireAddedRole(Role role) {
		for (ModelEventsListener l : listeners) {
			l.addedRoleFromCompany(role);
		}
	}
	
	public void registerListener(ModelEventsListener listener) {
		listeners.add(listener);
	}

	public void setName(String name) {
		this.name = name;
		
	}
	
	
	
}
