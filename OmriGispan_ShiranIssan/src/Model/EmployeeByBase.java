package Model;

public class EmployeeByBase extends Employee {

	public EmployeeByBase(String name, Role role, Department department, int begHour, String pref) throws Exception {
		super(name, role, department, begHour, pref);
		this.type = "By Base";
	}

}
