package com.example.firebase_auth.Medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase_auth.ConsultDoctor;
import com.example.firebase_auth.R;
import com.example.firebase_auth.SymptomsAdapter;

import java.util.ArrayList;


public class MedicalProblems extends AppCompatActivity {

    /**
     * medicalProblemImages ArrayList
     */
    ArrayList<Integer> problemsImages;
    /**
     * problemText ArrayList
     */
    ArrayList<String> problemsText;
    /**
     * medicalListView to  to show medical list
     */
    ListView medicalListView;

    Button button;

    /**
     * onCreate override method call first when activity start
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_symptoms);

        /** initializing medical listView  */
        medicalListView = (ListView) findViewById(R.id.listOfProblems);
        button = findViewById(R.id.cunsolt_doctor);

        /** UiThread to load medical images and text from resources */
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fillProblemsImages();
                fillProblemsText();
                SymptomsAdapter customMedicalProblemsAdapter = new SymptomsAdapter(MedicalProblems.this, problemsImages, problemsText);
                medicalListView.setAdapter(customMedicalProblemsAdapter);
            }
        });

        medicalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listItemSelector(i);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MedicalProblems.this, ConsultDoctor.class));
            }
        });

    }

    /**
     * listItemSelector method
     *
     * @param i
     */
    void listItemSelector(int i) {

        MedicinesController.index = (byte) i;
        /**
         * initializing intent to start another activity
         * @param MedicalProblems
         * @param RequiredMedicines
         * */
        Intent intent = new Intent(MedicalProblems.this, RequiredMedicines.class);
        startActivity(intent);
    }

    /**
     * fillProblemsImages method
     * to fill medical problem images
     */
    void fillProblemsImages() {
        problemsImages = new ArrayList();
        problemsImages.add(R.drawable.fever_face);
        problemsImages.add(R.drawable.headache);
        problemsImages.add(R.drawable.cold);
        problemsImages.add(R.drawable.vomiting);
        problemsImages.add(R.drawable.constipation);
        problemsImages.add(R.drawable.blood_presure);
        problemsImages.add(R.drawable.daily_health_care);
    }

    /**
     * fillProblemsText to load problem names
     * from resources
     */
    void fillProblemsText() {
        problemsText = new ArrayList();
        String[] problems = getResources().getStringArray(R.array.medicalProblems);

        for (int i = 0; i < problems.length; i++)
            problemsText.add(problems[i]);
    }
}
