package de.sep.sherloql.story;

import java.io.Serializable;

/**
 * @author Judy
 * @author Samreen
 * @author Khiet-Nhi
 */
public class Dialogue implements Serializable {
    private String person;
    private String text;
    private int image;
    private int position;

    public Dialogue(String person, String text, int image, int position) {
        this.person = person;
        this.text = text;
        this.image = image;
        this.position = position;

    }

    public String getPerson() {
        return person;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPosition() {return position;}

    public void setPosition(int position) {this.position = position;}

    public void setPerson(String person) {
        this.person = person;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
