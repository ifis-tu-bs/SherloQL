package riddle;

import java.util.Arrays;

import org.json.JSONObject;

import javafx.beans.property.SimpleStringProperty;

public class QuizElement {
	
	private SimpleStringProperty type = new SimpleStringProperty(), name = new SimpleStringProperty(),
			difficulty = new SimpleStringProperty(), image = new SimpleStringProperty(),
			question = new SimpleStringProperty(), coins = new SimpleStringProperty(),
			option0 = new SimpleStringProperty(), option1 = new SimpleStringProperty(),
			option2 = new SimpleStringProperty(), option3 = new SimpleStringProperty(),
			solution = new SimpleStringProperty();

	// constructors //

	public QuizElement(String type, String name, String diffic, String image, String question, String coins,
			String option0, String option1, String option2, String option3, String solution) {
		this.type.setValue(type);
		this.name.setValue(name);
		this.difficulty.setValue(diffic);
		this.image.setValue(image);
		this.question.setValue(question);
		this.coins.setValue(coins);
		this.option0.setValue(option0);
		this.option1.setValue(option1);
		this.option2.setValue(option2);
		this.option3.setValue(option3);
		this.solution.setValue(solution);
	}

	public QuizElement() {

	}

	// functions //

	/**
	 * returns formatted string to print
	 */
	public String toString() {
		return type.get() + "\n" + name.get() + "\n" + difficulty.get() + "\n" + image.get() + "\n" + question.get()
				+ "\n" + coins.get() + "\n"
				+ Arrays.toString(new String[] { option0.get(), option1.get(), option2.get(), option3.get() }) + "\n"
				+ solution.get() + "\n\n";

	}

	/**
	 * 
	 * @return
	 */
	public JSONObject toJSONObject() {
		JSONObject object = new JSONObject();
		object.put("type", type.get())
				.put("name", name.get())
				.put("difficulty", difficulty.get())
				.put("image", image.get().equals("") ? "null" : image.get())
				.put("points", coins.get())
				.put("choices",
						new String[] { option0.get().equals("") ? "null" : option0.get(),
								option1.get().equals("") ? "null" : option1.get(),
								option2.get().equals("") ? "null" : option2.get(),
								option3.get().equals("") ? "null" : option3.get() })
				.put("answer", solution.get()).put("question", question.get());

		return object;
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public boolean equals(QuizElement input) {
		if (!this.type.get().equals(input.getType()))
			return false;
		if (!this.name.get().equals(input.getName()))
			return false;
		if (!this.difficulty.get().equals(input.getDifficulty()))
			return false;
		if (!this.image.get().equals(input.getImage()))
			return false;
		if (!this.coins.get().equals(input.getCoins()))
			return false;
		if (!this.option0.get().equals(input.getOption0()))
			return false;
		if (!this.option1.get().equals(input.getOption1()))
			return false;
		if (!this.option2.get().equals(input.getOption2()))
			return false;
		if (!this.option3.get().equals(input.getOption3()))
			return false;
		if (!this.solution.get().equals(input.getSolution()))
			return false;
		if (!this.question.get().equals(input.getQuestion()))
			return false;
		return true;
	}

	// getters and setters //

	public String getType() {
		return type.get();
	}

	public void setType(String type) {
		this.type.setValue(type);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.setValue(name);
	}

	public String getDifficulty() {
		return difficulty.get();
	}

	public void setDifficulty(String difficulty) {
		this.difficulty.setValue(difficulty);
	}

	public String getImage() {
		return image.get();
	}

	public void setImage(String image) {
		this.image.setValue(image);
	}

	public String getQuestion() {
		return question.get();
	}

	public void setQuestion(String question) {
		this.question.setValue(question);
	}

	public String getPoints() {
		return coins.get();
	}

	public void setPoints(String points) {
		this.coins.setValue(points);
	}

	public String getOption0() {
		return option0.get();
	}

	public void setOption0(String option0) {
		this.option0.setValue(option0);
	}

	public String getOption1() {
		return option1.get();
	}

	public void setOption1(String option1) {
		this.option1.setValue(option1);
	}

	public String getOption2() {
		return option2.get();
	}

	public void setOption2(String option2) {
		this.option2.setValue(option2);
	}

	public String getOption3() {
		return option3.get();
	}

	public void setOption3(String option3) {
		this.option3.setValue(option3);
	}

	public String getCoins() {
		return coins.get();
	}

	public void setCoins(String coins) {
		this.coins.setValue(coins);
	}

	public String getSolution() {
		return solution.get();
	}

	public void setSolution(String solution) {
		this.solution.setValue(solution);
	}
}
