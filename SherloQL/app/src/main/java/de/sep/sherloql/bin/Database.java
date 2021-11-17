package de.sep.sherloql.bin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import de.sep.sherloql.R;
import de.sep.sherloql.database.database_activitys.ActivityDML;
import de.sep.sherloql.database.database_activitys.ActivityDiagram;
import de.sep.sherloql.database.database_activitys.ActivityQuery;
import de.sep.sherloql.database.database_logic.DBOpenHelper;
import de.sep.sherloql.database.database_logic.DBOpenHelperEn;
import de.sep.sherloql.savestate.SaveStateHelper;


/**
 * ....
 */
public class Database extends AppCompatActivity {

    private ImageButton ibHomebase;
    private ImageButton ibSelect;
    private ImageButton ibDML;
    private ImageButton ibDiagram;
    private ImageButton ibReset;
    private TextView databaseTextView;
    DBOpenHelper db;
    DBOpenHelperEn db_en;
    private SaveStateHelper stateHelper;

    private static final String TAG = "Datenbank";
    Button home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        db = new DBOpenHelper(this);
        db_en = new DBOpenHelperEn(this);
        Log.d(TAG, "onCreate: starts");
        initButtons();
        stateHelper = new SaveStateHelper(this);
        databaseTextView = (TextView) findViewById(R.id.tvDatabase);
        //deutsch oder englisch
        if (stateHelper.getLanguage() == 0) {
            databaseTextView.setText("Datenbank");
        } else {
            databaseTextView.setText("Database");
        }
    }

    private void initButtons() {
        Log.d(TAG, "initButtons: starts");
        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });



        ibHomebase = (ImageButton) findViewById(R.id.ibHomebase);
        ibSelect = (ImageButton) findViewById(R.id.ibSelect);
        ibDML = (ImageButton) findViewById(R.id.ibDML);
        ibDiagram = (ImageButton) findViewById(R.id.ibDiagram);
        ibReset = (ImageButton) findViewById(R.id.ibReset);

        ibHomebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        ibSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), ActivityQuery.class);
                startActivity(intent);
            }
        });

        ibDML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), ActivityDML.class);
                startActivity(intent);
            }
        });

        ibDiagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), ActivityDiagram.class);
                startActivity(intent);
            }
        });

        ibReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.reset();
                db_en.reset();
                Toast.makeText(getApplicationContext(), "Datenbank zurückgesetzt", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();  // optional depending on your needs
    }
}

