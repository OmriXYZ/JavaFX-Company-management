package View;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import Controller.Controller;
import Model.BallotBox;
import Model.Candidate;
import Model.Citizen;
import Model.Election;
import Model.PoliticalParty;
import Model.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import listeners.ElectionUIEventsListener;

public class ElectionMenuView implements AbstractElectionView {
	private ArrayList<ElectionUIEventsListener> allListeners = new ArrayList<ElectionUIEventsListener>();
	
	private TitledPane citizenTitle;
	private TitledPane ballotBoxTitle;
	private TitledPane politicalPartyTitle;
	private TitledPane showCitizensTitle;
	private TitledPane showBallotBoxesTitle;
	private TitledPane showPoliticalPartiesTitle;
	private TitledPane startElectionRoundTitle;
	private TitledPane showTotalVotesTitle;

	
	//Arrays for Citizens
	private ComboBox<String> citizensCombo = new ComboBox<>();
	private ArrayList<Citizen> citizensForDetails = new ArrayList<>();
	//Arrays for BallotBox
	private ComboBox<Integer> ballotBoxesCombo = new ComboBox<>();
	private ArrayList<BallotBox> ballotBoxesForDetails = new ArrayList<>();
	//Arrays for PoliticalParties
	private ComboBox<String> politicalPartiesCombo = new ComboBox<>();
	private ArrayList<PoliticalParty> politicalPartiesForDetails = new ArrayList<>();
	
	private boolean roundStart = false;
	private int lastSelectedIndex;
	private int lastSelectedPartyIndex;
	private boolean endElectionRound = false;
	private Label totalResults = new Label();


	
	public ElectionMenuView(Stage stage, Election election) throws Exception {
				
		stage.setTitle("Election Menu");
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);

		Label lblMenu = new Label("Please select an option:");
		
//////////CITIZEN PANE//////////
		GridPane citizenPane = citizenGridPane();
		citizenTitle = new TitledPane("Create a citizen or candidate", citizenPane);
		citizenTitle.setExpanded(false);
		citizenTitle.expandedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if (arg2) {
					ballotBoxTitle.setExpanded(false);
					politicalPartyTitle.setExpanded(false);
					showCitizensTitle.setExpanded(false);
					showBallotBoxesTitle.setExpanded(false);
					showPoliticalPartiesTitle.setExpanded(false);	
					startElectionRoundTitle.setExpanded(false);

				}
			}
        });
		
//////////BALLOTBOX PANE//////////
		ballotBoxTitle = new TitledPane("Create a ballot box", ballotBoxGridPane());
		ballotBoxTitle.setExpanded(false);
		ballotBoxTitle.expandedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if (arg2) {
					citizenTitle.setExpanded(false);
					politicalPartyTitle.setExpanded(false);
					showCitizensTitle.setExpanded(false);
					showBallotBoxesTitle.setExpanded(false);
					showPoliticalPartiesTitle.setExpanded(false);
					startElectionRoundTitle.setExpanded(false);

				}
			}
        });
		
//////////POLITICAL PARTY PANE//////////
		politicalPartyTitle = new TitledPane("Create political party", politicalPartyGridPane());
		politicalPartyTitle.setExpanded(false);
		politicalPartyTitle.expandedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if (arg2) {
					citizenTitle.setExpanded(false);
					ballotBoxTitle.setExpanded(false);
					showCitizensTitle.setExpanded(false);
					showBallotBoxesTitle.setExpanded(false);
					showPoliticalPartiesTitle.setExpanded(false);
					startElectionRoundTitle.setExpanded(false);

				}
			}
        });
//////////SHOW CITIZENS PANE//////////
		showCitizensTitle = new TitledPane("Show Citizens Voters", showCitizensGridPane());
		showCitizensTitle.setExpanded(false);
		showCitizensTitle.expandedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if (arg2) {
					citizenTitle.setExpanded(false);
					ballotBoxTitle.setExpanded(false);
					politicalPartyTitle.setExpanded(false);
					showBallotBoxesTitle.setExpanded(false);
					showPoliticalPartiesTitle.setExpanded(false);
					startElectionRoundTitle.setExpanded(false);

				}
			}
        });
