package de.sep.sherloql.uiraetsel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.sep.sherloql.R;
import de.sep.sherloql.bin.MainActivity;
import de.sep.sherloql.savestate.SaveStateHelper;


/**
 * Hier wird ein paar Buttons deklariert, um von einem Activity zum anderen zu wechseln.
 *
 * Eine Liste von Raetseln durch einem Adapter erstellt.
 *
 */
public class Raetselsammlung extends AppCompatActivity  {

    // Attribute deklariert
    DrawerLayout drawerLayout;
    private static final String TAG = "Raetselsammlung";
    private static ArrayList<Raetsel> raetselArrayList;
    private RecyclerView       recyclerView;
    private RaetselListAdapter rlAdapter;
    private SaveStateHelper stateHelper;
    private static ParseRaetsel parseRaetsel;
    private TextView total_coins;
    private ImageView total_coins_img;
    private Button fullscreenbutton;


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: starts");
        setContentView(R.layout.activity_test);

        SharedPreferences sp = getSharedPreferences("sp",MODE_PRIVATE);
        boolean firstRiddle = sp.getBoolean("firstRiddle", true);
        stateHelper = new SaveStateHelper(this);
        if (parseRaetsel == null) {
           Log.d(TAG, "onCreate: parse");
           parseRaetsel = new ParseRaetsel(this);
           raetselArrayList = parseRaetsel.parse();
           ArrayList<Raetsel> allgemeinArrayList = new ArrayList<Raetsel>();
           ArrayList<Raetsel> braunschweigArrayList = new ArrayList<Raetsel>();
           ArrayList<Raetsel> geographieArrayList = new ArrayList<Raetsel>();
           ArrayList<Raetsel> informatikArrayList = new ArrayList<Raetsel>();
           ArrayList<Raetsel> logikArrayList = new ArrayList<Raetsel>();
           ArrayList<Raetsel> mathematikArrayList = new ArrayList<Raetsel>();
           ArrayList<Raetsel> spracheArrayList = new ArrayList<Raetsel>();
           for (int i = 0; i < raetselArrayList.size(); i++){
               if(raetselArrayList.get(i).getCategory().equals("Allgemeines")) {
                   allgemeinArrayList.add(raetselArrayList.get(i));
                   if (firstRiddle) {
                       for (int j = 0; j < allgemeinArrayList.size(); j++) {
                           stateHelper.insertRBAllgemeines("false");
                       }
                       SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
                       SharedPreferences.Editor editor = sp1.edit();
                       editor.putBoolean("firstRiddle", false);
                       editor.apply();
                   }
               } else if(raetselArrayList.get(i).getCategory().equals("Braunschweig")){
                   braunschweigArrayList.add(raetselArrayList.get(i));
                   if (firstRiddle) {
                       for (int j = 0; j < braunschweigArrayList.size(); j++) {
                           stateHelper.insertRBBraunschweig("false");
                       }
                       SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
                       SharedPreferences.Editor editor = sp1.edit();
                       editor.putBoolean("firstRiddle", false);
                       editor.apply();
                   }
               } else if(raetselArrayList.get(i).getCategory().equals("Geographie")){
                   geographieArrayList.add(raetselArrayList.get(i));
                   if (firstRiddle) {
                       for (int j = 0; j < geographieArrayList.size(); j++) {
                           stateHelper.insertRBGeographie("false");
                       }
                       SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
                       SharedPreferences.Editor editor = sp1.edit();
                       editor.putBoolean("firstRiddle", false);
                       editor.apply();
                   }
               } else if(raetselArrayList.get(i).getCategory().equals("SQL")){
                   informatikArrayList.add(raetselArrayList.get(i));
                   if (firstRiddle) {
                       for (int j = 0; j < informatikArrayList.size(); j++) {
                           stateHelper.insertRBInformatik("false");
                       }
                       SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
                       SharedPreferences.Editor editor = sp1.edit();
                       editor.putBoolean("firstRiddle", false);
                       editor.apply();
                   }
               } else if(raetselArrayList.get(i).getCategory().equals("Logik")){
                   logikArrayList.add(raetselArrayList.get(i));
                   if (firstRiddle) {
                       for (int j = 0; j < logikArrayList.size(); j++) {
                           stateHelper.insertRBLogik("false");
                       }
                       SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
                       SharedPreferences.Editor editor = sp1.edit();
                       editor.putBoolean("firstRiddle", false);
                       editor.apply();
                   }
               } else if(raetselArrayList.get(i).getCategory().equals("Mathematik")){
                   mathematikArrayList.add(raetselArrayList.get(i));
                   if (firstRiddle) {
                       for (int j = 0; j < mathematikArrayList.size(); j++) {
                           stateHelper.insertRBMathematik("false");
                       }
                       SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
                       SharedPreferences.Editor editor = sp1.edit();
                       editor.putBoolean("firstRiddle", false);
                       editor.apply();
                   }
               } else if(raetselArrayList.get(i).getCategory().equals("Sprachen")){
                   spracheArrayList.add(raetselArrayList.get(i));
                   if (firstRiddle) {
                       for (int j = 0; j < spracheArrayList.size(); j++) {
                           stateHelper.insertRBSprache("false");
                       }
                       SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
                       SharedPreferences.Editor editor = sp1.edit();
                       editor.putBoolean("firstRiddle", false);
                       editor.apply();
                   }
               }
           }
       }

       recyclerView = findViewById(R.id.rvListRs);
        total_coins = findViewById(R.id.total_coins_text);
        total_coins.setText(Integer.toString(stateHelper.getCoins("1")));
        total_coins_img = findViewById(R.id.total_coins);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Adapter, um eine Liste von Raetseln zu zeigen.
        rlAdapter = new RaetselListAdapter(this, raetselArrayList);
        recyclerView.setAdapter(rlAdapter);



       drawerLayout = findViewById(R.id.drawer_layout);

    }
    public void ClickMenu(View view){
       //Open Drawer
    openDrawer(drawerLayout);


    }

    public static void openDrawer(DrawerLayout drawerLayout) {
       //Open drawer layout
       drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
       //Close drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
       //Closer drawer layout
        // Check condition
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            //When drawer is open
            //Close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){

        //Recreate activity
        recreate();
    }

    public void ClickAllgemeines(View view){

        //Redirect activity to Allgemeines
        redirectActivity(this, Allgemeines.class);
    }

    public void ClickBraunschweig(View view){

        //Redirect activity to Braunschweig
        redirectActivity(this, Braunschweig.class);
    }

    public void ClickGeographie(View view){

        //Redirect activity to Geographie
        redirectActivity(this, Geographie.class);
    }


    public void ClickInformatik(View view){

        //Redirect activity to Informatik
        redirectActivity(this, Informatik.class);
    }

    public void ClickLogik(View view){

        //Redirect activity to Logik
        redirectActivity(this, Logik.class);
    }

    public void ClickMathematik(View view){

        //Redirect activity to Mathematik
        redirectActivity(this, Mathematik.class);
    }
    public void ClickSprache(View view){

        //Redirect activity to Sprache
        redirectActivity(this, Sprache.class);
    }

    public static void redirectActivity(Activity activity,Class aClass ) {
       //Initialize intent
        Intent intent = new Intent(activity,aClass);
        // Set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Start activity
        activity.startActivity(intent);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        //Close drawer
        closeDrawer(drawerLayout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        total_coins.setText(Integer.toString(stateHelper.getCoins("1")));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}