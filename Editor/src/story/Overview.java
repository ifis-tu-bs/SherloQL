package story;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Overview {
	
	private SimpleStringProperty Chapter;
	private SimpleStringProperty Dependency;
	private SimpleStringProperty Place;
	private SimpleBooleanProperty Dialogue;
	private SimpleStringProperty Riddle;
	private SimpleStringProperty RiddleAnswer;
	
	public Overview(String chapter, String dependency, String place, boolean dialogue, String riddle, String riddleAnswer) {
		super();
		Chapter = new SimpleStringProperty(chapter);
		Dependency = new SimpleStringProperty(dependency);
		Place = new SimpleStringProperty(place);
		Dialogue = new SimpleBooleanProperty(dialogue);
		Riddle = new SimpleStringProperty(riddle);
		RiddleAnswer = new SimpleStringProperty(riddleAnswer);
	}

	public SimpleStringProperty getChapter() {
		return Chapter;
	}

	public void setChapter(SimpleStringProperty chapter) {
		Chapter = chapter;
	}

	public SimpleStringProperty getDependency() {
		return Dependency;
	}

	public void setDependency(SimpleStringProperty dependency) {
		Dependency = dependency;
	}

	public SimpleStringProperty getPlace() {
		return Place;
	}

	public void setPlace(SimpleStringProperty place) {
		Place = place;
	}

	public SimpleBooleanProperty isDialogue() {
		return Dialogue;
	}

	public void setDialogue(SimpleBooleanProperty dialogue) {
		Dialogue = dialogue;
	}

	public SimpleStringProperty getRiddle() {
		return Riddle;
	}

	public void setRiddle(SimpleStringProperty riddle) {
		Riddle = riddle;
	}
	
	public SimpleStringProperty getRiddleAnswer() {
		return RiddleAnswer;
	}

	public void setRiddleAnswer(SimpleStringProperty riddleAnswer) {
		RiddleAnswer = riddleAnswer;
	}
	
}
