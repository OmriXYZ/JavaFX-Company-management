package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Department implements Synchronizable, Preferences, Serializable {
	private String name;
	private boolean mustEmployeeSync;
	private boolean canChangePreferences;
	private ArrayList<Role> roles;
	private double totalEfficiency;
	private int begHour;
	private int endHour;
	private final int BASEHOURS = 160;


	public Department(String name) {
		this.name = name;
		this.mustEmployeeSync = false;
		this.canChangePreferences = false;
		this.roles = new ArrayList<>();
		this.totalEfficiency = 0;
		this.begHour = 8;
		this.endHour = 17;
	}
	
	public void addRoleToDepartment(Role role) {
		roles.add(role);
	}

	public void calcTotalEfficiency () {
		totalEfficiency = 0;
//		if (!canChangePreferences) {
//			for (Role role : roles) {
//				role.changePreference(canChangePreferences);
//			}
//		}
//		if (mustEmployeeSync) {
//			for (Role role : roles) {
//				role.sync(mustEmployeeSync, begHour, endHour);
//			}
//		}
		for (Role role : roles) {
			role.calcTotalEfficiency();
		}
	}
	
	public double getTotalEfficiency() {
		totalEfficiency = 0;
		for (Role role : roles) {
			totalEfficiency += role.getTotalEfficiency();
		}
		return totalEfficiency;
	}
	
	public Role getRole(int indexRole) {
		return roles.get(indexRole);
	}
	
	public ArrayList<Role> getRoles() {
		return roles;
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
	public void sync(boolean b, int syncHour, int endHour) {
		this.mustEmployeeSync = b;
		this.begHour = syncHour;
		this.endHour = endHour;
//		for (Role role : roles) {
//			role.sync(b);
//		}
	}

	@Override
	public void changePreference(boolean b) {
		this.canChangePreferences = b;
//		for (Role role : roles) {
//			role.changePreference(b);
//		}
	}
	
	public int getSyncHour() {
		return begHour;
	}
	
	public String toString() {
		String str = "Department: " + this.name + "\n";
		if (mustEmployeeSync) {
			str += "Employees must be synchronized by hours\n";
			str += "Employees works at: " + begHour + " - " + endHour + "\n";
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
	
	public boolean equals(Object d) {
		if (this.name.equalsIgnoreCase(((Department) d).name)) {
			return true;
		} else
			return false;
	}
	
	public int getBASEHOURS() {
		return BASEHOURS;
	}

	
}
