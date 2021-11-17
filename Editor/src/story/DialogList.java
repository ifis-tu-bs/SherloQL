package story;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

public class DialogList {
	
	private ObservableList<Dialog> dList = FXCollections.observableArrayList();

	public DialogList(JSONArray array){
		for(Object i: array){
			JSONObject tmpJObj = (JSONObject) i;
			Dialog tmpDialog = new Dialog();
			tmpDialog.setPerson(tmpJObj.getString("person"));
			tmpDialog.setBild(tmpJObj.getString("image"));
			tmpDialog.setPosition(tmpJObj.getString("position"));
			tmpDialog.setText(tmpJObj.getString("text"));
			this.dList.add(tmpDialog);
		}
	}

	public DialogList(){}

	public void addElement(Dialog elem) {
		dList.add(elem);
	}

	public boolean deleteElement(Dialog d) {
		for(int i = 0; i < dList.size(); i++) {
			if(dList.get(i).equals(d)) {
				dList.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	public ObservableList<Dialog> getList(){
		return this.dList;
	}
	
	public int getPosition(Dialog d) {
		for(int i = 0; i < dList.size(); i++) {
			if(dList.get(i).equals(d)) {
				return i;
			}
		}
		
		return -1;
	}

	public JSONArray getJSONarray(){
		JSONArray dialogue = new JSONArray();
		if(this.dList.isEmpty()){
			return dialogue;
		} else {
			for (Dialog d : dList) {
				dialogue.put(d.toJSON());
			}
			return dialogue;
		}
	}
}
