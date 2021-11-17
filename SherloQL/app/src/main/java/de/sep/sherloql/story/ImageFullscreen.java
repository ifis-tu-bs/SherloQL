package de.sep.sherloql.story;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import de.sep.sherloql.R;
import de.sep.sherloql.bin.Map;

public class ImageFullscreen extends FragmentActivity {

    private SubsamplingScaleImageView imageRiddle;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_fullscreen);
        Intent intent = getIntent();
        int name = intent.getIntExtra("image", -1);
        imageRiddle = (SubsamplingScaleImageView) findViewById(R.id.image_riddle);
        imageRiddle.setImage(ImageSource.resource(name));
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}