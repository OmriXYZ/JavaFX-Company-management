package Model;

import java.io.Serializable;

public class Citizen implements Serializable {
	protected final int ID;
	protected String name;
	protected final int birthYear;
	protected boolean inQuarantine;
	protected BallotBox nearBallotBox;
	protected int sickDays;
	
	public Citizen(int year, String name, int id) {
		this.ID = id;
		this.name = name;
		this.birthYear = year;
		this.inQuarantine = false;
		this.sickDays = 0;
	}
	
	public Citizen(int year, String name, int id, boolean quarantine, int sickDays) {
		this.ID = id;
		this.name = name;
		this.birthYear = year;
		this.inQuarantine = quarantine;
		this.sickDays = sickDays;
	}

	@Override
	public String toString() {
		String tmpCoronaMSG = "";
		if (inQuarantine) {
			tmpCoronaMSG = "\n" + this.name + " in Quarantine" + "\n" + name + " has been sick for " + this.sickDays + " days.";
		}
		String tmpMsgForBallotBox = "The citizen does not belong to any ballot box yet";
		if (nearBallotBox != null)
			tmpMsgForBallotBox = "Belongs to the ballot box number: " + nearBallotBox.getID() + ", address: " + nearBallotBox.getStreet();
			
		return "ID: " + ID 
				+"\nName of Citizen: " + name
				+ "\nBirth of Year: " + birthYear
				+ "\n" +tmpMsgForBallotBox
				+ tmpCoronaMSG
				+"\n";
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}
	
	public boolean getInQuarantine() {
		return inQuarantine;
	}
	
	public int getBirthYear() {
		return birthYear;
	}
	
	public void setBallotBox(BallotBox ballotbox) {
		nearBallotBox = ballotbox;
	}
	
	public void vote(int index) {
		nearBallotBox.vote(index);
	}
	
	public void setSickDays(int days) {
		sickDays = days;
	}
	
	public boolean equals(Citizen c) {
		if (ID == c.getID()) {
			return true;
		}
		else {
			return false;
		}
	}
	

}
