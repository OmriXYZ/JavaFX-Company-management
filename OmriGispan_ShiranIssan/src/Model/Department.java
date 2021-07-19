package Model;

import java.util.ArrayList;
import java.util.Iterator;

public class Department implements Synchronizable, Preferences {
	private String name;
	private boolean mustEmployeeSync;
	private boolean canChangePreferences;
	private ArrayList<Role> roles;
	private double totalEfficiency;
	
	public Department(String name, boolean mustEmployeeSync, boolean canChangePreferences) {
		this.name = name;
		this.mustEmployeeSync = mustEmployeeSync;
		this.canChangePreferences = canChangePreferences;
		this.roles = new ArrayList<>();
		this.totalEfficiency = 0;
	}
	
	public void addRoleToDepartment(Role role) {
		roles.add(role);
	}

	public void calcTotalEfficiency () {
		for (int i = 0; i < roles.size(); i++) {
			totalEfficiency += roles.get(i).getTotalEfficiency();
		}
	}
	
	public double getTotalEfficiency() {
		return totalEfficiency;
	}
	
	public Role getRole(int indexRole) {
		return roles.get(indexRole);
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getCanChangePreferences() {
		return canChangePreferences;
	}
	
	public boolean getMustEmployeeSync() {
		return mustEmployeeSync;
	}

	@Override
	public void sync(boolean b) {
		for (Role role : roles) {
			role.sync(b);
		}
	}

	@Override
	public void changePreference(boolean b) {
		for (Role role : roles) {
			role.changePreference(b);
		}		
	}
	
	public String toString() {
		String str = "Department: " + this.name + "\n";
		if (mustEmployeeSync) {
			str += "Employees must be synchronized by hours\n";
		} else
			str += "Employees don't need to be synchronized by hours\n";
		if (canChangePreferences) {
			str += "Employees can change their prefernces\n";
		} else
			str += "Employees can't change their prefernces\n";
		str += "List of Roles:\n";
		for (Role role : roles) {
			str += role.toString();
		}
		return str;
	}

	
}
