package story;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChapterList {

	private ObservableList<Chapter> chapterList = FXCollections.observableArrayList();
	private ObservableList<String> argsList = FXCollections.observableArrayList();
	

	public void addChapter(Chapter chapter) {
		if(!chapterList.contains(chapter)){
			chapterList.add(chapter);
		}
	}

	public void removeChapter(Chapter chapter) {
		if (chapterList.contains(chapter)) {
			chapterList.remove(chapter);
		}
	}
	
	/**
	 * parses chapterList to JSONObject as String
	 * 
	 * @return String (JSONObject)
	 */
	public String exportList() {
		if (chapterList.size() == 0) {
			return null;
		}

		JSONArray exportList = new JSONArray();

		for (Chapter chapter : chapterList) {
			exportList.put(chapter.toJSONObject());
		}

		return new JSONObject().put("Story", exportList).toString(4);
	}

	/**
	 * reads JSONArray and stores each element
	 * 
	 * @param
	 */
	public void importList(JSONArray chapterJSON) {
		for (int i = 0; i < chapterJSON.length(); i++) {
			Chapter chapter = jsonObjectToChapter(chapterJSON.getJSONObject(i));
			if(!chapterList.contains(chapter)){
				addChapter(chapter);
			}
		}
	}

	private Chapter jsonObjectToChapter(JSONObject obj){
		Chapter chapter = new Chapter();

		chapter.setFlag(obj.getString("flag"));
		chapter.setChapter(obj.getString("chapter"));
		chapter.setPlace(obj.getString("place"));
		chapter.setLat(obj.getString("latitude"));
		chapter.setLon(obj.getString("longitude"));
		chapter.setImage(obj.getString("image"));
		chapter.setCreateArgs(toLinkedList(obj.getJSONArray("dependency").toList()));
		chapter.setDestroyArgs(toLinkedList(obj.getJSONArray("TTL").toList()));
		chapter.setdList(new DialogList(obj.getJSONArray("dialogue")));
		chapter.setHints(jsonArrayToHint(obj.getJSONArray("hints")));
		chapter.setQuestion(obj.getString("question"));
		chapter.setAnswer(obj.getString("answer"));
		chapter.setType(obj.getString("type"));

		List artefacts = obj.getJSONArray("artefact").toList();
		String artefact = "";
		for(Object e: artefacts){
			if(!artefacts.get(0).equals(e)) {
				artefact += ", ";
			}
			artefact += e.toString();
		}
		chapter.setArtefact(artefact);

		return chapter;
	}

	private LinkedList<String> toLinkedList(List<Object> jArray){
		LinkedList<String> temp = new LinkedList<String>();
		for(Object i : jArray){
			temp.add(i.toString());
		}

		return temp;
	}

	public ObservableList<Chapter> getChapList(){
		return this.chapterList;
	}

	public ObservableList<String> getArgsList(){ return  this.argsList;}

	public void addArg(String text) {
		if(!argsList.contains(text)){
			argsList.add(text);
		}
	}

	private Hint jsonArrayToHint(JSONArray jArray){
		JSONObject h1 = jArray.getJSONObject(0),
				h2 = jArray.getJSONObject(1),
				h3 = jArray.getJSONObject(2);
		return new Hint(h1.getString("hint"), h2.getString("hint"), h3.getString("hint"),
				h1.getString("cost"), h2.getString("cost"), h3.getString("cost"));
	}

	public void addArgs(String[] args) {
		for(String i: args){
			if(!argsList.contains(i)) {
				argsList.add(i);
			}
		}
	}
}
