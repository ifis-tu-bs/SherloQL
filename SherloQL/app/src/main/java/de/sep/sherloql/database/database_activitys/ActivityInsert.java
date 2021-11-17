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

import java.util.LinkedList;

import de.sep.sherloql.bin.Database;
import de.sep.sherloql.R;
import de.sep.sherloql.bin.Map;
import de.sep.sherloql.database.database_logic.DBOpenHelper;
import de.sep.sherloql.database.database_logic.ConstValues;
import de.sep.sherloql.database.database_logic.DBOpenHelperEn;
import de.sep.sherloql.savestate.SaveStateHelper;

/**
 * @author Matthias T
 * @author Erkan
 */
public class ActivityInsert extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "ActivityInsert";

    private final int REQUEST_CODE_VALUE = 0;
    private LinkedList<String> tables;

    SaveStateHelper stateHelper;
    DBOpenHelper dbOpenHelper;
    DBOpenHelperEn dbOpenHelperEn;
    Button back, values, subqueri;
    Spinner into;
    TextView textView;
    String selectedTable;
    StringBuilder query = new StringBuilder();
    String valuesString;
    int chapter;
    boolean fail;
    boolean success;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        dbOpenHelper = new DBOpenHelper(this);
        dbOpenHelperEn = new DBOpenHelperEn(this);
        stateHelper = new SaveStateHelper(this);
        if (stateHelper.getLanguage() == 0) {
            tables = dbOpenHelper.getTables();
        } else {
            tables = dbOpenHelperEn.getTables();
        }
        success = false;
        Intent intent = getIntent();
        chapter = intent.getIntExtra("chapter", -1);
        fail = intent.getBooleanExtra("bool", false);
        initButtons();
        initSpinner();
        initTextView();
    }

    private void initTextView() {
        textView = (TextView) findViewById(R.id.sqlAbfrageLabel);
    }

    private void initButtons() {
        back = (Button) findViewById(R.id.insert_back);
        values = (Button) findViewById(R.id.insert_values);
        subqueri = (Button) findViewById(R.id.insert_subqueri);

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
        if (stateHelper.getLanguage() == 0) {
            back.setText("zurück");
        } else {
            back.setText("back");
        }

        values.setOnClickListener( (v) ->{
            Intent intent = new Intent(v.getContext(), ActivityValues.class);
            intent.putExtra(ConstValues.FROM, selectedTable);
            startActivityForResult(intent, REQUEST_CODE_VALUE);
        });
        subqueri.setOnClickListener( (v) ->{
            if (fail || valuesString == null || (valuesString != null && valuesString.isEmpty())) {
                //deutsch oder englisch
                if (stateHelper.getLanguage() == 0) {
                    Toast.makeText(getApplicationContext(), "Bitte VALUES vollständig ausfüllen", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill in VALUES completely", Toast.LENGTH_LONG).show();
                }
            }
            else {
                if (chapter == 11) {


                } else {
                    String[] valuearray;

                    //deutsch oder englisch
                    if (stateHelper.getLanguage() == 0) {
                        switch (selectedTable) {
                            case "personen":

                                valuearray = valuesString.split(", ");
                                dbOpenHelper.insertPersonen(valuearray[0], valuearray[1], valuearray[2], valuearray[3], valuearray[4], valuearray[5]);
                                break;

                            case "terminkalender":

                                valuearray = valuesString.split(", ");
                                dbOpenHelper.insertTerminkalender(valuearray[0], valuearray[1], valuearray[2]);
                                break;
                            case "abteilung":

                                valuearray = valuesString.split(", ");
                                dbOpenHelper.insertAbteilung(valuearray[0], valuearray[1]);
                                break;
                            case "person_in_abteilung":

                                valuearray = valuesString.split(", ");
                                dbOpenHelper.insertPersonInAbteilung(valuearray[0], valuearray[1]);
                                break;
                            case "gottesdienste":

                                valuearray = valuesString.split(", ");
                                dbOpenHelper.insertGottesdienst(valuearray[0], valuearray[1], valuearray[2], valuearray[3]);
                                break;
                            case "teilnahme_gottesdienst":

                                valuearray = valuesString.split(", ");
                                dbOpenHelper.insertTeilnahmeAnGD(valuearray[0], valuearray[1]);
                                break;
                            case "filme":

                                valuearray = valuesString.split(", ");
                                dbOpenHelper.insertFilme(valuearray[0], valuearray[1], valuearray[2]);
                                break;
                            case "arbeitsplan":

                                valuearray = valuesString.split(", ");
                                dbOpenHelper.insertArbeitsplan(valuearray[0], valuearray[1], valuearray[2], valuearray[3]);
                                break;
                            case "kreditkartenabrechnung":

                                valuearray = valuesString.split(", ");
                                dbOpenHelper.insertKreditkartenabrechnung(valuearray[0], valuearray[1], valuearray[2], valuearray[3], valuearray[4], valuearray[5]);
                                break;
                        }
                        Toast.makeText(getApplicationContext(), "Eingefügt", Toast.LENGTH_SHORT).show();
                    } else {
                        switch (selectedTable) {

                            case "persons":
                                valuearray = valuesString.split(", ");
                                dbOpenHelperEn.insertPersonen(valuearray[0], valuearray[1], valuearray[2], valuearray[3], valuearray[4], valuearray[5]);
                                break;

                            case "schedule":
                                valuearray = valuesString.split(", ");
                                dbOpenHelperEn.insertTerminkalender(valuearray[0], valuearray[1], valuearray[2]);
                                break;

                            case "department":
                                valuearray = valuesString.split(", ");
                                dbOpenHelperEn.insertAbteilung(valuearray[0], valuearray[1]);
                                break;

                            case "persin_in_department":
                                valuearray = valuesString.split(", ");
                                dbOpenHelperEn.insertPersonInAbteilung(valuearray[0], valuearray[1]);
                                break;

                            case "church_services":
                                valuearray = valuesString.split(", ");
                                dbOpenHelperEn.insertGottesdienst(valuearray[0], valuearray[1], valuearray[2], valuearray[3]);
                                break;

                            case "attendance_church_service":
                                valuearray = valuesString.split(", ");
                                dbOpenHelperEn.insertTeilnahmeAnGD(valuearray[0], valuearray[1]);
                                break;

                            case "movies":
                                valuearray = valuesString.split(", ");
                                dbOpenHelperEn.insertFilme(valuearray[0], valuearray[1], valuearray[2]);
                                break;

                            case "work_plan":
                                valuearray = valuesString.split(", ");
                                dbOpenHelperEn.insertArbeitsplan(valuearray[0], valuearray[1], valuearray[2], valuearray[3]);
                                break;

                            case "credit_card_billing":
                                valuearray = valuesString.split(", ");
                                dbOpenHelperEn.insertKreditkartenabrechnung(valuearray[0], valuearray[1], valuearray[2], valuearray[3], valuearray[4], valuearray[5]);
                                break;
                        }
                        Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();
                    }
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
        into = (Spinner) findViewById(R.id.insertIntoSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
               tables);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        into.setAdapter(adapter);
        into.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedTable = into.getItemAtPosition(position).toString();
        values.setVisibility(View.VISIBLE);
        valuesString = null;
        updateTextView();
    }

    private void updateTextView() {
        query.setLength(0);

        if (selectedTable != null) {
            query.append("INSERT INTO " + selectedTable + "\n");
        } else {
            query.append("INSERT INTO \n");
        }
        //fügt die Joins an den Builder an
        if (valuesString != null) {
            query.append("VALUES (" + valuesString + ")");
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
                case REQUEST_CODE_VALUE:
                    valuesString = intent.getStringExtra(ConstValues.ERGEBNISMENGE);
                    updateTextView();
                    break;

                default:
                    System.err.println("Hier scheint etwas unimplementiert zu sein!");
            }
        }
    }
}
