package de.sep.sherloql.story;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import de.sep.sherloql.R;
import de.sep.sherloql.bin.Database;
import de.sep.sherloql.bin.Map;
import de.sep.sherloql.database.database_activitys.ActivityQuery;
import de.sep.sherloql.savestate.SaveStateHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DialogueFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @author JudyAlchaar
 */
public class DialogueFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "DialogeuFragment";
    private ArrayList<Dialogue> dialogue;
    private int index;
    private Chapter chapter;
    private SaveStateHelper sHelper;
    TextView incomingText;
    TextView name;
    Button nextButton;
    Button prevButton;
    View itemView;
    ImageView personImage;
    ScrollView sv;


/*    public DialogueFragment() {
        // Required empty public constructor
    }*/

    public static DialogueFragment newInstance(ArrayList<Dialogue> dialogue, int index, Chapter chapter) {
        DialogueFragment fragment = new DialogueFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("DIALOGUE", dialogue);
        bundle.putSerializable("INDEX", index);
        bundle.putSerializable("CHAPTER", chapter);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogue = (ArrayList<Dialogue>) getArguments().getSerializable("DIALOGUE");
        index = (int) getArguments().getSerializable("INDEX");
        chapter = (Chapter) getArguments().getSerializable("CHAPTER");
        sHelper = new SaveStateHelper(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        itemView = inflater.inflate(R.layout.fragment_dialogue, container, false);
        incomingText = itemView.findViewById(R.id.text);
        nextButton = itemView.findViewById(R.id.next_button);
        prevButton = itemView.findViewById(R.id.prev_button);
        personImage = itemView.findViewById(R.id.imageView6);
        int position = dialogue.get(0).getPosition() % 100;
        int beforeOrAfter = (dialogue.get(0).getPosition() - position) / 100;

        itemView.setBackgroundResource(chapter.getDialogueImage()); //TODO: neue Story Datentruktur
        if (beforeOrAfter == 1 && index < dialogue.size() && index >= 0) {
            personImage.setBackgroundResource(chapter.getDialogueBefore(index).getImage());
        } else if (beforeOrAfter == 2 && index < dialogue.size() && index >= 0) {
            personImage.setBackgroundResource(chapter.getDialogueAfter(index).getImage());
        }
        name = itemView.findViewById(R.id.name);

        if (index < dialogue.size()) {
            if (!dialogue.get(index).getPerson().equals("Ohne")) {
                name.setText(dialogue.get(index).getPerson());
            }
            String user = sHelper.getUsername("1");
            Log.d(TAG, "name dia: " + user);
            String dia = dialogue.get(index).getText().replace("{$USER$}", user);
                incomingText.setText(dia);

        }

        nextButton.setHeight(incomingText.getMeasuredHeight());
        Log.d(TAG, "size1:" + nextButton.getMeasuredHeight());
        Log.d(TAG, "size1:" + incomingText.getMeasuredHeight());
        prevButton.setHeight(incomingText.getMeasuredHeight());
        Log.d(TAG, "size1:" + prevButton.getMeasuredHeight());
        Log.d(TAG, "size1:" + incomingText.getMeasuredHeight());
        Log.d(TAG, "RaetselListAdapter: starts");
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);


        return itemView;
    }

    @Override
    public void onClick(View v) {
        int position = dialogue.get(0).getPosition() % 100;
        int beforeOrAfter = (dialogue.get(0).getPosition() - position) / 100;
        Log.d(TAG, "dialoooog position " + position);
        Log.d(TAG, "dialoooog beforeOrAfter " + beforeOrAfter);
        Log.d(TAG, "dialoooog size " + dialogue.size());
        switch (v.getId()) {
            case R.id.next_button:
                Log.d(TAG, "dialoooog " + index);
                index++;
                break;
            case R.id.prev_button:
                index--;
                Log.d(TAG, "dialoooog " + index);
                break;
        }

        if (index < 0) {
            Log.d(TAG, "dialoooog index < 0 ");
            index++;
        } else if (dialogue.size() > index) {
            Log.d(TAG, "dialoooog dialogue.size() > index ");
            DialogueFragment fragment = newInstance(dialogue, index, chapter);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_map_root, fragment).commit();

        }else if (beforeOrAfter == 1){
            Log.d(TAG, "dialoooog beforeOrAfter == 1 ");

            if (chapter.getFlag() == 2) {
                Intent intent = new Intent(v.getContext(), Map.class);
                startActivity(intent);
            } else {
                RiddleFragment fragment = RiddleFragment.newInstance(chapter);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_map_root, fragment).addToBackStack("").commit();
            }
            //}
        } else if (beforeOrAfter == 2) {
            Log.d(TAG, "dialoooog beforeOrAfter == 2 ");
            Intent intent = new Intent(v.getContext(), Map.class);
            startActivity(intent);
        }
    }
}
