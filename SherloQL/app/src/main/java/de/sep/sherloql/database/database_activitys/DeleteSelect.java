package de.sep.sherloql.database.database_activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import de.sep.sherloql.R;
import de.sep.sherloql.savestate.SaveStateHelper;



/**
 * @author MatthiasFranz
 */
public class DeleteSelect extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerDelete;
    Button bttnDelete;
    TextView beschreibung1;
    private SaveStateHelper stateHelper;


    ArrayList<String> selectDeleteList = new ArrayList<String>();

    String result;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_select);

        stateHelper = new SaveStateHelper(this);

        Intent intent = getIntent();
        selectDeleteList = intent.getStringArrayListExtra("selectsList");

        initButtons();
        initSpinner();
    }

    private void initButtons(){
        bttnDelete = (Button) findViewById(R.id.deleteSelectOK);
        bttnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(result == null || result.equals("")){
                    return;
                }

                Intent intent = new Intent(v.getContext(), ActivityQuery.class);
                // Hier wird der JOIN-String zusammengebaut
                intent.putExtra("selectsDelete", result);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        beschreibung1 = (TextView) findViewById(R.id.textView7);

        //deutsch oder englisch
        if (stateHelper.getLanguage() == 0) {
            bttnDelete.setText("loeschen");
            beschreibung1.setText("Hier kannst du bereits ausgewählte Spalten \nwieder entfernen.");
        } else {
            bttnDelete.setText("delete");
            beschreibung1.setText("Here you can delete Columns that you have \nalready added.");
        }
    }

    private void initSpinner(){
        // FromSpinner
        spinnerDelete = (Spinner) findViewById(R.id.deleteSelectSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                selectDeleteList);
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
}