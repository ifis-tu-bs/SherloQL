

package de.sep.sherloql.database.database_activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

import de.sep.sherloql.bin.Database;
import de.sep.sherloql.R;
import de.sep.sherloql.bin.Map;
import de.sep.sherloql.bin.Tutorial;
import de.sep.sherloql.database.database_logic.ConstValues;
import de.sep.sherloql.database.database_logic.DBOpenHelper;
import de.sep.sherloql.database.database_logic.DBOpenHelperEn;
import de.sep.sherloql.savestate.SaveStateHelper;
import de.sep.sherloql.story.Chapter;
import de.sep.sherloql.story.Dialogue;
import de.sep.sherloql.story.DialogueFragment;
import de.sep.sherloql.savestate.SaveStateHelper;


/**
 * Implementiert die Logik der activity_select Activität
 *
 * @author Christian, MatthiasFranz, MatthiasThang, Erkan
 */
public class ActivityQuery extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Serializable {

    private static final String TAG = "ActivityQuery";

    private final int REQUEST_CODE_JOIN = 0;
    private final int REQUEST_CODE_WHERE = 1;
    private final int REQUEST_CODE_DELETE_JOIN = 2;
    private final int REQUEST_CODE_DELETE_WHERE = 3;
    private final int REQUEST_CODE_SELECT = 4;
    private final int REQUEST_CODE_DELETE_SELECT = 5;
    private final int REQUEST_CODE_GROUPBY = 6;
    private final int REQUEST_CODE_DELETE_GROUPBY = 7;
    int chapter;
    String chapter_name;

    private SaveStateHelper ssHelper;

    boolean success;

    private LinkedList<String> tables;

    DBOpenHelper dbOpenHelper;
    DBOpenHelperEn dbOpenHelperEn;
    private Map map;

    Button bttnBack;
    Button bttnJoin;
    Button bttnDeletJoin;
    Button bttnSelect;
    Button bttnDeleteSelect;
    Button bttnWhere;
    Button bttnDeleteWhere;
    Button bttnGroupBy;
    Button bttnRealGroupBy;
    Button bttnRealDeleteGroupBy;
    ImageButton erdiagram;
    CheckBox checkBoxDistinct;
    private boolean distinct;
    private SaveStateHelper stateHelper;

    TextView textView;

    Spinner selectedTableSpinner;
    //String selectedTable;

    StringBuilder query = new StringBuilder();

    ArrayList<String> selectsList = new ArrayList<>();
    ArrayList<String> joinsList = new ArrayList<>();
    ArrayList<String> wheresList = new ArrayList<>();
    ArrayList<String> selectedTables = new ArrayList<>();
    ArrayList<String> groupbyList = new ArrayList<>();
    Chapter chapterObj;

