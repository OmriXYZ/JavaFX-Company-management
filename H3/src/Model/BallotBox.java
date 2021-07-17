package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class BallotBox<T extends Citizen> implements Serializable {
	private final int ID;
	private String address;
	private ArrayList<Citizen> citizens;
	private float ratioVotesCitizen = 0;
	private ArrayList<Votes> votes;
	private int countTotalVotes = 0;

	public enum Type {
		NORMAL,
		CORONA,
		MILITARY,
		CORONA_MILITARY,
	}
	private Type type;
	private static int count = 1;
	
	public BallotBox(String address, String type) {
		ID = count;
		count++;
		this.address = address;
		this.type = Type.valueOf(type);
		this.citizens = new ArrayList<>();
		votes = new ArrayList<>();
	}

	public int getID() {
		return ID;
	}
	
	public String getStreet() {
		return address;
	}
	
	public String getType() {
		return type.name();
	}

	@Override
	public String toString() {
		String tmpCitizens = "No citizens on this ballot box";
		
		if (citizens.size()>0) {
			tmpCitizens = "Citizens on this ballot box: \n";
			for (int i = 0; i < citizens.size(); i++) {
				tmpCitizens += citizens.get(i).getName() + "\n";
			}
		}
		
		return "Ballot Box number: " + ID 
				+"\nAddress: " + address
				+"\nType: " + type.name()
				+ "\n" + tmpCitizens
				+"\n";
	}
	
	public void makeVotingList(String politicalParty) {
		addVote(politicalParty);
	}
	
	public void vote(int voteIndex) {
		votes.get(voteIndex).vote();
		countTotalVotes++;
	}
	
	public String showVotes() {
		String strVotes = "";
		for (int i = 0; i < votes.size(); i++) {
			strVotes += votes.get(i).toString() + "\n";
		}
		return strVotes;
	}
	
	public ArrayList<Votes> getVotes() {
		return votes;
	}
	
	public String showRatioVotes(int TotalVotesElection) {
		ratioVotesCitizen = ((float)countTotalVotes / (float)TotalVotesElection) * 100;
		return "The Ratio of voters to all voters is: " + ratioVotesCitizen + "%\n";
	}
	
	public void addCitizen(Citizen citizen) {
		citizens.add(citizen);
	}
	
	public void addVote(String politicalParty) {
		votes.add(new Votes(politicalParty));
	}
	
	public boolean equals(BallotBox b) {
		if (ID == b.ID) {
			return true;
		}
		return false;
	}


}
