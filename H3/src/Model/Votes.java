package Model;

import java.io.Serializable;

public class Votes implements Serializable {
	private String nameOfParty;
	private int votes = 0;
	
	public Votes(String politicalParty) {
		nameOfParty = politicalParty;
	}
	
	public void vote() {
		votes++;
	}
	
	public int getVotes() {
		return votes;
	}
	
	public String getNameOfParty() {
		return nameOfParty;
	}

	@Override
	public String toString() {
		return nameOfParty + ": " + votes;
	}
	
	
	
}
