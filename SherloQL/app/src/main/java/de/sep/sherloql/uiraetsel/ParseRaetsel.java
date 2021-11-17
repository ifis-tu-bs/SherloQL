package de.sep.sherloql.uiraetsel;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import de.sep.sherloql.bin.MainActivity;
import de.sep.sherloql.bin.Map;
import de.sep.sherloql.savestate.SaveStateHelper;
import de.sep.sherloql.uiraetsel.Raetsel;

/**
 * In dieser Klasse wird die JSON-Datei nog_raetsel.json von Ordner assets gelesen
 * und in die Variablen von Raetsel Klasse gespeichert.
 */
public class ParseRaetsel {
    // Attribute deklariert.
    private static final String TAG = "ParseRaetsel";
    protected Context context;
    private Object Picasso;
    private SaveStateHelper stateHelper;

    /**
     * Json Datei wird von Downloads Ordner gelesen.
     * IOException in parse Methode muss weg.
     * @param context
     * @return
     */
    private String readFromFile(Context context) {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        String ret = "";
        StringBuilder sbuilder = new StringBuilder();
        try {
            String FILENAME = "nog_raetsel.json";
            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File myFile = new File(folder, FILENAME);
            FileInputStream fstream = new FileInputStream(myFile);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fstream, StandardCharsets.UTF_8));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                // Gelesene zeile wird zu builder hinzugefügt.
                sbuilder.append(str);
            }
            fstream.close();


        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return new String(sbuilder);
    }
    /**
     *
     * @param context
     */
    public ParseRaetsel(Context context)
    {
        this.context = context;
        stateHelper = new SaveStateHelper(context);
    }
    /**
     * Json Datei von Ordner assets lesen.
     * @return gelesene Datei als ein String
     * @throws IOException
     */
    private String readJSONDataFromAssetsFile() throws IOException {
        Log.d(TAG, "readJSONDataFromAssetsFile: starts");
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();
        try {

            String jsonString;
            String PATH_JSON;
            if (stateHelper.getLanguage() == 0) {
                PATH_JSON = "nog_raetsel.json";
            } else {
                PATH_JSON = "nog_raetsel_en.json";
            }
            inputStream = context.getAssets().open(PATH_JSON);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            while ((jsonString = bufferedReader.readLine()) != null) {
                builder.append(jsonString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return new String(builder);
    }
    /**
     * Hier wird die Json Datei gelesen und alle Objekte in einem ArrayList gespeichert.
     * @return arraylist von Json Datei gelesene Objekte.
     */
    public ArrayList<Raetsel> parse() {
        ArrayList<Raetsel> raetselArrayList = new ArrayList<>();
        try {
            Log.d(TAG, "parse: starts");
            JSONObject jsonFile;
            jsonFile = new JSONObject(readJSONDataFromAssetsFile());

            JSONArray jsonItemsArray = jsonFile.getJSONArray("Raetsel");
            // In JsonArray durch Objekte iterieren
            for (int i = 0; i < jsonItemsArray.length(); ++i) {
                Log.d(TAG, "parse: starts");
                JSONObject jsonRaetsel = jsonItemsArray.getJSONObject(i);
                // Attribute jedes Objekt in Variablen speichern.
                String type = jsonRaetsel.getString("type");
                String name = jsonRaetsel.getString("name");
                String category = jsonRaetsel.getString("category");
                String difficulty = jsonRaetsel.getString("difficulty");
                String question = jsonRaetsel.getString("question");
                String points = jsonRaetsel.getString("points");
                String answer = jsonRaetsel.getString("answer");
                String image = jsonRaetsel.getString("image");
                String choiceOne = jsonRaetsel.getJSONArray("choices").getString(0);
                String choiceTwo = jsonRaetsel.getJSONArray("choices").getString(1);
                String choiceThree = jsonRaetsel.getJSONArray("choices").getString(2);
                String choiceFour = jsonRaetsel.getJSONArray("choices").getString(3);
                // Werte ins Raetsel Konstrukter übergeben.
                Raetsel raetsel = new Raetsel(type, name, category, difficulty,question, points, answer, image,  new Raetsel.Choices(choiceOne, choiceTwo, choiceThree, choiceFour), false);
                // Objekt in ArrayList hinzugefügt.
                Log.d(TAG, "parse: starts");
                raetselArrayList.add(raetsel);
            }
        } catch (JSONException | IOException jsone) {
            Log.e(TAG, "JSON oder IO Error: " + jsone.getMessage());
            jsone.printStackTrace();
        }
        return raetselArrayList;
    }
}