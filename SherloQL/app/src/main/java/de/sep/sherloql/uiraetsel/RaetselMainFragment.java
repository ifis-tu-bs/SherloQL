package de.sep.sherloql.uiraetsel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import de.sep.sherloql.R;
import de.sep.sherloql.savestate.SaveStateHelper;
import de.sep.sherloql.story.ImageFullscreen;

import android.widget.Toast;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *f
 */
public class RaetselMainFragment extends Fragment implements View.OnClickListener {
    // Attribute deklariert.
    private static final String TAG = "RaetselMainFragment";
    private int position;
    private Raetsel raetsel;
    private TextView  tvQuestion, tvAnswer, tvType, tvName;
    private Button    btnChoiceOne, btnChoiceTwo, btnChoiceThree, btnChoiceFour;
    private View itemView;
    private Button btn;
    private String chosenAnswer, points;
    private EditText input;
    private SaveStateHelper sHelper;
    private TextView total_coins;
    private SubsamplingScaleImageView imageRiddle;
    private String type;
    private Button fullscreen_image;
    private Pattern pattern;
    private Matcher matcher1;

    /**
     * Leeres Konstruktor von RaetselMainFragment
     */
    public RaetselMainFragment() {
        // Required empty public constructor
    }
    /**
     * Methode gebraucht, um Variablen von ein Java class zu einem Fragment überzugeben.
     * @param raetsel
     * @param index
     * @return
     */
    public static Fragment newInstance(Raetsel raetsel, int index) {
        Log.d(TAG, "newInstance: starts");
        RaetselMainFragment mainFragment = new RaetselMainFragment();
        Bundle extra = new Bundle();

        extra.putSerializable("POSITION", index);
        extra.putSerializable("RAETSEL" ,raetsel);

        mainFragment.setArguments(extra);
        return mainFragment;
    }


