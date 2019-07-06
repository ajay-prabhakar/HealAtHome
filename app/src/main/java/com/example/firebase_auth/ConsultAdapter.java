package com.example.firebase_auth;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Created by Chromicle on 6/7/19.
 */
public class ConsultAdapter extends ArrayAdapter<Consult> {
    private Activity context;
    private List<Consult> consultList;


    public ConsultAdapter(@NonNull Activity context, List<Consult> consultList) {
        super(context, R.layout.main_list_layout, consultList);
        this.context = context;
        this.consultList = consultList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();


        View listViewItem = inflater.inflate(R.layout.main_list_layout,null,true);

        TextView fname = listViewItem.findViewById(R.id.tvSym);
        TextView fgenre = listViewItem.findViewById(R.id.tvTime);
        Consult consult = consultList.get(position);
        fname.setText(consult.getDiseses());
        fgenre.setText(consult.getCount()+"        "+consult.getAddress());

        return listViewItem;
    }
}
