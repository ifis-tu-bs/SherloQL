package de.sep.sherloql.story;

import java.io.Serializable;

/**
 * @author Judy
 * @author Samreen
 * @author Khiet-Nhi
 */
public class Hints implements Serializable {
/**
     *
     */
    private String hint;
    private int price;
    boolean used;

    public Hints(String hint, int price, boolean used) {
        this.hint = hint;
        this.price = price;
        this.used = used;
    }

    public String getHint() {
        return hint;
    }

    public int getPrice() {
        return price;
    }

    public boolean isUsed() {
        return used;
    }
}