    private boolean importantWordFound = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        ssHelper = new SaveStateHelper(this);
        dbOpenHelper = new DBOpenHelper(this);
        dbOpenHelperEn = new DBOpenHelperEn(this);
        if (ssHelper.getLanguage() == 0) {
            tables = dbOpenHelper.getTables();
        } else {
            tables = dbOpenHelperEn.getTables();
        }
        Intent intent = getIntent();
        chapter = -1;
        chapter_name = "";
        chapter = intent.getIntExtra("chapter", -1);
        chapter_name = intent.getStringExtra("name");
        chapterObj = (Chapter) intent.getSerializableExtra("obj");
        success = false;
        distinct = false;
        stateHelper = new SaveStateHelper(this);
        initButtons();
        initOther();
        initSpinner();
    }

    private void initOther() {
        textView = (TextView) findViewById(R.id.sqlAbfrageLabel);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    private void initSpinner(){
        // FromSpinner
        selectedTableSpinner = (Spinner) findViewById(R.id.fromSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                tables);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        selectedTableSpinner.setAdapter(adapter);
        selectedTableSpinner.setOnItemSelectedListener(this);
    }

    private void initButtons() {
        //  Button Back
        bttnBack = (Button) findViewById(R.id.select_back);
        bttnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectsList.isEmpty() && joinsList.isEmpty() && wheresList.isEmpty() && groupbyList.isEmpty()) {
                    onBackPressed();
                }
                else {
                    selectsList.clear();
                    joinsList.clear();
                    wheresList.clear();
                    groupbyList.clear();
                    updateTextView();
                }
            }
        });
        erdiagram = (ImageButton) findViewById(R.id.er);
        erdiagram.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityDiagram.class);
                startActivity(intent);
            }
        });
        //deutsch oder englisch
        if (stateHelper.getLanguage() == 0) {
            bttnBack.setText("zurück");
        } else {
            bttnBack.setText("back");
        }

        //  Button Join
        bttnJoin = (Button) findViewById(R.id.selectJoin);
        bttnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityJoin.class);
                intent.putExtra(ConstValues.FROM, selectedTables);
                startActivityForResult(intent, REQUEST_CODE_JOIN);
            }
        });
        bttnDeletJoin = (Button) findViewById(R.id.selectBttnDeleteJoin);
        bttnDeletJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DeleteJoin.class);
                intent.putExtra(ConstValues.JOINS_LIST, joinsList);
                startActivityForResult(intent, REQUEST_CODE_DELETE_JOIN);
            }
        });


        //Select
        bttnSelect = (Button) findViewById(R.id.bttn_query_select);
        bttnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTables.get(0) != null) {
                    Intent intent = new Intent(v.getContext(), ActivitySelect.class);
                    intent.putExtra(ConstValues.FROM, selectedTables);
                    startActivityForResult(intent, REQUEST_CODE_SELECT);
                }
            }
        });
        bttnDeleteSelect = (Button) findViewById(R.id.select_deleteselect);
        bttnDeleteSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DeleteSelect.class);
                intent.putExtra("selectsList", selectsList);
                startActivityForResult(intent, REQUEST_CODE_DELETE_SELECT);
            }
        });

        //Where
        bttnWhere = (Button) findViewById(R.id.select_where);
        bttnWhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityWhere.class);
                intent.putExtra(ConstValues.FROM, selectedTables);
                if(wheresList.isEmpty()){
                    intent.putExtra("empty", true);
                }
                else{
                    intent.putExtra("empty", false);
                }
                startActivityForResult(intent, REQUEST_CODE_WHERE);
            }
        });
        bttnDeleteWhere = (Button) findViewById(R.id.bttnSelectDeleteWhere);
        bttnDeleteWhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DeleteWhere.class);
                intent.putExtra(ConstValues.WHERES_LIST, wheresList);
                startActivityForResult(intent, REQUEST_CODE_DELETE_WHERE);
            }
        });


        //checkboxDistinct
        checkBoxDistinct = (CheckBox) findViewById(R.id.checkBoxDistinct);
        checkBoxDistinct.setChecked(false);
        checkBoxDistinct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxDistinct.isChecked()) {
                    distinct = true;
                } else {
                    distinct = false;
                }
                updateTextView();
            }

        });

        //group by
        bttnRealGroupBy = (Button) findViewById(R.id.buttonRealGroupBy);
        bttnRealGroupBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTables.get(0) != null) {
                    Intent intent = new Intent(v.getContext(), ActivityGroupBy.class);
                    intent.putExtra(ConstValues.FROM, selectedTables);
                    startActivityForResult(intent, REQUEST_CODE_GROUPBY);
                }
            }
        });
        bttnRealDeleteGroupBy = (Button) findViewById(R.id.select_delete_realgroupby);
        bttnRealDeleteGroupBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DeleteGroupBy.class);
                intent.putExtra(ConstValues.GROUPBY_LIST, groupbyList);
                startActivityForResult(intent, REQUEST_CODE_DELETE_GROUPBY);
            }
        });


        //query ausführen
        bttnGroupBy = (Button) findViewById(R.id.select_groupby);
        bttnGroupBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = ssHelper.getAnswer(chapter_name);
                importantWordFound = false;
                if (selectsList.isEmpty()) {
                    //deutsch oder englisch
                    if (stateHelper.getLanguage() == 0) {
                        Toast.makeText(getApplicationContext(), "Keine gültige Anfrage", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "No valid Query", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Cursor resultCursor;
                    if (ssHelper.getLanguage() == 0) {
                        resultCursor = dbOpenHelper.getSelect(query.toString());
                    } else {
                        resultCursor = dbOpenHelperEn.getSelect(query.toString());
                    }

                    if (resultCursor.getCount() == 0) {
                        // show message
                        showMessage("Error", "Keine Ergebnisse");
                        return;
                    }
                    StringBuffer buffer;
                    // Schau, ob in Select ein * Vorkommt
                    if (checkSelect()) {
                        LinkedList<String> values;
                        if (stateHelper.getLanguage() == 0) {
                            values = dbOpenHelper.getValues(selectedTables.get(0));
                        } else {
                            values = dbOpenHelperEn.getValues(selectedTables.get(0));
                        }

                        buffer = new StringBuffer();
                        while (resultCursor.moveToNext()) {
                            for (int i = 0; i < values.size(); i++) {
                                buffer.append(values.get(i) + ": " + resultCursor.getString(i) + "\n");
                            }
                            buffer.append("\n");
                        }
                        if(values.size() == 1 && countOccurences(buffer, values.get(0)) > 0 && countOccurences(buffer, values.get(0)) < 2) {
                            String q = buffer.toString();
                            for(int i = 0; i < values.size(); i++) {
                                q.replace(values.get(i),"");
                            }
                            if(q.contains(answer)) {
                                //deutsch oder englisch
                                String out;
                                if (stateHelper.getLanguage() == 0) {
                                    out = "Korrekte Anfrage für Kapitel: " + chapter_name;
                                } else {
                                    out = "Correct query for chapter: " + chapter_name;
                                }
                                ssHelper.updateStory(chapter_name, "true", "true");
                                ArrayList<String> artefacts = ssHelper.getArtefacts(chapter_name);
                                for(int i = 0; i < artefacts.size(); i++) {
                                    Log.d(TAG, "query huihuihui: "+ artefacts.get(i) + ", chapter " + chapter_name);
                                    String check = "!" + artefacts.get(i);
                                    if (ssHelper.checkArtefactExists(check)) {
                                        ssHelper.updateArtefactUnlocked(check, "false");
                                    }
                                    ssHelper.updateArtefactUnlocked(artefacts.get(i), "true");
                                }
                                Toast toast = Toast.makeText(getApplicationContext(), out, Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    } else {
                        buffer = new StringBuffer();
                        while (resultCursor.moveToNext()) {
                            for (int i = 0; i < selectsList.size(); i++) {
                                buffer.append(selectsList.get(i) + ": " + resultCursor.getString(i) + "\n");
                            }
                            buffer.append("\n");
                        }
                        if(selectsList.size() == 1 && countOccurences(buffer, selectsList.get(0)) > 0 && countOccurences(buffer, selectsList.get(0)) < 2) {
                            String q = buffer.toString();
                            for(int i = 0; i < selectsList.size(); i++) {
                                q.replace(selectsList.get(i),"");
                            }
                            if(q.contains(answer)) {
                                //deutsch oder englisch
                                String out;
                                if (stateHelper.getLanguage() == 0) {
                                    out = "Korrekte Anfrage für Kapitel: " + chapter_name;
                                } else {
                                    out = "Correct query for chapter: " + chapter_name;
                                }
                                ssHelper.updateStory(chapter_name, "true", "true");
                                ArrayList<String> artefacts = ssHelper.getArtefacts(chapter_name);
                                for(int i = 0; i < artefacts.size(); i++) {
                                    Log.d(TAG, "query huihuihui: "+ artefacts.get(i) + ", chapter " + chapter_name);
                                    String check = "!" + artefacts.get(i);
                                    if (ssHelper.checkArtefactExists(check)) {
                                        ssHelper.updateArtefactUnlocked(check, "false");
                                    }
                                    ssHelper.updateArtefactUnlocked(artefacts.get(i), "true");
                                }
                                Toast toast = Toast.makeText(getApplicationContext(), out, Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    }
                    // Show all data
                    showMessage("Data", buffer.toString());
                }
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        if (chapter == -1) {
            Intent intent = new Intent(this, Database.class);
            startActivity(intent);
        }
        super.onBackPressed();  // optional depending on your needs
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedTables.clear();
        selectedTables.add(0, selectedTableSpinner.getItemAtPosition(position).toString());
        selectsList.clear();
        joinsList.clear();
        wheresList.clear();
        bttnDeletJoin.setVisibility(View.INVISIBLE);
        updateTextView();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * schaut, ob in der Select abfrage ein "*" vorkommt
     *
     * @return  boolean
     */
    private boolean checkSelect() {
        for (int i = 0; i < selectsList.size(); i++) {
            if (selectsList.get(i).equals("*")) {
                return true;
            }
        }
        return false;
    }

    private int countOccurences(StringBuffer buffer, String word) {
        String b = buffer.toString();
        int lastIndex = 0;
        int count = 0;

        while(lastIndex != -1){

            lastIndex = b.indexOf(word,lastIndex);

            if(lastIndex != -1){
                count ++;
                lastIndex += word.length();
            }
        }
        return count;
    }

    protected void updateTextView() {

        if (!wheresList.isEmpty()) {
            if (wheresList.get(0).substring(0, 3).equals("AND")) {
                Log.d(TAG, "updateTextView: and");
                wheresList.set(0, wheresList.get(0).substring(4));
            } else if (wheresList.get(0).substring(0, 2).equals("OR")) {
                wheresList.set(0, wheresList.get(0).substring(3));
            }
        }
        //bei jedem Aufruf wird der String von Anfang an neu gebaut
        query.setLength(0);

        //fügt die ausgewählten Attribute an den Builder an
        query.append("SELECT ");
        if (distinct) {
            query.append("DISTINCT ");
        }
        if (selectsList.size() != 0) {
            if (checkSelect()) {
                query.append("*" + "\n");
            }
            else {
                for (int i = 0; i < selectsList.size(); i++) {
                    if (i == selectsList.size() - 1) {
                        query.append(selectsList.get(i) + "\n");
                    } else {
                        query.append(selectsList.get(i) + ", ");
                    }
                }
            }

        } else {
            query.append("\n");
        }
        //fügt die ausgewählte Tabelle hinten an den Builder an
        if (selectedTables.get(0) != null) {
            query.append("FROM " + selectedTables.get(0) + "\n");
        } else {
            query.append("FROM \n");
        }
        //fügt die Joins an den Builder an
        if (joinsList.size() != 0) {
            for (int i = 0; i < joinsList.size(); i++) {
                query.append("\t\t\t" + joinsList.get(i) + "\n");
            }
        }
        //wheres werden angefügt
        if (wheresList.size() != 0) {
            query.append("WHERE\n");
            for (int i = 0; i < wheresList.size(); i++) {
                query.append("\t\t\t" + wheresList.get(i) + "\n");
            }
        }
        if (groupbyList.size() != 0) {
            query.append("GROUP BY ");
            for (int i = 0; i < groupbyList.size(); i++) {
                if (i != 0) {
                    query.append(", ");
                }
                query.append(groupbyList.get(i));
            }
            query.append("\n");
        }
        textView.setText(query.toString());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        int index;
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_JOIN:
                    joinsList.add(intent.getStringExtra(ConstValues.ERGEBNISMENGE));
                    selectedTables.add(intent.getStringExtra("table"));
                    if(joinsList.isEmpty() == false){
                        bttnDeletJoin.setVisibility(View.VISIBLE);
                    }
                    updateTextView();
                    break;
                case REQUEST_CODE_WHERE:
                    String whereNewCondition = intent.getStringExtra(ConstValues.ERGEBNISMENGE);
                    if (whereNewCondition.contains(";") || whereNewCondition.contains("--")) {
                        //deutsch oder englisch
                        Toast toast;
                        if (stateHelper.getLanguage() == 0) {
                            toast = Toast.makeText(getApplicationContext(), "Das ist keine valide WHERE-Bedingung.", Toast.LENGTH_LONG);
                        } else {
                            toast = Toast.makeText(getApplicationContext(), "This is not a valid WHERE-condition.", Toast.LENGTH_LONG);
                        }
                        toast.show();
                    }
                    else {
                        wheresList.add(whereNewCondition);
                    }
                    if(wheresList.isEmpty() == false){
                        bttnDeleteWhere.setVisibility(View.VISIBLE);
                    }
                    updateTextView();
                    break;
                case REQUEST_CODE_DELETE_JOIN:
                    index = joinsList.indexOf(intent.getStringExtra(ConstValues.JOINS_DELETE));
                    String[] valuearray;
                    while (joinsList.size() > index){
                        valuearray = joinsList.get(index).split(" ");
                        selectedTables.remove(valuearray[2]);
                        joinsList.remove(index);
                    }
                    if(joinsList.isEmpty()){
                        bttnDeletJoin.setVisibility(View.INVISIBLE);
                    }
                    updateTextView();
                    break;
                case REQUEST_CODE_DELETE_WHERE:
                    index = wheresList.indexOf(intent.getStringExtra(ConstValues.WHERES_DELETE));
                    wheresList.remove(index);
                    if(wheresList.isEmpty()){
                        bttnDeleteWhere.setVisibility(View.INVISIBLE);
                    }
                    updateTextView();
                    break;
                case REQUEST_CODE_SELECT:
                    if (intent.getStringExtra(ConstValues.ERGEBNISMENGE_AGGREGATION).equals(" ")) { //der FAll, dass keine Aggreggatsfunktion ausgewählt ist
                        selectsList.add(intent.getStringExtra(ConstValues.ERGEBNISMENGE)); //nur das ausgewählte Attribut wird der Lsite
                    } else { //Aggregatfunktion wurde ausgewählt
                        selectsList.add(intent.getStringExtra(ConstValues.ERGEBNISMENGE_AGGREGATION) + "(" + intent.getStringExtra(ConstValues.ERGEBNISMENGE) + ")");
                    }
                    bttnDeleteSelect.setVisibility(View.VISIBLE);
                    updateTextView();
                    break;
                case REQUEST_CODE_DELETE_SELECT:
                    index = selectsList.indexOf(intent.getStringExtra("selectsDelete"));
                    selectsList.remove(index);
                    if(selectsList.isEmpty()){
                        bttnDeleteSelect.setVisibility(View.INVISIBLE);
                    }
                    updateTextView();
                    break;
                case REQUEST_CODE_GROUPBY:
                    groupbyList.add(intent.getStringExtra(ConstValues.GROUPBY_LIST));
                    if(groupbyList.isEmpty() == false){
                        bttnRealDeleteGroupBy.setVisibility(View.VISIBLE);
                    }
                    updateTextView();
                    break;
                case REQUEST_CODE_DELETE_GROUPBY:
                    index = groupbyList.indexOf(intent.getStringExtra("groupByDelete"));
                    groupbyList.remove(index);
                    if(groupbyList.isEmpty()){
                        bttnRealDeleteGroupBy.setVisibility(View.INVISIBLE);
                    }
                    updateTextView();
                    break;
                default:
                    System.err.println("Hier scheint etwas unimplementiert zu sein!");
            }
        }
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
