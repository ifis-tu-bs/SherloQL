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
public class ActivitySet extends AppCompatActivity {
    private LinkedList<String> values;

    DBOpenHelper dbOpenHelper;
    DBOpenHelperEn dbOpenHelperEn;
    Button weiter;
    ListView attributes;
    String table1;
    TextView beschreibung1;
    StringBuilder set = new StringBuilder();
    LinkedList<String> attris = new LinkedList<>();
    private SaveStateHelper stateHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        dbOpenHelper = new DBOpenHelper(this);
        dbOpenHelperEn = new DBOpenHelperEn(this);
        stateHelper = new SaveStateHelper(this);
        Intent intent = getIntent();
        table1 = intent.getStringExtra("from");
        if (stateHelper.getLanguage() == 0) {
            values = dbOpenHelper.getValues(table1);
        } else {
            values = dbOpenHelperEn.getValues(table1);
        }

        initListView();
        initButtons();
    }

    private void initListView() {
        attributes = (ListView) findViewById(R.id.attributes);
        configAttributesTextString();
        AttributeListAdapter adapter = new AttributeListAdapter(this, R.layout.adapter_values_layout, attris);
        attributes.setAdapter(adapter);
    }
    private void configAttributesTextString(){
        attris.clear();
        for (int i = 0; i < values.size(); i++) {
            attris.add(values.get(i) + " = ");
        }
    }
    private void initButtons() {
        weiter = (Button) findViewById(R.id.weiter);
        weiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < values.size(); i++) {
                    EditText et = (EditText) attributes.getChildAt(i).findViewById(R.id.editText);
                    if (et.getText().toString().length() != 0) {
                        set.append(attris.get(i) + "'" + et.getText().toString() + "'" + ", ");
                    }
                }
                int index = set.toString().length() - 1;
                set.deleteCharAt(index);
                set.deleteCharAt(index - 1);
                Intent intent = new Intent(v.getContext(), ActivityQuery.class);
                // Hier wird der JOIN-String zusammengebaut
                intent.putExtra("ergebnismenge", set.toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        beschreibung1 = (TextView) findViewById(R.id.updateText);

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
