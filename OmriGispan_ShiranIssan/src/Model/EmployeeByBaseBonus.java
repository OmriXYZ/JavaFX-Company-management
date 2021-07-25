package Model;

public class EmployeeByBaseBonus extends EmployeeByBase {

	public EmployeeByBaseBonus(String name, Role role, Department department, int begHour, int payPerHour, String pref) throws Exception {
		super(name, role, department, begHour, payPerHour, pref);
		this.type = "By Base Bonus";
	}
	
	public void calculateSalary() {
		salary = department.getBASEHOURS() * payPerHour;
	}

}
