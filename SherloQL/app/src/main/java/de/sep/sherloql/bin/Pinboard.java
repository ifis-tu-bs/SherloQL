package de.sep.sherloql.bin;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.sep.sherloql.R;
import de.sep.sherloql.savestate.SaveStateHelper;

public class Pinboard extends AppCompatActivity {
    private final String TAG = "Pinboard";
    private ArrayList<Pinboardobjekt> pinboardArrayList = new ArrayList<>();
    private ArrayList<String> artefacts = new ArrayList<>();
    private ArrayList<String> items = new ArrayList<>();
    private SaveStateHelper sHelper;
    private RecyclerView recyclerViewPinboard;
    private PinboardListAdapter pinAdapter;
    private TextView pinboardTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinboard);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sHelper = new SaveStateHelper(this);
        artefacts = sHelper.getUnlockedArtefacts();

        setContentView(R.layout.activity_pinboard);
        pinboardTextView = (TextView) findViewById(R.id.tvPinboard);
        //deutsch oder englisch
        if (sHelper.getLanguage() == 0) {
            pinboardTextView.setText("Pinnwand");
        } else {
            pinboardTextView.setText("Pinboard");
        }

        if(!artefacts.isEmpty()) {
            Log.d(TAG, "Pinboard: huhu");
            for (int i = 0; i < artefacts.size(); i++) {
                Log.d(TAG, "unlockiiiiii: "+ artefacts.get(i));
                if (artefacts.get(i).charAt(0) == '?') {
                    if (!items.contains(artefacts.get(i).substring(1).toLowerCase()))
                    {
                        items.add(artefacts.get(i).substring(1).toLowerCase());
                    }
                }
            }

            initPinboard();
        }

        recyclerViewPinboard = findViewById(R.id.rvListPinboard);
        recyclerViewPinboard.setLayoutManager(new LinearLayoutManager(this));

        pinAdapter = new PinboardListAdapter(this, pinboardArrayList);
        recyclerViewPinboard.setAdapter(pinAdapter);
    }

    private void initPinboard() {
            for (int i = 0; i < items.size(); i++) {
                int index = items.get(i).indexOf("_");
                String fin;
                if (index != -1) {
                    String name = items.get(i).replace('_',' ');
                    fin = name.substring(0, 1).toUpperCase() + name.substring(1, index+1) + name.substring(index+1, index+2).toUpperCase() + name.substring(index+2);
                } else {
                    fin = items.get(i).substring(0, 1).toUpperCase() + items.get(i).substring(1);
                }
                Log.d(TAG, "initPinboard lulululu: " + fin + " lalalaa "+ items.get(i));
                pinboardArrayList.add(new Pinboardobjekt(fin, items.get(i)));
            }
    }
}
