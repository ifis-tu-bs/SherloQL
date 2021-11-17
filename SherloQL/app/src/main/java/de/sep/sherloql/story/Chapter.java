package de.sep.sherloql.story;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

/**
 * @author Judy
 * @author Samreen
 * @author Khiet-Nhi
 */
public class Chapter implements Serializable {

    private String name;
    private double longitude;
    private double latitude;
    private int flag;
    private ArrayList<Dialogue> dialogueAfterRiddle;
    private ArrayList<Dialogue> dialogueBeforeRiddle;
    private boolean unlocked;
    private boolean solved;
    private Riddle riddle;
    private ArrayList<String> dependencies;
    private ArrayList<String> ttl;
    private ArrayList<String> artefacts;
    private int dialogueImage;


    public Chapter(String name, double longitude, double latitude, int flag, ArrayList<Dialogue> dialogueAfterRiddle, ArrayList<Dialogue> dialogueBeforeRiddle, boolean unlocked, boolean solved, Riddle riddle, ArrayList<String> dependencies, ArrayList<String> ttl, ArrayList<String> artefacts, int dialogueImage) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.flag = flag;
        this.dialogueAfterRiddle = dialogueAfterRiddle;
        this.dialogueBeforeRiddle = dialogueBeforeRiddle;
        this.unlocked = unlocked;
        this.solved = solved;
        this.riddle = riddle;
        this.dependencies = dependencies;
        this.ttl = ttl;
        this.artefacts = artefacts;
        this.dialogueImage = dialogueImage;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlag() {
        return flag;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public Riddle getRiddle() {
        return riddle;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public ArrayList<Dialogue> getDialogueArrayAfter() {
        return dialogueAfterRiddle;
    }

    public ArrayList<Dialogue> getDialogueArrayBefore() {
        return dialogueBeforeRiddle;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public ArrayList<String> getTimeToLive() {
        return ttl;
    }

    public ArrayList<String> getDependencies() {
        return dependencies;
    }

    public ArrayList<String> getArtefacts() {
        return artefacts;
    }

    public ArrayList<String> getTTL() { return ttl; }

    public int getDialogueImage() {
        return dialogueImage;
    }


    public Dialogue getDialogueAfter(int position) {
        return dialogueAfterRiddle.get(position);
    }

    public Dialogue getDialogueBefore(int position) {
        return dialogueBeforeRiddle.get(position);
    }
}