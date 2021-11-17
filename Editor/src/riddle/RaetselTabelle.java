package riddle;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RaetselTabelle {
	public ObservableList<Raetsel> list = FXCollections.observableArrayList();
	public ArrayList<Integer> tmp_store = new ArrayList<Integer>();

	/**
	 * adds an Raetselelement to the end of the list -> one way linked list
	 * 
	 * @param element to add
	 */
	public void addElement(Raetsel element) {
		list.add(element);
	}

	/**
	 * Deletes the desired Raetselelement from the the list
	 * 
	 * @param raetsel
	 * @return a boolean, true if the element got deleted
	 */
	public boolean deleteElement(Raetsel raetsel) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(raetsel)) {
				list.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * parses RaetselTabelle to JSONObject as String
	 * 
	 * @return String of the JSONObject
	 */
	public String exportTabelle() {
		if (list.size() == 0)
			return "";

		JSONArray quizList = new JSONArray();

		for (Raetsel i : list)
			quizList.put(i.toJSONObject());

		return new JSONObject().put("Raetsel", quizList).toString(4);
	}

	public ObservableList<Raetsel> getRaetselTabelle() {
		return this.list;
	}

	public ArrayList<Integer> getTmp_store() {
		return tmp_store;
	}

}
