package Model;

import java.util.ArrayList;

public class Department {
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
	
}
