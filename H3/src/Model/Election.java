package Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import listeners.ElectionEventsListener;

public class Election {
	//CREATED DATE
	private final int YEAR, MONTH;
	
	//arrays of citizens, parties, ballotboxes, and election results
	private Set<Citizen> voteCitizens;
	private ArrayList<PoliticalParty> politicalParties;
	private ArrayList<BallotBox> ballotBoxes;
	private ArrayList<Votes> results;
	
	private ArrayList<ElectionEventsListener> listeners;
	
	//count total votes
	private int countTotalVotes = 0;
	private int countVotes= 0;
	
	private boolean activated = false;

	public Election(int year, int month) throws Exception {
		this.YEAR = year;
		this.MONTH = month;
		ballotBoxes = new ArrayList<BallotBox>();
		voteCitizens = new Set<Citizen>();
		politicalParties = new ArrayList<PoliticalParty>();
		results = new ArrayList<Votes>();
		
		listeners = new ArrayList<ElectionEventsListener>();
		
		
	}

	public ArrayList<PoliticalParty> getPoliticalParties() {
		return politicalParties;
	}
	
	public Set<Citizen> getCitizens() {
		return voteCitizens;
	}
	public ArrayList<BallotBox> getBallotBoxes() {
		return ballotBoxes;
	}

	public Citizen getCitizen(int index) {
		return voteCitizens.get(index);
	}


	//TO STRING FOR PARTIES, BALLOT BOXES AND CITIZENS

	public String toStringCitizen() {
		String citizens = "";
		for (int i = 0; i < voteCitizens.size(); i++) {
			citizens += (i + 1) + ": \n" + voteCitizens.get(i) + "\n";
		}
		return citizens;
	}

	public String toStringBallotBox() {
		String ballotbox = "";
		for (int i = 0; i < ballotBoxes.size(); i++) {
			ballotbox += ballotBoxes.get(i) + "\n";
		}
		return ballotbox;
	}

	public String toStringPoliticalParties() {
		String strPoliticalParties = "";
		for (int i = 0; i < politicalParties.size(); i++) {
			strPoliticalParties += "\n" + (i+1) + ":" + politicalParties.get(i);
		}
		return strPoliticalParties;
	}

	//Check if exist citizen with the same id and return a boolean value
	public boolean checkExistsId(int id) throws Exception {
		if (checkLength(id) != 9) {
			throw new Exception("ID must be 9 digits \n" + "Please try again");
		} else {
			for (int i = 0; i < voteCitizens.size(); i++) {
				if (voteCitizens.get(i).getID() == id) {
					throw new Exception("ID: \"" + id + "\", " + "exists in system\n" + "Please try again");
				}
			}
		}

		return true;
	}
	
