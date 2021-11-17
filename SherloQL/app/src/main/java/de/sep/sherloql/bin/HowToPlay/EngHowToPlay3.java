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

public class EngHowToPlay3 extends AppCompatActivity {

    private Button nexttut2;
    private static final String TAG = "EngHowToPlay";

    private SaveStateHelper stateHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: activity starts");
        setContentView(R.layout.tutorialx3_eng);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        stateHelper = new SaveStateHelper(this); //Datenbank vallah habib

        initButtons();

    }

    public void initButtons(){
        nexttut2 = (Button) findViewById(R.id.tut3tosqlnosql_eng);
        nexttut2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (stateHelper.getSQL() != 2) {
                    Intent intent = new Intent(view.getContext(), EngHowToPlay4.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(view.getContext(), EngHowToPlaySQLnoSQL.class);
                    startActivity(intent);
                }
                finish();

            }
        });
    }
}