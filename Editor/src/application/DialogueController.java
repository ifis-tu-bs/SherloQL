package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import story.Dialog;
import story.DialogList;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogueController implements Initializable {
	
	@FXML
	private ComboBox<String> cbPerson;
	
	@FXML
	private ComboBox<String> cbBild;
	
	@FXML
	private TextField tfPosition;
	
	@FXML
	private TextArea taDialog;
	
	@FXML
	private Button btnSave;
	
	@FXML
	private Button btnBack;
	
	@FXML
	private Label lblError;

	private DialogList dList;
	
	private final ObservableList<String> person = FXCollections.observableArrayList("Opfer", "Detektiv",
			"Polizist", "Täter", "Bester Freund", "Bester Freundin", "Professor", "Psychologin", "Priester",
			"Gärtnerin", "Ohne");
	private final ObservableList<String> bild = FXCollections.observableArrayList("Lsmiling", "Lthinking",
			"Lsurprised", "Rsmiling", "Rthinking", "Rsurprised");

	/**
	 * Function initializes the Dialog Window.
	 * @param arg0 URL
	 * @param arg1 ResourceBundle
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbPerson.setItems(person);
		cbBild.setItems(bild);
	}

	/**
	 * Function creates another window to add a new dialog to the chapter.
	 */
	@FXML
	private void addDialogue() {
		if(checkEntries()) {
			Dialog dObject = new Dialog(cbPerson.getValue(), cbBild.getValue(), tfPosition.getText(), taDialog.getText());
			
			dList.addElement(dObject);
			
			lblError.setText("Erfolgreich gespeichert!");
			
			clearAll();
		} else {
			lblError.setText("Speicherung fehlgeschlagen!");
		}
	}

	/**
	 * Function takes the stage of the button and closes the current window.
	 */
	@FXML
	private void closeWindow() {
		Stage stage = (Stage) btnBack.getScene().getWindow();
		stage.close();
	}

	/**
	 * Function clears all entries of the current window.
	 */
	private void clearAll() {
		cbPerson.setValue(null);
		cbBild.setValue(null);
		tfPosition.clear();
		taDialog.clear();
	}

	/**
	 * Function checks if all needed entries are done.
	 * @return boolean
	 */
	private boolean checkEntries() {			
		if(cbPerson.getValue().equals("Ohne")) {
			if(tfPosition.getText().equals(""))
				return false;
			else return !taDialog.getText().equals("");
		}
		
		if(tfPosition.getText().equals(""))
			return false;
		else if(taDialog.getText().equals(""))
			return false;
		else if(cbPerson.getValue() == null)
			return false;
		else return cbBild.getValue() != null;
	}

	/**
	 * Setter for dialogList.
	 * @param d DialogList
	 */
	public void setDialogList(DialogList d) {
		this.dList = d;
	}
}
