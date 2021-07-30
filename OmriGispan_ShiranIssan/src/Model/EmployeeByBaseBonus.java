package Model;

public class EmployeeByBaseBonus extends EmployeeByBase {
	private int salesPerMonth;

	public EmployeeByBaseBonus(String name, Role role, Department department, int begHour, int payPerHour, String pref, int salesPerMonth) throws Exception {
		super(name, role, department, begHour, payPerHour, pref);
		this.type = "By Base Bonus";
		this.salesPerMonth = salesPerMonth;
		calculateSalary();
		System.out.println(salesPerMonth);
	}
	
	public void calculateSalary() {
		super.calculateSalary();
		salary += salesPerMonth * department.getPercentFromBonus();
	}

}
