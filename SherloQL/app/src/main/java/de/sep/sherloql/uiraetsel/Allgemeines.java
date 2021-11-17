package de.sep.sherloql.uiraetsel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import de.sep.sherloql.R;
import de.sep.sherloql.bin.MainActivity;
import de.sep.sherloql.savestate.SaveStateHelper;

public class Allgemeines extends AppCompatActivity {
    //Initialize variable
    DrawerLayout drawerLayout;
    private static final String TAG = "Raetselsammlung";
    private static ArrayList<Raetsel> raetselArrayList;
    private RecyclerView recyclerView;
    private RaetselListAdapter rlAdapter;
    private SaveStateHelper stateHelper;
    private static ParseRaetsel parseRaetsel;
    private TextView total_coins;
    private ImageView total_coins_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: starts");
        setContentView(R.layout.activity_allgemeines);
        SharedPreferences sp = getSharedPreferences("sp",MODE_PRIVATE);
        boolean firstRiddle = sp.getBoolean("firstRiddle", true);
        stateHelper = new SaveStateHelper(this);
        if (parseRaetsel == null) {
            Log.d(TAG, "onCreate: parse");
            parseRaetsel = new ParseRaetsel(this);
                raetselArrayList = parseRaetsel.parse();
                if (firstRiddle) {
                    for (int i = 0; i < raetselArrayList.size(); i++) {
                        stateHelper.insertRBAllgemeines("false");
                    }
                    SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp1.edit();
                    editor.putBoolean("firstRiddle", false);
                    editor.apply();
                }

        }
        ArrayList<Raetsel> allgemeinArrayList = new ArrayList<Raetsel>();
        for (int i = 0; i < raetselArrayList.size(); i++)  {
            if(raetselArrayList.get(i).getCategory().equals("Allgemeines")) {
                recyclerView = findViewById(R.id.rvListRs);
                total_coins = findViewById(R.id.total_coins_text);
                total_coins.setText(Integer.toString(stateHelper.getCoins("1")));
                total_coins_img = findViewById(R.id.total_coins);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                // Adapter, um eine Liste von Raetseln zu zeigen.
                allgemeinArrayList.add(raetselArrayList.get(i));
                rlAdapter = new RaetselListAdapter(this, allgemeinArrayList);
                recyclerView.setAdapter(rlAdapter);
            }
        }


        drawerLayout = findViewById(R.id.drawer_layout);

    }

    /**@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allgemeines);

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);

    }*/

    public void ClickMenu(View view){
        //Open drawer
        Raetselsammlung.openDrawer(drawerLayout);
    }

    public void ClickLogo (View view){
        //Close drawer
        Raetselsammlung.closeDrawer(drawerLayout);
    }

    public void ClickHome (View view){
        //Redirect activity to home
        Raetselsammlung.redirectActivity(this, Raetselsammlung.class);
    }

    public void ClickAllgemeines(View view){
        //Recreate activity
        recreate();
    }

    public void ClickBraunschweig(View view){
        //Redirect activity to Braunschweig
        Raetselsammlung.redirectActivity(this, Braunschweig.class);
    }

    public void ClickGeographie(View view){
        //Redirect activity to Geographie
        Raetselsammlung.redirectActivity(this, Geographie.class);
    }

    public void ClickInformatik(View view){
        //Redirect activity to Informatik
        Raetselsammlung.redirectActivity(this, Informatik.class);
    }

    public void ClickLogik(View view){
        //Redirect activity to Logik
        Raetselsammlung.redirectActivity(this, Logik.class);
    }

    public void ClickMathematik(View view){
        //Redirect activity to Mathematik
        Raetselsammlung.redirectActivity(this, Mathematik.class);
    }

    public void ClickSprache(View view){
        //Redirect activity to Sprache
        Raetselsammlung.redirectActivity(this, Sprache.class);

    }
   /* public void ClickQuit(View view){
        //Close app
        Raetselsammlung.quit(this);
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        //Close drawer
        Raetselsammlung.closeDrawer(drawerLayout);
    }
}
