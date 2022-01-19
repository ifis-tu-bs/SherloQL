package de.sep.sherloql.bin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import de.sep.sherloql.BuildConfig;
import de.sep.sherloql.R;
import de.sep.sherloql.bin.HowToPlay.EngHowToPlay1;
import de.sep.sherloql.bin.HowToPlay.HowToPlay1;
import de.sep.sherloql.bin.HowToPlay.HowToPlay1Language;
import de.sep.sherloql.database.database_logic.DBOpenHelper;
import de.sep.sherloql.database.database_logic.DBOpenHelperEn;
import de.sep.sherloql.savestate.SaveStateHelper;

/**
 * @author Matthias T
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private final String LOG_TAG = "MainActivity";
    private Button database, tutorial, map, riddlebook, settings, pinboard;
    private ImageButton howto;

    private DBOpenHelper dbOpenHelper;
    private DBOpenHelperEn dbOpenHelperEn;
    private SaveStateHelper stateHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.parseColor("#1A000000"));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

/*        if(BuildConfig.DEBUG)
            StrictMode.enableDefaults();*/

        dbOpenHelper = new DBOpenHelper(this);
        dbOpenHelperEn = new DBOpenHelperEn(this);
        stateHelper = new SaveStateHelper(this);
        initButtons();

        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        boolean firstStart = sp.getBoolean("firstStart", true);

        if (firstStart) {
            Log.d(LOG_TAG, "onCreate: firststarterkannt");
            initDatabase();
            startActivity(new Intent(getApplicationContext(), HowToPlay1Language.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initButtons(){
        final MediaPlayer clickSound = MediaPlayer.create(this, R.raw.clicksound);
        database = (Button) findViewById(R.id.database);
        database.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateHelper.getSound() == 1) clickSound.start();
                Intent intent = new Intent(v.getContext(), Database.class);
                startActivity(intent);

            }
        });

        tutorial = (Button) findViewById(R.id.tutorial);
        tutorial.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateHelper.getSound() == 1) clickSound.start();
                Intent intent = new Intent(v.getContext(), Tutorial.class);
                startActivity(intent);
            }
        });

        settings = (Button) findViewById(R.id.settings);
        settings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateHelper.getSound() == 1) clickSound.start();
                Intent intent = new Intent(v.getContext(), Settings.class);
                startActivity(intent);
            }
        });

        map = (Button) findViewById(R.id.map);
        map.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateHelper.getSound() == 1) clickSound.start();
                Intent intent = new Intent(v.getContext(), Map.class);
                startActivity(intent);
            }
        });

        riddlebook = (Button) findViewById(R.id.riddlebook);
        riddlebook.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateHelper.getSound() == 1) clickSound.start();
                Intent intent = new Intent(v.getContext(), de.sep.sherloql.uiraetsel.Raetselsammlung.class);
                startActivity(intent);
            }
        });

        pinboard = (Button) findViewById(R.id.pinboard);
        pinboard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateHelper.getSound() == 1) clickSound.start();
                Intent intent = new Intent(v.getContext(), Pinboard.class);
                startActivity(intent);
            }
        });
        howto = (ImageButton) findViewById(R.id.tutorialbutton);
        howto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stateHelper.getLanguage() == 0) {
                    Intent intent = new Intent(view.getContext(), HowToPlay1.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(view.getContext(), EngHowToPlay1.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initDatabase(){
        dbOpenHelper.reset();
        dbOpenHelperEn.reset();

        stateHelper.insertCoins("0");
        for (int i = 0; i < 20; i++) {
            if (i == 0) {
                stateHelper.insertLocation(String.valueOf(i), "false", "true");
            } else {
                stateHelper.insertLocation(String.valueOf(i), "false", "false");
            }
        }



        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }
}
