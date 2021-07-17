package Model;


public class Candidate extends Citizen {
	private PoliticalParty politicalParty;
	private int votesPrimaries;

	public Candidate(int year, String name, int id, boolean quarantine, int sickDays, PoliticalParty politicalParty) {
		super(year, name, id, quarantine, sickDays);
		this.politicalParty = politicalParty;
		votesPrimaries = 0;
	}
	
	public Candidate(int year, String name, int id, PoliticalParty politicalParty) {
		super(year, name, id);
		this.politicalParty = politicalParty;
		votesPrimaries = 0;
	}

	@Override
	public String toString() {
		return super.toString()
				+ "Belong to " + politicalParty.getName() + " political party\n";
	}
	
	public void vote() {
		votesPrimaries++;
	}
	
	public int getVote() {
		return votesPrimaries;
	}
	
	public PoliticalParty getPoliticalParty() {
		return politicalParty;
	}
	
}
