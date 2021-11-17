package story;

import javafx.beans.property.SimpleStringProperty;

public class ChapterProperty {
	private SimpleStringProperty chapter;
	private SimpleStringProperty dependency1;
	private SimpleStringProperty dependency2;
	private SimpleStringProperty ort;
	private SimpleStringProperty riddle;
	private SimpleStringProperty answer;
	
	public ChapterProperty(){
		
	}
	
	public ChapterProperty(SimpleStringProperty kap, SimpleStringProperty dep1, SimpleStringProperty dep2, SimpleStringProperty place, SimpleStringProperty raetsel, SimpleStringProperty raetselantwort) {
		this.chapter = kap;
		this.dependency1 = dep1;
		this.dependency2 = dep2;
		this.ort = place;
		this.riddle = raetsel;
		this.answer = raetselantwort;
	}
	
/*	public void setChapter(Chapter c) {
		this.chapter = new SimpleStringProperty(c.getChaptername());
		this.dependency1 = new SimpleStringProperty(c.getDependency1());
		this.dependency2 = new SimpleStringProperty(c.getDependency2());
		this.ort = new SimpleStringProperty(c.getPlace());
		this.riddle = new SimpleStringProperty(c.getQuestion());
		this.answer = new SimpleStringProperty(c.getAnswer());
		
	}*/

	public String getChapter() {
		return chapter.get();
	}

	public void setChapter(String chapter) {
		this.chapter.set(chapter);
	}

	public String getDependency1() {
		return dependency1.get();
	}

	public void setDependency1(String dependency1) {
		this.dependency1.set(dependency1);
	}
	
	public String getDependency2() {
		return dependency2.get();
	}
	
	public void setDependency2(String dependency2) {
		this.dependency2 = new SimpleStringProperty(dependency2);
	}

	public String getOrt() {
		return ort.get();
	}

	public void setOrt(String ort) {
		this.ort.set(ort);
	}

	public String getRiddle() {
		return riddle.get();
	}

	public void setRiddle(String riddle) {
		this.riddle.set(riddle);
	}

	public String getAnswer() {
		return answer.get();
	}

	public void setAnswer(String answer) {
		this.answer.set(answer);
	}
}
