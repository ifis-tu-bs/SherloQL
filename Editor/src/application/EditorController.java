package application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import riddle.QuizElement;
import riddle.QuizList;
import story.Chapter;
import story.ChapterList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

//import application.MainController.MyEventHandler;

public class EditorController implements Initializable {

	private final QuizList quizList = new QuizList();
	private final ChapterList chapterList = new ChapterList();

	@FXML private Button quizImpBtn, quizExpBtn, quizCrtBtn, quizEdtBtn, quizDelBtn,
			storyImpBtn, storyExpBtn, storyCreate, storyDelete, storyEdit, storyShowTree;
	@FXML private Tab quizTab, storyTab;
	@FXML private TableView<QuizElement> quizTable;
	@FXML private TableColumn<QuizElement, String> quizCoins, quizName, quizType, quizDfc, quizQst, quizOpt0, quizOpt1,
			quizOpt2, quizOpt3, quizSln, quizImg;
	@FXML private Label quizErrorLabel, storyErrorLabel;
	@FXML private ListView<Chapter> storyTable;
	@FXML private Canvas canvasChapter;
	@FXML private RadioButton rbtnGerman, rbtnEnglish, rbtnSQL, rbtnNonSQL;
	@FXML private ToggleGroup toggleGroupLan, toggleGroupMode;


	/**
	 * Initialization function.
	 * @param location URL
	 * @param resources ResourceBundle
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initRiddleTab();

		initStoryTab();

	}

	/**
	 * Action for import-button. Opens a file dialog and imports the json-file, if
	 * it is valid!
	 *
	 */
	public void importRiddlesJSON() {
		quizErrorLabel.setText(null);
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json");
		fileChooser.getExtensionFilters().add(extFilter);
		File filePath = fileChooser.showOpenDialog(null);

		if (filePath == null) {
			quizErrorLabel.setText("Waehle valides JSON-File!");
			return;
		}

		File file = new File(filePath.getAbsolutePath());

		try {
			quizList.importList((new JSONObject(new String(Files.readAllBytes(Paths.get(file.toURI())), "UTF-8")))
					.getJSONArray("Raetsel"));
			quizErrorLabel.setText("Erfolreich importiert!");
		} catch (JSONException | IOException e1) {
			quizErrorLabel.setText("Importieren fehlgeschlagen!");
			e1.printStackTrace();
		}
	}

	/**
	 * Action for export-Button. Method opens a save-file-dialog to choose a name
	 * and a place to export as json-file.
	 *
	 */
	public void exportRiddlesJSON() {
		quizErrorLabel.setText(null);

		String jsonData = quizList.exportList();
		if (jsonData.equals("")) {
			quizErrorLabel.setText("Keine Daten zum exportieren!");
			return;
		}

		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setInitialFileName("nog_raetsel");
		fileChooser.setTitle("Speichern als");

		File file = fileChooser.showSaveDialog(null);
		if (file == null) {
			quizErrorLabel.setText("Beim erstellen der Datei ist etwas fehlgeschlagen!");
			return;
		}

		try {
			FileWriter writer = new FileWriter(file);
			writer.write(jsonData);
			writer.close();
			quizErrorLabel.setText("Erfolgreich exportiert!");
		} catch (Exception e1) {
			quizErrorLabel.setText("Exportieren fehlgeschlagen!");
			e1.printStackTrace();
		}
	}

