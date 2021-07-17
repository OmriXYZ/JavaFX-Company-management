package Model;

public class Employee implements Preferences {
	private int ID;
	private String name;
	private Role role;
	private Department department;
	private int begHour, endHour;
	private double efficiency;
	
	private enum Preferences {
		Earlier,
		Later,
		StayBasic,
		WorkHome,
	}
	private Preferences pref;
	
	public Employee(int iD, String name, Role role, Department department, int begHour, int endHour, String pref) {
		ID = iD;
		this.name = name;
		this.role = role;
		this.department = department;
		this.begHour = begHour;
		this.endHour = endHour;
		this.pref = Preferences.valueOf(pref);
	}
	
	@Override
	public void startWorkEarlier(int begHour) {
		int diff = this.begHour - begHour;
		this.begHour = begHour;
		this.endHour = endHour - diff;
	}
	@Override
	public void startWorkLater(int begHour) {
		int diff = begHour - this.begHour;
		this.begHour = begHour;
		this.endHour = endHour + diff;
	}
	@Override
	public void stayOnBasicWork() {
		//
	}
	@Override
	public void workInHome() {
		
	}
	
	public double getEfficiency() {
		return efficiency;
	}
	
//	public double calcEfficiency(boolean pd) {
//		
//	}
	
}
