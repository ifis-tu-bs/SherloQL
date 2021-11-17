package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import story.Dialog;
import story.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class StoryController implements Initializable {
	private final DialogList dialogList = new DialogList();
	private ChapterList chapterList = null;

	@FXML
	private AnchorPane chapterSettings, chapterDialogue, chapterRiddle;

	@FXML
	private ComboBox<String> cbPlace, cbDelete, cbCreate, cbRiddleType, cbCost1, cbCost2, cbCost3;

	@FXML
	private Button btnNewArtefact, btnExplorer, btnNext1, btnBack, btnDialog,
			btnEdit, btnDelete, btnNext2, btnBack1, btnBack2, btnSave;

	@FXML
	private RadioButton rbtnImage;

	@FXML
	private ToggleButton btnLastChapter;

	@FXML
	private TextField tfImage, tfName, tfArtefact, tfRiddleAnswer, tfHint1, tfHint2, tfHint3;

	@FXML
	private TextArea tfRiddleQuestion;

	@FXML
	private ListView<String> listViewCreate, listViewDelete;

	@FXML private TableView<Dialog> tvDialog;
	@FXML private TableColumn<Dialog, String> person;
	@FXML private TableColumn<Dialog, String> position;
	@FXML private TableColumn<Dialog, String> text;
	JSONArray jsonArray = new JSONArray();
	
	@FXML private Label lblStatus;
	
	private Chapter chapter = null;
	private String axisLong = null;
	private String axisLat = null;
	
	private final ObservableList<String> places = FXCollections.observableArrayList("Botanischer Garten Eingang",
			"Botanischer Garten Mitte", "TU Braunschweig", "Studentenwerk", "Rathaus", "Burgplatz", "Dom",
			"Neustadt Rathaus", "Alte Waage", "Astor Kino", "Medienhaus", "Altstadtmarkt",
			"Kohlmarkt", "Happy Rizzi Haus", "Stadt Museum", "Loewenwall", "Schloss", "Herzog Anton Ulrich Museum",
			"Staatstheater", "Gewandhaus", "Gaußberg Park", "Universitätsplatz");

	private final ObservableList<String> costs = FXCollections.observableArrayList("1", "2", "3", "4", "5");
	private final ObservableList<String> riddleType = FXCollections.observableArrayList("sql", "answer");

	private final ObservableList<String> argsDelete = FXCollections.observableArrayList();
	private final ObservableList<String> argsCreate = FXCollections.observableArrayList();

	/**
	 * Function initializes the window.
	 * @param arg0 URL
	 * @param arg1 ResourceBundle
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		person.setCellValueFactory(new PropertyValueFactory<>("person"));
		position.setCellValueFactory(new PropertyValueFactory<>("position"));
		text.setCellValueFactory(new PropertyValueFactory<>("text"));
		tvDialog.setItems(dialogList.getList());

		cbPlace.setItems(places);
		cbCost1.setItems(costs);
		cbCost2.setItems(costs);
		cbCost3.setItems(costs);

		cbRiddleType.setItems(riddleType);

		listViewDelete.setItems(argsDelete);
		listViewCreate.setItems(argsCreate);
		initializeTableViews();
	}

	public void setChapter(Chapter chapter){
		this.chapter = chapter;
	}

	/**
	 * Function switch the window to page1.
	 */
	@FXML
	private void page1(){
		chapterSettings.setVisible(true);
		chapterDialogue.setVisible(false);
		chapterRiddle.setVisible(false);
	}

	/**
	 * Function switch the window to page2.
	 */
	@FXML
	private void page2(){
		chapterSettings.setVisible(false);
		chapterDialogue.setVisible(true);
		chapterRiddle.setVisible(false);
	}

	/**
	 * Function switch the window to page3.
	 */
	@FXML
	private void page3(){
		chapterSettings.setVisible(false);
		chapterDialogue.setVisible(false);
		chapterRiddle.setVisible(true);
	}

	/**
	 * Checks if all important elements have input values.
	 * 
	 * @return boolean
	 */
	private boolean checkEntries() {
		if(tfName.getText().equals("") || (rbtnImage.isSelected() && tfImage.getText().equals("")) ||
				cbPlace.getValue() == null){
			lblStatus.setText("Missing entries on page 1");
			return false;
		} else if(tfRiddleQuestion.getText().equals("") || tfRiddleAnswer.getText().equals("") ||
				tfHint1.getText().equals("") || tfHint2.getText().equals("") || tfHint3.getText().equals("") ||
				cbRiddleType.getValue() == null || cbCost1.getValue() == null || cbCost2.getValue() == null ||
				cbCost3.getValue() == null){
			lblStatus.setText("Missing entries on page 3");
			return false;
		}
		return true;

	}

	/**
	 * Function loads a new dialog-Window.
	 */
	@FXML
	private void addDialogue() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/Dialogue.fxml"));
			Parent root = loader.load();
			DialogueController dCont = loader.getController();
			dCont.setDialogList(dialogList);
			Stage dialogue = new Stage();
			dialogue.getIcons().add(new Image("/layout/app_logo_neu.png"));
			dialogue.setScene(new Scene(root));
			dialogue.setTitle("Dialog");
			dialogue.showAndWait();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Function loads the dialog Window and initialize it with the selected dialog entry.
	 */
	@FXML
	private void editEntry() {
		Dialog selected;
		if((selected = tvDialog.getSelectionModel().getSelectedItem()) == null) {
			lblStatus.setText("Nichts gewaehlt");
			return;
		}
			
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/EditDialogue.fxml"));
			
			Parent root = loader.load();
			
			EditDialogueController dController = loader.getController();
			dController.setDialog(selected, dialogList);
			Stage stage = new Stage();
			stage.getIcons().add(new Image("/layout/app_logo_neu.png"));
			stage.setScene(new Scene(root));
			stage.showAndWait();

		} catch (IOException e) {
				e.printStackTrace();
		}
	}

	/**
	 * Function deletes the picked element out of the list.
	 */
	@FXML
	private void deleteElement() {
		if(tvDialog.getSelectionModel().getSelectedItem() == null) {
			lblStatus.setText("Nichts gewaehlt");
			return;
		}

		if (dialogList.deleteElement(tvDialog.getSelectionModel().getSelectedItem())) {
			lblStatus.setText("Element erfolgreich geloescht!");
		} else {
			lblStatus.setText("Element konnte nicht geloescht werden!");
		}
	}

	/**
	 * Function closes the current window.
	 */
	@FXML
	private void closeWindow() {
		Stage stage = (Stage) btnBack.getScene().getWindow();
		stage.close();
	}

	/**
	 * Function saves the current chapter in the chapterList.
	 */
	@FXML
	private void saveContent() {
		
		if(!checkEntries()) {
			return;
		}

		///page1:
		chapter.setChapter(tfName.getText());

		toLongLat(cbPlace.getValue());
		chapter.setPlace(cbPlace.getValue());
		chapter.setLat(axisLat);
		chapter.setLon(axisLong);

		if(rbtnImage.isSelected()) {
			chapter.setImage(tfImage.getText());
		}

		chapter.setArtefact(tfArtefact.getText());
		String artefacts = tfArtefact.getText();
		if(artefacts.contains(", ")){
			chapterList.addArgs(artefacts.split(", "));
		} else {
			chapterList.addArg(artefacts);
		}


		if(btnLastChapter.isSelected()){
			chapter.setFlag("1");
		}


		chapter.setCreateArgs(obsLstToLiLi(argsCreate));
		chapter.setDestroyArgs(obsLstToLiLi(argsDelete));

		/// page2:
		jsonArray = new JSONArray();
		if(dialogList.getList().size() == 0) {
			JSONObject jEmpty = new JSONObject();
			jEmpty.put("person", "");
			jEmpty.put("text", "");
			jEmpty.put("image", "");
			jEmpty.put("position", "");
			jsonArray.put(jEmpty);
		} else {
			dialogList.getList().forEach((dialog) -> jsonArray.put(dialog.toJSON()));
		}
		chapter.setdList(new DialogList(jsonArray));

		///page 3:
		chapter.setQuestion(tfRiddleQuestion.getText());
		chapter.setAnswer(tfRiddleAnswer.getText());
		chapter.setHints(new Hint(tfHint1.getText(), tfHint2.getText(), tfHint3.getText(),
				cbCost1.getValue(), cbCost2.getValue(), cbCost3.getValue()));
		chapter.setType(cbRiddleType.getValue());
		lblStatus.setText("Erfolgreich gespeichert!");

		chapter.setEdited();

		Stage curr = (Stage) btnSave.getScene().getWindow();
		curr.close();
	}

	/**
	 * Function opens an explorer dialog to select an image.
	 */
	@FXML
	private void openImage() {
		FileChooser fcImage = new FileChooser();
		
		fcImage.getExtensionFilters().add(new ExtensionFilter("PNG", "*.png"));
		File selectedFile = fcImage.showOpenDialog(null);

		if (selectedFile != null) {
			tfImage.setText(selectedFile.getName());
		} else {
			lblStatus.setText("Invalid file");
		}
	}

	/**
	 * Function opens an input dialog to add a new argument to the argsList.
	 */
	@FXML
	private void addArgs(){
		TextInputDialog textID = new TextInputDialog("Add an argument...");
		textID.setTitle("Add argument");
		textID.setHeaderText(null);

		Optional<String> newArg = textID.showAndWait();

		newArg.ifPresent(arg -> this.chapterList.addArg(arg));
	}

	/**
	 * Helper function determines the longitude and latitude of the given place.
	 * @param o String
	 */
	private void toLongLat(String o) {
		switch (o) {
			case "Gaußberg Park":
				axisLat = "52.27211956595413";
				axisLong = "10.524287130749073";
				return;

			case "Botanischer Garten Eingang":
				axisLat = "52.270587";
				axisLong = "10.533364";
				return;
			case "Botanischer Garten Mitte":
				axisLat = "52.271223";
				axisLong = "10.531335";
				return;
			case "TU Braunschweig":
				axisLat = "52.273436";
				axisLong = "10.529331";
				return;
			case "Studentenwerk":
				axisLat = "52.265731";
				axisLong = "10.527609";
				return;
			case "Rathaus":
				axisLat = "52.264297";
				axisLong = "10.524797";
				return;
			case "Burgplatz":
				axisLat = "52.264651";
				axisLong = "10.523752";
				return;
			case "Dom":
				axisLat = "52.264277";
				axisLong = "10.523859";
				return;
			case "Neustadt Rathaus":
				axisLat = "52.266312";
				axisLong = "10.521880";
				return;
			case "Alte Waage":
				axisLat = "52.267527";
				axisLong = "10.519888";
				return;
			case "Astor Kino":
				axisLat = "52.266903";
				axisLong = "10.517858";
				return;
			case "Medienhaus":
				axisLat = "52.266125";
				axisLong = "10.517216";
				return;
			case "Altstadtmarkt":
				axisLat = "52.262980";
				axisLong = "10.516911";
				return;
			case "Kohlmarkt":
				axisLat = "52.262478";
				axisLong = "10.519890";
				return;
			case "Happy Rizzi House":
				axisLat = "52.262340";
				axisLong = "10.528774";
				return;
			case "Stadt Museum":
				axisLat = "52.271204";
				axisLong = "10.531759";
				return;
			case "Loewenwall":
				axisLat = "52.259690";
				axisLong = "10.530932";
				return;
			case "Schloss":
				axisLat = "52.263450";
				axisLong = "10.527052";
				return;
			case "Herzog Anton Ulrich Museum":
				axisLat = "52.263679";
				axisLong = "10.532657";
				return;
			case "Staatstheater":
				axisLat = "52.265853";
				axisLong = "10.531325";
				return;
			case "Universitätsplatz":
				axisLat = "52.2734275073803";
				axisLong = "10.529241533208864";
				return;
			default:
				throw new IllegalStateException("Unexpected value: " + o);
		}
  	}

	/**
	 * Getter
	 * @return Chapter
	 */
	public Chapter getSavedChapter() {
		return chapter;
	}

	/**
	 * Helper function disables the image textfield if the radio button is not activated.
	 */
	@FXML
	public void rBtnImgAction() {
		btnExplorer.setDisable(!rbtnImage.isSelected());
		tfImage.setDisable(!rbtnImage.isSelected());

	}

	/**
	 * Function initializes the current chapterList and sets the dependencies.
	 * @param chapterList ChapterList
	 */
	public void setChapList(ChapterList chapterList) {
		this.chapterList = chapterList;

		cbCreate.setItems(chapterList.getArgsList());
		cbDelete.setItems(chapterList.getArgsList());

		cbDelete.setOnAction(event -> {
			{
				if(cbDelete.getValue() != null){
					argsDelete.add(cbDelete.getValue());
				}
				cbDelete.setSelectionModel(cbDelete.getSelectionModel());
			}
		});

		cbCreate.setOnAction(event -> {
			{
				if(cbCreate.getValue() != null && !argsCreate.contains(cbCreate.getValue())){
					argsCreate.add(cbCreate.getValue());
				}
			}

		});
	}

	/**
	 * Helper function initializes the TableView and sets the events.
	 */
	private void initializeTableViews(){
		ContextMenu delConMenu = new ContextMenu();
		MenuItem deleteArg = new MenuItem("Löschen");
		delConMenu.getItems().add(deleteArg);
		deleteArg.setOnAction(event -> argsDelete.remove(listViewDelete.getSelectionModel().getSelectedItem()));

		listViewDelete.setContextMenu(delConMenu);

		ContextMenu crtConMenu = new ContextMenu();
		MenuItem createArg = new MenuItem("Löschen");
		crtConMenu.getItems().add(createArg);
		createArg.setOnAction(event -> argsCreate.remove(listViewCreate.getSelectionModel().getSelectedItem()));

		listViewCreate.setContextMenu(crtConMenu);
	}

	/**
	 * Function parses a observableList into a linkedList. Type: String
	 * @param obsList ObservableList
	 * @return LinkedList
	 */
	private LinkedList<String> obsLstToLiLi(ObservableList<String> obsList){
        return new LinkedList<>(obsList);
	}

	/**
	 * Function fills all entries of this window with the information of the chosen chapter.
	 */
	public void loadChapter() {
		if(this.chapter != null){
			//Page1
			tfName.setText(chapter.getChapter());
			cbPlace.setValue(chapter.getPlace());
			argsCreate.addAll(chapter.getCreateArgs());
			argsDelete.addAll(chapter.getDestroyArgs());
			tfArtefact.setText(chapter.getArtefact());

			if(!chapter.getImage().equals("")){
				rbtnImage.setSelected(true);
				tfImage.setText(chapter.getImage());
			}
			btnLastChapter.setSelected(chapter.getFlag().equals("1"));
			//Page2
			dialogList.getList().addAll(chapter.getdList().getList());

			//Page3
			tfRiddleQuestion.setText(chapter.getQuestion());
			tfRiddleAnswer.setText(chapter.getAnswer());

			tfHint1.setText(chapter.getHints().getHints()[0]);
			tfHint2.setText(chapter.getHints().getHints()[1]);
			tfHint3.setText(chapter.getHints().getHints()[2]);
			cbCost1.setValue(chapter.getHints().getCosts()[0]);
			cbCost2.setValue(chapter.getHints().getCosts()[1]);
			cbCost3.setValue(chapter.getHints().getCosts()[2]);

			cbRiddleType.setValue(chapter.getType());

			chapter.getCreateArgs().forEach(i -> chapterList.addArg(i));
			chapter.getDestroyArgs().forEach(i -> chapterList.addArg(i));
			if(!chapter.getArtefact().equals("")) {
				chapterList.addArgs(chapter.getArtefact().split(", "));
			}
		}
	}
}
