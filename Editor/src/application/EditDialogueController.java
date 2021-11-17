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

public class EditDialogueController implements Initializable {
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
	
	private Dialog dObject = null;
	private DialogList list = null;
	
	private final ObservableList<String> person = FXCollections.observableArrayList("Opfer", "Detektiv", "Polizist", "Täter", "Bester Freund", "Bester Freundin", "Professor", "Psychologin", "Priester", "Gärtnerin", "Ohne");
	private final ObservableList<String> pose = FXCollections.observableArrayList("Lsmiling", "Lthinking", "Lsurprised", "Rsmiling", "Rthinking", "Rsurprised");

	/**
	 * Function initializes the Dialog Window.
	 * @param arg0 URL
	 * @param arg1 ResourceBundle
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbPerson.setItems(person);
		cbBild.setItems(pose);
	}

	/**
	 * Function saves the current dialogue.
	 */
	@FXML
	private void editDialogue() {
		if(checkEntries()) {
			int i = list.getPosition(dObject);
			
			dObject.setPerson(cbPerson.getValue());
			dObject.setBild(cbBild.getValue());
			dObject.setPosition(tfPosition.getText());
			dObject.setText(taDialog.getText());
			
			if(list.getList().set(i, dObject) != null) {
				lblError.setText("Erfolgreich gespeichert!");
			}
		
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
	 * Initialization function to store the dialogList and to fill the entries of the window.
	 * @param dia Dialog
	 * @param dList DialogList
	 */
	public void setDialog(Dialog dia, DialogList dList) {
		dObject = dia;
		list = dList;
		
		cbPerson.setValue(dia.getPerson());
		cbBild.setValue(dia.getBild());
		tfPosition.setText(dia.getPosition());
		taDialog.setText(dia.getText());
	}
}
