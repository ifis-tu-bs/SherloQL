package de.sep.sherloql.bin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import de.sep.sherloql.R;


public class TutorialListAdapter extends RecyclerView.Adapter<TutorialListAdapter.TutorialViewHolder>{

    private static final String TAG = "TutorialListAdapter";
    private ArrayList<Tutorialeinheit> tutorialeinheitArrayList = new ArrayList<>();
    private Context context;



    public TutorialListAdapter(Context context, ArrayList<Tutorialeinheit> tutorialeinheitArrayList)  {
        Log.d(TAG, "TutorialListAdapter: starts");
        this.context = context;
        this.tutorialeinheitArrayList = tutorialeinheitArrayList;
    }

    @Override
    public TutorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: starts");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tutoriallistirem, parent, false);
        Log.d(TAG, "onCreateViewHolder: starts");
        TutorialViewHolder viewHolder = new TutorialViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TutorialViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: starts");

        holder.performTutorialList(tutorialeinheitArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        if(tutorialeinheitArrayList.size() != 0){
            return tutorialeinheitArrayList.size();
        }
        return Integer.parseInt(null);
    }

    public Tutorialeinheit getTutorialeinheit(int position) {
        return ((tutorialeinheitArrayList != null) && (tutorialeinheitArrayList.size() !=0) ? tutorialeinheitArrayList.get(position) : null);
    }


    public class TutorialViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        View seperator;
        View viewTutorial;
        TextView tutName;
        TextView tutThema;
        TextView tutAbKapText;
        TextView tutAbKap;
        RelativeLayout relativeLayoutTut;

        public TutorialViewHolder(View itemView) {
            super(itemView);
            seperator = itemView.findViewById(R.id.viewTutSeperator);
            viewTutorial = itemView.findViewById(R.id.viewTutorial);
            tutName = (TextView) itemView.findViewById(R.id.tvTutName);
            tutThema = (TextView) itemView.findViewById(R.id.tvThemaEinheit);
            tutAbKap = (TextView) itemView.findViewById(R.id.tvAbKapitel);
            tutAbKapText = (TextView) itemView.findViewById(R.id.tvAbKapitelText);
            relativeLayoutTut = itemView.findViewById(R.id.relativeLayoutTut);
            itemView.setOnClickListener(this);
        }

        public void performTutorialList(Tutorialeinheit tutorialeinheit) {
            Log.d(TAG, "performTutorialList: starts");
            tutName.setText(tutorialeinheit.getName());
            tutThema.setText(tutorialeinheit.getThema());
            tutAbKap.setText(" " + tutorialeinheit.getBenoetigtFuerKapitel());
            tutAbKapText.setText(tutorialeinheit.getBenoetigtFuerKapitelText());
            Log.d(TAG, "performTutorialList: ends");
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
                Log.d(TAG, "onClick: starts");

                Bundle extra = new Bundle();

                extra.putSerializable("POSITION", itemPosition);
                extra.putSerializable("TUTORIALEINHEIT" ,getTutorialeinheit(itemPosition));
                extra.putSerializable("ARRAY", tutorialeinheitArrayList);


                Intent intent = new Intent(context, TutorialActivity.class);
                intent.putExtra("extra", extra);

                Log.d(TAG, "onClick: ends");
                context.startActivity(intent);
        }
    }
}
