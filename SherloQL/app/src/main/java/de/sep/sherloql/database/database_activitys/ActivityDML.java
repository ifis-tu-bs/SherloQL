package de.sep.sherloql.database.database_activitys;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import de.sep.sherloql.R;

/**
 * @author Matthias T
 */
public class ActivityDML extends AppCompatActivity {

    private Button insertButton, deleteButton, updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dml);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        initButtons();
    }

    private void initButtons() {
        final MediaPlayer clickSound = MediaPlayer.create(this, R.raw.clicksound);
        insertButton = (Button) findViewById(R.id.insert);
        insertButton.setOnClickListener( (v) ->{
            Intent intent = new Intent(v.getContext(), ActivityInsert.class);
            startActivity(intent);
        });

        deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener( (v) -> {
                    Intent intent = new Intent(v.getContext(), ActivityDelete.class);
                    startActivity(intent);
        });

        updateButton = (Button) findViewById(R.id.update);
        updateButton.setOnClickListener( (v) ->{
            Intent intent = new Intent(v.getContext(), ActivityUpdate.class);
            startActivity(intent);
        });
    }
}
