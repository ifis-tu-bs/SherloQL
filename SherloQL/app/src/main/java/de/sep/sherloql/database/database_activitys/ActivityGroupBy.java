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
import java.util.LinkedList;

import de.sep.sherloql.R;
import de.sep.sherloql.database.database_logic.ConstValues;
import de.sep.sherloql.database.database_logic.DBOpenHelper;
import de.sep.sherloql.database.database_logic.DBOpenHelperEn;
import de.sep.sherloql.savestate.SaveStateHelper;


/**
 * @author MatthiasFranz
 */
public class ActivityGroupBy extends AppCompatActivity{

    private static final String TAG = "ActivityGroupBy";
    Spinner selectSpinner;
    Button bttnWeiter;
    DBOpenHelper dbOpenHelper;
    DBOpenHelperEn dbOpenHelperEn;
    LinkedList<String> values = new LinkedList<>();
    ArrayList<String> tables;
    private SaveStateHelper stateHelper;
    TextView beschreibung1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupby);
        stateHelper = new SaveStateHelper(this);

        dbOpenHelper = new DBOpenHelper(this);
        dbOpenHelperEn = new DBOpenHelperEn(this);
        Intent intent = getIntent();
        tables = intent.getStringArrayListExtra(ConstValues.FROM);
        for (int i = 0; i < tables.size(); i++) {
            if (stateHelper.getLanguage() == 0) {
                values.addAll(dbOpenHelper.getValues(tables.get(i)));
            } else {
                values.addAll(dbOpenHelperEn.getValues(tables.get(i)));
            }

        }

        initButtons();
        initSpinner();
    }

    private void initButtons(){
        bttnWeiter = (Button) findViewById(R.id.selectWeiter);

        bttnWeiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityQuery.class);
                intent.putExtra(ConstValues.GROUPBY_LIST, (String) selectSpinner.getSelectedItem() );
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        beschreibung1 = (TextView) findViewById(R.id.selectFrageText);

        //deutsch oder englisch
        if (stateHelper.getLanguage() == 0) {
            bttnWeiter.setText("weiter");
            beschreibung1.setText("Hier kannst du deine Ergebnismenge gruppieren. \nFalls du die Ergebnismenge nach einem Attribut gruppieren möchtest, dann wähle es unten aus und tippe auf 'WEITER'.");
        } else {
            bttnWeiter.setText("go on");
            beschreibung1.setText("Here you can group rows that have the same values \ninto summary rows. If you want to group the rows by an attribute, just select it below and tap on 'GO ON'.");
        }
    }

    private void initSpinner(){
        selectSpinner = (Spinner) findViewById(R.id.selectAttributeSpinner);

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
