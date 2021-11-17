package de.sep.sherloql.bin.HowToPlay;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.sep.sherloql.R;

public class HowToPlay6 extends AppCompatActivity {

    private Button nexttut2;
    private static final String TAG = "HowToPlay";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: activity starts");
        setContentView(R.layout.tutorial6);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        initButtons();

    }

    public void initButtons(){
        nexttut2 = (Button) findViewById(R.id.bttn2tut7);
        nexttut2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HowToPlay7.class);
                startActivity(intent);
                finish();

            }
        });
    }
}