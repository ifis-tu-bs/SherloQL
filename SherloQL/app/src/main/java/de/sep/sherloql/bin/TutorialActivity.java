package de.sep.sherloql.bin;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import de.sep.sherloql.R;


public class TutorialActivity extends AppCompatActivity {

    private static final String TAG = "TutorialActivity";
    private ArrayList<Tutorialeinheit> tutorialeinheitArrayList = new ArrayList<>();

    TextView teErklärung;
    TextView teBeispiel;
    TextView teBspErklärung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorialeinheit);
        Log.d(TAG, "onCreate: starts");
        Bundle extra = getIntent().getBundleExtra("extra");

        Tutorialeinheit currentTutorialeinheit = (Tutorialeinheit) extra.getSerializable("TUTORIALEINHEIT");

        teErklärung = (TextView) findViewById(R.id.teErklärung);
        teErklärung.setText(currentTutorialeinheit.getErklaerung());
        teBeispiel = (TextView) findViewById(R.id.teBeispiel);
        teBeispiel.setText(currentTutorialeinheit.getBeispiel());
        teBspErklärung = (TextView) findViewById(R.id.teBspErklärung);
        teBspErklärung.setText(currentTutorialeinheit.getErklaerungBeispiel());

    }
}
