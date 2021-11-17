package de.sep.sherloql.database.database_activitys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.LinkedList;

import de.sep.sherloql.R;
import de.sep.sherloql.bin.Database;
import de.sep.sherloql.bin.Map;
import de.sep.sherloql.database.database_logic.DBOpenHelper;
import de.sep.sherloql.database.database_logic.ConstValues;
import de.sep.sherloql.database.database_logic.DBOpenHelperEn;
import de.sep.sherloql.savestate.SaveStateHelper;

/**
 * @author Matthias T
 * @author Erkan
 */
public class ActivityDelete extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "ActivityDelete";

    private final int REQUEST_CODE_WHERE = 1;
    private final int REQUEST_CODE_DELETE_WHERE = 2;
    private Button back, where, delete_where, subqueri;
    private Spinner from;
    private TextView textView;
    private LinkedList<String> tables;

    DBOpenHelper dbOpenHelper;
    DBOpenHelperEn dbOpenHelperEn;
    SaveStateHelper sHelper;
    int chapter;
    String chapter_name;
    ArrayList<String> selectedTables = new ArrayList<>();
    boolean success;
    private StringBuilder query = new StringBuilder();
    private ArrayList<String> wheresList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        dbOpenHelper = new DBOpenHelper(this);
        dbOpenHelperEn = new DBOpenHelperEn(this);
        sHelper = new SaveStateHelper(this);
        success = false;
        Intent intent = getIntent();
        chapter_name = "";
        chapter_name = intent.getStringExtra("name");
        chapter = intent.getIntExtra("chapter", -1);
        if (sHelper.getLanguage() == 0) {
            tables = dbOpenHelper.getTables();
        } else {
            tables = dbOpenHelperEn.getTables();
        }

        initButtons();
        initSpinner();
        initTextView();
    }

    private void initTextView()
    {
        textView = (TextView) findViewById(R.id.sqlAbfrageLabel);
    }

    private void initButtons() {
        back = (Button) findViewById(R.id.delete_back);
        where = (Button) findViewById(R.id.delete_where);
        subqueri = (Button) findViewById(R.id.delete_subqueri);
        delete_where = (Button) findViewById(R.id.delete_delwhere);

        if (sHelper.getLanguage() == 0) {
            back.setText("zurück");
        } else {
            back.setText("back");
        }

        back.setOnClickListener( (v) ->{
            onBackPressed();
        });

        where.setOnClickListener( (v) ->{
            Intent intent = new Intent(v.getContext(), ActivityWhere.class);
            intent.putExtra(ConstValues.FROM, selectedTables);
            if(wheresList.isEmpty()){
                intent.putExtra("empty", true);
            }
            else{
                intent.putExtra("empty", false);
            }
            startActivityForResult(intent, REQUEST_CODE_WHERE);
        });

        delete_where.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DeleteWhere.class);
                intent.putExtra(ConstValues.WHERES_LIST, wheresList);
                startActivityForResult(intent, REQUEST_CODE_DELETE_WHERE);
            }
        });

        subqueri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wheresList == null) {

                } else if(wheresList != null && wheresList.isEmpty()) {

                } else {
                    if (chapter == 0) {
                        if (sHelper.getLanguage() == 0) {
                            dbOpenHelper.deletedata(query.toString());
                        } else {
                            dbOpenHelperEn.deletedata(query.toString());
                        }

                        String answer = sHelper.getAnswer(chapter_name);
                        String ans[] = answer.split(",");
                        String story = "SELECT * FROM " + ans[0] + " WHERE " + ans[1] + " = " + ans[2];
                        Cursor resultCursor;
                        if (sHelper.getLanguage() == 0) {
                            resultCursor = dbOpenHelper.getSelect(story);
                        } else {
                            resultCursor = dbOpenHelperEn.getSelect(story);
                        }

                        if (resultCursor.getCount() == 0) {
                            String out = "Korrekte Anfrage für Kapitel: " + chapter_name;
                            sHelper.updateStory(chapter_name, "true", "true");
                            ArrayList<String> artefacts = sHelper.getArtefacts(chapter_name);
                            for(int i = 0; i < artefacts.size(); i++) {
                                Log.d(TAG, "delete huihuihui: "+ artefacts.get(i) + ", chapter " + chapter_name);
                                String check = "!" + artefacts.get(i);
                                if (sHelper.checkArtefactExists(check)) {
                                    sHelper.updateArtefactUnlocked(check, "false");
                                }
                                sHelper.updateArtefactUnlocked(artefacts.get(i), "true");
                            }
                            Toast toast = Toast.makeText(getApplicationContext(), out, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {
                        if (sHelper.getLanguage() == 0) {
                            dbOpenHelper.deletedata(query.toString());
                        } else {
                            dbOpenHelperEn.deletedata(query.toString());
                        }
                        if (sHelper.getLanguage() == 0) {
                            Toast.makeText(getApplicationContext(), "Gelöscht", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    } // end initButtons()
    @Override
    public void onBackPressed()
    {
        if(chapter == -1){
            Intent intent = new Intent(this, Database.class);
            startActivity(intent);
        }
        super.onBackPressed();  // optional depending on your needs
    }
    private void initSpinner() {
        from = (Spinner) findViewById(R.id.spinner_from);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
               tables);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        from.setAdapter(adapter);
        from.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedTables.clear();
        selectedTables.add(0, from.getItemAtPosition(position).toString());
        wheresList.clear();
        updateTextView();
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

        //fügt die ausgewählte Tabelle hinten an den Builder an
        if (selectedTables.get(0) != null) {
            query.append("DELETE FROM " + selectedTables.get(0) + "\n");
        } else {
            query.append("DELETE FROM \n");
        }

        //wheres werden angefügt
        if (wheresList.size() != 0) {
            query.append("WHERE\n");
            for (int i = 0; i < wheresList.size(); i++) {
                if (i == wheresList.size() - 1) {
                    query.append("\t\t\t" + wheresList.get(i) + "\n");
                } else {
                    query.append("\t\t\t" + wheresList.get(i) + ",\n");
                }
            }
        }
        textView.setText(query.toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        int index;
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_WHERE:
                    wheresList.add(intent.getStringExtra(ConstValues.ERGEBNISMENGE));
                    if(wheresList.isEmpty() == false){
                        delete_where.setVisibility(View.VISIBLE);
                    }
                    updateTextView();
                    break;
                case REQUEST_CODE_DELETE_WHERE:
                    index = wheresList.indexOf(intent.getStringExtra(ConstValues.WHERES_DELETE));
                    wheresList.remove(index);
                    if(wheresList.isEmpty()){
                        delete_where.setVisibility(View.INVISIBLE);
                    }
                    updateTextView();
                    break;
                default:
                    System.err.println("Hier scheint etwas unimplementiert zu sein!");
            }
        }
    }
}
