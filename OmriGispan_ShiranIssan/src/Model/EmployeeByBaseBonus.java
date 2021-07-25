package Model;

public class EmployeeByBaseBonus extends EmployeeByBase {

	public EmployeeByBaseBonus(String name, Role role, Department department, int begHour, String pref) throws Exception {
		super(name, role, department, begHour, pref);
		this.type = "By Base Bonus";

	}

}
