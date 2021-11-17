package story;

import javafx.beans.property.SimpleStringProperty;
import org.json.JSONObject;

public class Dialog {
	private SimpleStringProperty person;
	private SimpleStringProperty bild;
	private SimpleStringProperty position;
	private SimpleStringProperty text;
	
	public Dialog() {
		
	}
	
	public Dialog(String person, String bild, String position, String text) {
		super();
		this.person = new SimpleStringProperty(person);
		this.bild = new SimpleStringProperty(bild) ;
		this.position = new SimpleStringProperty(position);
		this.text = new SimpleStringProperty(text);
	}
	
	public void setDialog(Dialog d) {
		person = new SimpleStringProperty(d.getPerson());
		bild = new SimpleStringProperty(d.getBild());
		position = new SimpleStringProperty(d.getPosition());
		text = new SimpleStringProperty(d.getText());
	}
	
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject(); 
		
		jo.put("person", getPerson());
		jo.put("text", getText());
		jo.put("image", getBild());
		jo.put("position", getPosition());
		
		return jo;
		
	}
	
	public String getPerson() {
		return person.get();
	}

	public String getBild() {
		return bild.get();
	}

	public String getPosition() {
		return position.get();
	}

	public String getText() {
		return text.get();
	}

	public void setPerson(String person) {
		this.person = new SimpleStringProperty(person);
	}

	public void setBild(String bild) {
		this.bild = new SimpleStringProperty(bild);
	}
	
	public void setPosition(String position) {
		this.position = new SimpleStringProperty(position);
	}

	public void setText(String text) {
		this.text = new SimpleStringProperty(text);
	}
}