	/**
	 * Action for create-Button. Opens a new window to create a new QuizElement.
	 *
	 */
	public void createRiddle() {
		quizErrorLabel.setText(null);
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/QuizCreateWindow.fxml"));
			Parent root = loader.load();

			QuizCreateWindowController quizWindowController = loader.getController();
			quizWindowController.setQuizList(this.quizList);

			Stage quizTab = new Stage();
			quizTab.setScene(new Scene(root));
			quizTab.setTitle("Raetsel hinzufuegen");
			quizTab.show();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * ActionEvent for edit-Button. With the selected QuizElement the method opens a
	 * new window and loads in the element.
	 *
	 */
	public void editRiddle() {
		quizErrorLabel.setText(null);
		QuizElement selected;
		if ((selected = quizTable.getSelectionModel().getSelectedItem()) == null) {
			quizErrorLabel.setText("Kein Element ausgewaehlt!");
			return;
		}

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/QuizEditWindow.fxml"));
			Parent root = loader.load();

			QuizEditWindowController quizTabController = loader.getController();
			quizTabController.setEditable(selected, quizList);
			quizTabController.loadElement();

			Stage quizTab = new Stage();
			quizTab.setScene(new Scene(root));
			quizTab.setTitle("Raetsel bearbeiten");
			quizTab.show();
		} catch (IOException e1) {
			quizErrorLabel.setText("Bearbeitungsfenster konnte nicht geoeffnet werden!");
			e1.printStackTrace();
		}
	}

	/**
	 * ActionEvent for delete-button. Deletes the selected element out of the
	 * LinkedList; Opens an Alert Window to confirm the decision.
	 *
	 */
	public void deleteRiddle() {
		quizErrorLabel.setText(null);
		QuizElement selected;
		if ((selected = quizTable.getSelectionModel().getSelectedItem()) == null) {
			quizErrorLabel.setText("Kein Element ausgewaehlt!");
			return;
		}

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Raetsel loeschen?");
		alert.setHeaderText("Raetsel wirklich loeschen?");
		alert.setContentText(selected.getName());

		Optional<ButtonType> option = alert.showAndWait();

		if (option.isPresent() && option.get() == ButtonType.CANCEL) {
			this.quizErrorLabel.setText("Loeschen abgebrochen!");
			return;
		}

		if (quizList.deleteElement(selected)) {
			this.quizErrorLabel.setText("Element erfolgreich geloescht!");
		} else {
			this.quizErrorLabel.setText("Element konnte nicht geloescht werden!");
		}
	}

