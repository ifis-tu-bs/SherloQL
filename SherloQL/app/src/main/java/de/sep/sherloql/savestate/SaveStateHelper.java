package de.sep.sherloql.savestate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class SaveStateHelper extends SQLiteOpenHelper {

    private final String LOG_TAG = "SaveStateHelper";

    private final String TABLE_STORY = "story";
    private final String TABLE_HINTS = "hints";
    private final String TABLE_RBALLGEMEINES = "allgemeines";
    private final String TABLE_RBBRAUNSCHWEIG = "braunschweig";
    private final String TABLE_RBGEOGRAPHIE = "geographie";
    private final String TABLE_RBINFORMATIK = "informatik";
    private final String TABLE_RBLOGIK = "logik";
    private final String TABLE_RBMATHEMATIK = "mathematik";
    private final String TABLE_RBSPRACHE = "sprache";
    private final String TABLE_COINS = "coins";
    private final String TABLE_LOCATION = "location";
    private final String TABLE_LANGUAGE = "language";
    private final String TABLE_SQL = "sql";
    private final String TABLE_SOUND = "sound";
    private final String TABLE_USERNAME = "name";
    private final String TABLE_ARTEFACTS = "artefacts";
    private final String TABLE_CURRENT = "current";

    private final String STORY_COLUMN_RIDDLE = "LBriddle";
    private final String STORY_COLUMN_UNLOCKED = "LBunlocked";
    private final String STORY_COLUMN_SOLVED = "LBsolved";
    private final String STORY_COLUMN_ANSWER = "LBanswer";

    private final String HINT_COLUMN_ID = "id";
    private final String HINT_COLUMN_USED = "used";
    private final String HINT_COLUMN_CHAPTER = "chapterID";

    private final String RBALLGEMEINES_COLUMN_RIDDLE = "NLBalriddle";
    private final String RBALLGEMEINES_COLUMN_SOLVED = "NLBalsolved";

    private final String RBBRAUNSCHWEIG_COLUMN_RIDDLE = "NLBbsriddle";
    private final String RBBRAUNSCHWEIG_COLUMN_SOLVED = "NLBbssolved";

    private final String RBGEOGRAPHIE_COLUMN_RIDDLE = "NLBgeoriddle";
    private final String RBGEOGRAPHIE_COLUMN_SOLVED = "NLBgeosolved";

    private final String RBINFORMATIK_COLUMN_RIDDLE = "NLBinforiddle";
    private final String RBINFORMATIK_COLUMN_SOLVED = "NLBinfosolved";

    private final String RBLOGIK_COLUMN_RIDDLE = "NLBloriddle";
    private final String RBLOGIK_COLUMN_SOLVED = "NLBlosolved";

    private final String RBMATHEMATIK_COLUMN_RIDDLE = "NLBmariddle";
    private final String RBMATHEMATIK_COLUMN_SOLVED = "NLBmasolved";

    private final String RBSPRACHE_COLUMN_RIDDLE = "NLBspriddle";
    private final String RBSPRACHE_COLUMN_SOLVED = "NLBspsolved";

    private final String COINS_COLUMN_COINS = "coins";
    private final String COINS_COLUMN_AMOUNT = "amount";

    private final String LOCATION_COLUMN_ENTERED = "entered";
    private final String LOCATION_COLUMN_ID = "locationid";
    private final String LOCATION_COLUMN_CURRENT = "current";

    private final String LANGUAGE_COLUMN_LANG = "lang"; // 0 = deutsch, 1 = englisch
    private final String LANGUAGE_COLUMN_SELECTION = "selection";

    private final String SOUND_COLUMN_ID = "soundonoff";
    private final String SOUND_COLUMN_SELECTION = "selection"; // 0 = sound off, 1 = sound on

    private final String SQL_COLUMN_SQL = "sql"; // 0 = no sql, 1 = sql
    private final String SQL_COLUMN_select = "selected";

    private final String USERNAME_COLUMN_USER = "user";
    private final String USERNAME_COLUMN_NAME = "name";

    private final String ARTEFACTS_COLUMN_NAME = "artefact_name";
    private final String ARTEFACTS_COLUMN_UNLOCKED = "artefact_unlocked";
    private final String ARTEFACTS_COLUMN_CHAPTER = "artefact_chapter";

    private final String CURRENT_COLUMN_NAME = "current_name";
    private final String CURRENT_COLUMN_ANSWER = "current_answer";

    private final String CREATE_TABLE_STORY =
            "CREATE TABLE " + TABLE_STORY +
                    "( " + STORY_COLUMN_RIDDLE + " Text PRIMARY KEY " + ",\n"
                    + STORY_COLUMN_UNLOCKED + " Text " + ",\n"
                    + STORY_COLUMN_SOLVED + " Text " + ",\n"
                    + STORY_COLUMN_ANSWER + " Text "
                    + ");\n";

    private final String CREATE_TABLE_HINTS =
            "CREATE TABLE " + TABLE_HINTS +
                    "( " + HINT_COLUMN_ID + " INTEGER"  + ",\n"
                    + HINT_COLUMN_USED + " Text " + ",\n"
                    + HINT_COLUMN_CHAPTER + " INTEGER" + ",\n"
                    + "PRIMARY KEY (" + HINT_COLUMN_CHAPTER + ", " + HINT_COLUMN_ID + "\n)" + ");\n";


    private final String CREATE_TABLE_RBALLGEMEINES =
            "CREATE TABLE " + TABLE_RBALLGEMEINES +
                    "( " + RBALLGEMEINES_COLUMN_RIDDLE + " INTEGER PRIMARY KEY AUTOINCREMENT" + ",\n"
                    + RBALLGEMEINES_COLUMN_SOLVED + " Text "
                    + ");\n";

    private final String CREATE_TABLE_RBBRAUNSCHWEIG =
            "CREATE TABLE " + TABLE_RBBRAUNSCHWEIG +
                    "( " + RBBRAUNSCHWEIG_COLUMN_RIDDLE + " INTEGER PRIMARY KEY AUTOINCREMENT" + ",\n"
                    + RBBRAUNSCHWEIG_COLUMN_SOLVED + " Text "
                    + ");\n";

    private final String CREATE_TABLE_RBGEOGRAPHIE =
            "CREATE TABLE " + TABLE_RBGEOGRAPHIE +
                    "( " + RBGEOGRAPHIE_COLUMN_RIDDLE+ " INTEGER PRIMARY KEY AUTOINCREMENT" + ",\n"
                    + RBGEOGRAPHIE_COLUMN_SOLVED + " Text "
                    + ");\n";

    private final String CREATE_TABLE_RBINFORMATIK =
            "CREATE TABLE " + TABLE_RBINFORMATIK+
                    "( " + RBINFORMATIK_COLUMN_RIDDLE+ " INTEGER PRIMARY KEY AUTOINCREMENT" + ",\n"
                    + RBINFORMATIK_COLUMN_SOLVED + " Text "
                    + ");\n";

    private final String CREATE_TABLE_RBLOGIK =
            "CREATE TABLE " + TABLE_RBLOGIK +
                    "( " + RBLOGIK_COLUMN_RIDDLE + " INTEGER PRIMARY KEY AUTOINCREMENT" + ",\n"
                    + RBLOGIK_COLUMN_SOLVED + " Text "
                    + ");\n";

    private final String CREATE_TABLE_RBMATHEMATIK =
            "CREATE TABLE " + TABLE_RBMATHEMATIK +
                    "( " + RBMATHEMATIK_COLUMN_RIDDLE + " INTEGER PRIMARY KEY AUTOINCREMENT" + ",\n"
                    + RBMATHEMATIK_COLUMN_SOLVED + " Text "
                    + ");\n";

    private final String CREATE_TABLE_RBSPRACHE =
            "CREATE TABLE " + TABLE_RBSPRACHE +
                    "( " + RBSPRACHE_COLUMN_RIDDLE + " INTEGER PRIMARY KEY AUTOINCREMENT" + ",\n"
                    + RBSPRACHE_COLUMN_SOLVED + " Text "
                    + ");\n";

    private final String CREATE_TABLE_COINS =
            "CREATE TABLE " + TABLE_COINS +
                    "( " + COINS_COLUMN_COINS + " INTEGER PRIMARY KEY AUTOINCREMENT" + ",\n"
                    + COINS_COLUMN_AMOUNT + " INTEGER "
                    + ");\n";

    private final String CREATE_TABLE_LOCATION =
            "CREATE TABLE " + TABLE_LOCATION +
                    "( " + LOCATION_COLUMN_ID + " INTEGER PRIMARY KEY " + ",\n"
                    + LOCATION_COLUMN_ENTERED + " Text " + ",\n"
                    + LOCATION_COLUMN_CURRENT + " Text "
                    + ");\n";

    private final String CREATE_TABLE_LANGUAGE =
            "CREATE TABLE " + TABLE_LANGUAGE +
                    "( " + LANGUAGE_COLUMN_LANG + " INTEGER PRIMARY KEY AUTOINCREMENT" + ",\n"
                    + LANGUAGE_COLUMN_SELECTION + " INTEGER "
                    + ");\n";

    private final String CREATE_TABLE_SOUND =
            "CREATE TABLE " + TABLE_SOUND +
                    "( " + SOUND_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ",\n"
                    + SOUND_COLUMN_SELECTION + " INTEGER "
                    + ");\n";

    private final String CREATE_TABLE_SQL =
            "CREATE TABLE " + TABLE_SQL +
                    "( " + SQL_COLUMN_SQL + " INTEGER PRIMARY KEY AUTOINCREMENT" + ",\n"
                    + SQL_COLUMN_select + " INTEGER "
                    + ");\n";

    private final String CREATE_TABLE_USERNAME =
            "CREATE TABLE " + TABLE_USERNAME +
                    "( " + USERNAME_COLUMN_USER + " INTEGER PRIMARY KEY AUTOINCREMENT" + ",\n"
                    + USERNAME_COLUMN_NAME + " Text "
                    + ");\n";

    private final String CREATE_TABLE_ARTEFACTS =
            "CREATE TABLE " + TABLE_ARTEFACTS +
                    "( " + ARTEFACTS_COLUMN_NAME + " Text " + ",\n"
                    + ARTEFACTS_COLUMN_UNLOCKED + " Text " + ",\n"
                    + ARTEFACTS_COLUMN_CHAPTER + " Text " + ",\n"
                    + "PRIMARY KEY (" + ARTEFACTS_COLUMN_NAME + ", " + ARTEFACTS_COLUMN_CHAPTER + ")"
                    + ");\n";

    private final String CREATE_TABLE_CURRENT =
            "CREATE TABLE " + TABLE_CURRENT +
                    "( " + CURRENT_COLUMN_NAME + " Text " + ",\n"
                    + CURRENT_COLUMN_ANSWER + " Text "
                    + ");\n";

    public SaveStateHelper(Context context) {
        super(context, "SAVESTATEDATABASE", null, 1);
        Log.d(LOG_TAG, "SaveStateHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STORY);
        db.execSQL(CREATE_TABLE_HINTS);
        //db.execSQL(CREATE_TABLE_RIDDLEBOOK);
        db.execSQL(CREATE_TABLE_RBALLGEMEINES);
        db.execSQL(CREATE_TABLE_RBBRAUNSCHWEIG);
        db.execSQL(CREATE_TABLE_RBGEOGRAPHIE);
        db.execSQL(CREATE_TABLE_RBINFORMATIK);
        db.execSQL(CREATE_TABLE_RBLOGIK);
        db.execSQL(CREATE_TABLE_RBMATHEMATIK);
        db.execSQL(CREATE_TABLE_RBSPRACHE);
        db.execSQL(CREATE_TABLE_COINS);
        db.execSQL(CREATE_TABLE_LOCATION);
        db.execSQL(CREATE_TABLE_LANGUAGE);
        db.execSQL(CREATE_TABLE_SQL);
        db.execSQL(CREATE_TABLE_SOUND);
        db.execSQL(CREATE_TABLE_USERNAME);
        db.execSQL(CREATE_TABLE_ARTEFACTS);
        db.execSQL(CREATE_TABLE_CURRENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DELETE FROM " + TABLE_STORY);
        db.execSQL("DELETE FROM " + TABLE_HINTS);
        //db.execSQL("DELETE FROM " + TABLE_RIDDLEBOOK);
        db.execSQL("DELETE FROM " + TABLE_RBALLGEMEINES);
        db.execSQL("DELETE FROM " + TABLE_RBBRAUNSCHWEIG);
        db.execSQL("DELETE FROM " + TABLE_RBGEOGRAPHIE);
        db.execSQL("DELETE FROM " + TABLE_RBINFORMATIK);
        db.execSQL("DELETE FROM " + TABLE_RBLOGIK);
        db.execSQL("DELETE FROM " + TABLE_RBMATHEMATIK);
        db.execSQL("DELETE FROM " + TABLE_RBSPRACHE);
        db.execSQL("DELETE FROM " + TABLE_COINS);
        db.execSQL("DELETE FROM " + TABLE_LOCATION);
        db.execSQL("DELETE FROM " + TABLE_LANGUAGE);
        db.execSQL("DELETE FROM " + TABLE_SQL);
        db.execSQL("DELETE FROM " + TABLE_SOUND);
        db.execSQL("DELETE FROM " + TABLE_USERNAME);
        db.execSQL("DELETE FROM " + TABLE_ARTEFACTS);
        db.execSQL("DELETE FROM " + TABLE_CURRENT);
        onCreate(db);
    }

    public void insertCurrent(String name, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CURRENT_COLUMN_ANSWER, answer);
        cv.put(CURRENT_COLUMN_NAME, name);
        db.insert(TABLE_CURRENT, null, cv);
    }

    public void insertStory(String name, String unlocked, String solved, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STORY_COLUMN_RIDDLE, name);
        cv.put(STORY_COLUMN_UNLOCKED, unlocked);
        cv.put(STORY_COLUMN_SOLVED, solved);
        cv.put(STORY_COLUMN_ANSWER, answer);
        db.insert(TABLE_STORY, null, cv);
    }

    public void insertArtefacts(String name, String unlocked, String chapter) {

        SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(ARTEFACTS_COLUMN_NAME, name);
            cv.put(ARTEFACTS_COLUMN_UNLOCKED, unlocked);
            cv.put(ARTEFACTS_COLUMN_CHAPTER, chapter);

            db.insert(TABLE_ARTEFACTS, null, cv);
    }

    public boolean checkArtefactExists(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ARTEFACTS + " WHERE " + ARTEFACTS_COLUMN_NAME + " = '" + name + "'";
        Cursor res = db.rawQuery(query, null);
        if (res.getCount() == 0) {
            return false;
        }
        return true;
    }

    public void insertHints(String id, String used, String chapter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(HINT_COLUMN_ID, id);
        cv.put(HINT_COLUMN_USED, used);
        cv.put(HINT_COLUMN_CHAPTER, chapter);
        db.insert(TABLE_HINTS, null, cv);
    }

    public void insertRBAllgemeines(String solved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RBALLGEMEINES_COLUMN_SOLVED, solved);
        db.insert(TABLE_RBALLGEMEINES, null, cv);
    }

    public void insertRBBraunschweig(String solved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RBBRAUNSCHWEIG_COLUMN_SOLVED, solved);
        db.insert(TABLE_RBBRAUNSCHWEIG, null, cv);
    }

    public void insertRBGeographie(String solved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RBGEOGRAPHIE_COLUMN_SOLVED, solved);
        db.insert(TABLE_RBGEOGRAPHIE, null, cv);
    }

    public void insertRBInformatik(String solved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RBINFORMATIK_COLUMN_SOLVED, solved);
        db.insert(TABLE_RBINFORMATIK, null, cv);
    }

    public void insertRBLogik(String solved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RBLOGIK_COLUMN_SOLVED, solved);
        db.insert(TABLE_RBLOGIK, null, cv);
    }


    public void insertRBMathematik(String solved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RBMATHEMATIK_COLUMN_SOLVED, solved);
        db.insert(TABLE_RBMATHEMATIK, null, cv);
    }

    public void insertRBSprache(String solved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RBSPRACHE_COLUMN_SOLVED, solved);
        db.insert(TABLE_RBSPRACHE, null, cv);
    }

    public void insertCoins(String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COINS_COLUMN_AMOUNT, amount);
        db.insert(TABLE_COINS, null, cv);
    }

    public void insertLocation(String id, String entered, String current) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOCATION_COLUMN_ID, id);
        cv.put(LOCATION_COLUMN_ENTERED, entered);
        cv.put(LOCATION_COLUMN_CURRENT, current);
        db.insert(TABLE_LOCATION, null, cv);
    }

    public void insertLanguage(String select) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LANGUAGE_COLUMN_SELECTION, select);
        db.insert(TABLE_LANGUAGE, null, cv);
    }

    public void insertSound(String select) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SOUND_COLUMN_SELECTION, select);
        db.insert(TABLE_SOUND, null, cv);
    }

    public void insertSQL(String select) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SQL_COLUMN_select, select);
        db.insert(TABLE_SQL, null, cv);
    }

    public void insertUsername(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERNAME_COLUMN_NAME, name);
        db.insert(TABLE_USERNAME, null, cv);
    }

    public void updateLanguage(String lang, String select) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LANGUAGE_COLUMN_SELECTION, select);
        db.update(TABLE_LANGUAGE, cv, "lang = ?", new String[]{lang});
    }


    public void updateStory(String riddle, String unlocked, String solved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STORY_COLUMN_UNLOCKED, unlocked);
        cv.put(STORY_COLUMN_SOLVED, solved);
        db.update(TABLE_STORY, cv, "LBriddle = ?", new String[]{riddle});
    }

    public void updateStoryUnlocked(String riddle, String unlocked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STORY_COLUMN_UNLOCKED, unlocked);
        db.update(TABLE_STORY, cv, "LBriddle = ?", new String[]{riddle});
    }

    public void updateArtefactUnlocked(String name, String unlocked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ARTEFACTS_COLUMN_UNLOCKED, unlocked);
        db.update(TABLE_ARTEFACTS, cv, "artefact_name = ?", new String[]{name});
    }

    public void updateHints(String id, String used, String chapter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(HINT_COLUMN_USED, used);
        db.update(TABLE_HINTS,  cv, "id = ? and chapterID = ?", new String[]{id, chapter});
    }

    public void updateRBAllgemeines(String raetsel, String solved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RBALLGEMEINES_COLUMN_SOLVED, solved);
        db.update(TABLE_RBALLGEMEINES,  cv, "NLBalriddle = ?", new String[]{raetsel});
    }

    public void updateRBBraunschweig(String raetsel, String solved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RBBRAUNSCHWEIG_COLUMN_SOLVED, solved);
        db.update(TABLE_RBBRAUNSCHWEIG,  cv, "NLBbsriddle = ?", new String[]{raetsel});
    }

    public void updateRBGeographie(String raetsel, String solved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RBGEOGRAPHIE_COLUMN_SOLVED, solved);
        db.update(TABLE_RBGEOGRAPHIE,  cv, "NLBgeoriddle = ?", new String[]{raetsel});
    }

    public void updateRBInformatik(String raetsel, String solved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RBINFORMATIK_COLUMN_SOLVED, solved);
        db.update(TABLE_RBINFORMATIK,  cv, "NLBinforiddle = ?", new String[]{raetsel});
    }

    public void updateRBLogik(String raetsel, String solved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RBLOGIK_COLUMN_SOLVED, solved);
        db.update(TABLE_RBLOGIK,  cv, "NLBloriddle = ?", new String[]{raetsel});
    }

    public void updateRBMathematik(String raetsel, String solved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RBMATHEMATIK_COLUMN_SOLVED, solved);
        db.update(TABLE_RBMATHEMATIK,  cv, "NLBmariddle = ?", new String[]{raetsel});
    }

    public void updateRBSprache(String raetsel, String solved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RBSPRACHE_COLUMN_SOLVED, solved);
        db.update(TABLE_RBSPRACHE,  cv, "NLBspriddle = ?", new String[]{raetsel});
    }


    public void updateCoins(String coin, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COINS_COLUMN_AMOUNT, amount);
        db.update(TABLE_COINS, cv, "coins = ?", new String[]{coin});
    }

    public void updateLocation(String entered, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOCATION_COLUMN_ENTERED, entered);
        db.update(TABLE_LOCATION, cv, "locationid = ?", new String[]{id});
    }

    public void updateSound(String id, String selectSound) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SOUND_COLUMN_SELECTION, selectSound);
        db.update(TABLE_SOUND, cv, "soundonoff = ?", new String[]{id});
    }

    public void updateSQL(String sql, String selected) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SQL_COLUMN_select, selected);
        db.update(TABLE_SQL, cv, "sql = ?", new String[]{sql});
    }

    public void updateUsername(String newUsername, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERNAME_COLUMN_NAME, name);
        db.update(TABLE_USERNAME, cv, "user = ?", new String[]{newUsername});
    }


    public int getLanguage() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + LANGUAGE_COLUMN_SELECTION + " FROM " + TABLE_LANGUAGE  + " WHERE " + LANGUAGE_COLUMN_LANG + " = '" + "1" + "'", null);
        if (res.moveToFirst()) {
            return Integer.parseInt(res.getString(0));
        }
        return 0;
    }

    public int getSound() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + SOUND_COLUMN_SELECTION + " FROM " + TABLE_SOUND  + " WHERE " + SOUND_COLUMN_ID + " = '" + "1" + "'", null);
        if (res.moveToFirst()) {
            return Integer.parseInt(res.getString(0));
        }
        return 0;
    }

    public int getSQL() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + SQL_COLUMN_select + " FROM " + TABLE_SQL  + " WHERE " + SQL_COLUMN_SQL + " = '" + "1" + "'", null);
        if (res.moveToFirst()) {
            return Integer.parseInt(res.getString(0));
        }
        return 0;
    }

    public boolean getStoryUnlocked(String riddle) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + STORY_COLUMN_UNLOCKED + " FROM " + TABLE_STORY + " WHERE " + STORY_COLUMN_RIDDLE + " = '" + riddle + "'", null);
        if (res.moveToFirst()) {
            if (res.getString(0).equals("true")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean getStorySolved(String riddle) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + STORY_COLUMN_SOLVED + " FROM " + TABLE_STORY + " WHERE " + STORY_COLUMN_RIDDLE + " = '" + riddle + "'", null);
        if (res.moveToFirst()) {
            if (res.getString(0).equals("true")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean getArtefactUnlocked(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + ARTEFACTS_COLUMN_UNLOCKED + " FROM " + TABLE_ARTEFACTS + " WHERE " + ARTEFACTS_COLUMN_NAME + " = '" + name + "'", null);
        if (res.moveToFirst()) {
            if (res.getString(0).equals("true")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean checkStoryUnlocked(String riddle) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + STORY_COLUMN_UNLOCKED + " FROM " + TABLE_STORY + " WHERE " + STORY_COLUMN_RIDDLE + " = '" + riddle + "'", null);
        if (res.moveToFirst()) {
            if (res.getString(0).equals(null)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean getHintUsed(String id, String chapter) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + HINT_COLUMN_USED + " FROM " + TABLE_HINTS + " WHERE " + HINT_COLUMN_ID + " = '" + id + "'" + " AND " + HINT_COLUMN_CHAPTER + " = '" + chapter + "'", null);

        if (res.moveToFirst()) {
            if (res.getString(0).equals("true")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
        }

    public int getBlub() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + STORY_COLUMN_UNLOCKED + " FROM " + TABLE_STORY, null);
        int i = 0;
        while (res.moveToNext()) {
            i++;
        }

        return i;
    }

    public boolean getRBAllgemeinesSolved(String riddle) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + RBALLGEMEINES_COLUMN_SOLVED + " FROM " + TABLE_RBALLGEMEINES + " WHERE " + RBALLGEMEINES_COLUMN_RIDDLE + " = '" + riddle + "'", null);
        if (res.moveToFirst()) {
            if (res.getString(0).equals("true")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    public boolean getRBBraunschweigSolved(String riddle) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + RBBRAUNSCHWEIG_COLUMN_SOLVED + " FROM " + TABLE_RBBRAUNSCHWEIG + " WHERE " + RBBRAUNSCHWEIG_COLUMN_RIDDLE + " = '" + riddle + "'", null);
        if (res.moveToFirst()) {
            if (res.getString(0).equals("true")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean getRBGeographieSolved(String riddle) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + RBGEOGRAPHIE_COLUMN_SOLVED + " FROM " + TABLE_RBGEOGRAPHIE + " WHERE " + RBGEOGRAPHIE_COLUMN_RIDDLE + " = '" + riddle + "'", null);
        if (res.moveToFirst()) {
            if (res.getString(0).equals("true")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean getRBInformatikSolved(String riddle) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + RBINFORMATIK_COLUMN_SOLVED + " FROM " + TABLE_RBINFORMATIK + " WHERE " + RBINFORMATIK_COLUMN_RIDDLE + " = '" + riddle + "'", null);
        if (res.moveToFirst()) {
            if (res.getString(0).equals("true")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean getRBLogikSolved(String riddle) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + RBLOGIK_COLUMN_SOLVED + " FROM " + TABLE_RBLOGIK + " WHERE " + RBLOGIK_COLUMN_RIDDLE + " = '" + riddle + "'", null);
        if (res.moveToFirst()) {
            if (res.getString(0).equals("true")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean getRBMathematikSolved(String riddle) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + RBMATHEMATIK_COLUMN_SOLVED + " FROM " + TABLE_RBMATHEMATIK + " WHERE " + RBMATHEMATIK_COLUMN_RIDDLE + " = '" + riddle + "'", null);
        if (res.moveToFirst()) {
            if (res.getString(0).equals("true")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean getRBSpracheSolved(String riddle) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + RBSPRACHE_COLUMN_SOLVED + " FROM " + TABLE_RBSPRACHE + " WHERE " + RBSPRACHE_COLUMN_RIDDLE + " = '" + riddle + "'", null);
        if (res.moveToFirst()) {
            if (res.getString(0).equals("true")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }



    public int getCoins(String coin) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + COINS_COLUMN_AMOUNT + " FROM " + TABLE_COINS  + " WHERE " + COINS_COLUMN_COINS + " = '" + coin + "'", null);
        if (res.moveToFirst()) {
            return Integer.parseInt(res.getString(0));
        }
        return 0;
    }

    public int getCurrentAnswer(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + CURRENT_COLUMN_ANSWER + " FROM " + TABLE_CURRENT  + " WHERE " + CURRENT_COLUMN_NAME + " = '" + name + "'", null);
        if (res.moveToFirst()) {
            return Integer.parseInt(res.getString(0));
        }
        return 0;
    }

    public String getAnswer(String name) {
        ArrayList<String> ans = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + STORY_COLUMN_ANSWER + " FROM " + TABLE_STORY + " WHERE " + STORY_COLUMN_RIDDLE + " = '" + name + "'", null);
        while (res.moveToNext()) {
            return res.getString(0);
        }
        return "";
    }

    public String getChapterName(String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + CURRENT_COLUMN_NAME + " FROM " + TABLE_CURRENT  + " WHERE " + CURRENT_COLUMN_ANSWER + " = '" + answer + "'", null);
        if (res.moveToFirst()) {
            return res.getString(0);
        }
        return "";
    }

    public ArrayList<String> getArtefacts(String chapter) {
        ArrayList<String> artefacts = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + ARTEFACTS_COLUMN_NAME + " FROM " + TABLE_ARTEFACTS + " WHERE " + ARTEFACTS_COLUMN_CHAPTER + " = '" + chapter + "'" , null);
        while (res.moveToNext()) {
            artefacts.add(res.getString(0));
        }
        return artefacts;
    }

    public ArrayList<String> getUnlockedArtefacts() {
        ArrayList<String> artefacts = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + ARTEFACTS_COLUMN_NAME + " FROM " + TABLE_ARTEFACTS + " WHERE " + ARTEFACTS_COLUMN_UNLOCKED + " = 'true'" , null);
        while (res.moveToNext()) {
            artefacts.add(res.getString(0));
        }
        return artefacts;
    }

    public void deleteCurrent(String name) {
        String query = "DELETE FROM " + TABLE_CURRENT + " WHERE " + CURRENT_COLUMN_NAME + " = '" + name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }

    public String getUsername(String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + USERNAME_COLUMN_NAME + " FROM " + TABLE_USERNAME  + " WHERE " + USERNAME_COLUMN_USER + " = '" + user + "'", null);
        if (res.moveToFirst()) {
            return res.getString(0);
        }
        return "";
    }

    public boolean getLocation(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor coolCursor = db.rawQuery("SELECT " + LOCATION_COLUMN_ENTERED + " FROM " + TABLE_LOCATION + " WHERE " + LOCATION_COLUMN_ID + " = '" + id + "'", null);
        if (coolCursor.moveToFirst()) {
            if (coolCursor.getString(0).equals("true")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    public void updateCurrentChapter(String id, String current) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOCATION_COLUMN_CURRENT, current);
        db.update(TABLE_LOCATION, cv, "locationid = ?", new String[]{id});
    }
    public String getCurrentChapter() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor coolCursor = db.rawQuery("SELECT " + CURRENT_COLUMN_NAME + " FROM " + TABLE_CURRENT, null);
        if (coolCursor.moveToFirst()) {
            return coolCursor.getString(0);
        }
        return "";
    }
}