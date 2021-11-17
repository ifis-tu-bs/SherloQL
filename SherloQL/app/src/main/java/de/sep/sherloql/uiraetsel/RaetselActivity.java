package de.sep.sherloql.uiraetsel;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import de.sep.sherloql.R;

/**
 * Raetsel mit Adapter verbinden
 */
public class RaetselActivity extends AppCompatActivity {
    // Attribute deklariert.
    private static final String TAG = "RaetselActivity";
    // Screen slide animation.
    ViewPager           viewPagerRaetsel;
    RaetselPagerAdapter rtAdapter;
    public ArrayList<Raetsel> raetselArrayList = new ArrayList<>();


    /**
     *
     * Bundle wird im Allgemeinen zum Übergeben von Daten zwischen verschiedenen
     * Aktivitäten verwendet. Das Bundle
     * kann alle Arten von Werten enthalten und an die neue Aktivität übergeben.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raetsel_main);
        Log.d(TAG, "onCreate: starts");
        viewPagerRaetsel = findViewById(R.id.viewPagerRaetsel);
        Bundle extra = getIntent().getBundleExtra("extra");


        assert extra != null;
        Raetsel currentRaetsel = (Raetsel) extra.getSerializable("RAETSEL");

        int itemSelected = (int) extra.getSerializable("POSITION");
        raetselArrayList = (ArrayList<Raetsel>) extra.getSerializable("ARRAY");

        rtAdapter = new RaetselPagerAdapter(getSupportFragmentManager(), raetselArrayList);

        viewPagerRaetsel.setAdapter(rtAdapter);
        viewPagerRaetsel.setCurrentItem(itemSelected);
    }
}
