package de.sep.sherloql.story;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author Judy
 * @author Samreen
 * @author Khiet-Nhi
 */
public class Riddle implements Serializable {

    private String type;
    private int image;
    private String question;
    private String answer;
    private ArrayList<Hints> hints;

    /**
     *
     * @param type
     * @param image
     * @param question
     * @param answer
     */
    public Riddle(String type,/* String name, */int image, String question, String answer, ArrayList<Hints> hints) {
        this.type = type;
        //this.name = name;
        this.image = image;
        this.question = question;
        this.answer = answer;
        this.hints = hints;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getImage() {
        return image;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public ArrayList<Hints> getHints() { return hints; }

    public Hints getHint(int position) {
        return hints.get(position);
    }
}
