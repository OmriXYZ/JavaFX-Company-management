package Model;

import java.io.Serializable;

public class Employee implements Preferences, Synchronizable, Serializable {
	protected String name;
	protected Role role;
	protected Department department;
	protected int begHour, endHour;
	protected int prefHour;
	protected double efficiency;
	protected boolean mustEmployeeSync;
	protected boolean canChangePreferences;
	protected double salary;
	protected String type;
	protected int payPerHour;
	protected int hoursMonth;

	public static enum Preference {
		EARLIER,
		LATER,
		STAYBASIC,
		WORKHOME,
	}

	private Preference pref;

	public Employee(String name, Role role, Department department,int prefHour, int payPerHour, int hoursMonth, String pref) throws Exception {
		this.name = name;
		this.role = role;
		this.department = department;
		this.begHour = 8;
		this.endHour = 17;
		this.prefHour = prefHour;
		this.pref = Preference.valueOf(pref);
		this.efficiency = 0;
		this.salary = 0;
		if (payPerHour <= 0) {
			throw new Exception("Pay per hour - must be positive and bigger than zero");
		}
		this.payPerHour = payPerHour;
		if (hoursMonth <= 0) {
			throw new Exception("hours on month - must be positive and bigger than zero");
		}
		this.hoursMonth = hoursMonth;
		mustEmployeeSync = false;
		canChangePreferences = true;
		calculateSalary();
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

		if(!this.canChangePreferences) {
			this.pref = Preference.STAYBASIC;
		}

		int maxHour = Math.max(finalBegHour, 8);
		int minHour = Math.min(finalBegHour, 8);

		if (pref.toString().equals("WORKHOME")) { //WORK FROM HOME
			if (!this.canChangePreferences && this.mustEmployeeSync) {
				efficiency = (minHour - maxHour) * 0.2;
			} else if (!this.canChangePreferences) {
				efficiency = 0; //DELETE
			} else if (this.canChangePreferences && !this.mustEmployeeSync) {
				efficiency = 0.1 * 8;
				setFinalHours(prefHour);
			} else if (this.canChangePreferences && this.mustEmployeeSync) {
				efficiency = 0.1 * 8;
			}

		} else if (pref.toString().equals("EARLIER")) {
			if(this.mustEmployeeSync) {
				if (finalBegHour == 8) {
					efficiency = 0.0;
				} else {
					if (finalBegHour > 8) {
						efficiency = (8 - finalBegHour) * 0.2;
					} else {
						if(prefHour > finalBegHour) {
							efficiency = ((finalBegHour - prefHour) + (8 - prefHour)) * 0.2;
						} else {
							efficiency = (8 - finalBegHour) * 0.2;
						}
					}
				}
			}else {
				efficiency = (8 - prefHour) * 0.2;
			}

		} else if (pref.toString().equals("LATER")) {
			if(this.mustEmployeeSync) {
				if (finalBegHour == 8) {
					efficiency = 0.0;
				} else {
					if (finalBegHour > 8) {
						if(finalBegHour > prefHour) {
							efficiency = ((prefHour - finalBegHour) + (prefHour - 8)) * 0.2;
						} else {
							efficiency = (finalBegHour - 8) * 0.2;
						}
					} else {
						efficiency = (finalBegHour - 8) * 0.2;
					}
				}
			} else {
				efficiency = (prefHour - 8) * 0.2;
			}

		}

		else if (pref.toString().equals("STAYBASIC")) {
			if (this.mustEmployeeSync) {
				if(finalBegHour > 8)
					efficiency = (8 - finalBegHour) * 0.2;
				else
					efficiency = (finalBegHour - 8) * 0.2;
			}
		}
	}


	public void setFinalHours(int finalBegHour) {
		begHour = finalBegHour;
		endHour = (finalBegHour + 9);
	}

	@Override
	public void sync(boolean b, int syncHour, int endHour) {
		mustEmployeeSync = b;
	}

	@Override
	public void changePreference(boolean b) {
		canChangePreferences = b;
	}

	public void calculateSalary() {

	}

	public String toString() {
		String str = "Employee: " + this.name + "\n";
		str += "Type of employee: " + this.type + "\n";
		str += "Salary: " + this.salary + "\n";
		str += "Working hours: " + this.begHour + " - " + this.endHour + "\n";
		str += "Preference hours: " + this.prefHour + " - " + (this.prefHour+9) + "\n";
		str += "Efficiency: " + this.efficiency + "\n";
		if (canChangePreferences) {
			str += "can change preference\n";
		} else
			str += "cannot change preference\n";
		if (mustEmployeeSync) {
			str += "must be in sync\n";
		} else
			str += "no sync needed\n";
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
