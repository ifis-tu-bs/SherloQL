package de.sep.sherloql.database.database_activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

import de.sep.sherloql.R;
import de.sep.sherloql.database.database_logic.DBOpenHelper;
import de.sep.sherloql.database.database_logic.DBOpenHelperEn;
import de.sep.sherloql.savestate.SaveStateHelper;


/**
 * @author Matthias T
 * @author Erkan
 */
public class ActivityValues extends AppCompatActivity{

    Button weiter;
    ListView attributes;
    String table1;
    StringBuilder values = new StringBuilder();
    DBOpenHelper dbOpenHelper;
    DBOpenHelperEn dbOpenHelperEn;
    boolean fail;
    LinkedList<String> tableValues;
    TextView beschreibung1;

    private SaveStateHelper stateHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_values);
        fail = false;
        dbOpenHelper = new DBOpenHelper(this);
        dbOpenHelperEn = new DBOpenHelperEn(this);
        stateHelper = new SaveStateHelper(this);
        Intent intent = getIntent();
        table1 = intent.getStringExtra("from");

        if (stateHelper.getLanguage() == 0) {
            tableValues = dbOpenHelper.getValues(table1);
        } else {
            tableValues = dbOpenHelperEn.getValues(table1);
        }


        initListView();
        initButtons();
    }

    private void initListView() {
        attributes = (ListView) findViewById(R.id.attributes);
        AttributeListAdapter adapter = new AttributeListAdapter(this, R.layout.adapter_values_layout, tableValues);
        attributes.setAdapter(adapter);
    }

    private void initButtons() {
        weiter = (Button) findViewById(R.id.weiter);
        weiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO hier befindet sich noch ein Bugg: Der erste Wert enthält ebenfalls ein Komma...
                for (int i = 0; i < tableValues.size(); i++) {
                    EditText et = (EditText) attributes.getChildAt(i).findViewById(R.id.editText);
                    if (et.getText().toString().isEmpty()) {
                        fail = true;
                    }
                    if (i == tableValues.size() - 1) {
                        values.append(et.getText().toString());
                    }
                    else {
                        values.append(et.getText().toString() + ", ");
                    }
                }
                Intent intent = new Intent(v.getContext(), ActivityQuery.class);
                // Hier wird der JOIN-String zusammengebaut
                intent.putExtra("ergebnismenge", values.toString());
                intent.putExtra("bool", fail);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        beschreibung1 = (TextView) findViewById(R.id.valuesText);

        //deutsch oder englisch
        if (stateHelper.getLanguage() == 0) {
            weiter.setText("weiter");
            beschreibung1.setText("Hier kannst du die Attribute bestimmen.");
        } else {
            weiter.setText("go on");
            beschreibung1.setText("Here you can determine the attributes.");
        }
    }
}