//////////SHOW BALLOTBOX PANE//////////
		showBallotBoxesTitle = new TitledPane("Show Ballot Boxes List", showBallotBoxesGridPane());
		showBallotBoxesTitle.setExpanded(false);
		showBallotBoxesTitle.expandedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if (arg2) {
					citizenTitle.setExpanded(false);
					ballotBoxTitle.setExpanded(false);
					politicalPartyTitle.setExpanded(false);
					showCitizensTitle.setExpanded(false);
					showPoliticalPartiesTitle.setExpanded(false);
					startElectionRoundTitle.setExpanded(false);

				}
			}
      });
//////////SHOW POLITICALPARTIES PANE//////////
		showPoliticalPartiesTitle = new TitledPane("Show Political Parties List", showPoliticalPartiesGridPane());
		showPoliticalPartiesTitle.setExpanded(false);
		showPoliticalPartiesTitle.expandedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if (arg2) {
					citizenTitle.setExpanded(false);
					ballotBoxTitle.setExpanded(false);
					politicalPartyTitle.setExpanded(false);
					showCitizensTitle.setExpanded(false);
					showBallotBoxesTitle.setExpanded(false);
					politicalPartiesCombo.getSelectionModel().clearSelection();
					startElectionRoundTitle.setExpanded(false);

				}
			}
    });
//////////START ELECTION ROUND PANE//////////
		GridPane electionRoundPane = electionRoundGridPane();
		startElectionRoundTitle = new TitledPane("Start election round", electionRoundPane);
		startElectionRoundTitle.setExpanded(false);
		startElectionRoundTitle.expandedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if (arg2) {
					ballotBoxTitle.setDisable(true);
					politicalPartyTitle.setDisable(true);
					showCitizensTitle.setDisable(true);
					showBallotBoxesTitle.setDisable(true);
					showPoliticalPartiesTitle.setDisable(true);
					citizenTitle.setDisable(true);
					citizenTitle.setExpanded(false);
					ballotBoxTitle.setExpanded(false);
					politicalPartyTitle.setExpanded(false);
					showCitizensTitle.setExpanded(false);
					showBallotBoxesTitle.setExpanded(false);
					showPoliticalPartiesTitle.setExpanded(false);

					
					if (!roundStart) {
						for (ElectionUIEventsListener l : allListeners) {
							l.startElectionRoundFromUi();
							roundStart = true;
						}
					}

				}
			}
      });
