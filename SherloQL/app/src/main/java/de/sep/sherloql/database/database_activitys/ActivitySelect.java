package de.sep.sherloql.database.database_activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import de.sep.sherloql.R;
import de.sep.sherloql.database.database_logic.ConstValues;
import de.sep.sherloql.database.database_logic.DBOpenHelper;
import de.sep.sherloql.database.database_logic.DBOpenHelperEn;
import de.sep.sherloql.savestate.SaveStateHelper;


/**
 * @author MatthiasFranz, Christian
 */
public class
ActivitySelect extends AppCompatActivity {

    private static final String TAG = "ActivitySelect";

    Spinner selectSpinner;
    Button bttnWeiter;
    Spinner aggregationSpinner;
    TextView beschreibung1;
    TextView beschreibung2;

   // String table1;
    DBOpenHelper dbOpenHelper;
    DBOpenHelperEn dbOpenHelperEn;
    private SaveStateHelper stateHelper;

    LinkedList<String> values = new LinkedList<>();
    ArrayList<String> tables;
    LinkedList<String> aggregationList = new LinkedList<String>(Arrays.asList(new String[] {" ", "MIN", "MAX", "SUM", "AVG", "COUNT"} ));

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        dbOpenHelper = new DBOpenHelper(this);
        dbOpenHelperEn = new DBOpenHelperEn(this);
        stateHelper = new SaveStateHelper(this);
        Intent intent = getIntent();
        tables = intent.getStringArrayListExtra(ConstValues.FROM);
        for (int i = 0; i < tables.size(); i++) {
            if (stateHelper.getLanguage() == 0) {
                values.addAll(dbOpenHelper.getValues(tables.get(i)));
            } else {
                values.addAll(dbOpenHelperEn.getValues(tables.get(i)));
            }
        }
        values.add("*");


        initButtons();
        initSpinner();
    }

    private void initButtons(){
        bttnWeiter = (Button) findViewById(R.id.selectWeiter);

        bttnWeiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityQuery.class);
                intent.putExtra(ConstValues.ERGEBNISMENGE, (String) selectSpinner.getSelectedItem() );
                intent.putExtra(ConstValues.ERGEBNISMENGE_AGGREGATION, (String) aggregationSpinner.getSelectedItem());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        beschreibung1 = (TextView) findViewById(R.id.selectFrageText);
        beschreibung2 = (TextView) findViewById(R.id.textView2);

        //deutsch oder englisch
        if (stateHelper.getLanguage() == 0) {
            bttnWeiter.setText("weiter");
            beschreibung1.setText("Hier kannst du bestimmte Spalten selektieren. Welche Spalten möchtest du dir ausgeben lassen?");
            beschreibung2.setText("Falls du eine Aggregatfunktion verwenden möchtest, dann wähle einfach das entsprechende Aggregat unten aus und das dazugehörige Attribut oben.");
        } else {
            bttnWeiter.setText("go on");
            beschreibung1.setText("Here you can select specific columns. Which column would you like to have in your result set?");
            beschreibung2.setText("If you want to use an aggregate functionn, then just select the desired aggregate function below.");
        }
    }

    private void initSpinner(){
        selectSpinner = (Spinner) findViewById(R.id.selectAttributeSpinner);
        aggregationSpinner = (Spinner) findViewById(R.id.aggregationSpinner);

        //  aggregatSpinner initialisieren
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
            aggregationList);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        aggregationSpinner.setAdapter(adapter);

        //  attributSpinner initialisieren
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
               values);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        selectSpinner.setAdapter(adapter2);


    }

}
