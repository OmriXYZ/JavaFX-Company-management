package application;
	
import java.time.LocalDate;
import java.util.Date;

import Controller.Controller;
import Model.Election;
import View.AbstractElectionMenuView;
import View.AbstractElectionView;
import View.AbstractLoadingView;
import View.ElectionMenuView;
import View.LoadingScreen;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Main extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		LocalDate date = LocalDate.now();
		Election election = new Election(date.getYear(), date.getMonthValue());
		AbstractElectionView theView1 = new ElectionMenuView(primaryStage, election);
		Controller controller1 = new Controller(election, theView1);
		
//		election.addCitizen(1992, "omri", 318324613);
//		election.addPoliticalParty("likud", new Date(1992, 5, 0), "left");
//		election.addBallotBox("tlamim", "corona");
		//HARDCODED//
		//create 5 citizen
		election.addSickCitizen(1998, "Michelle", 212222345, true, 5);
		election.addCitizen(1928, "Yonatan", 123498765);
		election.addCitizen(1975, "Sonic", 318324222);
		election.addCitizen(1948, "David-ben-Gurion", 100000000);
		election.addSickCitizen(2002, "Moshe", 123123123, true, 6);
		//create 3 political parties
		election.addPoliticalParty("likud", new Date(1942, 5, 0), "right"); // 1
		election.addPoliticalParty("meretz", new Date(1964, 7, 0), "left"); // 2
		election.addPoliticalParty("kahol-lavan", new Date(1853, 3, 0), "center"); // 3
		//create 6 candidates for 3 political parties
		election.addCandidate(1960, "bibi", 122333444, 0);
		election.addCandidate(1995, "hod", 397777123, 1);
		election.addCandidate(1910, "sapir", 318222111, 2);
		election.addCandidate(1938, "ben", 333999111, 2);
		//create 5 ballot box (normal, corona, military, corona_military)
		election.addBallotBox("regavim, LOD", "normal");
		election.addBallotBox("hashmonaim, TEL-AVIV", "corona");
		election.addBallotBox("habsor, RAMLE", "military");
		election.addBallotBox("hadagim 3, EILAT", "normal");
		election.addBallotBox("shderot tzahal 48, JERUSALEM", "corona_military");
	}

}
