package de.sep.sherloql.bin.HowToPlay;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.sep.sherloql.savestate.SaveStateHelper;
import de.sep.sherloql.R;

public class EngHowToPlaySQLnoSQL extends AppCompatActivity {

    private Button nosqlEng, sqlEng;
    private static final String TAG = "EngHowToPlay";
    private SaveStateHelper stateHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: activity starts");
        setContentView(R.layout.tutorialsql_nosql_eng);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        stateHelper = new SaveStateHelper(this); //Datenbank vallah habib

        initButtons();

    }

    public void initButtons(){
        nosqlEng = (Button) findViewById(R.id.tutWithoutSQL_eng);
        nosqlEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateHelper.updateSQL("1", "0");
                Intent intent = new Intent(view.getContext(), EngHowToPlayNoSQL.class);
                startActivity(intent);
                finish();
            }
        });

        sqlEng = (Button) findViewById(R.id.tutWithSQL_eng);
        sqlEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateHelper.updateSQL("1", "1");
                Intent intent = new Intent(view.getContext(), EngHowToPlay4.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