	public boolean checkExistsNameParty(String name) {
		for (int i = 0; i < getCountPoliticalParties(); i++) {
			if (politicalParties.get(i).getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	//direct citizen to ballot box randomally according the type
	public void randomCitizenToBallotBox() {
		BallotBox tmpBB;
		for (int i = 0; i < getCountCitizens(); i++) {
			if (YEAR - voteCitizens.get(i).getBirthYear() < 22 && voteCitizens.get(i).getInQuarantine()) { //corona military ballot box
				tmpBB = getRandomBallotBoxIndexByType("Corona_Military");
				voteCitizens.get(i).setBallotBox(tmpBB);
				tmpBB.addCitizen(voteCitizens.get(i));
			}
			else if (voteCitizens.get(i).getInQuarantine()) { //corona ballot box
				tmpBB = getRandomBallotBoxIndexByType("corona");
				voteCitizens.get(i).setBallotBox(tmpBB);
				tmpBB.addCitizen(voteCitizens.get(i));
			}
			else if (YEAR - voteCitizens.get(i).getBirthYear() < 22) { //army ballot box
				tmpBB = getRandomBallotBoxIndexByType("Military");
				voteCitizens.get(i).setBallotBox(tmpBB);
				tmpBB.addCitizen(voteCitizens.get(i));
			}
			else { //normal ballot box
				tmpBB = getRandomBallotBoxIndexByType("normal");
				voteCitizens.get(i).setBallotBox(tmpBB);
				tmpBB.addCitizen(voteCitizens.get(i));
			}

		}
	}
	
	//return random ballotbox by type
	public BallotBox getRandomBallotBoxIndexByType(String type) {
		Random r = new Random();
		int sizeForType = 0;
		for (int i = 0; i < getCountBallotBoxes(); i++) {
			if (ballotBoxes.get(i).getType().equalsIgnoreCase(type)) {
				sizeForType++;
			}
		}
		int[] randomNumbers = new int [sizeForType];
		int count = 0;
		for (int i = 0; i < getCountBallotBoxes(); i++) {
			if (ballotBoxes.get(i).getType().equalsIgnoreCase(type)) {
				randomNumbers[count] = ballotBoxes.get(i).getID();
				count++;
			}
		}
		int nextRandomNumberIndex = r.nextInt(randomNumbers.length);
		return ballotBoxes.get(randomNumbers[nextRandomNumberIndex]-1);
		
	}

	public int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	public void createListForVoting() {
		for (int i = 0; i < getCountBallotBoxes(); i++) {
			for (int j = 0; j < getCountPoliticalParties(); j++) {
				ballotBoxes.get(i).makeVotingList(politicalParties.get(j).getName());
			}
		}
		for (int j = 0; j < getCountPoliticalParties(); j++) {
			addVote(politicalParties.get(j).getName());
		}
	}

	public String results() {
		String strResults = "";
		for (int i = 0; i < getCountPoliticalParties(); i++) {
			strResults += "For Political Party " + results.get(i).getNameOfParty() + ": \n" + results.get(i).getVotes() + "\n";
		}
		return strResults;
	}
	
	public String electionStats() {
		String electionStats = "";
		for (int i = 0; i < getCountBallotBoxes(); i++) {
			electionStats += "From ballotBox " + (i+1) + ": \n" + getBallotBoxes().get(i).showRatioVotes(getCountTotalVotes()) + getBallotBoxes().get(i).showVotes() + "\n";
		}
		return electionStats;
	}

	public void vote(int i) {
		countVotes++;
		results.get(i).vote();
		countTotalVotes++;
	}

	public void sortByPrimaries() {
		for (int i = 0; i < getCountPoliticalParties(); i++) {
			politicalParties.get(i).randomCandidates();
		}
	}

	public void addBallotBox(String street, String type) {
		ballotBoxes.add(new BallotBox<>(street, type.toUpperCase()));
		fireAddBallotBoxEvent(ballotBoxes.get(getCountBallotBoxes()-1));
	}
	
	private void fireAddBallotBoxEvent(BallotBox ballotBox) {
		for (ElectionEventsListener l : listeners) {
			l.addedBallotBoxToElection(ballotBox);
		}
	}
	
	public void addVote(String politicalParty) {
		results.add(new Votes(politicalParty));
	}
	
	public void addPoliticalParty(String name, Date createdDate, String stream) {
		PoliticalParty party = new PoliticalParty(name, createdDate, stream.toUpperCase());
		politicalParties.add(party);
		fireAddPoliticalPartyEvent(party);
	}
	
	private void fireAddPoliticalPartyEvent(PoliticalParty party) {
		for (ElectionEventsListener l : listeners) {
			l.addedPoliticalParty(party.getName(), party.getCreatedDate(), party.getStreamPositions());
		}
	}

	
	public void addCitizen(int year, String name, int id) throws Exception {
		
		boolean ageValidation = (getYEAR() - year) >= 18; //Calculate age
		
		if (checkExistsId(id) && ageValidation) {
			if (YEAR - year < 22) { // create Soldier when citizen under 22
				Citizen tmpCitizen = new Soldier(year, name, id);
				voteCitizens.add(tmpCitizen);
				fireAddCitizenEvent(tmpCitizen);
			} else {
				Citizen tmpCitizen = new Citizen(year, name, id);
				voteCitizens.add(tmpCitizen);
				fireAddCitizenEvent(tmpCitizen);
			}
		} else {
			throw new Exception("You will not be able to vote this year");
		}

	}
	
	public void addSickCitizen(int year, String name, int id, boolean quarantine, int sickDays) throws Exception {
				
		boolean ageValidation = (getYEAR() - year) >= 18; //Calculate age
		
		if (checkExistsId(id) && ageValidation) {
			if (YEAR - year < 22) { // create Soldier when citizen under 22
				Citizen tmpCitizen = new Soldier(year, name, id, quarantine, sickDays);
				voteCitizens.add(tmpCitizen);
				fireAddSickCitizenEvent(tmpCitizen);
			} else {
				Citizen tmpCitizen = new Citizen(year, name, id, quarantine, sickDays);
				voteCitizens.add(tmpCitizen);
				fireAddSickCitizenEvent(tmpCitizen);
			}
		} else {
			throw new Exception("You will not be able to vote this year");
		}
	}
	
	public void addCandidate(int year, String name, int id, int idParty) throws Exception {
		boolean ageValidation = (getYEAR() - year) >= 18; //Calculate age
		
		if (checkExistsId(id) && ageValidation) {
			PoliticalParty pointerParty = politicalParties.get(idParty);
			Candidate tmpCandidate = new Candidate(year, name, id, politicalParties.get(idParty));
			voteCitizens.add(tmpCandidate);
			pointerParty.addCandidateToList((Candidate)voteCitizens.get(voteCitizens.size()-1));
			fireAddCandidateEvent(tmpCandidate);
		} else
			throw new Exception("You will not be able to vote this year");
	}
	
	public void addSickCandidate(int year, String name, int id, boolean quarantine, int sickDays, int idParty) throws Exception {
		boolean ageValidation = (getYEAR() - year) >= 18; // Calculate age
		
		if (checkExistsId(id) && ageValidation) {
			PoliticalParty pointerParty = politicalParties.get(idParty);
			Candidate tmpCandidate = new Candidate(year, name, id, true, sickDays, pointerParty);
			voteCitizens.add(tmpCandidate);
			pointerParty.addCandidateToList((Candidate) voteCitizens.get(voteCitizens.size() - 1));
			fireAddSickCandidateEvent(tmpCandidate);
		} else
			throw new Exception("You will not be able to vote this year");
	}
	
	private void fireAddCitizenEvent(Citizen citizen) {
		for (ElectionEventsListener l : listeners) {
			l.addedCitizenToElection(citizen.getBirthYear(), citizen.name, citizen.ID);
		}
	}
	
	private void fireAddSickCitizenEvent(Citizen citizen) {
		for (ElectionEventsListener l : listeners) {
			l.addedSickCitizenToElection(citizen.getBirthYear(), citizen.name, citizen.ID, citizen.inQuarantine, citizen.sickDays);
		}
	}
	
	private void fireAddCandidateEvent(Candidate candidate) {
		for (ElectionEventsListener l : listeners) {
			l.addedCandidateToElection(candidate.getBirthYear(), candidate.getName(), candidate.getID(), candidate.getPoliticalParty());
			l.updatePoliticalPartiesOnUi(politicalParties);
		}
	}
	
	private void fireAddSickCandidateEvent(Candidate candidate) {
		for (ElectionEventsListener l : listeners) {
			l.addedSickCandidateToElection(candidate.getBirthYear(), candidate.getName(), candidate.getID(), true, candidate.sickDays, candidate.getPoliticalParty());
			l.updatePoliticalPartiesOnUi(politicalParties);
		}
	}
	
	public void registerListener(ElectionEventsListener listener) {
		listeners.add(listener);
	}

	public int getYEAR() {
		return YEAR;
	}

	public int getMONTH() {
		return MONTH;
	}

	public int getCountBallotBoxes() {
		return ballotBoxes.size();
	}

	public int getCountPoliticalParties() {
		return politicalParties.size();
	}

	public int getCountCitizens() {
		return voteCitizens.size();
	}

	public int getCountVotes() {
		return countVotes;
	}
	
	public void activate() {
		activated = true;
	}
	
	public boolean getActivated() {
		return activated;
	}
	
	public int getCountTotalVotes() {
		return countTotalVotes;
	}
	
	
	public void saveData() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("citizens.dat"));
		outFile.writeObject(voteCitizens);
		outFile.close();
		outFile = new ObjectOutputStream(new FileOutputStream("politicalparties.dat"));
		outFile.writeObject(politicalParties);
		outFile.close();
		outFile = new ObjectOutputStream(new FileOutputStream("ballotboxes.dat"));
		outFile.writeObject(ballotBoxes);
		outFile.close();
		outFile = new ObjectOutputStream(new FileOutputStream("votes.dat"));
		outFile.writeObject(results);
		outFile.close();
		outFile = new ObjectOutputStream(new FileOutputStream("activated.dat"));
		outFile.writeObject(activated);
		outFile.close();
		fireDataSaved();
//		outFile = new ObjectOutputStream(new FileOutputStream("countTotalVotes.dat"));
//		outFile.writeObject(countTotalVotes);
//		outFile.close();
//		outFile = new ObjectOutputStream(new FileOutputStream("countVotes.dat"));
//		outFile.writeObject(countVotes);
//		outFile.close();
	}
	
	
	private void fireDataSaved() {
		for (ElectionEventsListener l : listeners) {
			l.dataSaved();
		}
	}

	@SuppressWarnings("unchecked")
	public void loadData() throws Exception {
		try {
			ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("citizens.dat"));
			voteCitizens = (Set<Citizen>)inFile.readObject();
			inFile = new ObjectInputStream(new FileInputStream("politicalparties.dat"));
			politicalParties = (ArrayList<PoliticalParty>)inFile.readObject();
			inFile = new ObjectInputStream(new FileInputStream("ballotboxes.dat"));
			ballotBoxes = (ArrayList<BallotBox>)inFile.readObject();
			inFile = new ObjectInputStream(new FileInputStream("votes.dat"));
			results = (ArrayList<Votes>)inFile.readObject();
			inFile = new ObjectInputStream(new FileInputStream("activated.dat"));
			activated = (boolean) inFile.readObject();
//			inFile = new ObjectInputStream(new FileInputStream("countTotalVotes.dat"));
//			countTotalVotes = (int) inFile.readObject();
//			inFile = new ObjectInputStream(new FileInputStream("countVotes.dat"));
//			countVotes = (int) inFile.readObject();
			fireDataLoaded();
			
		} catch (FileNotFoundException e) {
			throw new Exception("Can't find all data");
		}
		catch (ClassNotFoundException e) {
			throw new Exception("Can't find all data");
		}
				
	}
	
	private void fireDataLoaded() {
		for (ElectionEventsListener l : listeners) {
			l.dataLoaded(voteCitizens, politicalParties, ballotBoxes);
		}
	}
	
	public int checkLength(int num) { // check length of number
		int counter = 0;

		while (num != 0) {
			counter++;
			num /= 10;
		}

		return counter;
	}
}
