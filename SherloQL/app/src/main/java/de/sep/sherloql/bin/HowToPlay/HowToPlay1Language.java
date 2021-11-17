package de.sep.sherloql.bin.HowToPlay;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.sep.sherloql.R;
import de.sep.sherloql.savestate.SaveStateHelper;

public class HowToPlay1Language extends AppCompatActivity {

    private ImageView tut1englisch, tut1deutsch;
    private static final String TAG = "HowToPlayLanguage";
    private SaveStateHelper stateHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: activity starts");
        setContentView(R.layout.tutorial1language);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        stateHelper = new SaveStateHelper(this);
        stateHelper.insertSQL("2");
        stateHelper.insertSound("1");
        stateHelper.insertUsername(" ");
        initButtons();

    }

    public void initButtons(){
        tut1deutsch = (ImageView) findViewById(R.id.tutGerFlag);
        tut1deutsch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateHelper.insertLanguage("0");
                Intent intent = new Intent(view.getContext(), HowToPlay1.class);
                startActivity(intent);
                finish();
                //TODO: set language in database
            }
        });

        tut1englisch = (ImageView) findViewById(R.id.tutUKFlag);
        tut1englisch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateHelper.insertLanguage("1");
                Intent intent = new Intent(view.getContext(), EngHowToPlay1.class);
                startActivity(intent);
                finish();
                //TODO: set language in database
            }
        });
    }
}
