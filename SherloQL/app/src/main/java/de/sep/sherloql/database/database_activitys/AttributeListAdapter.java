package de.sep.sherloql.database.database_activitys;

import android.content.Context;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;

import de.sep.sherloql.R;
/**
 * @author Matthias T
 * @author Erkan
 */
public class AttributeListAdapter extends ArrayAdapter<String> {
    private static final String TAG = "AttributeListAdapter";
    private Context mContext;
    int mRessource;
    private final LinkedList<String> list;


    public AttributeListAdapter(@NonNull Context context, int resource, LinkedList<String> attributes) {
        super(context, resource, attributes);
        mContext = context;
        mRessource = resource;
        list = attributes;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mRessource, parent, false);

        TextView attributeName = (TextView) convertView.findViewById(R.id.textView);
        attributeName.setText(list.get(position));

        EditText editName = (EditText) convertView.findViewById(R.id.editText);
        if(list.get(position).equals("geburtsdatum") || list.get(position).equals("orte")){
            editName.setHint("YYYYMMDD");
        }
        else if (list.get(position).equals("uhrzeit")) {
            editName.setHint("HHMM");
        }
        return convertView;
    }

}
