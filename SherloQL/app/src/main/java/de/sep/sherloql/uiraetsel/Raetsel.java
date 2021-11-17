package de.sep.sherloql.uiraetsel;

import java.io.Serializable;
/**
 * Hier werden alle Attribute eines Raetsels und get Methoden deklariert.
 */
public class Raetsel implements Serializable {
    // Attribute deklariert.
    private static final String TAG = "Raetsel";
    private              String type;
    private              String name;
    private              String category;
    private              String difficulty;
    private              String question;
    private              String points;
    private              String answer;
    private              String     image;
    private              Choices choices;
    private boolean solved;
    /**
    * Konstruktur von Klasse Raetsel
    * @param type
    * @param name
     * @param category
    * @param difficulty
    * @param question
    * @param points
    * @param answer
    * @param image
    * @param choices
    */
    public Raetsel(String type, String name, String category, String difficulty,  String question, String points,  String answer, String image, Choices choices, boolean solved) {
        this.type = type;
        this.name = name;
        this.category = category;
        this.difficulty = difficulty;
        this.question = question;
        this.points = points;
        this.answer = answer;
        this.image = image;
        this.choices = choices;
        this.solved = solved;
    }
    /**
    * Hier wird eine Choices Klasse gebraucht, um die vorformulierte
    * Antworten, die  zur Auswahl stehen in Variablen zu speichern.
     */
    public static class Choices implements Serializable {
        //Attribut deklariert.
        private static final String TAG = "Choices";
        private  String choiceOne;
        private  String choiceTwo;
        private  String choiceThree;
        private  String choiceFour;
        /**
         * Konstruktor von Klasse Choices
         * @param choiceOne
         * @param choiceTwo
         * @param choiceThree
         * @param choiceFour
         */
        public Choices(String choiceOne, String choiceTwo, String choiceThree, String choiceFour) {
            this.choiceOne = choiceOne;
            this.choiceTwo = choiceTwo;
            this.choiceThree = choiceThree;
            this.choiceFour = choiceFour;
        }
        /**
         * gibt erster Wahl zurück
         */
        public String getChoiceOne() {
            return choiceOne;
        }
        /**
         * gibt zweiter Wahl zurück
         */
        public String getChoiceTwo() {
            return choiceTwo;
        }
        /**
         * gibt dritter Wahl zurück
         */
        public String getChoiceThree() {
            return choiceThree;
        }
        /**
         * gibt vierter Wahl zurück
         */
        public String getChoiceFour() {
            return choiceFour;
        }
    }
    /**
     * gibt Typ des Raetsels zurück
     */
    public String getType() {
        return type;
    }
    /**
     * gibt Name des Raetsels zurück
     */
    public String getName() {
        return name;
    }
    /**
     * gibt Kategorie des Raetsels zurück
     */
    public String getCategory() {return category; }
    /**
     * gibt Schwierigkeit des Raetsel zurueck
     */
    public String getDifficulty() {
        return difficulty;
    }
    /**
     * gibt Frage des Raetsels zurück
     */
    public String getQuestion() {
        return question;
    }
    /**
     * gibt Punkte des Raetsels zurück
     */
    public String getPoints() {
        return points;
    }
    /**
     * gibt Antwort der Frage zurück
     */
    public String getAnswer()
    {
        return answer;
    }
    /**
     * gibt das Resource von einem Bild zurück
     */
    public String getImage() {
        return image;
    }
    /**
     * gibt ein Objekt von Klasse Choices züruck.
     */

    public Choices getChoices() {
        return choices;
    }

    public void setType(String type) {
        this.type = type;
    }
}
