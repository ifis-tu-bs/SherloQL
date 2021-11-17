/*package de.sep.sherloql.bin;


public class PinboardListAdapter {



}*/

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

public class PinboardListAdapter extends RecyclerView.Adapter<PinboardListAdapter.PinboardViewHolder>{

    private static final String TAG = "PinboardListAdapter";
    private ArrayList<Pinboardobjekt> pinboardArrayList;
    private Context context;

    public PinboardListAdapter(Context context, ArrayList<Pinboardobjekt> pinboardArrayList)  {
        Log.d(TAG, "PinboardListAdapter: starts");
        this.context = context;
        this.pinboardArrayList = pinboardArrayList;
    }

    @Override
    public PinboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: starts");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pinboarditemlist, parent, false);
        Log.d(TAG, "onCreateViewHolder: starts");
        PinboardViewHolder viewHolder = new PinboardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PinboardViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: starts");

        holder.performPinboardList(pinboardArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        if(pinboardArrayList.size() != 0){
            return pinboardArrayList.size();
        }
        return 0;
    }

    public Pinboardobjekt getPinboardobjekt(int position) {
        return ((pinboardArrayList != null) && (pinboardArrayList.size() !=0) ? pinboardArrayList.get(position) : null);
    }


    public class PinboardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        View seperator;
        View viewPinboard;
        TextView pinName;
        RelativeLayout relativeLayoutPin;

        public PinboardViewHolder(View itemView) {
            super(itemView);
            seperator = itemView.findViewById(R.id.viewPinSeperator);
            viewPinboard = itemView.findViewById(R.id.viewPinboard);
            pinName = itemView.findViewById(R.id.tvPinName);
            relativeLayoutPin = itemView.findViewById(R.id.relativeLayoutPin);
            itemView.setOnClickListener(this);
        }

        public void performPinboardList(Pinboardobjekt pinboardobjekt) {
            Log.d(TAG, "performPinboardList: starts");
            pinName.setText(pinboardobjekt.getName());
            Log.d(TAG, "performPinboardList: ends");
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Log.d(TAG, "onClick: starts");

            Bundle extra = new Bundle();

            extra.putSerializable("POSITION", itemPosition);
            extra.putSerializable("PINBOARDOBJEKT" ,getPinboardobjekt(itemPosition));
            extra.putSerializable("ARRAY", pinboardArrayList);


            Intent intent = new Intent(context, ItemviewinActivity.class);
            intent.putExtra("extra", extra);

            Log.d(TAG, "onClick: ends");
            context.startActivity(intent);
        }
    }
}

