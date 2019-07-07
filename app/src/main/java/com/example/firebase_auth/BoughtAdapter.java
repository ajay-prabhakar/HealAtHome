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
 * Created by Chromicle on 7/7/19.
 */
public class BoughtAdapter extends ArrayAdapter<Bought> {

    private Activity context;
    private List<Bought> boughtlist;


    public BoughtAdapter(@NonNull Activity context,List<Bought> boughtlist){
        super(context,R.layout.bought_list);
        this.context=context;
        this.boughtlist=boughtlist;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();


        View listViewItem = inflater.inflate(R.layout.bought_list,null,true);

        TextView fname = listViewItem.findViewById(R.id.disease);
        TextView fgenre = listViewItem.findViewById(R.id.count);
        TextView time = listViewItem.findViewById(R.id.time);
        TextView additional = listViewItem.findViewById(R.id.additional);

        Bought bought = boughtlist.get(position);

        fname.setText(bought.getDesieses());
        fgenre.setText(bought.getCount());
        time.setText(bought.getUserMail());
        additional.setText(bought.getAdditional());

        return listViewItem;
    }
}
