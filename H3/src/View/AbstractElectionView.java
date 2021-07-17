package View;

import java.util.ArrayList;
import java.util.Date;

import Model.BallotBox;
import Model.Citizen;
import Model.PoliticalParty;
import Model.Set;
import listeners.ElectionUIEventsListener;

public interface AbstractElectionView {
	void registerListener(ElectionUIEventsListener listener);
	void addCitizenToUI(int year, String name, int id);
	void addBallotBoxToUI(BallotBox ballotBox);
	void addPoliticalPartyToUI(String name, Date createdDate, String stream);
	void addSickCitizenToUI(int year, String name, int id, boolean quarantine, int sickDays);
	void addCandidateToUI(int year, String name, int id, PoliticalParty party);
	void addSickCandidateToUI(int year, String name, int id, boolean quarantine, int sickDays, PoliticalParty party);
	void updatePoliticalPartiesOnUi(ArrayList<PoliticalParty> party);
	void updateElectionResults(String str1, String str2);
	void loadData(Set<Citizen> citizensLoaded, ArrayList<PoliticalParty> partiesLoaded, ArrayList<BallotBox> ballotBoxesLoaded);
//	void addSickCandidate(int year, String name, int id, boolean quarantine, int sickDays, PoliticalParty party);
//	
//	void addPoliticalParty(String name, Date createdDate, String stream);
//	void addBallotBox(String street, String type);
	void errorMessage(String msg);
}
