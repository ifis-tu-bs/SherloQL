package de.sep.sherloql.database.database_activitys;

import android.content.Intent;
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
public class ActivityUpdate extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "ActivityInsert";

    private final int REQUEST_CODE_SET = 0;
    private final int REQUEST_CODE_WHERE = 1;
    private final int REQUEST_CODE_DELETE_WHERE = 2;
    private LinkedList<String> tables;

    DBOpenHelper dbOpenHelper;
    DBOpenHelperEn dbOpenHelperEn;
    SaveStateHelper sHelper;
    int chapter;
    Button back, set, where, delete_where, subqueri;
    Spinner update;
    TextView textView;
    ArrayList<String> selectedTables = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    String valuesString;
    boolean success;
    private ArrayList<String> wheresList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        dbOpenHelper = new DBOpenHelper(this);
        dbOpenHelperEn = new DBOpenHelperEn(this);
        sHelper = new SaveStateHelper(this);
        success = false;
        Intent intent = getIntent();
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

    private void initTextView() {
        textView = (TextView) findViewById(R.id.sqlAbfrageLabel);
    }

    private void initButtons() {
        back = (Button) findViewById(R.id.insert_back);
        set = (Button) findViewById(R.id.set_values);
        where = (Button) findViewById(R.id.where);
        subqueri = (Button) findViewById(R.id.insert_subqueri);
        delete_where = (Button) findViewById(R.id.deleteWhere);

        back.setOnClickListener( (v) ->{
            if(chapter != -1){
                Intent intent = new Intent(v.getContext(), Map.class);
                intent.putExtra("success", success);
                intent.putExtra("chapter", chapter);
                startActivity(intent);
            } else {
                Intent intent = new Intent(v.getContext(), Database.class);
                startActivity(intent);
            }
        });
        //deutsch oder englisch
        if (sHelper.getLanguage() == 0) {
            back.setText("zurück");
        } else {
            back.setText("back");
        }

        set.setOnClickListener( (v) ->{
            Intent intent = new Intent(v.getContext(), ActivitySet.class);
            intent.putExtra(ConstValues.FROM, selectedTables.get(0));
            startActivityForResult(intent, REQUEST_CODE_SET);
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
        subqueri.setOnClickListener( (v) ->{
            if (valuesString == null) {
                Toast.makeText(this, "Ungültige Anfrage", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "initButtons: value leer");
            } else if (valuesString != null && valuesString.isEmpty()) {
                Toast.makeText(this, "Ungültige Anfrage", Toast.LENGTH_SHORT).show();
            }else {
                if (chapter == 9) {
                    if (query.toString().equals("UPDATE personen\nSET telefonnr = '015963951534'\nWHERE\n\t\t\tvorname = 'Viktoria'\n\t\t\tAND nachname = 'Kästner'\n") || query.toString().equals("UPDATE personen\nSET telefonnr = '015963951534'\nWHERE\n\t\t\tvorname = 'Viktoria'\n") || query.toString().equals("UPDATE personen\nSET telefonnr = '015963951534'\nWHERE\n\t\t\tnachname = 'Kästner'\n")) {
                        Log.d(TAG, "chapter0");
                        success = true;
                        Toast toast = Toast.makeText(getApplicationContext(), "Korrekte Anfrage", Toast.LENGTH_SHORT);
                        toast.show();
                        if (sHelper.getLanguage() == 0) {
                            dbOpenHelper.updatedata(query.toString());
                        } else {
                            dbOpenHelperEn.updatedata(query.toString());
                        }
                        sHelper.updateStory(String.valueOf(chapter + 1), "true", "true");
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Falsche Anfrage", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    if (sHelper.getLanguage() == 0) {
                        dbOpenHelper.updatedata(query.toString());
                    } else {
                        dbOpenHelperEn.updatedata(query.toString());
                    }
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        if(chapter != -1){
            Intent intent = new Intent(this, Map.class);
            intent.putExtra("success", success);
            intent.putExtra("chapter", chapter);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, Database.class);
            startActivity(intent);
        }
        super.onBackPressed();  // optional depending on your needs
    }
    private void initSpinner() {
        update = (Spinner) findViewById(R.id.updateSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                tables);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        update.setAdapter(adapter);
        update.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedTables.clear();
        selectedTables.add(0, update.getItemAtPosition(position).toString());
        set.setVisibility(View.VISIBLE);
        valuesString = null;
        wheresList.clear();
        updateTextView();
    }

    private void updateTextView() {
        if (!wheresList.isEmpty()) {
            if (wheresList.get(0).substring(0, 3).equals("AND")) {
                Log.d(TAG, "updateTextView: and");
                wheresList.set(0, wheresList.get(0).substring(4));
            } else if (wheresList.get(0).substring(0, 2).equals("OR")) {
                wheresList.set(0, wheresList.get(0).substring(3));
            }
        }
        query.setLength(0);

        if (selectedTables.get(0) != null) {
            query.append("UPDATE " + selectedTables.get(0) + "\n");
        } else {
            query.append("UPDATE \n");
        }
        //fügt die Joins an den Builder an
        if (valuesString != null) {
            query.append("SET " + valuesString + "\n");
        }
        if (wheresList.size() != 0) {
            query.append("WHERE\n");
            for (int i = 0; i < wheresList.size(); i++) {
                query.append("\t\t\t" + wheresList.get(i) + "\n");

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
                case REQUEST_CODE_SET:
                    valuesString = intent.getStringExtra(ConstValues.ERGEBNISMENGE);
                    updateTextView();
                    break;
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
