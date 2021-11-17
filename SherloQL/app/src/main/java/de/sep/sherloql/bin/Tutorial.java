package de.sep.sherloql.bin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import de.sep.sherloql.R;
import de.sep.sherloql.savestate.SaveStateHelper;


public class Tutorial extends AppCompatActivity {
    Button home;
    private ArrayList<Tutorialeinheit> tutorialeinheitArrayList = new ArrayList<>();
    private RecyclerView recyclerViewTutorial;
    private TutorialListAdapter tutAdapter;
    private SaveStateHelper stateHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        stateHelper = new SaveStateHelper(this);

        //deutsch oder englisch
        if (stateHelper.getLanguage() == 0) {
            initTutorials();
        } else {
            initTutorialsEnglish();
        }


        recyclerViewTutorial = findViewById(R.id.rvListTutorial);
        recyclerViewTutorial.setLayoutManager(new LinearLayoutManager(this));

        tutAdapter = new TutorialListAdapter(this, tutorialeinheitArrayList);
        recyclerViewTutorial.setAdapter(tutAdapter);
    }

    private void initTutorials() {
        tutorialeinheitArrayList.add(new Tutorialeinheit("Lektion 1", "SELECT FROM",
                1, "Benötigt ab Kapitel: ",
                "Der SELECT FROM Befehl ist der Grundbau- stein einer SQL-Anfrage. Mit " +
                        "ihm kann man sich beliebig viele Spalten einer Tabelle ausgeben lassen. " +
                        "Schreibt man anstelle einer spezifischen Spalte das Zeichen *, so wird " +
                        "die komplette Tabelle ausgegeben.",
                "Beispiel 1:\nSELECT name, alter FROM personen;\n\n" +
                        "Beispiel 2:\nSELECT  *  FROM orte;",
                "Im ersten Beispiel wird die Abfrage an die Tabelle \"personen\" " +
                        "erstellt, wobei nur Name und Alter jeder Person ausgegeben wird. Die " +
                        "Namen der Spalten müssen dabei tatsächliche Spaltennamen der Tabelle " +
                        "sein. Im zweiten Beispiel wird die komplette Tabelle \"orte\" aus" +
                        "gegeben."));

        tutorialeinheitArrayList.add(new Tutorialeinheit("Lektion 2", "WHERE Teil 1",
                2, "Benötigt ab Kapitel: ",
                "WHERE ist ein weiterer grundlegender Bestandteil einer SQL Abfrage. " +
                        "Bei Verwendung wird er hinter SELECT FROM angehangen. " +
                        "Damit ist es möglich, aus einer abgefragten Tabelle nur bestimmte " +
                        "Zeilen zu erhalten, die Ergebnisse also nach Eigenschaften zu " +
                        "filtern. Dies geschieht mittels Boolscher Algebra.",
                "Beispiel 1:\nSELECT * FROM orte WHERE plz = 38100;\n\n" +
                        "Beispiel 2:\nSELECT vorname FROM personen WHERE alter > 17;",
                "In dem ersten Beispiel werden alle Einträge der Tabelle \"orte\" " +
                        "angezeigt, die die Postleitzahl 38100 haben. Mit dem '=' Operator " +
                        "lassen sich also Spalteneinträge mit Zahlenwerten oder Worten " +
                        "vergleichen.\n Im zweiten Beispiel wird das Alter aller Personen " +
                        "aus der Liste \"personen\" mit dem Wert 17 verglichen. " +
                        "Das Resultat zeigt alle Vornamen aus der Liste, deren Alter " +
                        "mindestens 18 ist."));

        tutorialeinheitArrayList.add(new Tutorialeinheit("Lektion 3", "WHERE Teil 2",
                2, "Benötigt ab Kapitel: ",
                "Um Anfragen mit WHERE noch weiter einzuschränken oder zu erweitern" +
                        "kann man mehrere boolsche Ausdrücke verwenden. Dies geht mittels AND und" +
                        "OR, indem man sie zwischen boolschen Ausdrücken platziert.",
                "Beispiel 1:\nSELECT * FROM orte WHERE plz = '38100' OR plz = '38106';\n\n" +
                        "Beispiel 2:\nSELECT * FROM personen WHERE alter = '56' AND beruf = 'Professor';",
                "Im ersten Beispiel wird in der Tabelle \"orte\" nach allen" +
                        "Elementen gesucht, deren Postleitzahl 38100 oder 38106 ist.\n Im " +
                        "zweiten Beispiel wird in der Tabelle \"personen\" nach Elementen" +
                        "gesucht, die 56 Jahre alt sind und als Professor arbeiten."));

        tutorialeinheitArrayList.add(new Tutorialeinheit("Lektion 4", "WHERE Teil 3",
                2, "Benötigt ab Kapitel: ",
                "Der Ausdruck WHERE lässt über die boolschen Ausdrücke " +
                        "hinaus auch Musterbasierte Suche zu. Diese wird mit dem dem Ausdruck " +
                        "LIKE benutzt. Ersetzungen erfolgen mit '%' bei dem sich beliebig " +
                        "viele Zeichen ersetzen lassen oder mit '_' bei dem sich ein Zeichen " +
                        "ersetzen lässt. Wird hinter WHERE der Ausdruck BETWEEN verwendet, " +
                        "so kann man Bereiche angeben, die ausgegeben werden sollen.",
                "Beispiel 1:\nSELECT * FROM personen WHERE vorname LIKE 'T_M;\n\n" +
                        "Beispiel 2:\nSELECT * FROM personen WHERE alter BETWEEN 0 AND 18;",
                "Im ersten Beispiel werden alle Vornamen aus der Tabelle " +
                        "\"personen\" mit ihrem Vornamen überprüft. Die Abfrage gibt alle " +
                        "Personen aus, deren Name Tim oder Tom ist.\n Im zweiten Beispiel " +
                        "werden alle Personen auf ihr Alter überprüft, ist dieses zwischen " +
                        "0 und 18, so wird die Person ausgegeben. Dabei ist zu beachten, " +
                        "dass die Grenzen mit eingeschlossen sind."));

        tutorialeinheitArrayList.add(new Tutorialeinheit("Lektion 5", "JOIN ON",
                6, "Benötigt ab Kapitel: ",
                "JOIN ON wird dazu verwendet, Zeilen zweier oder mehr Tabellen zu " +
                        "kombinieren. Dies geschieht anhand von vergleichbaren Spalteneinträgen. " +
                        "Genau wie WHERE wird JOIN hinter SELECT FROM angehängt. Nach JOIN wird " +
                        "die zu kombinierende Tabelle ausgewählt und nach ON die zu vergleichenden " +
                        "Spalten durch '='.",
                "Beispiel:\nSELECT vorname, alias FROM personen JOIN orte ON wohnort = alias;",
                "In dem Beispiel werden die Tabellen \"personen\" und \"orte\" gejoint. " +
                        "Dazu werden die Einträge wohnort der jeweiligen Person mit den \"alias\" " +
                        "Namen der Orte verglichen. Gibt es hier also Überschneidungen beider " +
                        "Tabellen, so werden von diesen die Vornamen und der Alias des Ortes " +
                        "ausgegeben."));

        tutorialeinheitArrayList.add(new Tutorialeinheit("Lektion 6", "INSERT INTO",
                4, "Benötigt ab Kapitel: ",
                "INSERT INTO ist, wie der SELECT Befehl auch, ein Teil der Daten " +
                        "Manipulatons Sprache, kurz DML. Mit ihm kann man neue Einträge in eine " +
                        "bereits vorhandene Tabelle einfügen. Dazu muss man ein Tupel, bestehend" +
                        "aus allen Attributen einer Tabelle angeben.",
                "Beispiel:\nINSERT INTO orte VALUES ('TUBS','Uni','Universitätsplatz','2','38092');",
                "In dem Beispiel wird in die Tabelle \"orte\n ein neuer " +
                        "Eintrag verfasst mit dem Inhalt alias=TUBS, besonderheit=Uni," +
                        "strasse=Universitätsplatz, hausnr=2 und der plz=38092"));

        tutorialeinheitArrayList.add(new Tutorialeinheit("Lektion 7", "UPDATE",
                3, "Benötigt ab Kapitel: ",
                "Auch UPDATE ist Teil der DML. Damit ist es möglich, bereits vorhandene " +
                        "Einträge einer Tabelle zu bearbeiten. Um nicht alle Einträge zu verändern, " +
                        "kann man wieder mittels boolscher Ausdrücke Eigenschaften Filtern.",
                "Beispiel:\nUPDATE personen SET nachname = 'Müller' WHERE nachname = 'Schmidt' AND" +
                        "vorname = 'Rico';",
                "Im Beispiel wird der Nachname aller Personen aus der" +
                        "Tabelle \"personen\" zu \"Müller\" verändert, die mit Vornamen Rico" +
                        "und mit Nachname Schmidt heißen."));

        tutorialeinheitArrayList.add(new Tutorialeinheit("Lektion 8", "DELETE FROM",
                5, "Benötigt ab Kapitel: ",
                "DELETE FROM ist dafür nötig, Zeilen, also Einträge, aus einer Tabelle " +
                        "zu löschen. Dazu benötigt der Ausdruck die Tabelle, in der Elemente " +
                        "gelöscht werden und Eigenschaften von diesen Elementen. Hierbei ist zu " +
                        "beachten, dass ALLE Elemente aus der Tabelle gelöscht, die den" +
                        "Eigenschaften entsprechen, es ist also besondere Vorsicht geboten.",
                "Beispiel:\nDELETE FROM personen WHERE alter < 0;",
                "Im Beispiel werden alle Personen aus der Tabelle \"personen\"" +
                        "gelöscht, deren Alter negativ ist."));

    }

    private void initTutorialsEnglish() {
        tutorialeinheitArrayList.add(new Tutorialeinheit("Lecture 1", "SELECT FROM",
                1,"Required from chapter: ",
                "The SELECT FROM statement is the base of an SQL-Query. With it " +
                        "you can select any column from a table. Instead of naming a special column, " +
                        "you could also use the characer *, then the whole table will be selected.",
                "Example 1:\nSELECT name, age FROM persons;\n\n" +
                        "Example 2:\nSELECT  *  FROM places;",
                "In the first example the query selects all the data from the " +
                        "columns \"name\" and \"age\" from the table \"persons\" " +
                        "In the second example the whole table \"places\" is selected"));

        tutorialeinheitArrayList.add(new Tutorialeinheit("Lecture 2", "WHERE Teil 1",
                2, "Required from chapter: ",
                "The WHERE clause is used to filter records. " +
                        "It is used to extract only those records that fulfill a specified condition.",
                "Example 1:\nSELECT * FROM places WHERE postcode = 38100;\n\n" +
                        "Example 2:\nSELECT first_name FROM persons WHERE age > 17;",
                "In the first example alle records of the table \"places\" "+
                        "are selected, that have a postcode of 38100. With the '=' operator" +
                        "you can compare all column entries with figures or strings.\n" +
                        "The second example selects the first names from the table \"persons\" " +
                        "that are 17 or older."));

        tutorialeinheitArrayList.add(new Tutorialeinheit("Lecture 3", "WHERE Teil 2",
                2, "Required from chapter: ",
                "To restrict the WHERE condition even more, conditions can be linked. " +
                        "This can be done with the \"AND\", \"OR\" and \"NOT\" operators.\n" +
                        "The \"AND\" operator displays a record if all the conditions separated by \"AND\" are TRUE.\n" +
                "The \"OR\" operator displays a record if any of the conditions separated by \"OR\" is TRUE.\n" +
                "The \"NOT\" operator displays a record if the condition(s) is NOT TRUE.",
                "Example 1:\nSELECT * FROM places WHERE plz = '38100' OR postcode = '38106';\n\n" +
                        "Example 2:\nSELECT * FROM persons WHERE age = '56' AND profession = 'Professor';",
                "The first example query searches in the table \"places\" for all records " +
                        "that have a postcode of 38100 or 38106.\n" +
                        "In the second example the query searches in the table \"persons\" for persons, " +
                        "are 56 years old or work as a professor"));

        tutorialeinheitArrayList.add(new Tutorialeinheit("Lecture 4", "WHERE Teil 3",
                2,"Required from chapter: ",
                "In the WHERE statement it is also possible to search for a specified pattern in a column. " +
                        "This can be done with the \"LIKE\" operator.\n" +
                        "There are two wildcards often used in conjunction with the LIKE operator:\n" +
                        "The percent sign % represents zero, one, or multiple characters" +
                        "The underscore sign _ represents one single character. The both wildcards "+
                        "can be used in combination.\n" +
                        "There is also a BETWEEN operator. This selects values within " +
                        "a given range. The values can be numbers, text, or dates.",
                "Example 1:\nSELECT * FROM persons WHERE first_name LIKE 'T_M;\n\n" +
                        "Example 2:\nSELECT * FROM persons WHERE age BETWEEN 0 AND 18;",
                "In the first example all the first names form the table \"persons\" "+
                        "are selected that are 3 characters long, start with a \"t\" and end with an \"m\"\n " +
                        "In the second example the age of all persons is checked. If their age is between 0 and 18, they are selected. " +
                        "The \"BETWEEN\" operator is inclusive, so if a person is 0 or 18 years old, they are selected too."));

        tutorialeinheitArrayList.add(new Tutorialeinheit("Lecture 5", "JOIN ON",
                6, "Required from chapter: ",
                "A \"JOIN ON\" clause is used to combine rows from two or more tables, based on a related column between them. " +
                "After the JOIN the table that should be combined is selected and after ON  the the related columns are named with an \"=\" operator in between.",
                "Example:\nSELECT first_name, alias FROM persons JOIN places ON place_of_residence = alias;",
                "In this example the tables \"persons\" and \"places\" are joint." +
                        "For this the entries of the place of residence of a person is " +
                        "compared with the alias of a place. If there is an overlap " +
                        "between the two tables, the first names and the alias of the place are output from them."));

        tutorialeinheitArrayList.add(new Tutorialeinheit("Lecture 6", "INSERT INTO",
                4, "Required from chapter: ",
                "The INSERT INTO statement is used to insert new records in a table and is " +
                        "also a part of the Data Manipulition Language DML. " +
                        "To insert data values for all columns must be specified, make sure " +
                        "the order od the values is the same order as the columns",
                "Example:\nINSERT INTO places VALUES ('TUBS','Uni','Universitätsplatz','2','38092');",
                "In the example a new record will be inserted in the table \"places\" " +
                        "with the values alias=TUBS, special_feature=Uni," +
                        "street=Universitätsplatz, house_number=2 und der postcode=38092"));

        tutorialeinheitArrayList.add(new Tutorialeinheit("Lecture 7", "UPDATE",
                3, "Required from chapter: ",
                "Also UPDATE is a part of the DML. The UPDATE statement is " +
                        "used to modify the existing records in a table. To change just specific records, " +
                        "certain ones can be extracted with the WHERE statement.",
                "Example:\nUPDATE persons SET last_name = 'Müller' WHERE last_name = 'Schmidt' AND " +
                        "first_name = 'Rico';",
                "In the example the last name of all persons, that are named Rico as " +
                        "first name and Schmidt as last name, are changed to Müller."));

        tutorialeinheitArrayList.add(new Tutorialeinheit("Lecture 8", "DELETE FROM",
                5, "Required from chapter: ",
                "The DELETE FROM statement is used to delete existing records in a table. " +
                        "To delete records the statement needs the table from where records should be deleted " +
                        "and a specific condition. ALL the records from the table that " +
                        "fulfil the condition are going to be deleted, so be careful.",
                "Example:\nDELETE FROM persons WHERE age < 0;",
                "In the example all records from the table \"persons\" with a negative age are going to be deleted."));

    }

}
