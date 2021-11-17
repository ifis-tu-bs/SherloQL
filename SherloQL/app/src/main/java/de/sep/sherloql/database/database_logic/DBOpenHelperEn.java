package de.sep.sherloql.database.database_logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;

public class DBOpenHelperEn extends SQLiteOpenHelper {

    private static final String LOG_TAG = "DBOpenHelper";

    private final String TABLE_PERSONEN = "persons";
    private final String PERSONEN_COLUMN_VORNAME = "first_name";
    private final String PERSONEN_COLUMN_NACHNAME = "surname";
    private final String PERSONEN_COLUMN_GEBURTSDATUM = "date_of_birth";
    private final String PERSONEN_COLUMN_BERUF = "profession";
    private final String PERSONEN_COLUMN_ID = "person_id";
    private final String PERSONEN_COLUMN_TNR = "phone_number";

    private final String TABLE_TERMINKALENDER = "schedule";
    private final String TERMINKALENDER_COLUMN_UHRZEIT_BEGIN = "sc_start";
    private final String TERMINKALENDER_COLUMN_UHRZEIT_ENDE = "sc_end";
    private final String TERMINKALENDER_COLUMN_TAETIGKEIT = "sc_task";

    private final String TABLE_ABTEILUNG = "department";
    private final String ABTEILUNG_COLUMN_ABTEILUNG = "dep_name";
    private final String ABTEILUNG_COLUMN_ETAGE = "dep_floor";

    private final String TABLE_PERSON_IN_ABTEILUNG = "person_in_department";
    private final String PERSON_IN_ABTEILUNG_COLUMN_PERSON = "pid_person";
    private final String PERSON_IN_ABTEILUNG_COLUMN_ABTEILUNG = "pid_department";

    //Kapitel Pastor David
    private final String TABLE_GOTTESDIENSTE = "church_services";
    private final String GD_ID = "cs_id";
    private final String GD_BEGIN = "cs_start";
    private final String GD_ENDE = "gd_end";
    private final String GD_PASTOR = "gd_pastor";

    private final String TABLE_TEILNAHME_GD = "attendance_church_service";
    private final String TEILNAHME_GD_PERSON = "acs_person";//gottesdienst teilnehmer
    private final String TEILNAHME_GD_ID = "acs_cs_id";


    //Kapitel Rico
    private final String TABLE_FILME = "movies";
    private final String FILM_NAME = "movie_name";
    private final String FILM_WTAG = "movie_weekday";
    private final String FILM_ZEIT = "movie_time";

    //Kapitel Gärtnerin
    private final String TABLE_ARBEITSPLAN = "work_plan";
    private final String ARBEITSPLAN_PERSON = "wp_person";
    private final String ARBEITSPLAN_UHRZEIT = "wp_time";
    private final String ARBEITSPLAN_TAETIGKEIT = "wp_task";
    private final String ARBEITSPLAN_BEMERKUNG = "wp_comment"; //...getauscht mit...

    //Kapitel Prof+Yvonne
    private final String TABLE_KREDITKARTENABRECHNUNG = "credt_card_billing";
    private final String KREDITKARTENABRECHNUNG_ID = "ccb_id";
    private final String KREDITKARTENABRECHNUNG_DATUM = "ccb_date";
    private final String KREDITKARTENABRECHNUNG_BETRAG = "ccb_amount";
    private final String KREDITKARTENABRECHNUNG_SENDER = "ccb_sender";
    private final String KREDITKARTENABRECHNUNG_EMPFAENGER = "ccb_receiver";
    private final String KREDITKARTENABRECHNUNG_BEMERKUNG = "ccb_comment";



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

