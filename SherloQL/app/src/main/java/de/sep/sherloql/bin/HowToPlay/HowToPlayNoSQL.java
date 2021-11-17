package de.sep.sherloql.bin.HowToPlay;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.sep.sherloql.R;
import de.sep.sherloql.bin.MainActivity;

public class HowToPlayNoSQL extends AppCompatActivity {

    private Button nossqlTo4;
    private static final String TAG = "HowToPlayNoSQL";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: activity starts");
        setContentView(R.layout.tutorial_nosql);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        initButtons();

    }

    public void initButtons(){
        nossqlTo4 = (Button) findViewById(R.id.tut_nosql_to4_ger);
        nossqlTo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HowToPlay4.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
