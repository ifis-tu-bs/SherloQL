package de.sep.sherloql.uiraetsel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.sep.sherloql.R;
import de.sep.sherloql.savestate.SaveStateHelper;


/**
 * Mit Hilfe von RaetselListAdapter Klasse, ist eine Liste von Raetseln ins App gezeigt.
 *
 *
 *
 *In Android werden Datensammlungen mit Hilfe von Adaptern an View-Elemente
 *der grafischen Benutzeroberfläche gebunden.
 *Die Adapter fungieren dann als Bindeglied zwischen Datenquelle und View-Element
 *
 *Ein AdapterView ist ein View-Element,
 *dessen Kind-Elemente (Child-Elements) von einem Adapter vorgegeben werden.
 *Ein AdapterView wird über einen Adapter mit einer Datenquelle verbunden.
 *Über den Adapter erhält der AdapterView Zugang zu den Elementen der Datenquelle.
 */
class RaetselListAdapter extends RecyclerView.Adapter<RaetselListAdapter.RaetselViewHolder> {
    // Attribute deklariert.
    private static final String TAG = "RaetselListAdapter";
    private ArrayList<Raetsel> raetselArrayList;
    private Context context;
    SaveStateHelper stateHelper;

    /**
     * Konstruktor von Klasse RaetselListAdapter
     * @param context
     * @param raetselArrayList
     */
    public RaetselListAdapter( Context context, ArrayList<Raetsel> raetselArrayList) {
        Log.d(TAG, "RaetselListAdapter: starts");
        this.context = context;
        this.raetselArrayList = raetselArrayList;
        stateHelper = new SaveStateHelper(this.context);
    }

    @Override
    /**
     * ViewHolder wird erstellt.
     */
    public RaetselViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d(TAG, "onCreateViewHolder: starts");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_raetsel, parent, false);
        Log.d(TAG, "onCreateViewHolder: starts");
        RaetselViewHolder viewHolder = new RaetselViewHolder(view);
        return viewHolder;
    }

    @Override
    /**
     * Die Liste von Raetseln auf ViewHolder umsetzen.
     */
    public void onBindViewHolder(RaetselViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: starts");  

        holder.performRaetselList(raetselArrayList.get(position));
    }

    @Override
    /**
     * Gibt die Länge v
     * on der ArrayList
     */
    public int getItemCount() {

        return raetselArrayList.size();
    }
    /**
     * Gibt die ausgewählte Raetsel zurück
     * @param position
     * @return
     */
    public Raetsel getRaetsel(int position) {
        return ((raetselArrayList != null) && (raetselArrayList.size() !=0) ? raetselArrayList.get(position) : null);
    }

    /**
     * Besondere ViewHolder für die Raetseln.
     */
    public class RaetselViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Attribute deklariert.
        private static final String TAG = "RaetselViewHolder";
        private TextView tvType;
        private TextView tvName;
        private TextView tvCategory;
        private TextView tvDifficulty;
        private TextView tvPoints;


        /**
         * Konstruktor von Klasse RaetselViewHolder
         * @param itemView
         */
        public RaetselViewHolder(View itemView) {
            super(itemView);

            Log.d(TAG, "RaetselViewHolder: starts");

            this.tvType = (TextView) itemView.findViewById(R.id.tvType);
            this.tvName = (TextView) itemView.findViewById(R.id.tvName);
            //this.tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            this.tvDifficulty = (TextView) itemView.findViewById(R.id.tvDifficulty);
            this.tvPoints = (TextView) itemView.findViewById(R.id.tvPoints);

            context = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        /**
         * Gelesene Werte werden ins Layout umgesetzt.
         * @param raetsel
         */
        public void performRaetselList(Raetsel raetsel) {
            Log.d(TAG, "performRaetselList: starts");
            tvType.setText(raetsel.getType());
            tvName.setText(raetsel.getName());
            tvDifficulty.setText(raetsel.getDifficulty());
            tvPoints.setText(raetsel.getPoints());
        }

        @Override
        /**
         * Hier werden benötigte Variablen zur ausgewählte Layout übergegeben, um da gezeigt zu werden.
         */
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Log.d(TAG, "onClick: starts");

            Bundle extra = new Bundle();
            extra.putSerializable("POSITION", itemPosition);
            extra.putSerializable("RAETSEL" ,getRaetsel(itemPosition));
            extra.putSerializable("ARRAY", raetselArrayList);

            Intent intent = new Intent(context, RaetselActivity.class);
            intent.putExtra("extra", extra);

            context.startActivity(intent);
            }
    }
}
