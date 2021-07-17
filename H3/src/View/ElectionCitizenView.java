package View;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JOptionPane;

import Model.PoliticalParty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import listeners.ElectionUIEventsListener;

public class ElectionCitizenView implements AbstractElectionView {
	private ArrayList<ElectionUIEventsListener> allListeners = new ArrayList<ElectionUIEventsListener>();
	private Stage stage;
	private TextField txtName;
	private TextField txtID;
	private TextField txtYear;
	private TextField txtSickDays;
	
	public ElectionCitizenView() {
		
		stage = new Stage();		
		stage.setTitle("Add a citizen");
		stage.setAlwaysOnTop(true);
		stage.setResizable(false);

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
		Label lblSickDays = new Label("How many days are you sick?");
		txtSickDays = new TextField();
		coronaGrid.add(lblSickDays, 0, 0);
		coronaGrid.add(txtSickDays, 0, 1);
		
		btnAddCitizen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				if (txtName.getText().isEmpty() || txtID.getText().isEmpty() || txtYear.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Do not leave an empty box");
				} else {
					for (ElectionUIEventsListener l : allListeners) {
						int tmpID = Integer.parseInt(txtID.getText());
						int tmpYear = Integer.parseInt(txtYear.getText());
						
						if (txtSickDays.getText().isEmpty()) {
							l.addCitizenFromUi(tmpYear, txtName.getText(), tmpID);
						} else {
							int tmpSickDays = Integer.parseInt(txtSickDays.getText());
							l.addSickCitizenFromUi(tmpYear, txtName.getText(), tmpID, true, tmpSickDays);
						}
					}

				}
			}
		});


		gpRoot.add(lblName, 0, 0);
		gpRoot.add(txtName, 1, 0);
		gpRoot.add(lblId, 0, 1);
		gpRoot.add(txtID, 1, 1);
		gpRoot.add(lblYear, 0, 2);
		gpRoot.add(txtYear, 1, 2);
		gpRoot.add(btnAddCitizen, 2, 6);
		gpRoot.add(tpane, 0, 3);


		stage.setScene(new Scene(gpRoot, 470, 280));
	}

	@Override
	public void registerListener(ElectionUIEventsListener newListener) {
		allListeners.add(newListener);
	}

	@Override
	public void addCitizenToUI(int year, String name, int id) {
		String citizensCreatedMsg = name + " added to election";
		JOptionPane.showMessageDialog(null, citizensCreatedMsg);
		txtName.setText("");
		txtID.setText("");
		txtYear.setText("");
		txtSickDays.setText("");
	}

	@Override
	public void errorMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}
	
	public void showScene() {
		stage.show();
	}


	



}
