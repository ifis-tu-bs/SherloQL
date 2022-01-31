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
import de.sep.sherloql.savestate.SaveStateHelper;


/**
 * @author MatthiasFranz, MatthiasT, Erkan, Christian
 */
public class DeleteJoin extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerDelete;
    Button bttnDelete;
    TextView beschreibung1;
    private SaveStateHelper stateHelper;


    ArrayList<String> joinsDeleteList = new ArrayList<String>();

    String result;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_join);

        stateHelper = new SaveStateHelper(this);

        Intent intent = getIntent();
        joinsDeleteList = intent.getStringArrayListExtra("joinsList");

        initButtons();
        initSpinner();
    }

    private void initButtons(){
        bttnDelete = (Button) findViewById(R.id.deleteJoinOK);
        bttnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(result == null || result.equals("")){
                    return;
                }
                
                Intent intent = new Intent(v.getContext(), ActivityQuery.class);
                // Hier wird der JOIN-String zusammengebaut
                intent.putExtra("joinsDelete", result);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        beschreibung1 = (TextView) findViewById(R.id.textView9);

        //deutsch oder englisch
        if (stateHelper.getLanguage() == 0) {
            bttnDelete.setText("loeschen");
            beschreibung1.setText("Hier kannst du bereits erstellte Joins wieder \nentfernen. Alle danach erstellten Joins werden \nautomatisch mit entfernt.");
        } else {
            bttnDelete.setText("delete");
            beschreibung1.setText("Here you can delete Joins that you have \nalready added. All previous added Joins are deleted \ntoo automatically.");
        }
    }

    private void initSpinner(){
        // FromSpinner
        spinnerDelete = (Spinner) findViewById(R.id.deleteJoinSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                joinsDeleteList);
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
