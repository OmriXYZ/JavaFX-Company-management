package Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import listeners.ModelEventsListener;

public class Company implements Serializable {
	private String name;
	private ArrayList<Department> departments; 	//All departments in the company
	private ArrayList<Employee> employees; 		//All employees in the company
	private double totalEfficiency; 			//Value of all department efficiencies
	
	//MVC
	private ArrayList<ModelEventsListener> listeners;
	
	public Company(String name) {
		this.name = name;
		this.departments = new ArrayList<Department>();
		this.employees = new ArrayList<Employee>();
		totalEfficiency = 0;
		
		//MVC
		listeners = new ArrayList<ModelEventsListener>();
	}
	
	public void addDepartment(String name) throws Exception { //Create department, cannot create department if exist with the same name
		Department newDep = new Department(name);
		if (checkIfExist(departments, newDep)) {
			throw new Exception("Company have a department with the same name, please enter another name");
		} else {
			departments.add(newDep);
			fireAddedDepartment(name);
		}
	}
	
	public void addRole(String name, int indexDepartment) throws Exception { //Create role, cannot create role if exist with the same name
		Role newRole = new Role(name);
		if (checkIfExist(departments.get(indexDepartment).getRoles(), newRole)) {
			throw new Exception("Department have a role with the same name, please enter another name");
		} else {
			departments.get(indexDepartment).addRoleToDepartment(newRole);
			fireAddedRole(name, indexDepartment);
		}
	}
	
	public void addEmployee(String name,int indexRole, int indexDepartment, int begHour, String pref, String typeOfWorker, int hoursMonth, int payPerHour) throws Exception { //Create employee by role,department indexes
		Department dep = departments.get(indexDepartment); //pointer for specific department
		Role role = dep.getRole(indexRole); //pointer for specific role into department
		Employee emp;

			if (typeOfWorker == "HOUR") {
				emp = new EmployeeByHours(name, role, dep, begHour, pref, payPerHour, hoursMonth); //Making employee by Hours
			} else if (typeOfWorker == "BASE") {
				emp = new EmployeeByBase(name, role, dep, begHour, payPerHour, pref); //Making employee by Base
			} else //BASE BONUS
				emp = new EmployeeByBaseBonus(name, role, dep, begHour, payPerHour, pref);
		
		employees.add(emp);
		role.addEmployeeToRole(emp); //Add employee to role under department
		fireAddedEmployee(name);
	}

	public void setName(String name) { //Set company name
		this.name = name;
		
	}
	
	public String toString() { //toString, get strings from departments roles and employees
		String str = "List of Departments:\n\n";
		for (Department department : departments) {
			str += department.toString() + "\n";
		}
		return str;
	}

	public void calcTotalEfficiency() { //Calculate efficiency (departments and employees)
		totalEfficiency = 0;
		for (int i = 0; i < departments.size(); i++) {
			departments.get(i).calcTotalEfficiency();
		}
		fireTotalEfficiency();
	}

	public void setPrefDepartmentFromGui(int indexDepartment, boolean b) { //Set department "can change preference" value
		departments.get(indexDepartment).changePreference(b);
		for (ModelEventsListener l : listeners) {
			l.sendCompanyDetails(toString());
		}
	}
	
	public void setSyncDepartmentFromGui(int indexDepartment, boolean b, int syncHour, int endHour) { //Set department "must be in sync" value
		departments.get(indexDepartment).sync(b, syncHour, endHour);
		for (ModelEventsListener l : listeners) {
			l.sendCompanyDetails(toString());
		}
	}
	
	public void setPrefRoleFromGui(int indexDepartment, int indexRole, boolean b) throws Exception { //Set role "can change preference" value
		boolean cannotChangePreference = !departments.get(indexDepartment).getCanChangePreferences();
		if (cannotChangePreference) {
			throw new Exception("The choice does not matter because department force all roles not to allow employees to change their preference");
		} else {
			departments.get(indexDepartment).getRole(indexRole).changePreference(b);
			for (ModelEventsListener l : listeners) {
				l.sendCompanyDetails(toString());
			}
		}
		
	}
	
	public void setSyncRoleFromGui(int indexDepartment, int indexRole, boolean b, int syncHour, int endHour) throws Exception { //Set role "must be in sync" value
		boolean syncEmployees = departments.get(indexDepartment).getMustEmployeeSync();
		if (syncEmployees) {
			throw new Exception("The choice does not matter because department force all roles to be synchronized");
		} else {
			departments.get(indexDepartment).getRole(indexRole).sync(b, syncHour, endHour);
			for (ModelEventsListener l : listeners) {
				l.sendCompanyDetails(toString());
			}
		}


	}
	
	public boolean getPrefDepartmentFromGui(int indexDepartment) {
		return departments.get(indexDepartment).getCanChangePreferences();
	}
	
