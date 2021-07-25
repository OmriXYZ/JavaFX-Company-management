package Model;

import java.io.Serializable;

public class Employee implements Preferences, Synchronizable, Serializable {
	protected String name;
	protected Role role;
	protected Department department;
	protected int begHour, endHour;
	protected int prefHour;
	protected double efficiency;
	protected int different;
	protected boolean mustEmployeeSync;
	protected boolean canChangePreferences;
	protected int salary;
	protected String type;
	protected int payPerHour;
	
	public static enum Preference {
		EARLIER,
		LATER,
		STAYBASIC,
		WORKHOME,
	}
	
	private Preference pref;
	
	public Employee(String name, Role role, Department department,int prefHour, int payPerHour, String pref) throws Exception {
		this.name = name;
		this.role = role;
		this.department = department;
		this.begHour = 8;
		this.endHour = 5;
		this.prefHour = prefHour;
		this.pref = Preference.valueOf(pref);
		this.efficiency = 0;
		this.different = 0;
		this.salary = 0;
		this.payPerHour = payPerHour;
		mustEmployeeSync = false;
		canChangePreferences = true;		
	}
	
	public void startWorkEarlier(int begHour) {
		different = this.begHour - begHour;
		this.begHour = begHour;
		this.endHour = endHour - different;
		efficiency = 0.2;
		System.out.println(different);
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
		efficiency = 0; //reset efficiency
		
		//Defining attribute
		boolean needToBeSync = department.getMustEmployeeSync();
		boolean canChangePrefrence = department.getCanChangePreferences();
		int finalBegHour=8, finalEndHour=17;

		if (needToBeSync) {
			mustEmployeeSync = true;
			finalBegHour = department.getSyncHour();
		} else {
			mustEmployeeSync = role.getMustEmployeeSync();
			if (mustEmployeeSync) {
				finalBegHour = role.getSyncHour();
			}
		}
		//set new beg end hours
		if (this.mustEmployeeSync) {
			setFinalHours(finalBegHour);
		}
		if (!canChangePrefrence) {
			this.canChangePreferences = false;
		} else {
			this.canChangePreferences = role.getCanChangePreferences();
		}

		int maxHour = Math.max(finalBegHour, 8);
		int minHour = Math.min(finalBegHour, 8);
		
		if (pref.toString().equals("WORKHOME")) { //WORK FROM HOME
			if (!this.canChangePreferences && this.mustEmployeeSync) {
				efficiency = (minHour - maxHour) * 0.2;
			} else if (!this.canChangePreferences) {
				efficiency = 0; //DELETE
			} else if (this.canChangePreferences) {
				efficiency = 0.1 * 8;
			}
				
		} else if (pref.toString().equals("EARLIER") || pref.toString().equals("LATER")) {
			int maxPHour = Math.max(this.prefHour, 8);
			int minPHour = Math.min(this.prefHour, 8);
			if (this.canChangePreferences && !this.mustEmployeeSync) {
				 setFinalHours(prefHour);
				 efficiency = (maxPHour - minPHour) * 0.2;
			} else if (this.canChangePreferences && this.mustEmployeeSync) {
				 int x = maxHour - minHour;
				 int y = maxPHour - minPHour;
				 efficiency = (Math.abs(x-y)) * 0.2;
			} else if (!this.canChangePreferences && this.mustEmployeeSync) {
				 int x = maxHour - minHour;
				 int y = maxPHour - minPHour;
				 efficiency = (Math.abs(x-y)) * -0.2;
			}

		}
//		else if (pref.toString().equals("STAYBASIC")) {
//			if (!this.mustEmployeeSync) {
//				efficiency = 0;
//			} else {
//				prefHour = 
//			}
//		}
	}
	
	private void setFinalHours(int finalBegHour) {
		begHour = finalBegHour;
		endHour = finalBegHour + 9;
	}

	public void changePreference(Preference pref, int newBegHour) throws Exception {
		if (canChangePreferences && pref.name() != "StayBasic") {
			switch (pref) {
			case EARLIER:
				startWorkEarlier(newBegHour);
				break;
			case LATER:
				startWorkLater(newBegHour);
				break;
			case STAYBASIC:
				stayOnBasicWork();
				break;
			case WORKHOME:
				workInHome();
				break;
			}
		}
	}

	@Override
	public void sync(boolean b, int syncHour, int endHour) {
		mustEmployeeSync = b;
	}

	@Override
	public void changePreference(boolean b) {
		canChangePreferences = b;
	}
	
	public String toString() {
		String str = "Employee: " + this.name + "\n";
		str += "Type of employee: " + this.type + "\n";
		return str;
	}
	
	public boolean equals(Object d) {
		if (this.name.equalsIgnoreCase(((Employee) d).name)) {
			return true;
		} else
			return false;
	}
	
	public void changePrefHours(int prefHour) {
		this.begHour = prefHour + 9;
	}
	
	public String getDepartmentName() {
		return department.getName();
	}
	
	public String getRoleName() {
		return role.getName();
	}
	

	
}
