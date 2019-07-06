package com.example.firebase_auth;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Chromicle on 6/7/19.
 */
public class SymptomsAdapter extends BaseAdapter {


    /**
     * declaring variables for context, imagesId arrayList
     * textViewlist
     */
    private Context context;
    private ArrayList<Integer> idImages;
    private ArrayList<String> textViewList;

    /**
     * {@link SymptomsAdapter} constructor
     *
     * @param context
     * @param listId
     * @param textViewList
     */
    public SymptomsAdapter(Context context, ArrayList<Integer> listId, ArrayList<String> textViewList) {
        this.context = context;
        this.idImages = listId;
        this.textViewList = textViewList;
    }

    /**
     * getCount override method
     *
     * @return textViewList.size
     */
    @Override
    public int getCount() {
        return textViewList.size();
    }

    /**
     * override getItem method
     *
     * @param i
     * @return Object
     */
    @Override
    public Object getItem(int i) {
        return textViewList.get(i);
    }

    /**
     * override getItemId method
     *
     * @param i
     * @return long
     */
    @Override
    public long getItemId(int i) {
        return idImages.get(i);
    }

    /**
     * override getView method
     *
     * @param i
     * @param view
     * @param viewGroup
     * @return view
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = view.inflate(context, R.layout.symptoms_list, null);
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.medicalProblemImage);
        TextView textView = (TextView) view.findViewById(R.id.medicalProblemText);

        imageView.setImageResource(idImages.get(i));
        textView.setText(textViewList.get(i));

        return view;
    }
}