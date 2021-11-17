package de.sep.sherloql.story;

import java.util.ArrayList;

/**
 * @author JudyAlchaar
 */
public class Story {

    private ArrayList<Chapter> chapterArrayList;

    public Story(ArrayList<Chapter> chapterArrayList) {
        this.chapterArrayList = chapterArrayList;
    }

    public ArrayList<Chapter> getChapterArrayList() {
        return chapterArrayList;
    }
}
