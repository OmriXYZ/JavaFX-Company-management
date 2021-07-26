package Model;

public class EmployeeByBase extends Employee {
	
	public EmployeeByBase(String name, Role role, Department department, int begHour, int payPerHour, String pref) throws Exception {
		super(name, role, department, begHour, payPerHour, department.getBASEHOURS(), pref);
		this.type = "By Base";
	}
	
	public void calculateSalary() {
		salary = hoursMonth * payPerHour;
	}

}
