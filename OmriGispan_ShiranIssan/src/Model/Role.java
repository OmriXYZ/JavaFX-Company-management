package Model;

import java.util.ArrayList;

public class Role {
	private String name;
	private ArrayList<Employee> employees;
	private boolean mustEmployeeSync;
	private boolean canChangePreferences;
	private int begHour, endHour;
	private double totalEfficiency;
	
	public Role(String name, boolean mustEmployeeSync, boolean canChangePreferences) {
		this.name = name;
		this.employees = new ArrayList<>();
		this.mustEmployeeSync = mustEmployeeSync;
		this.canChangePreferences = canChangePreferences;
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
		for (int i = 0; i < employees.size(); i++) {
			totalEfficiency += employees.get(i).getEfficiency();
		}
	}
	
	public double getTotalEfficiency() {
		return totalEfficiency;
	}
	
	
}