	/**
	 * Function opens a new window to create a new chapter.
	 */
	@FXML
	private void createChapter() {
		Chapter chapter = new Chapter();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/StoryErstellen.fxml"));
			Parent root = loader.load();
			
			StoryController stCont = loader.getController();

			stCont.setChapter(chapter);
			stCont.setChapList(this.chapterList);

			Stage story = new Stage();
			story.setScene(new Scene(root));
			story.setTitle("Story Inhalt");
			story.setResizable(false);
			story.getIcons().add(new Image("/layout/app_logo_neu.png"));
			story.showAndWait();
			chapter = stCont.getSavedChapter();
			if(chapter.isEdited()){
				chapterList.addChapter(chapter);
				System.out.print(chapter.toJSONObject().toString(4));
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Function creates a json out of the chapterList to export and save it.
	 */
	@FXML
	public void exportStoryJSON() {
		storyErrorLabel.setText(null);

		String jsonData = chapterList.exportList();
		if (jsonData == null) {
			storyErrorLabel.setText("Keine Daten zum exportieren!");
			return;
		}

		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setInitialFileName(getFileName());
		fileChooser.setTitle("Speichern als");

		File file = fileChooser.showSaveDialog(null);
		if (file == null) {

			storyErrorLabel.setText("Beim erstellen der Datei ist etwas fehlgeschlagen!");
			return;
		}

		try {
			FileWriter writer = new FileWriter(file);
			writer.write(jsonData);
			writer.close();
			storyErrorLabel.setText("Erfolgreich exportiert!");
		} catch (Exception e1) {

			storyErrorLabel.setText("Exportieren fehlgeschlagen!");
			e1.printStackTrace();
		}
	}

	/**
	 * Helper function to determine the name of the exported file.
	 * @return String file name
	 */
	private String getFileName() {
		String fileName = "story";
		if(rbtnEnglish.isSelected()){
			fileName += "_en";
		} else {
			fileName += "_de";
		}

		if(rbtnSQL.isSelected()){
			fileName += "_sql";
		} else {
			fileName += "_nonsql";
		}
		return fileName;
	}

	/**
	 * Function imports a json file and parses it into the chapterList.
	 */
	@FXML
	private void importStoryJSON() {
		storyErrorLabel.setText(null);
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json");
		fileChooser.getExtensionFilters().add(extFilter);
		File filePath = fileChooser.showOpenDialog(null);

		if (filePath == null) {

			storyErrorLabel.setText("Waehle valides JSON-File!");
			return;
		}
		File file = new File(filePath.getAbsolutePath());

		try {
			chapterList.importList((new JSONObject(
					new String(Files.readAllBytes(Paths.get(file.toURI())), "UTF-8")))
					.getJSONArray("Story"));
			storyErrorLabel.setText("Erfolgreich importiert!");
			initializeSettings(filePath.getName());
		} catch (JSONException | IOException e1) {

			storyErrorLabel.setText("Importieren fehlgeschlagen!");
			e1.printStackTrace();
		}

	}

	/**
	 * Helper function to determine the settings of a imported json file.
	 * @param name String
	 */
	private void initializeSettings(String name) {
		rbtnEnglish.setSelected(name.contains("_en"));
		rbtnGerman.setSelected(name.contains("_de"));
		rbtnNonSQL.setSelected(name.contains("_nonsql"));
		rbtnSQL.setSelected(name.contains("_sql"));
	}

	/**
	 * Function initializes the riddle tab.
	 */
	private void initRiddleTab(){
		quizCoins.setCellValueFactory(new PropertyValueFactory<>("coins"));
		quizType.setCellValueFactory(new PropertyValueFactory<>("type"));
		quizName.setCellValueFactory(new PropertyValueFactory<>("name"));
		quizDfc.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
		quizQst.setCellValueFactory(new PropertyValueFactory<>("question"));
		quizOpt0.setCellValueFactory(new PropertyValueFactory<>("option0"));
		quizOpt1.setCellValueFactory(new PropertyValueFactory<>("option1"));
		quizOpt2.setCellValueFactory(new PropertyValueFactory<>("option2"));
		quizOpt3.setCellValueFactory(new PropertyValueFactory<>("option3"));
		quizSln.setCellValueFactory(new PropertyValueFactory<>("solution"));
		quizImg.setCellValueFactory(new PropertyValueFactory<>("image"));
		quizTable.setItems(quizList.getList());

		quizErrorLabel.textProperty().addListener((observable, oldValue, newValue) -> {
			Timer t = new Timer();
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					Platform.runLater(() -> quizErrorLabel.setText(""));

				}
			}, 2000);

		});
	}

	/**
	 * Function initializes the story tab.
	 */
	private void initStoryTab() {
		this.storyTable.setItems(chapterList.getChapList());
		this.canvasChapter.setVisible(false);

		ContextMenu delConMenu = new ContextMenu();
		MenuItem deleteItem = new MenuItem("Löschen");
		MenuItem editItem = new MenuItem("Bearbeiten");
		delConMenu.getItems().addAll(deleteItem, editItem);
		deleteItem.setOnAction(event -> chapterList.removeChapter(storyTable.getSelectionModel().getSelectedItem()));

		editItem.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/StoryErstellen.fxml"));
				Parent root = loader.load();

				StoryController stCont = loader.getController();

				stCont.setChapter(storyTable.getSelectionModel().getSelectedItem());
				stCont.setChapList(this.chapterList);
				stCont.loadChapter();

				Stage story = new Stage();
				story.setScene(new Scene(root));
				story.setTitle("Story Inhalt");
				story.setResizable(false);
				story.showAndWait();
		} catch (IOException e) {
				e.printStackTrace();
			}});
		storyTable.setContextMenu(delConMenu);
	}
}
