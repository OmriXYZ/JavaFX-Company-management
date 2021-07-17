package listeners;

import java.util.ArrayList;
import java.util.Date;

import Model.BallotBox;
import Model.Citizen;
import Model.PoliticalParty;
import Model.Set;

public interface ElectionEventsListener {
	void addedCitizenToElection(int year, String name, int id);
	void addedSickCitizenToElection(int year, String name, int id, boolean quarantine, int sickDays);
	void addedBallotBoxToElection(BallotBox ballotBox);
	void addedPoliticalParty(String name, Date createdDate, String stream);
	void addedCandidateToElection(int year, String name, int id, PoliticalParty party);
	void addedSickCandidateToElection(int year, String name, int id, boolean quarantine, int sickDays, PoliticalParty party);
	void dataSaved();
	void dataLoaded(Set<Citizen> citizensLoaded, ArrayList<PoliticalParty> partiesLoaded, ArrayList<BallotBox> ballotBoxesLoaded);
	void updatePoliticalPartiesOnUi(ArrayList<PoliticalParty> party);

	/*
	void addSickCandidate(int year, String name, int id, boolean quarantine, int sickDays, PoliticalParty party);
	
	
	void loadData();
	void saveData();
	void addPoliticalParty(String name, Date createdDate, String stream);
	void addBallotBox(String street, String type);
	*/
}
