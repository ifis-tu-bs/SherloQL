package de.sep.sherloql.story;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.sep.sherloql.R;
import de.sep.sherloql.bin.Map;
import de.sep.sherloql.bin.Pinboard;
import de.sep.sherloql.database.database_activitys.ActivityDelete;
import de.sep.sherloql.database.database_activitys.ActivityQuery;
import de.sep.sherloql.savestate.SaveStateHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RiddleFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @author JudyAlchaar
 */
public class RiddleFragment extends Fragment implements View.OnClickListener, Serializable {

    private static final String TAG = "RiddleFragment";
    private Chapter chapter;
    private TextView tvQuestion;
    private TextView tvAnswer;
    private EditText input;
    private Button btn;
    private SaveStateHelper sHelper;
    private ImageButton hint_icon;
    private SubsamplingScaleImageView imageRiddle;
    private ImageView total_coins_image;
    private int i = 0;
    private int j = 0;
    private TextView total_coins;
    private String type;
    private Button backToMap;
    private Matcher matcher;
    private Pattern pattern;
    private Button database;
    private Button pinboard;

    public RiddleFragment() {

    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param chapter
     * @return A new instance of fragment RiddleFragment.
     */
    public static RiddleFragment newInstance(Chapter chapter) {
        RiddleFragment fragment = new RiddleFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("CHAPTER", chapter);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chapter = (Chapter) getArguments().getSerializable("CHAPTER");
        sHelper = new SaveStateHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (sHelper.getStorySolved(chapter.getName())) {
            if (chapter.getRiddle().getType().toLowerCase().equals("answer")) {
                Log.d(TAG, "Type:" + chapter.getRiddle().getType());
                type = chapter.getRiddle().getType().toLowerCase();
                chapter.getRiddle().setType("solved");
            }

        }
        View itemView;

        switch (chapter.getRiddle().getType()) {
            case "solved":
                if (chapter.getRiddle().getImage() == 0)  {
                    itemView = inflater.inflate(R.layout.fragment_raetsel_input_withoutimage_story, container, false);
                } else {
                    itemView = inflater.inflate(R.layout.fragment_raetsel_input_story, container, false);
                }
                this.tvQuestion = itemView.findViewById(R.id.tvQuestion);
                tvQuestion.setText(chapter.getRiddle().getQuestion());
                imageRiddle = (SubsamplingScaleImageView) itemView.findViewById(R.id.image_riddle);
                imageRiddle.setImage(ImageSource.resource(chapter.getRiddle().getImage()));
                imageRiddle.setOnClickListener(this);
                input = itemView.findViewById(R.id.simpleEditText);
                if (chapter.getName().equals("Kapitel 0")) {
                    input.setText(sHelper.getUsername("1"));
                } else {
                    input.setText(chapter.getRiddle().getAnswer());
                }
                input.setKeyListener(null);
                btn =  itemView.findViewById(R.id.floatingActionButton);
                btn.setVisibility(View.INVISIBLE);
                total_coins = itemView.findViewById(R.id.total_coins_text);
                total_coins.setText(Integer.toString(sHelper.getCoins("1")));
                backToMap = (Button) itemView.findViewById(R.id.backToMap);
                backToMap.setVisibility(View.VISIBLE);
                backToMap.setOnClickListener(this);
                total_coins_image = itemView.findViewById(R.id.total_coins);
                total_coins_image.setImageResource(R.drawable.total_coins);
                pinboard = (Button) itemView.findViewById(R.id.pinboard);
                pinboard.setVisibility(View.INVISIBLE);
                pinboard.setOnClickListener(this);

                break;
            case "Answer":
            case "answer":
                Log.d(TAG, "onCreateView: input");
                if (chapter.getRiddle().getImage() == 0)  {
                    itemView = inflater.inflate(R.layout.fragment_raetsel_input_withoutimage_story, container, false);
                } else {
                    itemView = inflater.inflate(R.layout.fragment_raetsel_input_story, container, false);
                }
                this.tvQuestion = (TextView) itemView.findViewById(R.id.tvQuestion);
                tvQuestion.setText(chapter.getRiddle().getQuestion());
                hint_icon = (ImageButton) itemView.findViewById(R.id.hint_icon);
                hint_icon.setVisibility(View.VISIBLE);
                hint_icon.setImageResource(R.drawable.hint_icon);
                hint_icon.setOnClickListener(this);
                imageRiddle = (SubsamplingScaleImageView) itemView.findViewById(R.id.image_riddle);
                imageRiddle.setImage(ImageSource.resource(chapter.getRiddle().getImage()));
                imageRiddle.setOnClickListener(this);
                input = itemView.findViewById(R.id.simpleEditText);
                btn = itemView.findViewById(R.id.floatingActionButton);
                btn.setOnClickListener(this);
                backToMap = (Button) itemView.findViewById(R.id.backToMap);
                backToMap.setVisibility(View.VISIBLE);
                backToMap.setOnClickListener(this);
                total_coins_image = itemView.findViewById(R.id.total_coins);
                total_coins_image.setImageResource(R.drawable.total_coins);
                total_coins = itemView.findViewById(R.id.total_coins_text);
                total_coins.setText(Integer.toString(sHelper.getCoins("1")));
                pinboard = (Button) itemView.findViewById(R.id.pinboard);
                pinboard.setVisibility(View.VISIBLE);
                pinboard.setOnClickListener(this);
                break;
            case "SQL":
            case "sql":
                Log.d(TAG, "onCreateView: sql");

                itemView = inflater.inflate(R.layout.fragment_sql_input, container, false);
                this.tvQuestion = (TextView) itemView.findViewById(R.id.tvQuestion);
                tvQuestion.setText(chapter.getRiddle().getQuestion());
                hint_icon = (ImageButton) itemView.findViewById(R.id.hint_icon);
                hint_icon.setVisibility(View.VISIBLE);
                hint_icon.setImageResource(R.drawable.hint_icon);
                hint_icon.setOnClickListener(this);
                btn = itemView.findViewById(R.id.floatingActionButton);
                btn.setOnClickListener(this);
                backToMap = (Button) itemView.findViewById(R.id.backToMap);
                backToMap.setVisibility(View.VISIBLE);
                backToMap.setOnClickListener(this);
                total_coins_image = itemView.findViewById(R.id.total_coins);
                total_coins_image.setImageResource(R.drawable.total_coins);
                total_coins = itemView.findViewById(R.id.total_coins_text);
                total_coins.setText(Integer.toString(sHelper.getCoins("1")));
                pinboard = (Button) itemView.findViewById(R.id.pinboard);
                pinboard.setVisibility(View.VISIBLE);
                pinboard.setOnClickListener(this);
                database = (Button) itemView.findViewById(R.id.db);
                database.setVisibility(View.VISIBLE);
                database.setOnClickListener(this);
                break;
            case "SQL-delete":
            case "sql-delete":
                itemView = inflater.inflate(R.layout.fragment_sql_input, container, false);
                this.tvQuestion = (TextView) itemView.findViewById(R.id.tvQuestion);
                tvQuestion.setText(chapter.getRiddle().getQuestion());
                hint_icon = (ImageButton) itemView.findViewById(R.id.hint_icon);
                hint_icon.setVisibility(View.VISIBLE);
                hint_icon.setImageResource(R.drawable.hint_icon);
                hint_icon.setOnClickListener(this);
                btn = itemView.findViewById(R.id.floatingActionButton);
                btn.setOnClickListener(this);
                backToMap = (Button) itemView.findViewById(R.id.backToMap);
                backToMap.setVisibility(View.VISIBLE);
                backToMap.setOnClickListener(this);
                total_coins_image = itemView.findViewById(R.id.total_coins);
                total_coins_image.setImageResource(R.drawable.total_coins);
                total_coins = itemView.findViewById(R.id.total_coins_text);
                total_coins.setText(Integer.toString(sHelper.getCoins("1")));
                pinboard = (Button) itemView.findViewById(R.id.pinboard);
                pinboard.setVisibility(View.VISIBLE);
                pinboard.setOnClickListener(this);
                database = (Button) itemView.findViewById(R.id.db);
                database.setVisibility(View.VISIBLE);
                database.setOnClickListener(this);
                break;
            default:
                itemView = inflater.inflate(R.layout.fragment_raetsel_input_story, container, false);
                this.tvQuestion = itemView.findViewById(R.id.tvQuestion);
                tvQuestion.setText(chapter.getRiddle().getQuestion());
                imageRiddle = (SubsamplingScaleImageView) itemView.findViewById(R.id.image_riddle);
                imageRiddle.setImage(ImageSource.resource(chapter.getRiddle().getImage()));
                imageRiddle.setOnClickListener(this);
                input = itemView.findViewById(R.id.simpleEditText);
                input.setText(chapter.getRiddle().getAnswer());
                input.setKeyListener(null);
                btn =  itemView.findViewById(R.id.floatingActionButton);
                btn.setVisibility(View.INVISIBLE);
                total_coins = itemView.findViewById(R.id.total_coins_text);
                total_coins.setText(Integer.toString(sHelper.getCoins("1")));
                backToMap = (Button) itemView.findViewById(R.id.backToMap);
                backToMap.setVisibility(View.VISIBLE);
                backToMap.setOnClickListener(this);
                total_coins_image = itemView.findViewById(R.id.total_coins);
                total_coins_image.setImageResource(R.drawable.total_coins);
                pinboard = (Button) itemView.findViewById(R.id.pinboard);
                pinboard.setVisibility(View.INVISIBLE);
                pinboard.setOnClickListener(this);

                break;
        }

        return itemView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.floatingActionButton:
                if (chapter.getRiddle().getType().equals("sql") || chapter.getRiddle().getType().equals("sql-delete")) {
                    if (sHelper.getStorySolved(chapter.getName())) {
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle(sHelper.getLanguage() == 0 ? "Sehr gut!": "Great!");
                        LayoutInflater factory = LayoutInflater.from(getActivity());
                        final View view = factory.inflate(R.layout.alert_dialog_raetsel, null);
                        alertDialog.setView(view);
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
                                sHelper.getLanguage() == 0 ? "Weiter" : "Next",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (!chapter.getDialogueArrayAfter().isEmpty()) {
                                            DialogueFragment fragment = DialogueFragment.newInstance(chapter.getDialogueArrayAfter(), 0, chapter);
                                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                            transaction.replace(R.id.activity_map_root, fragment).commit();
                                        } else {
                                            Intent intent = new Intent(v.getContext(), Map.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                        alertDialog.show();
                    } else {
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                sHelper.getLanguage() == 0 ? "Falsche Antwort": "Wrong answer",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    String answer = chapter.getRiddle().getAnswer().toLowerCase();
                    if (chapter.getName().equals("Kapitel 0")) {
                        if (input.getText().toString().equals("")) {
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                    sHelper.getLanguage() == 0 ? "Ungültiger Name": "Invalid " +
                                            "name",
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            sHelper.updateUsername("1", input.getText().toString());
                            Log.d(TAG, "name: " + input.getText().toString());
                            sHelper.updateStory(chapter.getName(), "true", "true");
                            for (int i = 0; i < chapter.getArtefacts().size(); i++) { //Artefacts
                                Log.d(TAG, "riddle huihuihui: "+ chapter.getArtefacts().get(i) + ", chapter " + chapter.getName());
                                String check = "!" + chapter.getArtefacts().get(i);
                                if (sHelper.checkArtefactExists(check)) {
                                    sHelper.updateArtefactUnlocked(check, "false");
                                }
                                sHelper.updateArtefactUnlocked(chapter.getArtefacts().get(i), "true");
                            }
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setTitle(sHelper.getLanguage() == 0 ? "Sehr gut!": "Great!");
                            LayoutInflater factory = LayoutInflater.from(getActivity());
                            final View view = factory.inflate(R.layout.alert_dialog_raetsel, null);
                            alertDialog.setView(view);
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
                                    sHelper.getLanguage() == 0 ? "Weiter": "Next",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (!chapter.getDialogueArrayAfter().isEmpty()) {
                                                DialogueFragment fragment = DialogueFragment.newInstance(chapter.getDialogueArrayAfter(), 0, chapter);
                                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                transaction.replace(R.id.activity_map_root, fragment).commit();
                                            } else {
                                                Intent intent = new Intent(v.getContext(), Map.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                            alertDialog.show();
                        }
                    } else {
                        pattern = Pattern.compile(answer);
                        matcher = pattern.matcher(input.getText().toString().toLowerCase());
                        if (matcher.find()) {
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setTitle(sHelper.getLanguage() == 0 ? "Sehr gut!": "Great!");
                            LayoutInflater factory = LayoutInflater.from(getActivity());
                            final View view = factory.inflate(R.layout.alert_dialog_raetsel, null);
                            alertDialog.setView(view);

                            sHelper.updateStory(chapter.getName(), "true", "true");
                            for (int i = 0; i < chapter.getArtefacts().size(); i++) { //Artefacts
                                Log.d(TAG, "riddle huihuihui: "+ chapter.getArtefacts().get(i) + ", chapter " + chapter.getName());
                                String check = "!" + chapter.getArtefacts().get(i);
                                if (sHelper.checkArtefactExists(check)) {
                                    sHelper.updateArtefactUnlocked(check, "false");
                                }
                                sHelper.updateArtefactUnlocked(chapter.getArtefacts().get(i), "true");
                            }

                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
                                    sHelper.getLanguage() == 0 ? "Weiter" : "Next",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (!chapter.getDialogueArrayAfter().isEmpty()) {
                                                DialogueFragment fragment = DialogueFragment.newInstance(chapter.getDialogueArrayAfter(), 0, chapter);
                                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                transaction.replace(R.id.activity_map_root, fragment).commit();
                                            } else {
                                                Intent intent = new Intent(v.getContext(), Map.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                            alertDialog.show();
                        } else {
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                    sHelper.getLanguage() == 0 ? "Falsche Antwort": "Wrong answer",
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }
                break;
            /**case R.id.button:
                Intent intent = new Intent(v.getContext(), ActivityQuery.class);
                intent.putExtra("chapter", chapter.getName());
                startActivity(intent);
                break;*/
            case R.id.pinboard:
                Intent intent = new Intent(v.getContext(), Pinboard.class);
                startActivity(intent);
                break;
            case R.id.db:
                Intent intent2;
                if (chapter.getRiddle().getType().equals("sql-delete")) {
                    intent2 = new Intent(v.getContext(), ActivityDelete.class);
                } else {
                    intent2 = new Intent(v.getContext(), ActivityQuery.class);
                }
                intent2.putExtra("chapter", 0);
                intent2.putExtra("name", chapter.getName());
                startActivity(intent2);
                break;
            case R.id.backToMap:
                getActivity().onBackPressed();
                break;
            case R.id.hint_icon:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        getContext());
                alertDialog.setTitle("Hinweis");
                if (i < chapter.getRiddle().getHints().size()) {
                    if (!sHelper.getHintUsed(String.valueOf(i), String.valueOf(chapter.getName()))) {
                        if(sHelper.getLanguage() == 0) {
                            alertDialog.setMessage("Du hast noch " +
                                    (chapter.getRiddle().getHints().size() - j) +" Hinweis(e) zur " +
                                    "Verfügung. Möchtest du ein Hinweis für " +
                                    chapter.getRiddle().getHint(i).getPrice() + " Coin(s) kaufen?");
                        } else {
                            alertDialog.setMessage("You still have " +
                                    (chapter.getRiddle().getHints().size() - j) +" hint(s) " +
                                    "available. Do you want to buy one for " +
                                    chapter.getRiddle().getHint(i).getPrice() + " Coin(s)?");
                        }


                        alertDialog.setPositiveButton(sHelper.getLanguage() == 0 ? "Hinweis " +
                                        "kaufen": "Buy hint",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which1) {
                                        dialog.dismiss();
                                        if (i < chapter.getRiddle().getHints().size()) {
                                            if (sHelper.getCoins("1") - chapter.getRiddle().getHint(i).getPrice() >= 0) {
                                                alertDialog.setMessage(chapter.getRiddle().getHint(i).getHint());
                                                AlertDialog alert = alertDialog.create();
                                                alert.show();
                                                alert.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.INVISIBLE);
                                                alert.show();
                                                sHelper.updateHints(String.valueOf(i), "true", String.valueOf(chapter.getName()));
                                                sHelper.updateCoins("1", Integer.toString(sHelper.getCoins("1") - chapter.getRiddle().getHint(i).getPrice()));
                                                i++;
                                                j++;
                                            } else {
                                                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                                        sHelper.getLanguage() == 0 ?
                                                                "Du hast nicht genügend Coins!":
                                                        "You have not enough Coins!",
                                                        Toast.LENGTH_SHORT);
                                                toast.show();
                                            }
                                        }
                                    }
                                });

                        alertDialog.setNegativeButton(sHelper.getLanguage() == 0 ? "Zurück":
                                        "Back",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which2) {
                                        dialog.dismiss();
                                        reload();
                                    }
                                });
                        alertDialog.show();
                    } else {

                    }
                } else {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                            sHelper.getLanguage() == 0 ?
                                    "Es gibt keine Hinweise mehr":
                            "There are no hints left to buy", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.image_riddle:
                Intent intent3 = new Intent(v.getContext(), ImageFullscreen.class);
                intent3.putExtra("image", chapter.getRiddle().getImage());
                startActivity(intent3);
                break;
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
