package de.sep.sherloql.database.database_activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import de.sep.sherloql.R;
import de.sep.sherloql.savestate.SaveStateHelper;


public class ActivityDiagram extends AppCompatActivity {
    private Button back;
    private SubsamplingScaleImageView image;
    SaveStateHelper stateHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagram);
        stateHelper = new SaveStateHelper(this);
        image = (SubsamplingScaleImageView) findViewById(R.id.diagram);
        if (stateHelper.getLanguage() == 0) {
            image.setImage(ImageSource.resource(getResources().getIdentifier("erdiagram", "drawable", getPackageName())));
        } else {
            image.setImage(ImageSource.resource(getResources().getIdentifier("erdiagram_en", "drawable", getPackageName())));
        }
        back = (Button) findViewById(R.id.back_diagram);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
