package de.sep.sherloql.story;

import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import de.sep.sherloql.R;

public class SpecialChapter extends Fragment implements View.OnClickListener, Serializable {

    private static final String TAG = "SpecialChapter";
    private ArrayList<Dialogue> dialogue;
    private int index;
    private Chapter chapter;
    private static Story story;
    private static ParseStory parseStory;
    private static ArrayList<Chapter> chapterArrayList;
    private static int length;
    TextView name;
    Button nextButton;
    Button prevButton;
    View itemView;
    ImageView personImage;
    ScrollView sv;
    public static Boolean Professor = false;
    public static Boolean Viktoria = false;
    public static Boolean Henrik = false;
    public static Boolean Psycho = false;
    public static Boolean Moriarty = false;

    private boolean chapterFinished = false;
    private static SpecialChapter specialChapter = null;

    private SpecialChapter() { }

    //Singleton
    public static SpecialChapter getInstance() {
        if(specialChapter == null) {
            specialChapter = new SpecialChapter();
            Bundle bundle = new Bundle();
            specialChapter.setArguments(bundle);
        }
        return specialChapter;
    }

    public boolean isChapterFinished(){
        return chapterFinished;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_raetsel_special_chapter, container, false);
        if (parseStory == null) {
            parseStory = new ParseStory(getContext());
            story = parseStory.parse();
            length = parseStory.getItemCount();
            chapterArrayList = story.getChapterArrayList();
        }


        TextView tvHelpText = itemView.findViewById(R.id.tvHelpText);
        tvHelpText.setText(chapterArrayList.get(17).getRiddle().getQuestion());

        nextButton = itemView.findViewById(R.id.donezo);
        prevButton = itemView.findViewById(R.id.prev_button);
        personImage = itemView.findViewById(R.id.imageView6);
        nextButton.setOnClickListener(this);

        return itemView;
    }

    public static void Professor(){
        Professor = true;
    }
    public static void Yvonne(){
        Psycho = true;
    }
    public static void Rico(){
        Henrik = true;
    }
    public static void Victoria(){
        Viktoria = true;
    }
    public static void Moriarty(){
        Moriarty = true;
    }

    @Override
    public void onClick(View v) {
        EditText incomingText = itemView.findViewById(R.id.textEditGuess);
        CharSequence guess = incomingText.getText();
        String guessActual = guess.toString().toLowerCase();

        switch (guessActual) {
            case "professor":
            case "thomas":
            case "muench":
            case "münch":
            case "thomas muench":
            case "thomas münch":
            case "4":
                Professor();
                chapterFinished = true;
                break;

            case "yvonne":
            case "beyer":
            case "yvonne beyer":
            case "psychologin":
            case "714":
                Chapter chapter = chapterArrayList.get(2);
                Yvonne();
                chapterFinished = true;
                break;

            case "viktoria":
            case "kästner":
            case "kaestner":
            case "viktoria kästner":
            case "viktoria kaestner":
            case "6":
                Victoria();
                chapterFinished = true;
                break;

            case "riko":
            case "rico":
            case "henrik":
            case "weiß":
            case "henrik weiß":
            case "1"://id
                Rico();
                chapterFinished = true;
                break;

            case "moriarty":
            case "detektiv":
            case "detective":
            case "hannes":
            case "barton":
            case "hannes barton":
            case "42":
                Moriarty();
                chapterFinished = true;
                break;

            default:
                break;
        }
        getActivity().onBackPressed();

    }
}
