package de.sep.sherloql.bin;

import java.io.Serializable;

public class Tutorialeinheit implements Serializable {

    private static final String TAG = "Tutorialeinheit";
    private              String name;
    private              String thema;
    private              int benoetigtFuerKapitel;
    private              String benoetigtFuerKapitelText;
    private              String erklaerung;
    private              String beispiel;
    private              String erklaerungBeispiel;

    public Tutorialeinheit(String name, String thema, int benoetigtFuerKapitel, String benoetigtFuerKapitelText, String erklaerung, String beispiel, String erklaerungBeispiel) {
        this.name = name;
        this.thema = thema;
        this.benoetigtFuerKapitel = benoetigtFuerKapitel;
        this.benoetigtFuerKapitelText = benoetigtFuerKapitelText;
        this.erklaerung = erklaerung;
        this.beispiel = beispiel;
        this.erklaerungBeispiel = erklaerungBeispiel;
    }

    public String getName() {
        return name;
    }

    public String getBeispiel() {
        return beispiel;
    }

    public String getErklaerung() {
        return erklaerung;
    }

    public String getErklaerungBeispiel() {
        return erklaerungBeispiel;
    }

    public String getThema() {
        return thema;
    }

    public int getBenoetigtFuerKapitel() {
        return benoetigtFuerKapitel;
    }

    public String getBenoetigtFuerKapitelText() { return benoetigtFuerKapitelText; }
}
