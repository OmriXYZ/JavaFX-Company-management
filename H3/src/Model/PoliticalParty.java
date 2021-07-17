package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class PoliticalParty implements Serializable {
	private String name;
	private enum StreamPositions {
		LEFT,
		RIGHT,
		CENTER
	}
	private StreamPositions stream;
	private Date createdDate;
	private ArrayList<Candidate> candidates;
	
	public PoliticalParty(String name, Date createdDate, String stream) {
		this.name = name;
		this.createdDate = createdDate;
		this.stream = StreamPositions.valueOf(stream);
		this.candidates =  new ArrayList<Candidate>();
	}
	
	public String toString() {
		String tmpCandidatesMsg = "\nNo candidates in the list";
		
		if (getCountCandidates() > 0) {
			tmpCandidatesMsg = "\nList of Candidates:";
			for (int i = 0; i < getCountCandidates(); i++) {
				tmpCandidatesMsg +="\n" + (i+1) + ": " + candidates.get(i).getName();
			}
		}

		return "\nName of Political Party: " + name
				+"\nStream Position: " + stream.name()
				+ "\nCreated date: " + createdDate.getMonth() + "." + createdDate.getYear()
				+ tmpCandidatesMsg
				+"\n";
	}
	
	public String getName() {
		return name;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public String getStreamPositions() {
		return stream.name();
	}
	
	public void addCandidateToList(Candidate candidate) {
		candidates.add(candidate);
	}
	
	public ArrayList<Candidate> getCandidates() {
		return candidates;
	}
	
	public int getCountCandidates() {
		return candidates.size();
	}
	
	public void randomCandidates() {
	    Random rnd = new Random();

		for (int i = 0; i < getCountCandidates(); i++) {
			int index = rnd.nextInt(i + 1);
		      Candidate a = candidates.get(index);
		      candidates.set(index, candidates.get(i));
		      candidates.set(i, a);
		}
	}
	
	public boolean equals(PoliticalParty o) {
		if (name.equals(o.name)) {
			return true;
		}
		return false;
	}
	
}
