package Model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AnonymousCountry {
	
	private static Messageable ui = new ConsoleUi();
	
	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException, Exception {
		LocalDate date = LocalDate.now();
		Election election = new Election(date.getYear(), date.getMonthValue());
		
		//HARDCODED//
		//create 5 citizen
		addSickCitizen(1998, "Michelle", 212222345, true, 5, election);
		addCitizen(1928, "Yonatan", 123498765, election);
		addCitizen(1975, "Sonic", 318324222, election);
		addCitizen(1948, "David-ben-Gurion", 100000000, election);
		addSickCitizen(2002, "Moshe", 123123123, true, 6, election);
		//create 3 political parties
		addPoliticalParty("likud", 1992, 5, "right", election); // 1
		addPoliticalParty("meretz", 1994, 2, "left", election); // 2
		addPoliticalParty("kahol-lavan", 2018, 8, "center", election); // 3
		//create 6 candidates for 3 political parties
		addCandidate(1960, "bibi", 122333444, 1,  election);
		addSickCandidate(1953, "regev", 122313444, true, 3, 1,  election);
		addCandidate(1995, "hod", 397777123, 2,  election);
		addSickCandidate(1920, "shachar", 422373695, true, 4, 2,  election);
		addCandidate(1910, "sapir", 318222111, 3,  election);
		addCandidate(1938, "ben", 333999111, 3,  election);
		//create 5 ballot box (normal, corona, military, corona_military)
		addBallotBox("regavim, LOD", "normal", election);
		addBallotBox("hashmonaim, TEL-AVIV", "corona", election);
		addBallotBox("habsor, RAMLE", "military", election);
		addBallotBox("hadagim 3, EILAT", "normal", election);
		addBallotBox("shderot tzahal 48, JERUSALEM", "corona_military", election);
		
		loadData(election);
		
		int userSelect;
		do {
			ui.showMsg(
					"\nPlease select an option:\n"
							+ "1 - Add Ballot Box\n"
							+ "2 - Add a citizen to voter list\n"
							+ "3 - Add Political Party\n"
							+ "4 - Add a candidates\n"
							+ "5 - Print Ballot boxes\n"
							+ "6 - Print the citizens who can vote\n"
							+ "7 - Print Political Parties\n"
							+ "8 - Start election round\n"
							+ "9 - Print election results\n"
							+ "10 - Exit and save the data\n" );
			userSelect = numOnRange(10);

			switch (userSelect) {

			default:
				ui.showMsg("please select digit between 1 to 9");
				break;

			case 1: //Add Ballot Box
				String tmpStreetName = ui.getMsg("Enter street name of ballot box: ");
				String tmpType = ui.getMsg("Enter the type of ballot box from this list: \nNormal, Military, Corona, Corona_Military");
				
				while (!(tmpType.equalsIgnoreCase("Corona")) && !(tmpType.equalsIgnoreCase("Normal")) && !(tmpType.equalsIgnoreCase("Military")) && !(tmpType.equalsIgnoreCase("Corona_Military"))) {
					tmpType = ui.getErrMsg("Wrong choise, try to enter again..");
				}
				
				addBallotBox(tmpStreetName, tmpType, election);
				ui.showMsg("Ballot box number " + election.getCountBallotBoxes() + " created");

				break;
				
			case 2: //Add a citizen to voter list
				ui.showMsg("Enter year of birth: ");
				int tmpYearBirth = validateNumber();
				int tmpAge = election.getYEAR() - tmpYearBirth; //Calculate age
				if ((tmpAge >= 18)) { //Check that Citizen can only vote over the age of 18
					String tmpName = ui.getMsg("Enter name of citizen: ");
					
					ui.showMsg("Enter ID of citizen (must be 9 digits): ");
					int tmpID = checkIdValidation(election);
					
					try {
						if (question("Are you in quarantine?").equalsIgnoreCase("yes")) {
							ui.showMsg("How many days are you sick?");
							int tmpSickDays = numOnRange(2147483647);
							addSickCitizen(tmpYearBirth, tmpName, tmpID, true, tmpSickDays, election);
						}
						else 
							addCitizen(tmpYearBirth, tmpName, tmpID, election);
						ui.showMsg("Citizen number " + election.getCountCitizens() + " created");
					} catch (Exception e) {
						ui.showErrMsg(e.getMessage());
					}
				}
				else {
					ui.showErrMsg("You will not be able to vote this year");
				}
				
				break;
				
			case 3: //Add Political Party
				String tmpPartyName = ui.getMsg("Enter name of political party: ");
				while (election.checkExistsNameParty(tmpPartyName)) {
					tmpPartyName = ui.getMsg("Name of political party is been taken, Please choose another name");
				}
				ui.showMsg("In what year was the political party created: (before " + election.getYEAR() + ")");
				int createdYear = numOnRange(election.getYEAR());
				ui.showMsg("In what month was the political party created, type in number");
				int createdMonth=0;
				if (createdYear == election.getYEAR()) { //check if created year is equal to election year, then maximum month is the election month
					createdMonth = numOnRange(election.getMONTH());
				} else { //12 month
					createdMonth = numOnRange(12);
				}
				
				String tmpStream = ui.getMsg("Enter the stream positions: \nLeft, Right, Center");
				
				while (!(tmpStream.equalsIgnoreCase("Left")) && !(tmpStream.equalsIgnoreCase("Right")) && !(tmpStream.equalsIgnoreCase("Center"))) {
					tmpStream = ui.getErrMsg("Wrong choise, try to enter again..");
				}
				
				addPoliticalParty(tmpPartyName, createdYear, createdMonth, tmpStream, election);
				ui.showMsg("Political party number " + election.getPoliticalParties().size() + " created");

				break;
				
			case 4: //Add a candidates
					ui.showMsg("Enter year of birth: ");
					int tmpYearBirthCandidate = validateNumber();
					int tmpCandidateAge = election.getYEAR() - tmpYearBirthCandidate; //Calculate age
					if (tmpCandidateAge>= 18) { //validate age
						String tmpName = ui.getMsg("Enter name of candidate: ");
						ui.showMsg("Enter ID of candidate (must be 9 digits): ");
						int tmpID = checkIdValidation(election);
						ui.showMsg("What political party do you belong to? (Enter the number)");
						for (int i = 0; i < election.getPoliticalParties().size(); i++) {
							ui.showMsg(i+1 + ": " +election.getPoliticalParties().get(i).getName());
						}
						int tmpIndex = numOnRange(election.getPoliticalParties().size());
						try {
							if (question("Are you in quarantine?").equalsIgnoreCase("yes")) {
								ui.showMsg("How many days are you sick?");
								int tmpSickDays = numOnRange(2147483647);
									addSickCandidate(tmpYearBirthCandidate, tmpName, tmpID, true, tmpSickDays, tmpIndex, election);
								}
								else 
									addCandidate(tmpYearBirthCandidate, tmpName, tmpID, tmpIndex, election);
							ui.showMsg("Citizen number " + election.getCountCitizens() + " created");
						} catch (Exception e) {
							ui.showErrMsg(e.getMessage());
						}
						
					}
					else {
						ui.showErrMsg("Candidate must be Adult");
					}
					
				break;
				
			case 5: //Print Ballot boxes
				ui.showMsg(election.toStringBallotBox());
				break;
			case 6: //Print the citizens who can vote
				ui.showMsg(election.toStringCitizen());
				break;
			case 7: //Print Political Parties
				ui.showMsg(election.toStringPoliticalParties());
				break;
			case 8: //Start election round
				election.activate(); //activate for showing stats on case 9
				election.randomCitizenToBallotBox(); //random match citizen to ballot box
				election.createListForVoting(); //create vote lists for ballot boxes
				
				ui.showMsg("--Random Primaries Vote--\n");
				election.sortByPrimaries(); //random candidates in political parties
				
				ui.showMsg("--Election Vote--\n");
				for (int i = 0; i < election.getCountCitizens(); i++) { //ask citizen one by one
					ui.showMsg("Hi, " + election.getCitizen(i).getName()); //welcome msg for citizen
					String decide = question("Do you want to vote?"); //method question - check that input must be "yes" or "no"

					if (decide.equalsIgnoreCase("yes") && election.getCitizen(i).getInQuarantine()) { //CITIZENS IN QUARANTINE - checks citizen wants to vote and he is in quarantine

						if (question("Do you have protective suit? ").equalsIgnoreCase("yes")) { //citizen must have protective suit
							printPoliticalPartiesList(election);
							ui.showMsg("Enter the number for vote: ");
							int voteIndex = numOnRange(election.getCountPoliticalParties());
							election.getCitizen(i).vote(voteIndex);
							election.vote(voteIndex);
						}
					} else if (decide.equalsIgnoreCase("yes")) { //REST CITIZENS WHO WANT TO VOTE
						printPoliticalPartiesList(election);
						ui.showMsg("Enter the number for vote: ");
						int voteIndex = numOnRange(election.getCountPoliticalParties());
						election.getCitizen(i).vote(voteIndex);
						election.vote(voteIndex);
					}
				}
				break;
			case 9: //Print election results
				if (election.getActivated()) {
					String electionStats = "";
					for (int i = 0; i < election.getCountBallotBoxes(); i++) {
						electionStats += "From ballotBox " + (i+1) + ": \n" + election.getBallotBoxes().get(i).showRatioVotes(election.getCountTotalVotes()) + election.getBallotBoxes().get(i).showVotes() + "\n";
					}
					ui.showMsg(electionStats);
					ui.showMsg(election.results());
				} else {
					ui.showErrMsg("There was no election round");
				}


				break;
				
			case 10:
				saveData(election);
				ui.showMsg("Good bye");
				ui.showMsg("Saves all data");
				break;

			} //end switch
		} //end while
		while (userSelect != 10);
	}
	
	public static int checkLength(int num) { // check length of number
		int counter = 0;

		while (num != 0) {
			counter++;
			num /= 10;
		}

		return counter;
	}

	public static void addCitizen(int year, String name, int id, Election election) throws Exception { //send inputs to election object for creating a citizen
		election.addCitizen(year, name, id);
	}
	
	public static void addSickCitizen(int year, String name, int id, boolean quarantine, int sickDays, Election election) throws Exception { //send inputs to election object for creating a citizen
		election.addSickCitizen(year, name, id, true, sickDays);
	}
	
	public static void addPoliticalParty(String name, int year, int month, String stream, Election election) { //send inputs to election object for creating a political party
		election.addPoliticalParty(name, new Date(year, month+1, 0), stream);
	}
	
	public static void addCandidate(int year, String name, int id, int index, Election election) throws Exception { //send inputs to election object for creating a candidate
		election.addCandidate(year, name, id, election.getPoliticalParties().get(index-1));
	}
	
	public static void addSickCandidate(int year, String name, int id, boolean quarantine, int sickDays, int index, Election election) throws Exception { //send inputs to election object for creating a candidate
		election.addSickCandidate(year, name, id, quarantine, sickDays, election.getPoliticalParties().get(index-1));
	}

	public static void addBallotBox(String street, String type, Election election) {
		election.addBallotBox(street, type.toUpperCase());
	}
	
	public static String question(String str) { // check that input must be "yes" or "no"
		String option = ui.getMsg(str + "(yes or no)");
		while (!(option.equalsIgnoreCase("yes")) && !(option.equalsIgnoreCase("no"))) {
			option = ui.getErrMsg("Wrong choise, try to enter again..");
		}
		return option;
	}
	
	public static int numOnRange(int max) { // check correct index range
		int num = validateNumber();
		while ((num <= 0 || num > max)) {
			ui.showErrMsg("Wrong range, try to enter again..");
			num = validateNumber();
		}
		return num;
	}
	
	public static void printPoliticalPartiesList(Election election) {
		ui.showMsg(election.toStringPoliticalParties());
	}

	public static int validateNumber() { //check what entered is a number
		int number = 0;
		boolean validInput = false;
		
		while (!validInput) {
			try {
				number = ui.getInt();
				validInput = true;
			} catch (NumberFormatException e) {
				ui.showErrMsg(e.getLocalizedMessage() + ", is not a valid number, please enter a number");
			}
		}
		return number;

	}
	
	public static int checkIdValidation(Election election) {
		int tmpID = validateNumber();
		while (checkLength(tmpID) != 9) { //check 9 digits on ID // and check if id exist in system
			if (checkLength(tmpID) != 9)
				ui.showErrMsg("ID must be 9 digits");
			tmpID = validateNumber();
		} //end while
		return tmpID;
	}
	
	private static void saveData(Election election) throws FileNotFoundException, ClassNotFoundException, IOException {
		election.saveData();
	}
	
	private static void loadData(Election election) throws FileNotFoundException, ClassNotFoundException, IOException {
		if (question("Do you want to load the data from the last save?").equalsIgnoreCase("yes")) {
			try {
				election.loadData();
				ui.showMsg("The data was loaded successfully");
			} catch (Exception e) {
				ui.showErrMsg(e.getMessage());
			}
		}
	}
	
}
