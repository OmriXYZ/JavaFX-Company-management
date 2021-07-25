package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Role implements Preferences, Synchronizable, Serializable {
	private String name;
	private ArrayList<Employee> employees;
	private boolean mustEmployeeSync;
	private boolean canChangePreferences;
	private int begHour, endHour;
	private double totalEfficiency;

	
	public Role(String name) {
		this.name = name;
		this.employees = new ArrayList<>();
		this.mustEmployeeSync = false;
		this.canChangePreferences = false;
		this.totalEfficiency = 0;
		this.begHour = 8;
		this.endHour = 17;
	}
	
	public void addEmployeeToRole(Employee emp) {
		employees.add(emp);
	}
	
	public String getName() {
		return name;
	}
	
	public void calcTotalEfficiency () {
		totalEfficiency = 0;
		for (Employee employee : employees) {
			employee.calcEfficiency();
		}
	}
	
	public double getTotalEfficiency() {
		totalEfficiency = 0;
		for (Employee employee : employees) {
			totalEfficiency += employee.getEfficiency();
		}

		return totalEfficiency;
	}

	@Override
	public void sync(boolean b, int syncHour, int endHour) {
		this.begHour = syncHour;
		this.endHour = endHour;
		this.mustEmployeeSync = b;
//		for (Employee employee : employees) {
//			employee.sync(b, syncHour, endHour);
//		}
	}

	@Override
	public void changePreference(boolean b) {
		this.canChangePreferences = b;
//		for (Employee employee : employees) {
//			employee.changePreference(b);
//		}		
	}
	
	public boolean getCanChangePreferences() {
		return canChangePreferences;
	}
	
	public boolean getMustEmployeeSync() {
		return mustEmployeeSync;
	}
	
	public String toString() {
		String str = "Role: " + this.name + "\n";
		if (mustEmployeeSync) {
			str += "Employees must be synchronized by hours\n";
			str += "Employees works at: " + begHour + " - " + endHour + "\n";
		} else
			str += "Employees don't need to be synchronized by hours\n";
		if (canChangePreferences) {
			str += "Employees can change their prefernces\n";
		} else
			str += "Employees can't change their prefernces\n";

		
		for (Employee employee : employees) {
			str += employee.toString();
		}
		return str;
	}
	
	public int getSyncHour() {
		return begHour;
	}
	
	public boolean equals(Object d) {
		if (this.name.equalsIgnoreCase(((Role) d).name)) {
			return true;
		} else
			return false;
	}
	
	
	
}
