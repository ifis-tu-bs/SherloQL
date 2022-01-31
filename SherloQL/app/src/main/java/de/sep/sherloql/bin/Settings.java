package de.sep.sherloql.bin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.sep.sherloql.R;
import de.sep.sherloql.savestate.SaveStateHelper;

public class Settings extends AppCompatActivity {
    Button achievementsBttn;
    Button home;
    TextView languageTextView;
    ImageView germanFlag;
    ImageView ukFlag;
    Switch tonSwitch;

    private SaveStateHelper stateHelper;
    ImageView soundOn;
    ImageView soundOff;
    ImageButton editUsername;
    EditText newUsernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        stateHelper = new SaveStateHelper(this);
        initButtons();
        if (stateHelper.getLanguage() == 0) {
            ukFlag.setAlpha(0.5f);
            germanFlag.setAlpha(1f);
            languageTextView.setText("Sprache:");
            tonSwitch.setText("Ton");
            if (stateHelper.getUsername("1").equals(" ")) {
                newUsernameTextView.setHint("Neuer Benutzername");
            }
            else {
                newUsernameTextView.setHint(stateHelper.getUsername("1"));
            }
        } else {
            germanFlag.setAlpha(0.5f);
            ukFlag.setAlpha(1f);
            languageTextView.setText("Language:");
            tonSwitch.setText("Sound");

            if (stateHelper.getUsername("1").equals(" ")) {
                newUsernameTextView.setHint("New username");
            }
            else {
                newUsernameTextView.setHint(stateHelper.getUsername("1"));
            }
        }
        if (stateHelper.getSound() == 1) {
            tonSwitch.setChecked(true);
            soundOff.setVisibility(View.INVISIBLE);
            soundOn.setVisibility(View.VISIBLE);
        } else {
            tonSwitch.setChecked(false);
            soundOn.setVisibility(View.INVISIBLE);
            soundOff.setVisibility(View.VISIBLE);
        }
    }
    private void initButtons() {
        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        languageTextView = (TextView) findViewById(R.id.languageTextview);
        germanFlag = (ImageView) findViewById(R.id.germanFlag);
        ukFlag = (ImageView) findViewById(R.id.ukFlag);

        tonSwitch = (Switch) findViewById(R.id.switch2);

        editUsername = (ImageButton) findViewById(R.id.username_button);
        newUsernameTextView = (EditText) findViewById(R.id.username_text);

        germanFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ukFlag.setAlpha(0.5f);
                germanFlag.setAlpha(1f);
                languageTextView.setText("Sprache:");
                tonSwitch.setText("Ton");
                if (stateHelper.getUsername("1").equals(" ")) {
                    newUsernameTextView.setHint("Neuer Benutzername");
                }
                else {

                    newUsernameTextView.setHint(stateHelper.getUsername("1"));
                }
                stateHelper.updateLanguage("1", "0");
            }
        });

        ukFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                germanFlag.setAlpha(0.5f);
                ukFlag.setAlpha(1f);
                languageTextView.setText("Language:");
                tonSwitch.setText("Sound");
                if (stateHelper.getUsername("1").equals(" ")) {
                    newUsernameTextView.setHint("New username");
                }
                else {
                    newUsernameTextView.setHint(stateHelper.getUsername("1"));
                }
                stateHelper.updateLanguage("1", "1");
            }
        });



        tonSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (tonSwitch.isChecked()) {
                    stateHelper.updateSound("1", "1");
                    soundOff.setVisibility(View.INVISIBLE);
                    soundOn.setVisibility(View.VISIBLE);
                } else {
                    stateHelper.updateSound("1", "0");
                    soundOn.setVisibility(View.INVISIBLE);
                    soundOff.setVisibility(View.VISIBLE);
                }
            }
        });

        editUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                stateHelper.updateUsername("1", newUsernameTextView.getText().toString());
            }
        });

        soundOn = (ImageView) findViewById(R.id.imageViewSoundOn);
        soundOff = (ImageView) findViewById(R.id.imageViewNoSound);

    }
}
