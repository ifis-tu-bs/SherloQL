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

import de.sep.sherloql.R;
import de.sep.sherloql.database.database_logic.ConstValues;
import de.sep.sherloql.savestate.SaveStateHelper;


/**
 * @author MatthiasFranz & Christian
 */
public class DeleteWhere extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerDelete;
    Button bttnDelete;
    private SaveStateHelper stateHelper;
    TextView beschreibung1;


    ArrayList<String> wheresDeleteList = new ArrayList<String>();

    String result;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_where);
        stateHelper = new SaveStateHelper(this);

        Intent intent = getIntent();
        wheresDeleteList = intent.getStringArrayListExtra(ConstValues.WHERES_LIST);

        initButtons();
        initSpinner();
    }

    private void initButtons(){
        bttnDelete = (Button) findViewById(R.id.deleteWhereOK);
        bttnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityQuery.class);
                // Hier wird der JOIN-String zusammengebaut
                intent.putExtra(ConstValues.WHERES_DELETE, result);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        beschreibung1 = (TextView) findViewById(R.id.textView8);

        //deutsch oder englisch
        if (stateHelper.getLanguage() == 0) {
            bttnDelete.setText("loeschen");
            beschreibung1.setText("Hier kannst du bereits erstellte Bedingungen \nwieder entfernen.");
        } else {
            bttnDelete.setText("delete");
            beschreibung1.setText("Here you can delete a Condition that you have \nadded so far.");
        }
    }

    private void initSpinner(){
        // FromSpinner
        spinnerDelete = (Spinner) findViewById(R.id.deleteWhereSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                wheresDeleteList);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerDelete.setAdapter(adapter);
        spinnerDelete.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        result = spinnerDelete.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
