package riddle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class QuizList {

	private ObservableList<QuizElement> list = FXCollections.observableArrayList();

	/**
	 * adds an QuizElement element to the end of the list -> one way linked list
	 * 
	 * @param element to add
	 */
	public void addElement(QuizElement element) {
		list.add(element);
	}

	/**
	 * parses QuizList to JSONObject as String
	 * 
	 * @return String aka JSONObject
	 */
	public String exportList() {
		if (list.size() == 0) {
			return "";
		}

		JSONArray quizList = new JSONArray();

		for (QuizElement i : list) {
			quizList.put(i.toJSONObject());
		}

		return new JSONObject().put("Raetsel", quizList).toString(4);
	}

	/**
	 * reads JSONArray and stores each element
	 * 
	 * @param fileName
	 */
	public void importList(JSONArray input) {
		for (int i = 0; i < input.length(); i++) {
			addElement(json2QuizList(input.getJSONObject(i)));
		}
	}

	/**
	 * parses JSONObject to QuizElement
	 * 
	 * @param object JSONOnject
	 * @return element QuizElement
	 */
	public QuizElement json2QuizList(JSONObject object) {
		
		if(object == null) {
			return null;
		}
		
		QuizElement element = new QuizElement();
		element.setType(object.getString("type"));
		element.setDifficulty(object.getString("difficulty"));
		element.setPoints(object.getString("points"));
		element.setName(object.getString("name"));
		element.setQuestion(object.getString("question"));
		element.setSolution(object.getString("answer"));
		element.setImage(object.getString("image").equals("null") ? "" : object.getString("image"));

		JSONArray array = object.getJSONArray("choices");

		element.setOption0(array.getString(0).equals("null") ? "" : array.getString(0));
		element.setOption1(array.getString(1).equals("null") ? "" : array.getString(1));
		element.setOption2(array.getString(2).equals("null") ? "" : array.getString(2));
		element.setOption3(array.getString(3).equals("null") ? "" : array.getString(3));

		return element;
	}


	/**
	 * deletes the given element
	 * @param element to delete
	 * @return true, if element got deleted, else false
	 */
	public boolean deleteElement(QuizElement element) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).equals(element)) {
				list.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * returns the observable list
	 * @return list
	 */
	public ObservableList<QuizElement> getList(){
		return this.list;
	}
	
	public int getPosition(QuizElement element) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).equals(element)) {
				return i;
			}
		}
		
		return -1;
	}
}
