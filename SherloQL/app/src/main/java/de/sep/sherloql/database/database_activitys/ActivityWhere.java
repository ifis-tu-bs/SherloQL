package de.sep.sherloql.database.database_activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

import de.sep.sherloql.R;
import de.sep.sherloql.database.database_logic.DBOpenHelper;
import de.sep.sherloql.database.database_logic.ConstValues;
import de.sep.sherloql.database.database_logic.DBOpenHelperEn;
import de.sep.sherloql.savestate.SaveStateHelper;


/**
 * @author MatthiasFranz
 */
public class ActivityWhere extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    DBOpenHelper dbOpenHelper;
    DBOpenHelperEn dbOpenHelperEn;

    LinkedList<String> tableValues = new LinkedList<>();
    TextView whereLabel;
    TextView whereFrage;
    TextView whereBetweenAND;

    EditText comparisonText;
    EditText comparisonValue1;
    EditText comparisonValue2;

    Spinner attributeSpinner;
    Spinner operationSpinner;

    Button weiter;

    TextView orText;
    TextView andText;
    Switch andorSwitch;

    ArrayList<String> operators = new ArrayList<>();

    boolean wheresListempty;
    StringBuilder intentExtra = new StringBuilder();

    //String table1;  // das was beim ersten Spinner in der ActivityQuery ausgewählt wird
    ArrayList<String> tables;

    private SaveStateHelper stateHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where);
        dbOpenHelper = new DBOpenHelper(this);
        dbOpenHelperEn = new DBOpenHelperEn(this);
        stateHelper = new SaveStateHelper(this);
        Intent intent = getIntent();
        tables = intent.getStringArrayListExtra("from");
        for (int i = 0; i < tables.size(); i++) {

            if (stateHelper.getLanguage() == 0) {
                tableValues.addAll(dbOpenHelper.getValues(tables.get(i)));
            } else {
                tableValues.addAll(dbOpenHelperEn.getValues(tables.get(i)));
            }
        }
        wheresListempty = intent.getBooleanExtra("empty", true);



        //fill list operators
        fillListOperators();

        initOthers();
        initButtons();
        initSpinner();

    }

    private void initOthers() {
        whereLabel = (TextView) findViewById(R.id.whereLabelText);
        whereFrage = (TextView) findViewById(R.id.whereFrageText);
        comparisonText = (EditText) findViewById(R.id.whereComparisonValue);
        comparisonValue1 = (EditText) findViewById(R.id.whereeditText);
        comparisonValue2 = (EditText) findViewById(R.id.whereeditText2);
        whereBetweenAND = (TextView) findViewById(R.id.wheretextView6);
        orText = (TextView) findViewById(R.id.textView11);
        andText = (TextView) findViewById(R.id.textView10);
        andorSwitch = (Switch) findViewById(R.id.switch5);

        if(wheresListempty){
            orText.setVisibility(View.INVISIBLE);
            andText.setVisibility(View.INVISIBLE);
            andorSwitch.setVisibility(View.INVISIBLE);
            whereFrage.setVisibility(View.VISIBLE);
        }
        else{
            orText.setVisibility(View.VISIBLE);
            andText.setVisibility(View.VISIBLE);
            andorSwitch.setVisibility(View.VISIBLE);
            whereFrage.setVisibility(View.INVISIBLE);
        }
    }

    private void initButtons(){
        weiter = (Button) findViewById(R.id.whereWeiter);
        weiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityQuery.class);
                buildIntentExtra();
                intent.putExtra(ConstValues.ERGEBNISMENGE, intentExtra.toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        //deutsch oder englisch
        if (stateHelper.getLanguage() == 0) {
            weiter.setText("weiter");
            comparisonText.setHint("Vergleichswert");
            comparisonValue1.setHint("niedriger Wert");
            comparisonValue2.setHint("hoher Wert");
            whereFrage.setText("Hier kannst du Zeilen rausfiltern, die bestimmte Kriterien erfüllen. Welches Kriterium soll erfüllt sein?");
        } else {
            weiter.setText("go on");
            comparisonText.setHint("comparison value");
            comparisonValue1.setHint("lower value");
            comparisonValue2.setHint("higher value");
            whereFrage.setText("Here you can filter rows, that just fulfill certain conditions. Which condition should be fulfilled?");
        }

    }

    private void initSpinner(){
        attributeSpinner = (Spinner) findViewById(R.id.whereAttributeSpinner);
        operationSpinner = (Spinner) findViewById(R.id.whereOperationSpinner);

        //  attributSpinner initialisieren
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                tableValues);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        attributeSpinner.setAdapter(adapter2);

        // operationSpinner initialisieren
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                operators);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        operationSpinner.setAdapter(adapter);
        operationSpinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (operationSpinner.getItemAtPosition(position).toString()){
            case "=":
            case "<":
            case ">":
            case "<=":
            case ">=":
            case "LIKE":
                comparisonText.setVisibility(View.VISIBLE);
                comparisonValue1.setVisibility(View.INVISIBLE);
                comparisonValue2.setVisibility(View.INVISIBLE);
                whereBetweenAND.setVisibility(View.INVISIBLE);
                break;
            case "BETWEEN":
                comparisonText.setVisibility(View.INVISIBLE);
                comparisonValue1.setVisibility(View.VISIBLE);
                comparisonValue2.setVisibility(View.VISIBLE);
                whereBetweenAND.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void fillListOperators(){
        operators.add("=");
        operators.add(">");
        operators.add("<");
        operators.add(">=");
        operators.add("<=");
        operators.add("BETWEEN");
        operators.add("LIKE");
    }

    // Hier wird der WHERE-String zusammengebaut
    private void buildIntentExtra(){
        intentExtra.setLength(0);
        if (wheresListempty == false) {
            if (andorSwitch.isChecked()) {
                intentExtra.append("OR ");
            } else {
                intentExtra.append("AND ");
            }
        }
        if(!operationSpinner.getSelectedItem().equals("BETWEEN")) {
            if (comparisonText.getText().toString().equals("0")) {
                intentExtra.append(attributeSpinner.getSelectedItem() + " " + operationSpinner.getSelectedItem() + " 0");
            } else {
                intentExtra.append(attributeSpinner.getSelectedItem() + " " + operationSpinner.getSelectedItem() + " \'" + comparisonText.getText().toString() + "\'");
            }
        }
        if(operationSpinner.getSelectedItem().equals("BETWEEN")) {
            if(comparisonValue1.getText().toString().equals("0") && comparisonValue2.getText().toString().equals("0")){
                intentExtra.append(attributeSpinner.getSelectedItem() + " " + operationSpinner.getSelectedItem() + " 0 AND 0" );
            }
            else if (comparisonValue1.getText().toString().equals("0")) {
                intentExtra.append(attributeSpinner.getSelectedItem() + " " + operationSpinner.getSelectedItem() + " 0 AND " + comparisonValue2.getText().toString());
            }
            else if (comparisonValue2.getText().toString().equals("0")) {
                intentExtra.append(attributeSpinner.getSelectedItem() + " " + operationSpinner.getSelectedItem() + " " + comparisonValue1.getText().toString() + " AND 0");
            }
            else {
                intentExtra.append(attributeSpinner.getSelectedItem() + " " + operationSpinner.getSelectedItem() + " " + comparisonValue1.getText().toString() + " AND " + comparisonValue2.getText().toString());
            }
        }
    }
}
