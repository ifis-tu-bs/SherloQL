package de.sep.sherloql.database.database_activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.LinkedList;

import de.sep.sherloql.R;
import de.sep.sherloql.database.database_logic.DBOpenHelper;
import de.sep.sherloql.database.database_logic.DBOpenHelperEn;
import de.sep.sherloql.savestate.SaveStateHelper;


/**
 * @author MatthiasFranz, MatthiasThang, Erkan, Christian
 */
public class ActivityJoin extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private LinkedList<String> possibleJoins;

    private DBOpenHelper dbOpenHelper;
    private DBOpenHelperEn dbOpenHelperEn;
    private Spinner selectSpinner;
    private Spinner on1Spinner;
    private Spinner on2Spinner;

    private Button buttonContinue;
    private TextView textViewDescription;

    ArrayList<String> queriedTables;
    LinkedList<String> queriedTableColumns = new LinkedList<>();
    private SaveStateHelper stateHelper;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        dbOpenHelper = new DBOpenHelper(this);
        dbOpenHelperEn = new DBOpenHelperEn(this);
        stateHelper = new SaveStateHelper(this);
        Intent intent = getIntent();
        queriedTables = intent.getStringArrayListExtra("from");

        if (stateHelper.getLanguage() == 0) {
            possibleJoins = dbOpenHelper.getTables();
        } else {
            possibleJoins = dbOpenHelperEn.getTables();
        }

        //prevent self joins
        for (String str: queriedTables) {
            possibleJoins.remove(str);
        }

        //add all possible column names into the the list
        for (int i = 0; i < queriedTables.size(); i++) {
            if (stateHelper.getLanguage() == 0) {
                queriedTableColumns.addAll(dbOpenHelper.getValues(queriedTables.get(i)));
            } else {
                queriedTableColumns.addAll(dbOpenHelperEn.getValues(queriedTables.get(i)));
            }
        }

        initButtons();
        initSpinner();

    }

    private void initButtons(){
        buttonContinue = (Button) findViewById(R.id.joinWeiter);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityQuery.class);
                // Hier wird der JOIN-String zusammengebaut
                intent.putExtra("ergebnismenge", " JOIN " + selectSpinner.getSelectedItem() + " ON " + on1Spinner.getSelectedItem() + " = "
                        + on2Spinner.getSelectedItem());
                intent.putExtra("table", ""+ selectSpinner.getSelectedItem());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        textViewDescription = (TextView) findViewById(R.id.selectLabel);

        //deutsch oder englisch
        if (stateHelper.getLanguage() == 0) {
            buttonContinue.setText("weiter");
            textViewDescription.setText("Mit welcher Tabelle möchtest du joinen? Wähle die \nTabelle und die entsprechenden Attribute aus");
        } else {
            buttonContinue.setText("go on");
            textViewDescription.setText("Which table do you want to join? Select the table \nand the related columns.");
        }
    }

    private void initSpinner(){
        selectSpinner = (Spinner) findViewById(R.id.fromSpinner);
        on1Spinner = (Spinner) findViewById(R.id.onSpinner);
        on2Spinner = (Spinner) findViewById(R.id.on2Spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                possibleJoins);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        selectSpinner.setAdapter(adapter);
        selectSpinner.setOnItemSelectedListener(this);

        //  on1Spinner initialisieren
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                queriedTableColumns);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        on1Spinner.setAdapter(adapter2);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        LinkedList<String> possibleJoinColumns;
        // Create an ArrayAdapter using the string array and a default spinner layout
        if (stateHelper.getLanguage() == 0) {
            possibleJoinColumns = dbOpenHelper.getValues(selectSpinner.getItemAtPosition(position).toString());
        } else {
            possibleJoinColumns = dbOpenHelperEn.getValues(selectSpinner.getItemAtPosition(position).toString());
        }

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                possibleJoinColumns);
        // Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        on2Spinner.setAdapter(adapter3);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