	public boolean getSyncDepartmentFromGui(int indexDepartment) {
		return departments.get(indexDepartment).getMustEmployeeSync();
	}
	
	public boolean getPrefRoleFromGui(int indexDepartment, int indexRole) {
		return departments.get(indexDepartment).getRole(indexRole).getCanChangePreferences();
	}
	
	public boolean getSyncRoleFromGui(int indexDepartment, int indexRole) {
		return departments.get(indexDepartment).getRole(indexRole).getMustEmployeeSync();
	}
	
	public boolean checkIfExist(ArrayList<?> list, Object object) {
		boolean exist = false;
		for (Object l : list) {
			if (l.equals(object)) {
				return true;
			}
		}
		return exist;
	}
	
	//----MVC----//Fire Methods to (Controller -> Gui)
	private void fireAddedEmployee(String name) {
		for (ModelEventsListener l : listeners) {
			l.addedEmployeeFromCompany(name);
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
	
	private void fireTotalEfficiency() {
		for (Department department : departments) {
			totalEfficiency += department.getTotalEfficiency();
		}
		fireDepartmentsEfficiency();
		fireEmployeesEfficiency();
		for (ModelEventsListener l : listeners) {
			l.sendTotalEfficiency(totalEfficiency);
		}
	}
	
	private void fireEmployeesEfficiency() {
		ArrayList<Double> employeesEfficiency = new ArrayList<Double>();
		for (Employee employee : employees) {
			employeesEfficiency.add(employee.getEfficiency());
		}
		for (ModelEventsListener l : listeners) {
			l.sendEmployeesEfficiency(employeesEfficiency);
		}
	}

	private void fireDepartmentsEfficiency() {
		ArrayList<Double> departmentsEfficiency = new ArrayList<Double>();
		for (Department department : departments) {
			departmentsEfficiency.add(department.getTotalEfficiency());
		}
		for (ModelEventsListener l : listeners) {
			l.sendDepartmentsEfficiency(departmentsEfficiency);
		}
	}

	public void registerListener(ModelEventsListener listener) {
		listeners.add(listener);
	}
	
	public void saveData() throws FileNotFoundException, IOException { //Save data to binary files
		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("nameOfCompany.dat"));
		outFile.writeObject(name);
		outFile.close();
		outFile = new ObjectOutputStream(new FileOutputStream("departments.dat"));
		outFile.writeObject(departments);
		outFile.close();
		outFile = new ObjectOutputStream(new FileOutputStream("employees.dat"));
		outFile.writeObject(employees);
		outFile.close();
		outFile = new ObjectOutputStream(new FileOutputStream("totalEfficiency.dat"));
		outFile.writeObject(totalEfficiency);
		outFile.close();
		fireDataMsg("Saved");
	}
	
	@SuppressWarnings("unchecked")
	public void loadData() throws Exception { //Load data from binary files, after add it to Gui through Controller
		try {
			ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("nameOfCompany.dat"));
			name = (String)inFile.readObject();
			inFile = new ObjectInputStream(new FileInputStream("departments.dat"));
			departments = (ArrayList<Department>)inFile.readObject();
			inFile = new ObjectInputStream(new FileInputStream("employees.dat"));
			employees = (ArrayList<Employee>)inFile.readObject();
			inFile = new ObjectInputStream(new FileInputStream("totalEfficiency.dat"));
			totalEfficiency = (Double)inFile.readObject();
			fireDataMsg("Loaded");
			
			//Send data to view
			
			for (Department department : departments) { // SEND DEPARTMENTS
				fireAddedDepartment(department.getName());
			}
			for (Employee employee : employees) { // SEND EMPLOYEES
				fireAddedEmployee(employee.name);
			}
			for (int i = 0; i < departments.size(); i++) { //SEND ROLES
				int sizeRoles = departments.get(i).getRoles().size();
				for (int j = 0; j < sizeRoles; j++) {
					String nameRole = departments.get(i).getRole(j).getName();
					fireAddedRole(nameRole, i);
				}
			}
			fireTotalEfficiency(); // SEND TOTAL EFFICIENCY
			//////////END SENDING DATA/////////
		} catch (FileNotFoundException e) {
			throw new Exception("Can't find all data");
		}
		catch (ClassNotFoundException e) {
			throw new Exception("Can't find all data");
		}
				
	}

	private void fireDataMsg(String type) { //Send "save or load" message to Gui through Controller
		String dataMsg = type +": \n";
		dataMsg += "Departments: " + departments.size() + "\n";
		dataMsg += "Employees: " + employees.size() + "\n";
		for (ModelEventsListener l : listeners) {
			l.sendDataMsg(dataMsg);
		}
		
	}
	
}