    /**
     * In der onSaveInstanceState()
     * Methode werden wir den Inhalt des ListViews,
     * also die Elemente seiner Datenquelle,
     * auslesen und in einem Bundle-Objekt ablegen.
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("POSITION", position);
        outState.putSerializable("RAETSEL", raetsel);
        super.onSaveInstanceState(outState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sHelper = new SaveStateHelper(getActivity());
        if (getArguments() != null) {
            raetsel = (Raetsel) getArguments().getSerializable("RAETSEL");
            position = (int) getArguments().getSerializable("POSITION");
        }

        points = raetsel.getPoints();
        type = "multiple choice";


        switch(raetsel.getCategory()) {
            case "Allgemeines":
                if (sHelper.getRBAllgemeinesSolved(Integer.toString(position + 1))) {
                    if (raetsel.getType().toLowerCase().equals("antwort")) {
                        Log.d(TAG, "Type:" + raetsel.getType());
                        type = raetsel.getType().toLowerCase();
                        raetsel.setType("Solved");
                    } else if (raetsel.getType().toLowerCase().equals("multiple choice")) {
                        Log.d(TAG, "Type:" + raetsel.getType());
                        type = raetsel.getType().toLowerCase();
                        raetsel.setType("Solved");
                    }
                }
                break;
            case "Braunschweig":
                if (sHelper.getRBBraunschweigSolved(Integer.toString(position + 1))) {
                    if (raetsel.getType().toLowerCase().equals("antwort")) {
                        Log.d(TAG, "Type:" + raetsel.getType());
                        type = raetsel.getType().toLowerCase();
                        raetsel.setType("Solved");
                    } else if (raetsel.getType().toLowerCase().equals("multiple choice")) {
                        Log.d(TAG, "Type:" + raetsel.getType());
                        type = raetsel.getType().toLowerCase();
                        raetsel.setType("Solved");
                    }
                }
                break;
            case "Geographie":
                if (sHelper.getRBGeographieSolved(Integer.toString(position + 1))) {
                    if (raetsel.getType().toLowerCase().equals("antwort")) {
                        Log.d(TAG, "Type:" + raetsel.getType());
                        type = raetsel.getType().toLowerCase();
                        raetsel.setType("Solved");
                    } else if (raetsel.getType().toLowerCase().equals("multiple choice")) {
                        Log.d(TAG, "Type:" + raetsel.getType());
                        type = raetsel.getType().toLowerCase();
                        raetsel.setType("Solved");
                    }
                }
                break;
            case "SQL":
                if (sHelper.getRBInformatikSolved(Integer.toString(position + 1))) {
                    if (raetsel.getType().toLowerCase().equals("antwort")) {
                        Log.d(TAG, "Type:" + raetsel.getType());
                        type = raetsel.getType().toLowerCase();
                        raetsel.setType("Solved");
                    } else if (raetsel.getType().toLowerCase().equals("multiple choice")) {
                        Log.d(TAG, "Type:" + raetsel.getType());
                        type = raetsel.getType().toLowerCase();
                        raetsel.setType("Solved");
                    }
                }
                break;
            case "Logik":
                if (sHelper.getRBLogikSolved(Integer.toString(position + 1))) {
                    if (raetsel.getType().toLowerCase().equals("antwort")) {
                        Log.d(TAG, "Type:" + raetsel.getType());
                        type = raetsel.getType().toLowerCase();
                        raetsel.setType("Solved");
                    } else if (raetsel.getType().toLowerCase().equals("multiple choice")) {
                        Log.d(TAG, "Type:" + raetsel.getType());
                        type = raetsel.getType().toLowerCase();
                        raetsel.setType("Solved");
                    }
                }
                break;
            case "Mathematik":
                if (sHelper.getRBMathematikSolved(Integer.toString(position + 1))) {
                    if (raetsel.getType().toLowerCase().equals("antwort")) {
                        Log.d(TAG, "Type:" + raetsel.getType());
                        type = raetsel.getType().toLowerCase();
                        raetsel.setType("Solved");
                    } else if (raetsel.getType().toLowerCase().equals("multiple choice")) {
                        Log.d(TAG, "Type:" + raetsel.getType());
                        type = raetsel.getType().toLowerCase();
                        raetsel.setType("Solved");
                    }
                }
                break;
            case "Sprachen":
                if (sHelper.getRBSpracheSolved(Integer.toString(position + 1))) {
                    if (raetsel.getType().toLowerCase().equals("antwort")) {
                        Log.d(TAG, "Type:" + raetsel.getType());
                        type = raetsel.getType().toLowerCase();
                        raetsel.setType("Solved");
                    } else if (raetsel.getType().toLowerCase().equals("multiple choice")) {
                        Log.d(TAG, "Type:" + raetsel.getType());
                        type = raetsel.getType().toLowerCase();
                        raetsel.setType("Solved");
                    }
                }
                break;
            default:
                break;
        }

        switch (raetsel.getType()) {
            case "Antwort":
            case "antwort":
                Log.d(TAG, "onCreateView: input");
                if (raetsel.getImage().equals("null")) {
                    itemView = inflater.inflate(R.layout.fragment_raetsel_input_withoutimage, container, false);
                } else {
                    itemView = inflater.inflate(R.layout.fragment_raetsel_input, container, false);
                }
                input = itemView.findViewById(R.id.simpleEditText);
                btn = itemView.findViewById(R.id.floatingActionButton);
                btn.setOnClickListener(this);
                break;
            case "solved":
            case  "Solved":
                if (type.equals("antwort")) {

                    if (raetsel.getImage().equals("null")) {
                        itemView = inflater.inflate(R.layout.fragment_raetsel_input_withoutimage, container, false);
                    } else {
                        itemView = inflater.inflate(R.layout.fragment_raetsel_input, container, false);
                    }
                    input = itemView.findViewById(R.id.simpleEditText);
                    btn = itemView.findViewById(R.id.floatingActionButton);
                    btn.setVisibility(View.INVISIBLE);
                    input.setText(raetsel.getChoices().getChoiceOne());
                    input.setKeyListener(null);

                } else if(type.equals("multiple choice")) {

                    if (raetsel.getImage().equals("null")) {
                        itemView = inflater.inflate(R.layout.fragment_raetsel_mc_withoutimage, container, false);
                    } else {
                        itemView = inflater.inflate(R.layout.fragment_raetsel_mc, container, false);
                    }

                    this.btnChoiceOne = itemView.findViewById(R.id.btnChoiceOne);
                    this.btnChoiceTwo = itemView.findViewById(R.id.btnChoiceTwo);
                    this.btnChoiceThree = itemView.findViewById(R.id.btnChoiceThree);
                    this.btnChoiceFour = itemView.findViewById(R.id.btnChoiceFour);

                    btnChoiceOne.setText(raetsel.getChoices().getChoiceOne());
                    btnChoiceTwo.setText(raetsel.getChoices().getChoiceTwo());
                    btnChoiceThree.setText(raetsel.getChoices().getChoiceThree());
                    btnChoiceFour.setText(raetsel.getChoices().getChoiceFour());

                    if (raetsel.getChoices().getChoiceOne().equals(raetsel.getAnswer())) {
                        btnChoiceOne.setBackgroundColor(getResources().getColor(R.color.color21));
                    } else if (raetsel.getChoices().getChoiceTwo().equals(raetsel.getAnswer())) {
                        btnChoiceTwo.setBackgroundColor(getResources().getColor(R.color.color21));
                    } else if (raetsel.getChoices().getChoiceThree().equals(raetsel.getAnswer())) {
                        btnChoiceThree.setBackgroundColor(getResources().getColor(R.color.color21));
                    } else if (raetsel.getChoices().getChoiceFour().equals(raetsel.getAnswer())) {
                        btnChoiceFour.setBackgroundColor(getResources().getColor(R.color.color21));
                    }
                }

                break;
            case "Multiple Choice":
            case "multiple choice":
                Log.d(TAG, "onCreateView: multiple choice");
                if (raetsel.getImage().equals("null")) {
                    itemView = inflater.inflate(R.layout.fragment_raetsel_mc_withoutimage, container, false);
                } else {
                    itemView = inflater.inflate(R.layout.fragment_raetsel_mc, container, false);
                }

                this.btnChoiceOne = itemView.findViewById(R.id.btnChoiceOne);
                this.btnChoiceTwo = itemView.findViewById(R.id.btnChoiceTwo);
                this.btnChoiceThree = itemView.findViewById(R.id.btnChoiceThree);
                this.btnChoiceFour = itemView.findViewById(R.id.btnChoiceFour);

                btnChoiceOne.setText(raetsel.getChoices().getChoiceOne());
                btnChoiceTwo.setText(raetsel.getChoices().getChoiceTwo());
                btnChoiceThree.setText(raetsel.getChoices().getChoiceThree());
                btnChoiceFour.setText(raetsel.getChoices().getChoiceFour());

                btnChoiceOne.setOnClickListener(this);
                btnChoiceTwo.setOnClickListener(this);
                btnChoiceThree.setOnClickListener(this);
                btnChoiceFour.setOnClickListener(this);
                break;

        }

        this.tvQuestion = itemView.findViewById(R.id.tvQuestion);
        this.tvAnswer = itemView.findViewById(R.id.tvAnswer);
        this.tvType = itemView.findViewById(R.id.tvType);
        this.tvName = itemView.findViewById(R.id.tvName);

        tvType.setText(raetsel.getType());
        tvName.setText(raetsel.getName());

        tvQuestion.setText(raetsel.getQuestion());
        total_coins = itemView.findViewById(R.id.total_coins_text);
        total_coins.setText(Integer.toString(sHelper.getCoins("1")));
        imageRiddle = (SubsamplingScaleImageView) itemView.findViewById(R.id.image_riddle);
        imageRiddle.setImage(ImageSource.resource(getResources().getIdentifier(raetsel.getImage(), "drawable", getContext().getPackageName())));
        imageRiddle.setOnClickListener(this);
        return itemView;


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnChoiceOne :
                chosenAnswer =  btnChoiceOne.getText().toString();
                String answer1 = raetsel.getAnswer().toLowerCase();
                if (chosenAnswer.toLowerCase().equals(raetsel.getAnswer().toLowerCase()) || chosenAnswer.toLowerCase().equals(answer1)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Sehr gut!");
                    alertDialog.setMessage("Richtig! Du hast " + points + " Coins gewonnen!");
                    sHelper.updateCoins("1", String.valueOf(sHelper.getCoins("1") + Integer.parseInt(points)));


                    switch (raetsel.getCategory()) {
                        case "Allgemeines":
                            sHelper.updateRBAllgemeines(Integer.toString(position + 1), "true");
                            break;
                        case "Braunschweig":
                            sHelper.updateRBBraunschweig(Integer.toString(position + 1), "true");
                            break;
                        case "Geographie":
                            sHelper.updateRBGeographie(Integer.toString(position + 1), "true");
                            break;
                        case "SQL":
                            sHelper.updateRBInformatik(Integer.toString(position + 1), "true");
                            break;
                        case "Logik":
                            sHelper.updateRBLogik(Integer.toString(position + 1), "true");
                            break;
                        case "Mathematik":
                            sHelper.updateRBMathematik(Integer.toString(position + 1), "true");
                            break;
                        case "Sprachen":
                            sHelper.updateRBSprache(Integer.toString(position + 1), "true");
                            break;
                        default:
                            sHelper.updateRBAllgemeines(Integer.toString(position + 1), "false");
                            sHelper.updateRBBraunschweig(Integer.toString(position + 1), "false");
                            sHelper.updateRBGeographie(Integer.toString(position + 1), "false");
                            sHelper.updateRBInformatik(Integer.toString(position + 1), "false");
                            sHelper.updateRBLogik(Integer.toString(position + 1), "false");
                            sHelper.updateRBMathematik(Integer.toString(position + 1), "false");
                            sHelper.updateRBSprache(Integer.toString(position + 1), "false");
                            break;
                    }

                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    reload();
                                }
                            });
                    alertDialog.show();

                } else {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Falsche Antwort!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.btnChoiceTwo :
                chosenAnswer = btnChoiceTwo.getText().toString();
                String answer2 = raetsel.getAnswer().toLowerCase();
                if (chosenAnswer.toLowerCase().equals(raetsel.getAnswer().toLowerCase()) || chosenAnswer.toLowerCase().equals(answer2)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Sehr gut!");
                    alertDialog.setMessage("Richtig! Du hast " + points + " Coins gewonnen!");
                    sHelper.updateCoins("1", String.valueOf(sHelper.getCoins("1") + Integer.parseInt(points)));

                    switch (raetsel.getCategory()) {
                        case "Allgemeines":
                            sHelper.updateRBAllgemeines(Integer.toString(position + 1), "true");
                            break;
                        case "Braunschweig":
                            sHelper.updateRBBraunschweig(Integer.toString(position + 1), "true");
                            break;
                        case "Geographie":
                            sHelper.updateRBGeographie(Integer.toString(position + 1), "true");
                            break;
                        case "SQL":
                            sHelper.updateRBInformatik(Integer.toString(position + 1), "true");
                            break;
                        case "Logik":
                            sHelper.updateRBLogik(Integer.toString(position + 1), "true");
                            break;
                        case "Mathematik":
                            sHelper.updateRBMathematik(Integer.toString(position + 1), "true");
                            break;
                        case "Sprachen":
                            sHelper.updateRBSprache(Integer.toString(position + 1), "true");
                            break;
                        default:
                            sHelper.updateRBAllgemeines(Integer.toString(position + 1), "false");
                            sHelper.updateRBBraunschweig(Integer.toString(position + 1), "false");
                            sHelper.updateRBGeographie(Integer.toString(position + 1), "false");
                            sHelper.updateRBInformatik(Integer.toString(position + 1), "false");
                            sHelper.updateRBLogik(Integer.toString(position + 1), "false");
                            sHelper.updateRBMathematik(Integer.toString(position + 1), "false");
                            sHelper.updateRBSprache(Integer.toString(position + 1), "false");
                            break;
                    }

                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    reload();
                                }
                            });
                    alertDialog.show();

                } else {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Falsche Antwort!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.btnChoiceThree :
                chosenAnswer = btnChoiceThree.getText().toString();
                String answer3 = raetsel.getAnswer().toLowerCase();
                if (chosenAnswer.toLowerCase().equals(raetsel.getAnswer().toLowerCase()) || chosenAnswer.toLowerCase().equals(answer3)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Sehr gut!");
                    alertDialog.setMessage("Richtig! Du hast " + points + " Coins gewonnen!");
                    sHelper.updateCoins("1", String.valueOf(sHelper.getCoins("1") + Integer.parseInt(points)));

                    switch (raetsel.getCategory()) {
                        case "Allgemeines":
                            sHelper.updateRBAllgemeines(Integer.toString(position + 1), "true");
                            break;
                        case "Braunschweig":
                            sHelper.updateRBBraunschweig(Integer.toString(position + 1), "true");
                            break;
                        case "Geographie":
                            sHelper.updateRBGeographie(Integer.toString(position + 1), "true");
                            break;
                        case "SQL":
                            sHelper.updateRBInformatik(Integer.toString(position + 1), "true");
                            break;
                        case "Logik":
                            sHelper.updateRBLogik(Integer.toString(position + 1), "true");
                            break;
                        case "Mathematik":
                            sHelper.updateRBMathematik(Integer.toString(position + 1), "true");
                            break;
                        case "Sprachen":
                            sHelper.updateRBSprache(Integer.toString(position + 1), "true");
                            break;
                        default:
                            sHelper.updateRBAllgemeines(Integer.toString(position + 1), "false");
                            sHelper.updateRBBraunschweig(Integer.toString(position + 1), "false");
                            sHelper.updateRBGeographie(Integer.toString(position + 1), "false");
                            sHelper.updateRBInformatik(Integer.toString(position + 1), "false");
                            sHelper.updateRBLogik(Integer.toString(position + 1), "false");
                            sHelper.updateRBMathematik(Integer.toString(position + 1), "false");
                            sHelper.updateRBSprache(Integer.toString(position + 1), "false");
                            break;
                    }

                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    reload();
                                }
                            });
                    alertDialog.show();

                } else {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Falsche Antwort!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.btnChoiceFour :
                chosenAnswer = btnChoiceFour.getText().toString();
                String answer4 = raetsel.getAnswer().toLowerCase();
                if (chosenAnswer.toLowerCase().equals(raetsel.getAnswer().toLowerCase()) || chosenAnswer.toLowerCase().equals(answer4)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Sehr gut!");
                    alertDialog.setMessage("Richtig! Du hast " + points + " Coins gewonnen!");
                    sHelper.updateCoins("1", String.valueOf(sHelper.getCoins("1") + Integer.parseInt(points)));

                    switch (raetsel.getCategory()) {
                        case "Allgemeines":
                            sHelper.updateRBAllgemeines(Integer.toString(position + 1), "true");
                            break;
                        case "Braunschweig":
                            sHelper.updateRBBraunschweig(Integer.toString(position + 1), "true");
                            break;
                        case "Geographie":
                            sHelper.updateRBGeographie(Integer.toString(position + 1), "true");
                            break;
                        case "SQL":
                            sHelper.updateRBInformatik(Integer.toString(position + 1), "true");
                            break;
                        case "Logik":
                            sHelper.updateRBLogik(Integer.toString(position + 1), "true");
                            break;
                        case "Mathematik":
                            sHelper.updateRBMathematik(Integer.toString(position + 1), "true");
                            break;
                        case "Sprachen":
                            sHelper.updateRBSprache(Integer.toString(position + 1), "true");
                            break;
                        default:
                            sHelper.updateRBAllgemeines(Integer.toString(position + 1), "false");
                            sHelper.updateRBBraunschweig(Integer.toString(position + 1), "false");
                            sHelper.updateRBGeographie(Integer.toString(position + 1), "false");
                            sHelper.updateRBInformatik(Integer.toString(position + 1), "false");
                            sHelper.updateRBLogik(Integer.toString(position + 1), "false");
                            sHelper.updateRBMathematik(Integer.toString(position + 1), "false");
                            sHelper.updateRBSprache(Integer.toString(position + 1), "false");
                            break;
                    }

                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    reload();
                                }
                            });
                    alertDialog.show();

                } else {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Falsche Antwort!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.floatingActionButton :
                chosenAnswer = input.getText().toString();
                String answer = raetsel.getAnswer().toLowerCase();
                Log.d(TAG, "REPLACE" + answer);
                pattern = Pattern.compile(answer);
                Log.d(TAG, "onClick: a" + answer);
                Log.d(TAG, "onClick: aa" + input.getText().toString() + " " + input.getText().toString().toLowerCase() + " " + raetsel.getType());
                matcher1 = pattern.matcher(input.getText().toString());
                Log.d(TAG, "REPLACE" + answer);
                if (matcher1.find()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Sehr gut!");
                    alertDialog.setMessage("Richtig! Du hast " + points + " Coins gewonnen!");
                    sHelper.updateCoins("1", String.valueOf(sHelper.getCoins("1") + Integer.parseInt(points)));

                    switch (raetsel.getCategory()) {
                        case "Allgemeines":
                            sHelper.updateRBAllgemeines(Integer.toString(position + 1), "true");
                            break;
                        case "Braunschweig":
                            sHelper.updateRBBraunschweig(Integer.toString(position + 1), "true");
                            break;
                        case "Geographie":
                            sHelper.updateRBGeographie(Integer.toString(position + 1), "true");
                            break;
                        case "SQL":
                            sHelper.updateRBInformatik(Integer.toString(position + 1), "true");
                            break;
                        case "Logik":
                            sHelper.updateRBLogik(Integer.toString(position + 1), "true");
                            break;
                        case "Mathematik":
                            sHelper.updateRBMathematik(Integer.toString(position + 1), "true");
                            break;
                        case "Sprachen":
                            sHelper.updateRBSprache(Integer.toString(position + 1), "true");
                            break;
                        default:
                            sHelper.updateRBAllgemeines(Integer.toString(position + 1), "false");
                            sHelper.updateRBBraunschweig(Integer.toString(position + 1), "false");
                            sHelper.updateRBGeographie(Integer.toString(position + 1), "false");
                            sHelper.updateRBInformatik(Integer.toString(position + 1), "false");
                            sHelper.updateRBLogik(Integer.toString(position + 1), "false");
                            sHelper.updateRBMathematik(Integer.toString(position + 1), "false");
                            sHelper.updateRBSprache(Integer.toString(position + 1), "false");
                            break;
                    }

                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    reload();
                                }
                            });
                    alertDialog.show();

                } else {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Falsche Antwort!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.image_riddle :
                Intent intent = new Intent(v.getContext(), ImageFullscreen.class);
                intent.putExtra("image", getResources().getIdentifier(raetsel.getImage(), "drawable", getContext().getPackageName()));
                startActivity(intent);
                break;

            default:
               throw new IllegalStateException("Halloooooooooo");

        }
    }
    public void reload() {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }
}
