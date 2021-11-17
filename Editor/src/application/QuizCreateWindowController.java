package application;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import riddle.QuizElement;
import riddle.QuizList;

public class QuizCreateWindowController implements Initializable {

	private QuizList quizList;

	@FXML
	private TextField name, option0, option1, option2, option3, image;
	@FXML
	private ComboBox<String> points, type, difficulty;
	@FXML
	private RadioButton rBtn1, rBtn2, rBtn3, rBtn4, rBtnImg;
	@FXML
	private TextArea question;
	@FXML
	private Button createBtn, cancelBtn, explorerBtn;
	@FXML
	private Label errorLabel;
	@FXML
	private ToggleGroup btnGroup;

	ObservableList<String> pointsList = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9");
	ObservableList<String> typeList = FXCollections.observableArrayList("Multiple Choice", "Antwort");
	ObservableList<String> difficultyList = FXCollections.observableArrayList("1", "2", "3", "4");

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
	 * editor/rätsel
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
	public void createBtnEvent() {

		if (!validateEntries()) {
			errorLabel.setText("ERROR: Eingabe aller Informationen noetig!");
			return;
		}

		QuizElement element = new QuizElement();

		element.setType(type.getValue());
		element.setName(name.getText());
		element.setDifficulty(difficulty.getValue());
		element.setImage(rBtnImg.isSelected() ? image.getText() : "");
		element.setQuestion(question.getText());
		element.setCoins(points.getValue());
		element.setOption0((option0.getText() != null) ? option0.getText() : "");
		element.setOption1((option1.getText() != null) ? option1.getText() : "");
		element.setOption2((option2.getText() != null) ? option2.getText() : "");
		element.setOption3((option3.getText() != null) ? option3.getText() : "");
		element.setSolution(Objects.requireNonNull(getCorrectAnswer()).getText());

		quizList.addElement(element);

		errorLabel.setText("Erfolgreich erstellt!");

		resetEntries();

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
	 * Checks the entries of the tab
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
			option2.setDisable(true);
			option3.setDisable(true);
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
	 * clears all Entries -> reset to new window
	 */
	private void resetEntries() {
		type.setValue(null);
		difficulty.setValue(null);
		points.setValue(null);
		name.clear();
		image.clear();
		question.clear();
		option0.clear();
		option1.clear();
		option2.clear();
		option3.clear();
		rBtn1.setSelected(true);
	}

	/**
	 * method to initialize the quizlist. Needed to save new created elements
	 * directly into the list.
	 * 
	 * @param list to store new elements
	 */
	public void setQuizList(QuizList list) {
		this.quizList = list;

	}
}
