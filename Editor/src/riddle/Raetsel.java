package riddle;

import java.util.ArrayList;

import org.json.JSONObject;

import javafx.beans.property.SimpleStringProperty;

public class Raetsel {

	Raetsel next = null;
	

	private SimpleStringProperty question;
	private SimpleStringProperty hint1, hint2, hint3;
	private SimpleStringProperty solution, image;
	private String H1_coins;
	private String H2_coins;
	private String H3_coins;
	private String type;

	ArrayList<String> allHints = new ArrayList<String>();
	ArrayList<String> allCoins = new ArrayList<String>();

	public Raetsel(String type, String question, String hint1, String h1_coins, String hint2, String h2_coins, String hint3,
			String h3_coins, String solution, String image) {
		super();
		this.question = new SimpleStringProperty(question);
		this.hint1 = new SimpleStringProperty(hint1);
		this.hint2 = new SimpleStringProperty(hint2);
		this.hint3 = new SimpleStringProperty(hint3);
		this.solution = new SimpleStringProperty(solution);
		this.image = new SimpleStringProperty(image);
		H1_coins = h1_coins;
		H2_coins = h2_coins;
		H3_coins = h3_coins;
		this.type = type;

	}

	public Raetsel() {
	}

	/**
	 * stores all the hints in an arrayList
	 * 
	 * @param r raetsel
	 * @return an arraylist of tpe string containing all the hints
	 */
	public ArrayList<String> getAllHints(Raetsel r) {
		ArrayList<String> temp = new ArrayList<String>();
		if (r.getHint1() != null &&  !r.getHint1().isEmpty() )
			temp.add(r.getHint1());
		if (r.getHint2() != null && !r.getHint2().isEmpty())
			temp.add(r.getHint2());
		if (r.getHint3() != null  && !r.getHint3().isEmpty())
			temp.add(r.getHint3());
		
		return allHints = temp;
	}

	public ArrayList<String> getAllCoins(Raetsel r) {
		if (r.getHint1() != null &&  !r.getHint1().isEmpty() )
			allCoins.add(r.getH1_coins());
		if (r.getHint2() != null && !r.getHint2().isEmpty())
			allCoins.add(r.getH2_coins());
		if (r.getHint3() != null  && !r.getHint3().isEmpty())
			allCoins.add(r.getH3_coins());
		
		return allCoins;
	}
	
	/**
	 * 
	 * @return
	 */
	public JSONObject toJSONObject() {

		JSONObject object = new JSONObject();
		object.put("question", getQuestion());
		object.put("answer", getSolution());
//		object.put("hints", getAllHints());

		return object;
	}

	public String toString() {
		return "Question: " + question.get() + "\n" + "Solution:  " +  solution.get() + "\n";
	}

//-----------------getters and setters-----------------------------
	public Raetsel getNext() {
		return next;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	public String getH1_coins() {
		return H1_coins;
	}

	public void setH1_coins(String h1_coins) {
		H1_coins = h1_coins;
	}

	public String getH2_coins() {
		return H2_coins;
	}

	public void setH2_coins(String h2_coins) {
		H2_coins = h2_coins;
	}

	public String getH3_coins() {
		return H3_coins;
	}

	public void setH3_coins(String h3_coins) {
		H3_coins = h3_coins;
	}

	public void setQuestion(String question) {
		this.question = new SimpleStringProperty(question);
	}

	public void setNext(Raetsel next) {
		this.next = next;
	}

	public String getQuestion() {
		return question.get();
	}

	public String getHint1() {
		return hint1.get();
	}

	public void setHint1(String hint1) {
		this.hint1 = new SimpleStringProperty(hint1);
	}

	public String getHint2() {
		return hint2.get();
	}

	public void setHint2(String hint2) {
		this.hint2 = new SimpleStringProperty(hint2);
	}

	public String getHint3() {
		return hint3.get();
	}

	public void setHint3(String hint3) {
		this.hint3 = new SimpleStringProperty(hint3);
	}

	public String getSolution() {
		return solution.get();
	}

	public void setSolution(String solution) {
		this.solution = new SimpleStringProperty(solution);
	}

	public String getImage() {
		return image.get();
	}

	public void setImage(String image) {
		this.image =new SimpleStringProperty(image);
	}

}