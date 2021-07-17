package listeners;

import java.util.ArrayList;
import java.util.Date;

import Model.Citizen;
import Model.PoliticalParty;

public interface ElectionUIEventsListener {
	void addCitizenFromUi(int year, String name, int id);
	void addSickCitizenFromUi(int year, String name, int id, boolean quarantine, int sickDays);
	void addBallotBoxFromUi(String street, String type);
	void addPoliticalPartyFromUi(String name, Date createdDate, String stream);
	void addCandidateFromUi(int year, String name, int id, int idParty);
	void addSickCandidateFromUi(int year, String name, int id, boolean quarantine, int sickDays, int idParty);
	void startElectionRoundFromUi();
	void voteFromUi(int indexOfCitizen, int indexVoteParty);
	void endElectionRound();
	void saveDataFromUi();
	void loadDataFromUi();

	/*
	void addSickCandidate(int year, String name, int id, boolean quarantine, int sickDays, PoliticalParty party);
	
	
	void loadData();
	void saveData();
	void addPoliticalParty(String name, Date createdDate, String stream);
	void addBallotBox(String street, String type);
	*/
}
