package de.sep.sherloql.bin;

import java.io.Serializable;

public class Pinboardobjekt implements Serializable {

    private static final String TAG = "Pinboardobjekt";
    private              String name;
    private              String image;

    public Pinboardobjekt(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

}
