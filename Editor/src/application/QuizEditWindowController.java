package application;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import riddle.QuizElement;
import riddle.QuizList;

public class QuizEditWindowController implements Initializable {

	@FXML
	private TextField name, option0, option1, option2, option3, image;
	@FXML
	private ComboBox<String> points, type, difficulty;
	@FXML
	private RadioButton rBtn1, rBtn2, rBtn3, rBtn4, rBtnImg;
	@FXML
	private TextArea question;
	@FXML
	private Button saveBtn, cancelBtn, explorerBtn;
	@FXML
	private Label errorLabel;
	@FXML
	private ToggleGroup btnGroup;

	private final ObservableList<String> pointsList = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9");
	private final ObservableList<String> typeList = FXCollections.observableArrayList("Multiple Choice", "Antwort");
	private final ObservableList<String> difficultyList = FXCollections.observableArrayList("1", "2", "3", "4");

	private QuizElement editable = null;
	private QuizList list = null;

	/**
	 * initializes the layout (combo-box values/...)
	 */
	@Override
	public void initialize(URL location, ResourceBundle ressources) {
		points.setItems(pointsList);
		type.setItems(typeList);
		difficulty.setItems(difficultyList);
	}

	/**
	 * Action event for rBtnImg if selected, enables explorerBtn and image textField
	 *
	 */
	public void rBtnImgAction() {

		explorerBtn.setDisable(!rBtnImg.isSelected());
		image.setDisable(!rBtnImg.isSelected());

	}

	/**
	 * Action event for cancelBtn: if pressed, close window and go back to
	 * editor/r�tsel
	 *
	 */
	public void cancelBtnAction() {
		Stage stage = (Stage) cancelBtn.getScene().getWindow();
		stage.close();
	}

	/**
	 * Action event for createBtn if pressed, create new instance of QuizElement and
	 * clears all fields and boxes
	 *
	 */
	public void saveBtnEvent() {

		if (!validateEntries()) {
			errorLabel.setText("ERROR: Es fehlen Eingaben!");
			return;
		}
		
		int i = list.getPosition(editable);
		
		editable.setType(type.getValue());
		editable.setName(name.getText());
		editable.setDifficulty(difficulty.getValue());
		editable.setImage(rBtnImg.isSelected() ? image.getText() : "");
		editable.setQuestion(question.getText());
		editable.setCoins(points.getValue());
		editable.setOption0((option0.getText() != null) ? option0.getText() : "");
		editable.setOption1((option1.getText() != null) ? option1.getText() : "");
		editable.setOption2((option2.getText() != null) ? option2.getText() : "");
		editable.setOption3((option3.getText() != null) ? option3.getText() : "");
		editable.setSolution(Objects.requireNonNull(getCorrectAnswer()).getText());
		
		if(list.getList().set(i, editable) != null) {
			errorLabel.setText("Erfolgreich gespeichert!");
		} else {
			errorLabel.setText("ERROR: nicht gepeichert!");
		}
	}

	/**
	 * Action event for explorerBtn Opens Explorer windows, if pressed
	 *
	 */
	public void explorerBtnAction() {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null) {
			image.setText(selectedFile.getName());
		} else {
			errorLabel.setText("ERROR: Waehle valides Bild!");
		}
	}

	/**
	 * Validates each entry of the window to avoid null pointer exceptions
	 * 
	 * @return true, if all entries are valid, false, if one or more is not
	 */
	private boolean validateEntries() {
		if (type.getValue() == null) {
			return false;
		} else if (type.getValue().equals("Multiple Choice")) {
			if (option0.getText().equals(""))
				return false;
			if (option1.getText().equals(""))
				return false;
			if (option2.getText().equals(""))
				return false;
			if (option3.getText().equals(""))
				return false;
		} else {
			if (option0.getText().equals(""))
				return false;
		}
		if (name.getText().equals(""))
			return false;
		if (difficulty.getValue() == null)
			return false;
		if (rBtnImg.isSelected() && image.getText().equals(""))
			return false;
		if (question.getText().equals(""))
			return false;
		return points.getValue() != null;
	}

	/**
	 * Action event for ComboBox type if the value of type changed the method is
	 * executed.
	 *
	 */
	public void typeTextFieldAction() {
		if (type.getValue() == null)
			return;
		if (type.getValue().equals("Multiple Choice")) {

			rBtn1.setSelected(true);

			rBtn2.setDisable(false);
			rBtn3.setDisable(false);
			rBtn4.setDisable(false);

			option1.setDisable(false);
			option2.setDisable(false);
			option3.setDisable(false);
		} else if (type.getValue().equals("Antwort")) {

			rBtn1.setSelected(true);

			rBtn2.setDisable(true);
			rBtn3.setDisable(true);
			rBtn4.setDisable(true);

			option1.setDisable(true);
			option1.clear();
			option2.setDisable(true);
			option2.clear();
			option3.setDisable(true);
			option3.clear();
		}
	}

	/**
	 * checks, which button is selected
	 * 
	 * @return selected answer, null if nothing is selected
	 */
	private TextField getCorrectAnswer() {
		if (rBtn1.isSelected()) {
			return option0;
		} else if (rBtn2.isSelected()) {
			return option1;
		} else if (rBtn3.isSelected()) {
			return option2;
		} else if (rBtn4.isSelected()) {
			return option3;
		}
		return null;
	}

	/**
	 * Is called by parent class EditorController. The function set all values of
	 * each element of the window frame.
	 */
	public void loadElement() {
		
		type.setValue(editable.getType());
		difficulty.setValue(editable.getDifficulty());
		points.setValue(editable.getCoins());
		name.setText(editable.getName());
		question.setText(editable.getQuestion());
		
		if (!editable.getImage().equals("")) {
			rBtnImg.setSelected(true);
			image.setText(editable.getImage());
		}
		
		option0.setText(editable.getOption0());
		option1.setText(editable.getOption1().equals("") ? "" : editable.getOption1());
		option2.setText(editable.getOption2().equals("") ? "" : editable.getOption2());
		option3.setText(editable.getOption3().equals("") ? "" : editable.getOption3());

		typeTextFieldAction();
		rBtnImgAction();

		if (editable.getOption0().equals(editable.getSolution()))
			rBtn1.setSelected(true);
		if (editable.getOption1().equals(editable.getSolution()))
			rBtn2.setSelected(true);
		if (editable.getOption2().equals(editable.getSolution()))
			rBtn3.setSelected(true);
		if (editable.getOption3().equals(editable.getSolution()))
			rBtn4.setSelected(true);

	}

	/**
	 * Is called by parent class EditorController. It overwrites the QuizElement
	 * which the user wants to edit.
	 * 
	 * @param editable which gets edited.
	 */
	public void setEditable(QuizElement editable, QuizList list) {
		this.editable = editable;
		this.list = list;
	}
}
