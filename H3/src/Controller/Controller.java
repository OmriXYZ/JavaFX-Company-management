package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import Model.BallotBox;
import Model.Citizen;
import Model.Election;
import Model.PoliticalParty;
import Model.Set;
import View.AbstractElectionView;
import View.AbstractLoadingView;
import listeners.ElectionEventsListener;
import listeners.ElectionUIEventsListener;

public class Controller implements ElectionEventsListener, ElectionUIEventsListener {
	private Election electionModel;
	private AbstractElectionView electionView;
	
	public Controller(Election model, AbstractElectionView view) {
		electionModel = model;
		electionView = view;
		
		electionModel.registerListener(this);
		electionView.registerListener(this);
	}

	@Override
	public void addedCitizenToElection(int year, String name, int id) {
		electionView.addCitizenToUI(year, name, id);
	}
	
	public void addCitizenFromUi(int year, String name, int id) {
		try {
			electionModel.addCitizen(year, name, id);
		} catch (Exception e) {
			electionView.errorMessage(e.getMessage());
		}
		
	}

	@Override
	public void addedSickCitizenToElection(int year, String name, int id, boolean quarantine, int sickDays) {
		electionView.addSickCitizenToUI(year, name, id, quarantine, sickDays);
	}

	@Override
	public void addSickCitizenFromUi(int year, String name, int id, boolean quarantine, int sickDays) {
		try {
			electionModel.addSickCitizen(year, name, id, true, sickDays);
		} catch (Exception e) {
			electionView.errorMessage(e.getMessage());
		}		
	}

	@Override
	public void addBallotBoxFromUi(String street, String type) {
		electionModel.addBallotBox(street, type);
	}

	@Override
	public void addedBallotBoxToElection(BallotBox ballotBox) {
		electionView.addBallotBoxToUI(ballotBox);
	}

	@Override
	public void addPoliticalPartyFromUi(String name, Date createdDate, String stream) {
		electionModel.addPoliticalParty(name, createdDate, stream);
		
	}

	@Override
	public void addedPoliticalParty(String name, Date createdDate, String stream) {
		electionView.addPoliticalPartyToUI(name, createdDate, stream);
	}

	@Override
	public void addCandidateFromUi(int year, String name, int id, int idParty) {
		try {
			electionModel.addCandidate(year, name, id, idParty);
		} catch (Exception e) {
			electionView.errorMessage(e.getMessage());
		}
	}
	@Override
	public void addSickCandidateFromUi(int year, String name, int id, boolean quarantine, int sickDays,
			int idParty) {
		try {
			electionModel.addSickCandidate(year, name, id, quarantine, sickDays, idParty);
		} catch (Exception e) {
			electionView.errorMessage(e.getMessage());
		}
		
	}

	@Override
	public void addedCandidateToElection(int year, String name, int id, PoliticalParty party) {
		electionView.addCandidateToUI(year, name, id, party);
	}

	@Override
	public void updatePoliticalPartiesOnUi(ArrayList<PoliticalParty> party) {
		electionView.updatePoliticalPartiesOnUi(party);
	}

	@Override
	public void startElectionRoundFromUi() {
		System.out.println("Starting election round");
		electionModel.activate();
		electionModel.randomCitizenToBallotBox();
		electionModel.createListForVoting();
		electionModel.sortByPrimaries();
	}

	@Override
	public void voteFromUi(int indexOfCitizen, int indexVoteParty) {
		electionModel.getCitizen(indexOfCitizen).vote(indexVoteParty);
		electionModel.vote(indexVoteParty);
	}

	@Override
	public void endElectionRound() {
		electionView.updateElectionResults(electionModel.results(), electionModel.electionStats());	
	}

	@Override
	public void addedSickCandidateToElection(int year, String name, int id, boolean quarantine, int sickDays,
			PoliticalParty party) {
		electionView.addSickCandidateToUI(year, name, id, true, sickDays, party);
	}

	@Override
	public void saveDataFromUi() {
		try {
			electionModel.saveData();
		} catch (ClassNotFoundException | IOException e) {
			electionView.errorMessage(e.getMessage());
		}
		
	}

	@Override
	public void dataSaved() {
		electionView.errorMessage("Data saved");
		
	}

	@Override
	public void loadDataFromUi() {
		try {
			electionModel.loadData();
		} catch (Exception e) {
			electionView.errorMessage(e.getMessage());
		}
	}

	@Override
	public void dataLoaded(Set<Citizen> citizensLoaded, ArrayList<PoliticalParty> partiesLoaded, ArrayList<BallotBox> ballotBoxesLoaded) {
		electionView.loadData(citizensLoaded, partiesLoaded, ballotBoxesLoaded);
		electionView.errorMessage("Data loaded");
	}



	


	
	
}
