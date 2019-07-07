package com.example.firebase_auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BoughtSurveys extends AppCompatActivity {

    ListView boughtList;
    List<Bought> boughts;
    DatabaseReference databaseBought;
    FirebaseAuth mAuth;
    Button readAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bought_surveys);

        boughtList = findViewById(R.id.bought_surveys);

        mAuth = FirebaseAuth.getInstance();

        readAll = findViewById(R.id.readAll);


        boughts = new ArrayList<>();


        final FirebaseUser user = mAuth.getCurrentUser();

        String email = user.getEmail();

        final int index = email.indexOf('@');
        String mail = email.substring(0, index);

        databaseBought = FirebaseDatabase.getInstance().getReference("make");

        readAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                databaseBought.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        boughts.clear();
                        for (DataSnapshot boughtSnapshot : dataSnapshot.getChildren()) {

                            Bought bought = boughtSnapshot.getValue(Bought.class);
                            boughts.add(bought);
                        }

                        BoughtAdapter adapter = new BoughtAdapter(BoughtSurveys.this, boughts);
                        boughtList.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Toast.makeText(getApplicationContext(),"some thing error",Toast.LENGTH_SHORT).show();

                    }


                });

            }
        });

    }


}
