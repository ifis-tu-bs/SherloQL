package de.sep.sherloql.database.database_logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;

/**
 * @author Christian
 *
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "DBOpenHelper";

    private final String TABLE_PERSONEN = "personen";
    private final String PERSONEN_COLUMN_VORNAME = "vorname";
    private final String PERSONEN_COLUMN_NACHNAME = "nachname";
    private final String PERSONEN_COLUMN_GEBURTSDATUM = "geburtsdatum";
    private final String PERSONEN_COLUMN_BERUF = "beruf";
    private final String PERSONEN_COLUMN_ID = "person_id";
    private final String PERSONEN_COLUMN_TNR = "telefonnr";

    private final String TABLE_TERMINKALENDER = "terminkalender";
    private final String TERMINKALENDER_COLUMN_UHRZEIT_BEGIN = "tk_begin";
    private final String TERMINKALENDER_COLUMN_UHRZEIT_ENDE = "tk_ende";
    private final String TERMINKALENDER_COLUMN_TAETIGKEIT = "tk_taetigkeit";

    private final String TABLE_ABTEILUNG = "abteilung";
    private final String ABTEILUNG_COLUMN_ABTEILUNG = "abt_name";
    private final String ABTEILUNG_COLUMN_ETAGE = "abt_etage";

    private final String TABLE_PERSON_IN_ABTEILUNG = "person_in_abteilung";
    private final String PERSON_IN_ABTEILUNG_COLUMN_PERSON = "pia_person";
    private final String PERSON_IN_ABTEILUNG_COLUMN_ABTEILUNG = "pia_abteilung";

    //Kapitel Pastor David
    private final String TABLE_GOTTESDIENSTE = "gottesdienste";
    private final String GD_ID = "gd_id";
    private final String GD_BEGIN = "gd_begin";
    private final String GD_ENDE = "gd_ende";
    private final String GD_PASTOR = "gd_pastor";

    private final String TABLE_TEILNAHME_GD = "teilnahme_gottesdienst";
    private final String TEILNAHME_GD_PERSON = "tag_person";//gottesdienst teilnehmer
    private final String TEILNAHME_GD_ID = "tag_gd_id";


    //Kapitel Rico
    private final String TABLE_FILME = "filme";
    private final String FILM_NAME = "film_name";
    private final String FILM_WTAG = "film_wochentag";
    private final String FILM_ZEIT = "film_zeit";

    //Kapitel Gärtnerin
    private final String TABLE_ARBEITSPLAN = "arbeitsplan";
    private final String ARBEITSPLAN_PERSON = "ap_person";
    private final String ARBEITSPLAN_UHRZEIT = "ap_zeit";
    private final String ARBEITSPLAN_TAETIGKEIT = "ap_taetigkeit";
    private final String ARBEITSPLAN_BEMERKUNG = "ap_bemerkung"; //...getauscht mit...

    //Kapitel Prof+Yvonne
    private final String TABLE_KREDITKARTENABRECHNUNG = "kreditkartenabrechnung";
    private final String KREDITKARTENABRECHNUNG_ID = "kka_id";
    private final String KREDITKARTENABRECHNUNG_DATUM = "kka_datum";
    private final String KREDITKARTENABRECHNUNG_BETRAG = "kka_betrag";
    private final String KREDITKARTENABRECHNUNG_SENDER = "kka_sender";
    private final String KREDITKARTENABRECHNUNG_EMPFAENGER = "kka_empfaenger";
    private final String KREDITKARTENABRECHNUNG_BEMERKUNG = "kka_bemerkung";



    private final String CREATE_TABLE_PERSON =
        "CREATE TABLE " + TABLE_PERSONEN +
        "( " + PERSONEN_COLUMN_ID + " INTEGER PRIMARY KEY"  + ",\n"
        + PERSONEN_COLUMN_BERUF + " Text " + ",\n"
        + PERSONEN_COLUMN_GEBURTSDATUM + " Text " + ",\n"
        + PERSONEN_COLUMN_NACHNAME + " Text " + ",\n"
        + PERSONEN_COLUMN_VORNAME + " Text " + ",\n"
        + PERSONEN_COLUMN_TNR + " Text "
        + ");\n";

    private final String CREATE_TABLE_TERMINKALENDER= "CREATE TABLE " + TABLE_TERMINKALENDER +
        "( " + TERMINKALENDER_COLUMN_TAETIGKEIT + " TEXT PRIMARY KEY" + ",\n"
        + TERMINKALENDER_COLUMN_UHRZEIT_BEGIN + " INTEGER " + ",\n"
        + TERMINKALENDER_COLUMN_UHRZEIT_ENDE + " INTEGER "
        + ");\n";

    private final String CREATE_TABLE_ABTEILUNG= "CREATE TABLE " + TABLE_ABTEILUNG +
        "( " + ABTEILUNG_COLUMN_ABTEILUNG + " TEXT PRIMARY KEY" + ",\n"
        + ABTEILUNG_COLUMN_ETAGE + " TEXT "
        + ");\n";

    private final String CREATE_TABLE_PERSON_IN_ABTEILUNG= "CREATE TABLE " + TABLE_PERSON_IN_ABTEILUNG +
        "( " + PERSON_IN_ABTEILUNG_COLUMN_ABTEILUNG + " TEXT " + ",\n"
        + PERSON_IN_ABTEILUNG_COLUMN_PERSON + " TEXT " + ",\n"
        + "FOREIGN KEY("+PERSON_IN_ABTEILUNG_COLUMN_ABTEILUNG+") REFERENCES "+ TABLE_ABTEILUNG + "("+ ABTEILUNG_COLUMN_ABTEILUNG +")" + ",\n"
        + "FOREIGN KEY("+PERSON_IN_ABTEILUNG_COLUMN_PERSON+") REFERENCES "+ TABLE_PERSONEN + "("+ PERSONEN_COLUMN_ID +")" + ",\n"
        + "PRIMARY KEY ("+PERSON_IN_ABTEILUNG_COLUMN_ABTEILUNG+", "+PERSON_IN_ABTEILUNG_COLUMN_PERSON+")"
        + ");\n";

    //gottesdienste(_gdid,_gdtime,gdend,gdpastor)
    private final String CREATE_TABLE_GOTTESDIENSTE = "CREATE TABLE " + TABLE_GOTTESDIENSTE +
        "( " + GD_ID + " TEXT " + ",\n"
        + GD_BEGIN + " TEXT " + ",\n"
        + GD_ENDE + " TEXT " + ",\n"
        + GD_PASTOR + " TEXT " + " REFERENCES " + TABLE_PERSONEN + "(" + PERSONEN_COLUMN_ID + "),\n"
        + "PRIMARY KEY (" + GD_ID + ", " + GD_BEGIN + ")"
        + ");\n";

    private final String CREATE_TABLE_TEILNAHME_GD = "CREATE TABLE " + TABLE_TEILNAHME_GD +
        "( " + TEILNAHME_GD_ID + " TEXT " + ",\n"
        + TEILNAHME_GD_PERSON + " TEXT " + ",\n"
        + "FOREIGN KEY(" + TEILNAHME_GD_ID + ") REFERENCES " + TABLE_GOTTESDIENSTE + "("+ GD_ID +")" + ",\n"
        + "FOREIGN KEY(" + TEILNAHME_GD_PERSON + ") REFERENCES " + TABLE_PERSONEN + "("+ PERSONEN_COLUMN_ID +")" + ",\n"
        + "PRIMARY KEY(" + TEILNAHME_GD_ID +", " + TEILNAHME_GD_PERSON + ")"
        + ");\n";

    private final String CREATE_TABLE_FILME = "CREATE TABLE " + TABLE_FILME +
        "( " + FILM_NAME + " TEXT " + ",\n"
        + FILM_WTAG + " TEXT " + ",\n"
        + FILM_ZEIT + " TEXT " + ",\n"
        + "PRIMARY KEY ("+ FILM_NAME +", " + FILM_WTAG + ", " + FILM_ZEIT + ")"
        + ");\n";

    private final String CREATE_TABLE_ARBEITSPLAN = "CREATE TABLE " + TABLE_ARBEITSPLAN +
        "( " + ARBEITSPLAN_PERSON + " TEXT REFERENCES " + TABLE_PERSONEN + "(" + PERSONEN_COLUMN_ID + ")" + ",\n"
        + ARBEITSPLAN_UHRZEIT + " TEXT " + ",\n"
        + ARBEITSPLAN_TAETIGKEIT + " TEXT " + ",\n"
        + ARBEITSPLAN_BEMERKUNG + " TEXT " + ",\n"
        + "PRIMARY KEY (" + ARBEITSPLAN_UHRZEIT + "," + ARBEITSPLAN_PERSON + ")"
        + ");\n";

    private final String CREATE_TABLE_KREDITKARTENABRECHNUNG = "CREATE TABLE " + TABLE_KREDITKARTENABRECHNUNG +
        "( " + KREDITKARTENABRECHNUNG_ID + " TEXT PRIMARY KEY" + ",\n"
        + KREDITKARTENABRECHNUNG_DATUM + " TEXT " + ",\n"
        + KREDITKARTENABRECHNUNG_BETRAG + " TEXT " + ",\n"
        + KREDITKARTENABRECHNUNG_EMPFAENGER + " TEXT " + ",\n"
        + KREDITKARTENABRECHNUNG_SENDER + " TEXT " + ",\n"
        + KREDITKARTENABRECHNUNG_BEMERKUNG + " TEXT "
        + ");\n";

    public DBOpenHelper(Context context) {
        super(context, "PLATZHALTER_DATENBANKNAME", null, 1);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PERSON);
        db.execSQL(CREATE_TABLE_TERMINKALENDER);
        db.execSQL(CREATE_TABLE_ABTEILUNG);
        db.execSQL(CREATE_TABLE_PERSON_IN_ABTEILUNG);
        db.execSQL(CREATE_TABLE_GOTTESDIENSTE);
        db.execSQL(CREATE_TABLE_TEILNAHME_GD);
        db.execSQL(CREATE_TABLE_FILME);
        db.execSQL(CREATE_TABLE_ARBEITSPLAN);
        db.execSQL(CREATE_TABLE_KREDITKARTENABRECHNUNG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        deleteAllTables(db);

        onCreate(db);
    }

    private void deleteAllTables(SQLiteDatabase db){
        db.execSQL("DELETE FROM " + TABLE_PERSONEN);
        db.execSQL("DELETE FROM " + TABLE_TERMINKALENDER);
        db.execSQL("DELETE FROM " + TABLE_ABTEILUNG);
        db.execSQL("DELETE FROM " + TABLE_PERSON_IN_ABTEILUNG);
        db.execSQL("DELETE FROM " + TABLE_GOTTESDIENSTE);
        db.execSQL("DELETE FROM " + TABLE_TEILNAHME_GD);
        db.execSQL("DELETE FROM " + TABLE_FILME);
        db.execSQL("DELETE FROM " + TABLE_ARBEITSPLAN);
        db.execSQL("DELETE FROM " + TABLE_KREDITKARTENABRECHNUNG);
    }

    /**
     *
     * @param query
     * @return
     */
    public Cursor getSelect(String query){
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("DELETE FROM "+ TABLE_NAME + " where NAME = 'hsjd'");
        Cursor res = db.rawQuery(query, null);

        return res;
    }

    public void getDML(String query){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }

    public void reset() {
        SQLiteDatabase db = this.getWritableDatabase();
        deleteAllTables(db);

        //id beruf geburtsdatum(yyyymmdd) nachname vorname telefonnr
        insertPersonen("77","Hauptentwickler", "19960402", "Mayer", "Moritz", "015733567813");
        insertPersonen("2", "Künstlerin", "19840101", "Thomason", "Lily", "053156569961");
        insertPersonen("3","Gärtnerin","19740213","Baumgarten","Arianne", "014797348865");
        insertPersonen("4","Professor","19760911","Münch","Thomas", "015562937755");
        insertPersonen("6","Studentin","19960814","Kästner","Viktoria", "01596395xxxx");
        insertPersonen("7","Pastor","19581123","Papst","David", "019254789257");
        insertPersonen("8","Meeresbiologin", "19460621", "Nagel", "Ute", "018671329858");
        insertPersonen("9","Polizist", "19730508", "Seiler", "Wilhelm", "017217346162");
        insertPersonen("10","Angestellter", "19660231", "Hoffmeister", "Rüdiger", "015736303188");
        insertPersonen("311","Kauffrau", "19950508", "Winkler", "Petra", "017536338100");
        insertPersonen("912","Risiko Analyst", "19891224", "Tobias", "Manuéll", "053615599621");
        insertPersonen("13","Wirtschaftsmathematikerin", "19911003", "Schmitt", "Elisabett", "053196420187");
        insertPersonen("714","Psychologin","19831212","Beyer","Yvonne", "017271815463");
        insertPersonen("15","Psychotherapeut","19771025","Müller","Rolf", "053123456789");
        insertPersonen("16", "Student", "20000215", "Garcia", "Joe", "053199111364");
        insertPersonen("17", "Metallbauer","19920503","Kunze","Wilhelm", "01621495920");
        insertPersonen("818", "Maschinenführer", "19850918", "Krause", "Jürgen", "053156564813");
        insertPersonen("819", "Studentin", "19960814", "Koslow", "Vittoria", "015963951234");
        insertPersonen("119", "Rechtsreferendar","19980512","Schulze","David", "014106840860");
        insertPersonen("520", "Postbotin", "19660630", "Weiß", "Kerstin", "053114121399");
        insertPersonen("321", "Telefonistin", "19770817", "Vogel", "Stefanie", "013021977454");
        insertPersonen("22", "Architekt", "19541112", "Freytag", "Dominic", "053199111399");
        insertPersonen("23", "Dolmetscherin", "19801208", "Koenig", "Wilhelmina", "016629916779");
        insertPersonen("24", "Trainingmanager", "19921016", "Furst", "Thomas", "015586558501");
        insertPersonen("125", "Netzelektrikerin", "19950810", "Neustadt", "Katja", "017723502566");
        insertPersonen("26", "Lehrer", "19761219", "Probst", "Swen", "016785256284");
        insertPersonen("27", "Sachbearbeiterin", "19701114", "Traugott", "Anke", "016532864648");
        insertPersonen("28", "Künstlerin", "19500102", "Friedmann", "Michelle", "016592642345");
        insertPersonen("229", "Medizinfachangestelltin", "19810202", "Baader", "Silke", "015298040784");
        insertPersonen("30", "Zulassungsbeauftragter", "19780912", "Wechsler", "Julian", "010421850647");
        insertPersonen("42", "Detektiv", "19670803", "Barton", "Hannes", "016495728645");
        insertPersonen("243", "Verkäuferin", "19880412", "Bauer", "Emanuela", "0531456545678");
        insertPersonen("744", "Bäcker", "19790218", "Groß", "Wilhelm", "053187987987");
        insertPersonen("247", "Forschungsassistent", "19911022", "Kaufmann", "Johannes", "018312525424");
        insertPersonen("952", "Steinmetz", "19720723", "Biermann", "Anton", "019459732584");
        insertPersonen("55", "Erzieher", "19781113", "Erhart", "Leon", "01737986392");
        insertPersonen("365", "Gärtner", "19750814", "Dunkler", "Horst", "050865966576");
        insertPersonen("765", "Gärtnerin", "19920512", "Rosen", "Gisela", "053155976411");
        insertPersonen("1", "Entwickler","19950702","Weiß","Rico Henrik", "016265892208");
        insertPersonen("89", "Studentin", "19980812", "Wilhelm", "Vanessa", "017687487584");
        insertPersonen("690", "Kinderarzt", "19770120", "Wirtz", "Markus", "018456809432");
        insertPersonen("634", "Schneiderin", "19871110", "Beyer", "Hanna", "016953209432");
        insertPersonen("551", "Heizungsmonteurin", "19900521", "Baier", "Sandra", "0180734589627");


        //Terminkalender Professor DingsBums
        insertTerminkalender("0800", "0830", "Vorlesung Grundlagen IT-Sicherheit - online.");
        insertTerminkalender("0945", "1115", "Internes Meeting mit Seminar-Gruppe \"sql-injection\"");
        insertTerminkalender("1215", "1400", "Brunch mit der Geschäftsführung.");
        insertTerminkalender("1730", "2115", "Präsentation Paper \"modern security breaches\".");

        insertAbteilung("Studienfinanzierung",  "1");
        insertAbteilung("ServicePoint",         "1");
        insertAbteilung("Wohnen",               "2");
        insertAbteilung("Psychotherapeutische Beratungsstelle (PBS)", "2");
        insertAbteilung("Apartments #1",           "3");
        insertAbteilung("Apartments #2",           "4");

        insertPersonInAbteilung("714", "Psychotherapeutische Beratungsstelle (PBS)");
        insertPersonInAbteilung("15", "Psychotherapeutische Beratungsstelle (PBS)");
        insertPersonInAbteilung("10", "ServicePoint");
        insertPersonInAbteilung("311", "Studienfinanzierung");
        insertPersonInAbteilung("912", "Studienfinanzierung");
        insertPersonInAbteilung("13", "Studienfinanzierung");

        //id zeit ende pastor(id) --> Zeiten in mmhh
        insertGottesdienst("1", "0800", "0915", "7");
        insertGottesdienst("2", "1115", "1230", "7");
        insertGottesdienst("3", "1400", "1515", "7");
        insertGottesdienst("4", "1745", "1900", "7");

        //gottd-id personenid
        insertTeilnahmeAnGD("1", "15");
        insertTeilnahmeAnGD("1", "119");
        insertTeilnahmeAnGD("1", "17");
        insertTeilnahmeAnGD("1", "321");
        insertTeilnahmeAnGD("1", "24");
        insertTeilnahmeAnGD("1", "247");
        insertTeilnahmeAnGD("1", "89");
        insertTeilnahmeAnGD("2", "125");//2
        insertTeilnahmeAnGD("2", "22");
        insertTeilnahmeAnGD("2", "243");
        insertTeilnahmeAnGD("2", "27");
        insertTeilnahmeAnGD("2", "311");
        insertTeilnahmeAnGD("2", "28");
        insertTeilnahmeAnGD("2", "229");
        insertTeilnahmeAnGD("3", "23");//3
        insertTeilnahmeAnGD("3", "744");
        insertTeilnahmeAnGD("3", "26");
        insertTeilnahmeAnGD("3", "952");
        insertTeilnahmeAnGD("3", "10");
        insertTeilnahmeAnGD("3", "690");
        insertTeilnahmeAnGD("3", "12");
        insertTeilnahmeAnGD("4", "714");//4
        insertTeilnahmeAnGD("4", "16");
        insertTeilnahmeAnGD("4", "818");
        insertTeilnahmeAnGD("4", "520");
        insertTeilnahmeAnGD("4", "21");
        insertTeilnahmeAnGD("4", "912");
        insertTeilnahmeAnGD("4", "55");
        insertTeilnahmeAnGD("4", "744");
        insertTeilnahmeAnGD("4", "30");

        //insertFilme(String name, String wochentag, String zeit);
        insertFilme("Der Herr der Schlinge: Die Rückkehr des Sklavens", "Montag", "1200");//Montag
        insertFilme("Sherlock Holmes v/s Moriarty Undercover", "Montag", "1530");
        insertFilme("Per Anhalter durch die Sesamstraße", "Montag", "1700");
        insertFilme("The Dark Stable Boy", "Montag", "1830");
        insertFilme("Intenet", "Montag", "1900");
        insertFilme("Die Galaxie schlägt zurück", "Montag", "2130");

        insertFilme("Montana Lones: Krempel des Todes", "Dienstag", "1530");//Dienstag
        insertFilme("The Dark Stable Boy", "Dienstag", "1100");
        insertFilme("Star Peace: Opisede 6", "Dienstag", "1300");
        insertFilme("Per Anhalter durch die Sesamstraße", "Dienstag", "1730");
        insertFilme("Interception", "Dienstag", "2000");

        insertFilme("Einer flog über den Planeten", "Mittwoch", "1330");//Mittwoch
        insertFilme("Dance Club", "Mittwoch", "1500");
        insertFilme("Ziemlich beste Nachbarn", "Mittwoch", "1730");
        insertFilme("1 Uhr Nachts", "Mittwoch", "2100");
        insertFilme("Die Galaxie schlägt zurück", "Mittwoch", "2230");

        insertFilme("The Revengers: Age of Multitron", "Donnerstag", "1330");//Donnerstag
        insertFilme("Star Peace: Opisede 6", "Donnerstag", "1400");
        insertFilme("Montana Lones: Krempel des Todes", "Donnerstag", "1530");
        insertFilme("Der Herr der Schlinge: Die Rückkehr des Sklavens", "Donnerstag", "1600");
        insertFilme("Barry Plotter und Heiligtümer des Mordes", "Donnerstag", "1700");

        insertFilme("Star Peace: Opisede 6", "Freitag", "1500");//Freitag
        insertFilme("Die Sieben Riesen: der Berg ist zu groß.", "Freitag", "1730");
        insertFilme("Ziemlich beste Nachbarn", "Freitag", "1800");
        insertFilme("Die Galaxie schlägt zurück", "Freitag", "1930");
        insertFilme("Interception", "Freitag", "2000");


        //insertArbeitsplan(String id, String personID, String uhrzeit, String taetigkeit, String bemerkung)
        insertArbeitsplan("3", "1130", "Friedhofspflanzen wässern.", "");
        insertArbeitsplan("365", "1200", "Museumspark Rasenfläche mähen.", "");
        insertArbeitsplan("365", "1330", "Werkzeuge reinigen.", "");
        insertArbeitsplan("765", "1300", "Baumpflege Hagenmarkt.", "");
        insertArbeitsplan("365", "1500", "Museumspark Rasenfläche mähen.", "");
        insertArbeitsplan("765", "1200", "Turmstraße Grünflächen.", "");
        insertArbeitsplan("3", "1730", "Gauß-Berg-Park Grünflächenpflege.", "getauscht mit Horst");
        insertArbeitsplan("365", "1800", "Museumspark Rasenfläche mähen.", "getauscht mit Arianne");


        //insertKreditkartenabrechnung(String id, String datum, String betrag, String sender, String empfaenger, String bemerkung)
        insertKreditkartenabrechnung("1", "0503", "200,00", "Harald Hinze", "Hannes Barton", "Schutzgeld");
        insertKreditkartenabrechnung("2", "1231", "11,20", "Hannes Barton", "Worst ISP", "Prepaid-Karte");
        insertKreditkartenabrechnung("3", "0621", "09,00", "Hannes Barton", "EDEKA City", "EDEKA sacht dange");
        insertKreditkartenabrechnung("4", "0403", "159,06", "Hannes Barton", "Chemieshop Online", "Ihr Kauf vom 04.02");
        insertKreditkartenabrechnung("5", "0430", "52,63", "Hannes Barton", "Tankstelle LARA", "");
        insertKreditkartenabrechnung("6", "0603", "49,99", "Hannes Barton", "Amaizong DE", "1TB Festplatte von ZATA");
        insertKreditkartenabrechnung("7", "0813", "28,62", "Hannes Barton", "Kaffeemanufaktur BS", "Vielen Dank");
        insertKreditkartenabrechnung("8", "0505", "34,22", "Hannes Barton", "Theatershop.de", "Ihr Einkauf vom 05.05");
        insertKreditkartenabrechnung("9", "0813", "150,00", "Frank Kraus", "Hannes Barton", "Letzte Rate");
        insertKreditkartenabrechnung("10","1308", "195,00", "Daniella Zimmermann", "Hannes Barton", "ABey-Kunstschminke");
    }

    public void insertArbeitsplan(String personID, String uhrzeit, String taetigkeit, String bemerkung){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ARBEITSPLAN_PERSON, personID);
        cv.put(ARBEITSPLAN_UHRZEIT, uhrzeit);
        cv.put(ARBEITSPLAN_TAETIGKEIT, taetigkeit);
        cv.put(ARBEITSPLAN_BEMERKUNG, bemerkung);
        db.insert(TABLE_ARBEITSPLAN, null, cv);
    }

    public void insertKreditkartenabrechnung(String id, String datum, String betrag, String sender, String empfaenger, String bemerkung) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KREDITKARTENABRECHNUNG_BETRAG, betrag);
        cv.put(KREDITKARTENABRECHNUNG_DATUM, datum);
        cv.put(KREDITKARTENABRECHNUNG_ID, id);
        cv.put(KREDITKARTENABRECHNUNG_SENDER, sender);
        cv.put(KREDITKARTENABRECHNUNG_EMPFAENGER, empfaenger);
        cv.put(KREDITKARTENABRECHNUNG_BEMERKUNG, bemerkung);
        db.insert(TABLE_KREDITKARTENABRECHNUNG, null, cv);
    }

    public void insertGottesdienst(String id, String zeit, String ende, String pastor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GD_ID, id);
        cv.put(GD_BEGIN, zeit);
        cv.put(GD_ENDE, ende);
        cv.put(GD_PASTOR, pastor);
        db.insert(TABLE_GOTTESDIENSTE, null, cv);
    }

    public void insertTeilnahmeAnGD(String gid, String pid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TEILNAHME_GD_ID, gid);
        cv.put(TEILNAHME_GD_PERSON, pid);
        db.insert(TABLE_TEILNAHME_GD, null, cv);
    }

    public void insertAbteilung(String abteilung, String etage){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ABTEILUNG_COLUMN_ABTEILUNG, abteilung);
        cv.put(ABTEILUNG_COLUMN_ETAGE, etage);
        db.insert(TABLE_ABTEILUNG, null, cv);
    }

    public void insertFilme(String name, String wochentag, String zeit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FILM_NAME, name);
        cv.put(FILM_WTAG, wochentag);
        cv.put(FILM_ZEIT, zeit);
        db.insert(TABLE_FILME, null, cv);
    }

    public void insertPersonInAbteilung(String personId, String abteilung) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PERSON_IN_ABTEILUNG_COLUMN_PERSON, personId);
        cv.put(PERSON_IN_ABTEILUNG_COLUMN_ABTEILUNG, abteilung);
        db.insert(TABLE_PERSON_IN_ABTEILUNG, null, cv);
    }

    public void insertPersonen(String id, String beruf, String geburtsdatum, String nachname, String vorname, String telefonnr){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PERSONEN_COLUMN_ID, id);
        cv.put(PERSONEN_COLUMN_BERUF, beruf);
        cv.put(PERSONEN_COLUMN_GEBURTSDATUM, geburtsdatum);
        cv.put(PERSONEN_COLUMN_NACHNAME, nachname);
        cv.put(PERSONEN_COLUMN_VORNAME, vorname);
        cv.put(PERSONEN_COLUMN_TNR, telefonnr);
        db.insert(TABLE_PERSONEN, null, cv);
    }

    public void insertTerminkalender(String start, String ende, String taetigkeit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TERMINKALENDER_COLUMN_UHRZEIT_BEGIN, start);
        cv.put(TERMINKALENDER_COLUMN_UHRZEIT_ENDE, ende);
        cv.put(TERMINKALENDER_COLUMN_TAETIGKEIT, taetigkeit);
        db.insert(TABLE_TERMINKALENDER, null, cv);
    }

    public void deletedata(String query){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }

    public void updatedata(String query){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }

    public LinkedList<String> getTables(){
        LinkedList<String> arrTblNames = new LinkedList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name!='android_metadata' AND name!='sqlite_sequence'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {

                arrTblNames.add(c.getString(c.getColumnIndex("name")));

                c.moveToNext();
            }
        }
        return arrTblNames;
    }

    public LinkedList<String> getValues(String table){
        LinkedList<String> arrTblNames = new LinkedList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + table, null);

        c.moveToFirst();
        String[] COLUMNS = c.getColumnNames();
        for(int j=0;j<COLUMNS.length;j++){
            c.move(j);
            arrTblNames.add(COLUMNS[j]);
        }
        return arrTblNames;
    }
}
