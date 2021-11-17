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

import de.sep.sherloql.bin.Database;
import de.sep.sherloql.R;
import de.sep.sherloql.database.database_logic.DBOpenHelper;
import de.sep.sherloql.database.database_logic.DBOpenHelperEn;
import de.sep.sherloql.savestate.SaveStateHelper;

/**
 * Implementiert die Logik der activity_select Activität
 *
 * @author Christian, MatthiasFranz, MatthiasThang, Erkan
 */
public class ActivitySubquery extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "ActivitySelect";

    private final int REQUEST_CODE_JOIN = 0;
    private final int REQUEST_CODE_WHERE = 1;
    private final int REQUEST_CODE_DELETE_JOIN = 2;

    private final String JOINS_DELETE = "joinsDelete";
    private final String ERGEBNISMENGE = "ergebnismenge";
    private LinkedList<String> tables;

    DBOpenHelper dbOpenHelper;
    DBOpenHelperEn dbOpenHelperEn;
    SaveStateHelper sHelper;
    Button bttnBack;
    Button bttnJoin;
    Button bttnWhat;
    Button bttnWhere;
    Button bttnDeletJoin;

    TextView textView;

    Spinner selectedTableSpinner;
    String selectedTable;

    StringBuilder query = new StringBuilder();

    ArrayList<String> selectsList = new ArrayList<>();
    ArrayList<String> joinsList = new ArrayList<>();
    ArrayList<String> wheresList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subquery);
        dbOpenHelper = new DBOpenHelper(this);
        dbOpenHelperEn = new DBOpenHelperEn(this);
        sHelper = new SaveStateHelper(this);
        if (sHelper.getLanguage() == 0) {
            tables = dbOpenHelper.getTables();
        } else {
            tables = dbOpenHelperEn.getTables();
        }
        initButtons();
        initOther();
        initSpinner();
    }

    private void initOther() {
        textView = (TextView) findViewById(R.id.sqlAbfrageLabel);
    }

    private void initSpinner(){
        // FromSpinner
        selectedTableSpinner = (Spinner) findViewById(R.id.fromSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                tables);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        selectedTableSpinner.setAdapter(adapter);
        selectedTableSpinner.setOnItemSelectedListener(this);
    }

    private void initButtons() {
        //  Button Back
        bttnBack = (Button) findViewById(R.id.select_back);
        bttnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Database.class);
                startActivity(intent);
            }
        });

        //  Button Join
        bttnJoin = (Button) findViewById(R.id.selectJoin);
        bttnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityJoin.class);
                intent.putExtra("from", selectedTable);
                startActivityForResult(intent, REQUEST_CODE_JOIN);
            }
        });

        bttnDeletJoin = (Button) findViewById(R.id.selectBttnDeleteJoin);
        bttnDeletJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DeleteJoin.class);
                intent.putExtra("joinsList", joinsList);
                startActivityForResult(intent, REQUEST_CODE_DELETE_JOIN);
            }
        });

        bttnWhat = (Button) findViewById(R.id.select_what);
        bttnWhere = (Button) findViewById(R.id.select_where);
        bttnWhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityWhere.class);
                intent.putExtra("from", selectedTable);
                startActivityForResult(intent, REQUEST_CODE_WHERE);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedTable = selectedTableSpinner.getItemAtPosition(position).toString();
        joinsList.clear();
        wheresList.clear();
        bttnDeletJoin.setVisibility(View.INVISIBLE);
        updateTextView();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    protected void updateTextView() {
        //bei jedem Aufruf wird der String von Anfang an neu gebaut
        query.setLength(0);

        //fügt die ausgewählten Attribute an den Builder an
        query.append("SELECT ");
        if (selectsList.size() != 0) {
            for (int i = 0; i < selectsList.size(); i++) {
                if (i == selectsList.size() - 1) {
                    query.append(selectsList.get(i) + "\n");
                } else {
                    query.append(selectsList.get(i) + ", ");
                }
            }
        } else {
            query.append("\n");
        }
        //fügt die ausgewählte Tabelle hinten an den Builder an
        if (selectedTable != null) {
            query.append("FROM " + selectedTable + "\n");
        } else {
            query.append("FROM \n");
        }
        //fügt die Joins an den Builder an
        if (joinsList.size() != 0) {
            for (int i = 0; i < joinsList.size(); i++) {
                if (i == joinsList.size() - 1) {
                    query.append("\t" + joinsList.get(i) + "\n");
                } else {
                    query.append("\t" + joinsList.get(i) + ",\n");
                }
            }
        }
        //wheres werden angefügt
        if (wheresList.size() != 0) {
            for (int i = 0; i < wheresList.size(); i++) {
                if (i == wheresList.size() - 1) {
                    query.append("\t" + wheresList.get(i) + "\n");
                } else {
                    query.append("\t" + wheresList.get(i) + ",\n");
                }
            }
        }
        textView.setText(query.toString());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_JOIN:
                    joinsList.add(intent.getStringExtra(ERGEBNISMENGE));
                    if(joinsList.isEmpty() == false){
                        bttnDeletJoin.setVisibility(View.VISIBLE);
                    }
                    updateTextView();
                    break;
                case REQUEST_CODE_WHERE:
                    wheresList.add(intent.getStringExtra(ERGEBNISMENGE));
                    if(wheresList.isEmpty() == false){
                        //TODO
                        // bttnDeleteWhere.setVisibility(View.VISIBLE);
                    }
                    updateTextView();
                    break;
                case REQUEST_CODE_DELETE_JOIN:
                    int index = joinsList.indexOf(intent.getStringExtra(JOINS_DELETE));
                    while (joinsList.size() > index){
                        joinsList.remove(index);
                    }
                    if(joinsList.isEmpty()){
                        bttnDeletJoin.setVisibility(View.INVISIBLE);
                    }
                    updateTextView();
                    break;
                default:
                    System.err.println("Hier scheint etwas unimplementiert zu sein!");
            }
        }
    }
}