    public DBOpenHelperEn(Context context) {
        super(context, "PLATZHALTER_DATENBANKNAME1", null, 1);
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
        insertPersonen("77","Main developer", "19960402", "Mayer", "Moritz", "015733567813");
        insertPersonen("2", "Artist", "19840101", "Thomason", "Lily", "053156569961");
        insertPersonen("3","Gardener","19740213","Baumgarten","Arianne", "014797348865");
        insertPersonen("4","Professor","19760911","Münch","Thomas", "015562937755");
        insertPersonen("6","Student","19960814","Kästner","Viktoria", "01596395xxxx");
        insertPersonen("7","Pastor","19581123","Papst","David", "019254789257");
        insertPersonen("8","Marine Biologist", "19460621", "Nagel", "Ute", "018671329858");
        insertPersonen("9","Policeman", "19730508", "Seiler", "Wilhelm", "017217346162");
        insertPersonen("10","Employee", "19660231", "Hoffmeister", "Rüdiger", "015736303188");
        insertPersonen("311","Businesswoman", "19950508", "Winkler", "Petra", "017536338100");
        insertPersonen("912","Risk Analyst", "19891224", "Tobias", "Manuéll", "053615599621");
        insertPersonen("13","Business Mathematics", "19911003", "Schmitt", "Elisabett", "053196420187");
        insertPersonen("714","Psychologist","19831212","Beyer","Yvonne", "017271815463");
        insertPersonen("15","Psychotherapist","19771025","Müller","Rolf", "053123456789");
        insertPersonen("16", "Student", "20000215", "Garcia", "Joe", "053199111364");
        insertPersonen("17", "Metal worker","19920503","Kunze","Wilhelm", "01621495920");
        insertPersonen("818", "Machine operator", "19850918", "Krause", "Jürgen", "053156564813");
        insertPersonen("819", "Student", "19960814", "Koslow", "Vittoria", "015963951234");
        insertPersonen("119", "Legal trainee","19980512","Schulze","David", "014106840860");
        insertPersonen("520", "Postwoman", "19660630", "Weiß", "Kerstin", "053114121399");
        insertPersonen("321", "Telephonist", "19770817", "Vogel", "Stefanie", "013021977454");
        insertPersonen("22", "Architect", "19541112", "Freytag", "Dominic", "053199111399");
        insertPersonen("23", "Interpreter", "19801208", "Koenig", "Wilhelmina", "016629916779");
        insertPersonen("24", "Training Manager", "19921016", "Furst", "Thomas", "015586558501");
        insertPersonen("125", "Network electrician", "19950810", "Neustadt", "Katja", "017723502566");
        insertPersonen("26", "Teacher", "19761219", "Probst", "Swen", "016785256284");
        insertPersonen("27", "Administrator", "19701114", "Traugott", "Anke", "016532864648");
        insertPersonen("28", "Artist", "19500102", "Friedmann", "Michelle", "016592642345");
        insertPersonen("229", "Medical Assistant", "19810202", "Baader", "Silke", "015298040784");
        insertPersonen("30", "Admissions representative", "19780912", "Wechsler", "Julian", "010421850647");
        insertPersonen("42", "Detective", "19670803", "Barton", "Hannes", "016495728645");
        insertPersonen("243", "Saleswoman", "19880412", "Bauer", "Emanuela", "0531456545678");
        insertPersonen("744", "Baker", "19790218", "Groß", "Wilhelm", "053187987987");
        insertPersonen("247", "Research Assistant", "19911022", "Kaufmann", "Johannes", "018312525424");
        insertPersonen("952", "Stonemason", "19720723", "Biermann", "Anton", "019459732584");
        insertPersonen("55", "Educator", "19781113", "Erhart", "Leon", "01737986392");
        insertPersonen("365", "Gardener", "19750814", "Dunkler", "Horst", "050865966576");
        insertPersonen("765", "Gardener", "19920512", "Rosen", "Gisela", "053155976411");
        insertPersonen("1", "Developer","19950702","Weiß","Rico Henrik", "016265892208");
        insertPersonen("89", "Student", "19980812", "Wilhelm", "Vanessa", "017687487584");
        insertPersonen("690", "Pediatrician", "19770120", "Wirtz", "Markus", "018456809432");
        insertPersonen("634", "Dressmaker", "19871110", "Beyer", "Hanna", "016953209432");
        insertPersonen("551", "Heating engineer", "19900521", "Baier", "Sandra", "0180734589627");


        //Terminkalender Professor DingsBums
        insertTerminkalender("0800", "0830", "Lecture Introduction IT-Security - online.");
        insertTerminkalender("0945", "1115", "Internal meeting with seminar group \"sql-injection\"");
        insertTerminkalender("1215", "1400", "Brunch with the management.");
        insertTerminkalender("1730", "2115", "Presentation Paper \"modern security breaches\".");

        insertAbteilung("Student financing",  "1");
        insertAbteilung("ServicePoint",         "1");
        insertAbteilung("Living",               "2");
        insertAbteilung("Psychotherapeutic counseling center (PBS)", "2");
        insertAbteilung("Apartments #1",           "3");
        insertAbteilung("Apartments #2",           "4");

        insertPersonInAbteilung("714", "Psychotherapeutic counseling center (PBS)");
        insertPersonInAbteilung("15", "Psychotherapeutic counseling center (PBS)");
        insertPersonInAbteilung("10", "ServicePoint");
        insertPersonInAbteilung("311", "Student financing");
        insertPersonInAbteilung("912", "Student financing");
        insertPersonInAbteilung("13", "Student financing");

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
        insertFilme("Lord of the Sling: The return of the slave", "Monday", "1200");//Montag
        insertFilme("Sherlock Holmes v/s Moriarty Undercover", "Monday", "1530");
        insertFilme("Hitchhiker's Guide to the Sesame Street", "Monday", "1700");
        insertFilme("The Dark Stable Boy", "Monday", "1830");
        insertFilme("Intenet", "Monday", "1900");
        insertFilme("The Galaxy strikes back", "Monday", "2130");

        insertFilme("Montana Lones: Clutter of death", "Tuesday", "1530");//Dienstag
        insertFilme("The Dark Stable Boy", "Tuesday", "1100");
        insertFilme("Star Peace: Opisede 6", "Tuesday", "1300");
        insertFilme("Per Anhalter durch die Sesamstraße", "Tuesday", "1730");
        insertFilme("Interception", "Tuesday", "2000");

        insertFilme("Hitchhiker's Guide to the Sesame Street", "Wednesday", "1330");//Mittwoch
        insertFilme("Dance Club", "Wednesday", "1500");
        insertFilme("Very Best Neighbors", "Wednesday", "1730");
        insertFilme("1 am", "Wednesday", "2100");
        insertFilme("The Galaxy strikes back", "Wednesday", "2230");

        insertFilme("The Revengers: Age of Multitron", "Thursday", "1330");//Donnerstag
        insertFilme("Star Peace: Opisede 6", "Thursday", "1400");
        insertFilme("Montana Lones: Clutter of death", "Thursday", "1530");
        insertFilme("Lord of the Sling: The return of the slave", "Thursday", "1600");
        insertFilme("Barry Plotter and the Homicidal Hollows", "Thursday", "1700");

        insertFilme("Star Peace: Opisede 6", "Friday", "1500");//Freitag
        insertFilme("The Seven Giants: the mountain is too big.", "Friday", "1730");
        insertFilme("Very Best Neighbors", "Friday", "1800");
        insertFilme("The Galaxy strikes back", "Friday", "1930");
        insertFilme("Interception", "Friday", "2000");


        //insertArbeitsplan(String id, String personID, String uhrzeit, String taetigkeit, String bemerkung)
        insertArbeitsplan("3", "1130", "Water cemetery plants.", "");
        insertArbeitsplan("365", "1200", "Museumspark Lawn Mowing.", "");
        insertArbeitsplan("365", "1330", "Clean tools.", "");
        insertArbeitsplan("765", "1300", "Tree Care Hagenmarkt.", "");
        insertArbeitsplan("365", "1500", "Museumspark Lawn Mowing.", "");
        insertArbeitsplan("765", "1200", "Turmstraße Green Spaces.", "");
        insertArbeitsplan("3", "1730", "Gauß-Berg-Park Green space maintenance.", "switched with Horst");
        insertArbeitsplan("365", "1800", "Museumspark Lawn Mowing.", "switched with Arianne");


        //insertKreditkartenabrechnung(String id, String datum, String betrag, String sender, String empfaenger, String bemerkung)
        insertKreditkartenabrechnung("1", "0503", "200,00", "Harald Hinze", "Hannes Barton", "Protection money");
        insertKreditkartenabrechnung("2", "1231", "11,20", "Hannes Barton", "Worst ISP", "Prepaid-card");
        insertKreditkartenabrechnung("3", "0621", "09,00", "Hannes Barton", "EDEKA City", "EDEKA sacht dange");
        insertKreditkartenabrechnung("4", "0403", "159,06", "Hannes Barton", "Chemieshop Online", "Your purchase from 04.02");
        insertKreditkartenabrechnung("5", "0430", "52,63", "Hannes Barton", "Tankstelle LARA", "");
        insertKreditkartenabrechnung("6", "0603", "49,99", "Hannes Barton", "Amaizong DE", "1TB ZATA Hard drive");
        insertKreditkartenabrechnung("7", "0813", "28,62", "Hannes Barton", "Kaffeemanufaktur BS", "Thank you very much");
        insertKreditkartenabrechnung("8", "0505", "34,22", "Hannes Barton", "Theatershop.de", "Your purchase from 05.05");
        insertKreditkartenabrechnung("9", "0813", "150,00", "Frank Kraus", "Hannes Barton", "Last installment");
        insertKreditkartenabrechnung("10","1308", "195,00", "Daniella Zimmermann", "Hannes Barton", "ABey-Art makeup");
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