//////////SHOW VOTES PANE//////////
		GridPane showVotesPane = showVotesGridPane();
		showTotalVotesTitle = new TitledPane("Show Total Votes", showVotesPane);
		showTotalVotesTitle.setExpanded(false);
		showTotalVotesTitle.setDisable(true);
		showTotalVotesTitle.expandedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if (arg2) {
				}
			}
    });
		Button btnLoadData = new Button("Load election data");
		btnLoadData.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				for (ElectionUIEventsListener l : allListeners) {
					l.loadDataFromUi();
				}
			}
		});
		Button btnSaveData = new Button("Save election data");
		btnSaveData.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				for (ElectionUIEventsListener l : allListeners) {
					l.saveDataFromUi();
				}
			}
		});

		gpRoot.add(lblMenu, 0, 0);
		gpRoot.add(citizenTitle, 0, 1);
		gpRoot.add(ballotBoxTitle, 0, 2);
		gpRoot.add(politicalPartyTitle, 0, 3);
		gpRoot.add(showCitizensTitle, 0, 4);
		gpRoot.add(showBallotBoxesTitle, 0, 5);
		gpRoot.add(showPoliticalPartiesTitle, 0, 6);
		gpRoot.add(startElectionRoundTitle, 0, 7);
		gpRoot.add(showTotalVotesTitle, 0, 8);
		gpRoot.add(btnSaveData, 0, 13);
		gpRoot.add(btnLoadData, 0, 14);


		stage.setScene(new Scene(gpRoot, 520, 750));
		stage.show();
	}
	
	public GridPane citizenGridPane() throws Exception {
		
		TextField txtName;
		TextField txtID;
		TextField txtYear;
		TextField txtSickDays;
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);

		Label lblName = new Label("Enter Citizen's name: ");
		txtName = new TextField();
		Label lblId = new Label("Enter ID of citizen (must be 9 digits): ");
		txtID = new TextField();
		Label lblYear = new Label("Enter year of birth: ");
		txtYear = new TextField();
		
		Button btnAddCitizen = new Button("Add Citizen");
		
		GridPane coronaGrid = new GridPane();
		coronaGrid.setPadding(new Insets(10));
		coronaGrid.setHgap(4);
		coronaGrid.setVgap(4);
		TitledPane tpane = new TitledPane("In quarantine?", coronaGrid);
		tpane.setExpanded(false);

		Label lblSickDays = new Label("How many days are you sick?");
		txtSickDays = new TextField();
		coronaGrid.add(lblSickDays, 0, 0);
		coronaGrid.add(txtSickDays, 0, 1);

		TitledPane politicalPartiesForCandidate = new TitledPane();
		politicalPartiesForCandidate.setText("Want to belong to Political party?");

		ComboBox<String> tmpPoliticalPartiesCombo = new ComboBox<>();
		tmpPoliticalPartiesCombo.setItems(politicalPartiesCombo.getItems());
		Label lblPartyBelong = new Label("Choose Political Party:");
		Button btnClearOptionParty = new Button("Clear");
		btnClearOptionParty.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				tmpPoliticalPartiesCombo.getSelectionModel().clearSelection();
				politicalPartiesForCandidate.setExpanded(false);
			}
		});
		
		btnAddCitizen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				if (txtName.getText().isEmpty() || txtID.getText().isEmpty() || txtYear.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Do not leave an empty box");
				} else {
					for (ElectionUIEventsListener l : allListeners) {
						int tmpID=0;
						int tmpYear=0;
						int tmpSickDays=0;

						try {
							tmpID = Integer.parseInt(txtID.getText());
							tmpYear = Integer.parseInt(txtYear.getText());

						} catch (Exception e) {
							errorMessage(e.getMessage());
							throw e;

						}
						if (!txtSickDays.getText().isEmpty()) {
							tmpSickDays = Integer.parseInt(txtSickDays.getText());
						}
						if (tmpSickDays == 0) {
							txtSickDays.setText("");
						}
						//when the user choose party to connect
						if (!tmpPoliticalPartiesCombo.getSelectionModel().isEmpty() && txtSickDays.getText().isEmpty()) {
							l.addCandidateFromUi(tmpYear, txtName.getText(), tmpID, tmpPoliticalPartiesCombo.getSelectionModel().getSelectedIndex());
						} else if (!tmpPoliticalPartiesCombo.getSelectionModel().isEmpty() && tmpSickDays > 0) {
							l.addSickCandidateFromUi(tmpYear, txtName.getText(), tmpID, true, tmpSickDays, tmpPoliticalPartiesCombo.getSelectionModel().getSelectedIndex());
						} else { //when the user want citizen or sick citizen
							if (txtSickDays.getText().isEmpty() || tmpSickDays <= 0) {
								l.addCitizenFromUi(tmpYear, txtName.getText(), tmpID);
							} else {
								l.addSickCitizenFromUi(tmpYear, txtName.getText(), tmpID, true, tmpSickDays);
							}
						}
					}

				}
			}
		});

		GridPane partyGrid = new GridPane();
		partyGrid.setPadding(new Insets(10));
		partyGrid.setHgap(4);
		partyGrid.setVgap(4);
		partyGrid.add(lblPartyBelong, 0, 0);
		partyGrid.add(tmpPoliticalPartiesCombo, 0, 1);
		partyGrid.add(btnClearOptionParty, 1, 1);

		politicalPartiesForCandidate.setContent(partyGrid);
		politicalPartiesForCandidate.setExpanded(false);
	
		gpRoot.add(politicalPartiesForCandidate, 1, 3);

		gpRoot.add(lblName, 0, 0);
		gpRoot.add(txtName, 1, 0);
		gpRoot.add(lblId, 0, 1);
		gpRoot.add(txtID, 1, 1);
		gpRoot.add(lblYear, 0, 2);
		gpRoot.add(txtYear, 1, 2);
		gpRoot.add(btnAddCitizen, 2, 6);
		gpRoot.add(tpane, 0, 3);

		
		return gpRoot;
	}


	public GridPane ballotBoxGridPane() {

		TextField txtStreetName = new TextField();
		Label lblStreetName = new Label("Enter Street name: ");

		Label lblType = new Label("Choose the type of ballot box:");
		RadioButton c1 = new RadioButton("Corona");
		c1.setUserData("Corona");
		RadioButton c2 = new RadioButton("Normal");
		c2.setUserData("Normal");
		RadioButton c3 = new RadioButton("Military");
		c3.setUserData("Military");
		RadioButton c4 = new RadioButton("Corona Military");
		c4.setUserData("Corona_Military");
		Button btnAddBallotBox = new Button("Add Ballot Box");


		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);

		gpRoot.add(lblStreetName, 0, 0);
		gpRoot.add(txtStreetName, 1, 0);

		gpRoot.add(lblType, 0, 1);
		gpRoot.add(c1, 0, 2);
		gpRoot.add(c2, 0, 3);
		gpRoot.add(c3, 0, 4);
		gpRoot.add(c4, 0, 5);
		gpRoot.add(btnAddBallotBox, 5, 8);


		ToggleGroup t = new ToggleGroup();
		c1.setToggleGroup(t);
		c2.setToggleGroup(t);
		c3.setToggleGroup(t);
		c4.setToggleGroup(t);

		c1.setSelected(true);
		EventHandler<ActionEvent> handler = e -> {
			if (c1.isSelected()) {

			}
		};
		c1.setOnAction(handler);
		c2.setOnAction(handler);
		c3.setOnAction(handler);
		c4.setOnAction(handler);
		
		
		btnAddBallotBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				if (txtStreetName.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Do not leave an empty box");
				} else {
					for (ElectionUIEventsListener l : allListeners) {
						l.addBallotBoxFromUi(txtStreetName.getText(), t.getSelectedToggle().getUserData().toString());
					}

				}
			}
		});

		return gpRoot;

	}
	
	public GridPane politicalPartyGridPane() {

		TextField txtName = new TextField();
		Label lblName = new Label("Enter the name of political party: ");
		Label lblType = new Label("Choose the type of stream positions:");
		RadioButton c1 = new RadioButton("Left");
		c1.setUserData("Left");
		RadioButton c2 = new RadioButton("Right");
		c2.setUserData("Right");
		RadioButton c3 = new RadioButton("Center");
		c3.setUserData("Center");

		Button btnAddPoliticalParty = new Button("Add Political party");
		
		DatePicker partyCreatedDate = new DatePicker();
		partyCreatedDate.setValue(LocalDate.now());
		
		partyCreatedDate.setDayCellFactory(param -> new DateCell() {
	        @Override
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
	        }
	    });

		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);

		gpRoot.add(lblName, 0, 0);
		gpRoot.add(txtName, 1, 0);

		gpRoot.add(lblType, 0, 1);
		gpRoot.add(c1, 0, 2);
		gpRoot.add(c2, 0, 3);
		gpRoot.add(c3, 0, 4);
		gpRoot.add(partyCreatedDate, 0, 5);

		gpRoot.add(btnAddPoliticalParty, 5, 8);


		ToggleGroup t = new ToggleGroup();
		c1.setToggleGroup(t);
		c2.setToggleGroup(t);
		c3.setToggleGroup(t);

		c1.setSelected(true);
		EventHandler<ActionEvent> handler = e -> {
			if (c1.isSelected()) {

			}
		};
		c1.setOnAction(handler);
		c2.setOnAction(handler);
		c3.setOnAction(handler);
		
		
		btnAddPoliticalParty.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				if (txtName.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Do not leave an empty box");
				} else {
					for (ElectionUIEventsListener l : allListeners) {
						Date createdDate = new Date(partyCreatedDate.getValue().getYear(), partyCreatedDate.getValue().getMonthValue()+1, 0);
						l.addPoliticalPartyFromUi(txtName.getText(), createdDate, t.getSelectedToggle().getUserData().toString());
					}

				}
			}
		});

		return gpRoot;

	}

	public GridPane showCitizensGridPane() {
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		
		Label lblMsgDetails = new Label("Choose citizen to show details:");
		Label lblDetails = new Label("");
		
		/////Set details to citizensDetails when check citizen from combo list
		citizensCombo.setOnAction((event) -> {
		    int selectedIndex = citizensCombo.getSelectionModel().getSelectedIndex();
			lblDetails.setText(citizensForDetails.get(selectedIndex).toString());

		});

		gpRoot.add(lblMsgDetails, 0, 0);
		gpRoot.add(citizensCombo, 0, 1);
		gpRoot.add(lblDetails, 0, 3);

		return gpRoot;
	}

	public GridPane showBallotBoxesGridPane() {
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		
		Label lblMsgDetails = new Label("Choose ballot box to show details:");
		Label lblDetails = new Label("");
		
		/////Set details to ballotbox details when check ballotbox from combo list
		ballotBoxesCombo.setOnAction((event) -> {
		    int selectedIndex = ballotBoxesCombo.getSelectionModel().getSelectedIndex();
		    Object selectedItem = ballotBoxesCombo.getSelectionModel().getSelectedItem();
			lblDetails.setText(ballotBoxesForDetails.get(selectedIndex).toString());

		});

		gpRoot.add(lblMsgDetails, 0, 0);
		gpRoot.add(ballotBoxesCombo, 0, 1);
		gpRoot.add(lblDetails, 0, 3);

		return gpRoot;
	}

	public GridPane showPoliticalPartiesGridPane() {
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		
		Label lblMsgDetails = new Label("Choose Political Party to show details:");
		Label lblPartyDetails = new Label("");
		
		/////Set details to Political Parties details when check Party from combo list
		politicalPartiesCombo.setOnAction((event) -> {
		    int selectedIndex = politicalPartiesCombo.getSelectionModel().getSelectedIndex();
		    lblPartyDetails.setText(politicalPartiesForDetails.get(selectedIndex).toString());
		});

		gpRoot.add(lblMsgDetails, 0, 0);
		gpRoot.add(politicalPartiesCombo, 0, 1);
		gpRoot.add(lblPartyDetails, 0, 3);

		return gpRoot;
	}

	public GridPane electionRoundGridPane() {

		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		
		
		Label lblMsgCitizen = new Label("Select citizen to vote:");
		ComboBox<String> tmpCitizensCombo = new ComboBox<>();
		tmpCitizensCombo.setItems(citizensCombo.getItems());
		ComboBox<String> tmpPartiesCombo = new ComboBox<>();
		tmpPartiesCombo.setItems(politicalPartiesCombo.getItems());
		Button btnVote = new Button("Vote");
		Button btnEndVote = new Button("End the vote");
		
		ArrayList<Integer> blockedVoters = new ArrayList<>();
		ArrayList<Integer> whoVote = new ArrayList<>();

		tmpCitizensCombo.setOnAction((event) -> {
		    int selectedIndex = tmpCitizensCombo.getSelectionModel().getSelectedIndex();
		    lastSelectedIndex = selectedIndex;
		    if (citizensForDetails.get(selectedIndex).getInQuarantine()) {
				Alert alert = new Alert(Alert.AlertType.NONE);
				alert.setContentText("Do you have protective suit?");
				ButtonType okButton = new ButtonType("Yes", ButtonData.YES);
				ButtonType noButton = new ButtonType("No", ButtonData.NO);
				alert.getButtonTypes().setAll(okButton, noButton);
				alert.showAndWait().ifPresent(type -> {
					if (type == okButton) {
						if (!blockedVoters.isEmpty()) {
							blockedVoters.remove(selectedIndex);
						}
					} else if (type == noButton) {
						if (!blockedVoters.contains(selectedIndex)) {
							blockedVoters.add(selectedIndex);
						}
					}
					
					for (int i = 0; i < blockedVoters.size(); i++) {
						if (selectedIndex == blockedVoters.get(i)) {
							makeAlert("Can't vote");
							tmpCitizensCombo.getSelectionModel().clearSelection();
						}
					}
						
					});
			}
		});
		
		tmpPartiesCombo.setOnAction((event) -> {
		    int selectedIndex = tmpPartiesCombo.getSelectionModel().getSelectedIndex();
		    lastSelectedPartyIndex = selectedIndex;

		});
		
		btnVote.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				if (!tmpCitizensCombo.getSelectionModel().isEmpty() && !tmpPartiesCombo.getSelectionModel().isEmpty()) {
					if (whoVote.contains(lastSelectedIndex)) {
						makeAlert("You have already voted");
					} else {
						whoVote.add(lastSelectedIndex);
						makeAlert(citizensForDetails.get(lastSelectedIndex).getName() + " vote to: " + politicalPartiesForDetails.get(lastSelectedPartyIndex).getName());
						tmpCitizensCombo.getSelectionModel().select(lastSelectedIndex+1);
						for (ElectionUIEventsListener l : allListeners) {
							l.voteFromUi(lastSelectedIndex, lastSelectedPartyIndex);
						}
					}
				} else
					makeAlert("Do not leave blank");
			}
		});
		
		btnEndVote.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				startElectionRoundTitle.setDisable(true);
				startElectionRoundTitle.setExpanded(false);
				showTotalVotesTitle.setDisable(false);

				for (ElectionUIEventsListener l : allListeners) {
					l.endElectionRound();
				}
			}
		});
		
		gpRoot.add(lblMsgCitizen, 0, 0);

		gpRoot.add(tmpCitizensCombo, 0, 1);
		gpRoot.add(tmpPartiesCombo, 1, 1);
		gpRoot.add(btnVote, 2, 1);
		gpRoot.add(btnEndVote, 3, 1);

		return gpRoot;
	}

	public GridPane showVotesGridPane() {
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		
		ScrollPane scrollMsg = new ScrollPane();


		
		Label lblMsgTotalVotes = new Label("Total Votes of election round:");
		scrollMsg.setContent(totalResults);


		gpRoot.add(lblMsgTotalVotes, 0, 0);

		gpRoot.add(scrollMsg, 0, 2);

		return gpRoot;
	}
	
	private void makeAlert(String str) {
		JOptionPane.showMessageDialog(null, str);
	}

	@Override
	public void registerListener(ElectionUIEventsListener newListener) {
		allListeners.add(newListener);
	}

	@Override
	public void addCitizenToUI(int year, String name, int id) {
		Citizen tmpCitizen = new Citizen(year, name, id);
		String citizensCreatedMsg = name + " added to election";
		JOptionPane.showMessageDialog(null, citizensCreatedMsg);
		citizensCombo.getItems().add(tmpCitizen.getName());
		citizensForDetails.add(tmpCitizen);

//		txtName.setText("");
//		txtID.setText("");
//		txtYear.setText("");
//		txtSickDays.setText("");
	}
	
	@Override
	public void addSickCitizenToUI(int year, String name, int id, boolean quarantine, int sickDays) {
		Citizen tmpCitizen = new Citizen(year, name, id, quarantine, sickDays);
		String citizensCreatedMsg = name + " added to election";
		JOptionPane.showMessageDialog(null, citizensCreatedMsg);
		citizensCombo.getItems().add(tmpCitizen.getName());
		citizensForDetails.add(tmpCitizen);		
	}

	@Override
	public void errorMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	@Override
	public void addBallotBoxToUI(BallotBox ballotBox) {
		String ballotBoxCreatedMsg = ballotBox.getType() + " Ballot Box in " + ballotBox.getStreet() + " added to election";
		JOptionPane.showMessageDialog(null, ballotBoxCreatedMsg);
		ballotBoxesCombo.getItems().add(ballotBox.getID());
		ballotBoxesForDetails.add(ballotBox);		
	}

	@Override
	public void addPoliticalPartyToUI(String name, Date createdDate, String stream) {
		PoliticalParty tmpPoliticalParty = new PoliticalParty(name, createdDate, stream);
		String politicalPartyCreatedMsg = "Political Party " + name + " added to election";
		JOptionPane.showMessageDialog(null, politicalPartyCreatedMsg);
		politicalPartiesCombo.getItems().add(tmpPoliticalParty.getName());
	}

	@Override
	public void addCandidateToUI(int year, String name, int id, PoliticalParty party) {
		Candidate tmpCandidate = new Candidate(year, name, id, party);
		String citizensCreatedMsg = name + " added to election";
		JOptionPane.showMessageDialog(null, citizensCreatedMsg);
		citizensCombo.getItems().add(tmpCandidate.getName());
		citizensForDetails.add(tmpCandidate);
	}
	@Override
	public void addSickCandidateToUI(int year, String name, int id, boolean quarantine, int sickDays,
			PoliticalParty party) {
		Candidate tmpCandidate = new Candidate(year, name, id, quarantine, sickDays, party);
		String citizensCreatedMsg = name + " added to election";
		JOptionPane.showMessageDialog(null, citizensCreatedMsg);
		citizensCombo.getItems().add(tmpCandidate.getName());
		citizensForDetails.add(tmpCandidate);
	}

	@Override
	public void updatePoliticalPartiesOnUi(ArrayList<PoliticalParty> party) {
		politicalPartiesForDetails = party;
	}

	@Override
	public void updateElectionResults(String str1, String str2) {
		totalResults.setText(str1 + "\n" + str2);
	}
	
	public int stringToInt(String str) throws Exception {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			throw new Exception(e.getMessage() + " not a number");
		}
	}

	@Override
	public void loadData(Set<Citizen> citizensLoaded, ArrayList<PoliticalParty> partiesLoaded, ArrayList<BallotBox> ballotBoxesLoaded) {
		politicalPartiesForDetails = partiesLoaded;
		ballotBoxesForDetails = ballotBoxesLoaded;
		citizensForDetails.removeAll(citizensForDetails);
		for (int i = 0; i < citizensLoaded.size(); i++) {
			citizensForDetails.add(citizensLoaded.get(i));
		}		
		
		citizensCombo.getItems().clear();
		for (int i = 0; i < citizensLoaded.size(); i++) {
			citizensCombo.getItems().add(citizensLoaded.get(i).getName());
		}
		politicalPartiesCombo.getItems().clear();
		for (int i = 0; i < partiesLoaded.size(); i++) {
			politicalPartiesCombo.getItems().add(partiesLoaded.get(i).getName());
		}		
		ballotBoxesCombo.getItems().clear();
		for (int i = 0; i < ballotBoxesLoaded.size(); i++) {
			ballotBoxesCombo.getItems().add(ballotBoxesLoaded.get(i).getID());
		}
	}





}
