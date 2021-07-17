package Model;

public class Soldier extends Citizen {

	public Soldier(int year, String name, int id) {
		super(year, name, id);
	}
	
	public Soldier(int year, String name, int id, boolean quarantine, int sickDays) {
		super(year, name, id, quarantine, sickDays);
	}
	
	public String carryWeapon() {
		return "The soldier carries a weapon";
	}
	
	public String toString() {
		return super.toString() + carryWeapon();
	}

}
