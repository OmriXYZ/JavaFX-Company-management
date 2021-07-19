package Model;

public class Employee implements Preferences, Synchronizable {
	private String name;
	private Role role;
	private Department department;
	private int begHour, endHour;
	private double efficiency;
	private int different;
	private boolean mustEmployeeSync;
	private boolean canChangePreferences;
	private int salary;
	
	public static enum Preference {
		Earlier,
		Later,
		StayBasic,
		WorkHome,
	}
	
	private Preference pref;
	
	public Employee(String name, Role role, Department department,int begHour, String pref) throws Exception {
		this.name = name;
		this.role = role;
		this.department = department;
		this.begHour = 8;
		this.endHour = 5;
		this.pref = Preference.valueOf(pref);
		this.efficiency = 0;
		this.different = 0;
		this.salary = 0;
		changePreference(Preference.valueOf(pref), begHour);
	}
	
	public void startWorkEarlier(int begHour) {
		different = this.begHour - begHour;
		this.begHour = begHour;
		this.endHour = endHour - different;
		efficiency = 0.2;
	}

	public void startWorkLater(int begHour) {
		different = begHour - this.begHour;
		this.begHour = begHour;
		this.endHour = endHour + different;
		efficiency = 0.2;
	}

	public void stayOnBasicWork() {
		efficiency = 0;
	}

	public void workInHome() {
		efficiency = 0.1;
	}
	
	public double getEfficiency() {
		return efficiency;
	}
	
	public void calcEfficiency() {
		if (pref.name().equals("Earlier") && pref.name().equals("Later")) {
			if (mustEmployeeSync) {
				
			}
			efficiency = efficiency * different;
		} else if (pref.name().equals("WorkHome")) {
			efficiency = efficiency * 9;
		}
	}
	
	public void changePreference(Preference pref, int newBegHour) throws Exception {
		if (canChangePreferences && pref.name() != "StayBasic") {
			switch (pref) {
			case Earlier:
				startWorkEarlier(newBegHour);
				break;
			case Later:
				startWorkLater(newBegHour);
				break;
			case StayBasic:
				stayOnBasicWork();
				break;
			case WorkHome:
				workInHome();
				break;
			}
		} else
			throw new Exception("Department approve to stay on basic hours");
	}

	@Override
	public void sync(boolean b) {
		mustEmployeeSync = b;
	}

	@Override
	public void changePreference(boolean b) {
		canChangePreferences = b;
	}
	
	public String toString() {
		String str = "Employee: " + this.name + "\n";

		return str;
	}
	
}
