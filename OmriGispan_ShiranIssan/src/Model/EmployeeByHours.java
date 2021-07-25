package Model;

public class EmployeeByHours extends Employee {
	
	private int hoursMonth;

	public EmployeeByHours(String name, Role role, Department department, int begHour, String pref, int hoursMonth) throws Exception {
		super(name, role, department, begHour, pref);
		this.hoursMonth = hoursMonth;
		this.type = "By Hours";

	}

}
