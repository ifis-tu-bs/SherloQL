package de.sep.sherloql.bin.HowToPlay;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.sep.sherloql.savestate.SaveStateHelper;
import de.sep.sherloql.R;

public class HowToPlaySQLnoSQL extends AppCompatActivity {

    private Button nosqlGer, sqlGer;
    private static final String TAG = "HowToPlayLanguage";
    private SaveStateHelper stateHelper;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: activity starts");
        setContentView(R.layout.tutorial_sql_nosql);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        stateHelper = new SaveStateHelper(this); //Datenbank vallah habib
        initButtons();

    }

    public void initButtons(){
        nosqlGer = (Button) findViewById(R.id.tutWithoutSQL);
        nosqlGer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateHelper.updateSQL("1", "0");
                Intent intent = new Intent(view.getContext(), HowToPlayNoSQL.class);
                startActivity(intent);
                finish();
            }
        });

        sqlGer = (Button) findViewById(R.id.tutWithSQL);
        sqlGer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateHelper.updateSQL("1", "1");
                Intent intent = new Intent(view.getContext(), HowToPlay4.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
