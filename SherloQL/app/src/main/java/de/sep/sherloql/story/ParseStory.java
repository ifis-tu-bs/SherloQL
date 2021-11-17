package de.sep.sherloql.story;

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

import de.sep.sherloql.savestate.SaveStateHelper;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * @author Judy
 * @author Samreen
 * @author Khiet-Nhi
 */
public class ParseStory {

    protected Context context;
    private ArrayList<Chapter> chapterArrayList;
    private Story story;
    private SaveStateHelper stateHelper;

    public ParseStory(Context context)
    {
        this.context = context;
        stateHelper = new SaveStateHelper(context);
    }

    private String readJSONDataFromAssetsFile() throws IOException {
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();
        try {
            String jsonString;
            String PATH_JSON;
            if (stateHelper.getSQL() == 1 && stateHelper.getLanguage() == 0) {
                PATH_JSON = "story_de_sql.json";
            } else if (stateHelper.getSQL() == 1 && stateHelper.getLanguage() == 1) {
                PATH_JSON = "story_en_sql.json";
            } else if (stateHelper.getSQL() == 0 && stateHelper.getLanguage() == 0){
                PATH_JSON = "story_de_non_sql.json";
            } else {
                PATH_JSON = "story_de_non_sql.json";
            }
            // Verbindung
            inputStream = context.getAssets().open(PATH_JSON);
            // Liest Json datei
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            // In datei Zeile für Zeile durchlaufen.
            while ((jsonString = bufferedReader.readLine()) != null) {
                // Gelesene zeile wird zu builder hinzugefügt.
                builder.append(jsonString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return new String(builder);
    }


    public Story parse() {
        Log.d(TAG, "parsing");
        chapterArrayList = new ArrayList<>();

        try {
            JSONObject jsonFile = new JSONObject(readJSONDataFromAssetsFile());
            JSONArray jsonItemsArray = jsonFile.getJSONArray("Story");

            for (int i = 0; i < jsonItemsArray.length(); ++i) {
                JSONObject jsonStory = jsonItemsArray.getJSONObject(i);

                /**
                 * chapter          String
                 * image            String
                 * flag             String      -> Int
                 * question         String
                 * longitude         String     -> float/double
                 * latitude         String      -> float/double
                 * type             String
                 * answer           String
                 * dependency       String[]
                 * artefact         String[]
                 * TTL              String[]
                 * hints            JSONObject  -> Hints[]
                 *      cost        String      -> Int
                 *      hint        String
                 * dialogue
                 *      image       String
                 *      person      String
                 *      text        String
                 *      position    String      -> Int
                 * place            String
                 */

                String chapter      = jsonStory.getString("chapter");
                String image        = jsonStory.getString("image").toLowerCase();
                int flag            = Integer.parseInt(jsonStory.getString("flag"));
                String question     = jsonStory.getString("question");
                double longitude    = Double.parseDouble(jsonStory.getString("longitude"));
                double latitude     = Double.parseDouble(jsonStory.getString("latitude"));
                String type         = jsonStory.getString("type");
                String answer       = jsonStory.getString("answer");
                String placeImg     = jsonStory.getString("place").toLowerCase().replace(" ","_" );

                int imageRiddle = context.getResources()
                        .getIdentifier(image, "drawable", context.getPackageName());
                int place = context.getResources()
                        .getIdentifier(placeImg, "drawable", context.getPackageName());


                JSONArray dependencyArray = jsonStory.getJSONArray("dependency");
                ArrayList<String> dependencyArrayList = new ArrayList<>();
                for (int k = 0; k < dependencyArray.length(); k++) {
                    String dependencyName = dependencyArray.getString(k);
                    dependencyArrayList.add(dependencyName);
                }

                ArrayList<String> artefactArrayList = new ArrayList<>();
                JSONArray artefactArray = jsonStory.getJSONArray("artefact");
                for (int k = 0; k < artefactArray.length(); k++) {
                    String artefactName = artefactArray.getString(k);
                    artefactArrayList.add(artefactName);
                }

                ArrayList<String> ttlArrayList = new ArrayList<>();
                JSONArray ttlArray = jsonStory.getJSONArray("TTL");
                for (int k = 0; k < ttlArray.length(); k++) {
                    String ttlObject = ttlArray.getString(k);
                    ttlArrayList.add(ttlObject);
                }

                JSONArray hintArray = jsonStory.getJSONArray("hints");
                ArrayList<Hints> hintsArrayList = new ArrayList<>();
                for (int k = 0; k < hintArray.length(); k++) {
                    JSONObject hintObject = hintArray.getJSONObject(k);
                    String hint = hintObject.getString("hint");
                    String strCost = hintObject.getString("cost");
                    int cost = strCost.equals("") ? 0: Integer.parseInt(strCost);

                    Hints hints = new Hints(hint, cost, false);
                    hintsArrayList.add(hints);
                }


                JSONArray dialogueArray = jsonStory.getJSONArray("dialogue");
                ArrayList<Dialogue> dialoguesBeforeRiddle = new ArrayList<>();
                ArrayList<Dialogue> dialoguesAfterRiddle = new ArrayList<>();

                for (int j = 0; j < dialogueArray.length(); j++) {
                    JSONObject dialogueObject = dialogueArray.getJSONObject(j);

                    String person = dialogueObject.getString("person");
                    String personImage = person.replace(" ", "").toLowerCase();
                    String text = dialogueObject.getString("text");
                    int imageDialogue = context.getResources().getIdentifier(personImage
                            + dialogueObject.getString("image").toLowerCase(), "drawable", context.getPackageName());
                    int position = Integer.parseInt(dialogueObject.getString("position"));
                    int dialoguePosition = position % 100;
                    int beforeOrAfter = (position - dialoguePosition) / 100;
                    if (beforeOrAfter == 1) {
                        Dialogue dialogue = new Dialogue(person, text, imageDialogue, position);
                        dialoguesBeforeRiddle.add(dialogue);
                    } else if (beforeOrAfter == 2) {
                        Dialogue dialogue = new Dialogue(person, text, imageDialogue, position);
                        dialoguesAfterRiddle.add(dialogue);
                    }
                }

                Riddle riddle = new Riddle(type, imageRiddle, question, answer, hintsArrayList);
                boolean unlocked = i == 0 ? true : false;
                Chapter chapterObject = new Chapter(
                        chapter,
                        longitude,
                        latitude,
                        flag,
                        dialoguesAfterRiddle,
                        dialoguesBeforeRiddle,
                        unlocked,
                        false,
                        riddle,
                        dependencyArrayList,
                        ttlArrayList,
                        artefactArrayList,
                        place);
                chapterArrayList.add(chapterObject);
            }

            story = new Story(chapterArrayList);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return story;
    }
    public int getItemCount() {
        return chapterArrayList.size();
    }

}
