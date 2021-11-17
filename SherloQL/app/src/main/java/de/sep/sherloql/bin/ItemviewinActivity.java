package de.sep.sherloql.bin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import de.sep.sherloql.R;


public class ItemviewinActivity extends AppCompatActivity {

    private static final String TAG = "ItemviewinActivity";
    SubsamplingScaleImageView unterlage;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinboarditem);
        Log.d(TAG, "onCreate: starts");
        Bundle extra = getIntent().getBundleExtra("extra");
        Pinboardobjekt currentPinboardobjekt = (Pinboardobjekt) extra.getSerializable("PINBOARDOBJEKT");

        unterlage = (SubsamplingScaleImageView) findViewById(R.id.imagePinboard);
       // unterlage.setImageResource(getResources().getIdentifier(currentPinboardobjekt.getImage(), "drawable", getPackageName()));
        unterlage.setImage(ImageSource.resource(getResources().getIdentifier(currentPinboardobjekt.getImage(), "drawable", getPackageName())));
    }

}
