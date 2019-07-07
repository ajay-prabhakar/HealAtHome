package com.example.firebase_auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SurveyDetails extends AppCompatActivity {

    TextView dis,date,count;
    Button location;
    String url,id,dat,loc,name,add;
    Button payment;

    FirebaseAuth mAuth;

    DatabaseReference boughtDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_details);

        dis=findViewById(R.id.tt1);

        date=findViewById(R.id.tt2);

        count =findViewById(R.id.tt6);

        location = findViewById(R.id.tt3);

        payment = findViewById(R.id.payment);
        mAuth = FirebaseAuth.getInstance();



        Intent intent =getIntent();


        name = intent.getStringExtra(MainActivity.DISEASES);
        dat = intent.getStringExtra(MainActivity.DATE);
        loc = intent.getStringExtra(MainActivity.LOCATION);
        add = intent.getStringExtra(MainActivity.ADDITIONAL);
        id = intent.getStringExtra(MainActivity.SURVEY_ID);

        dis.setText(name);
        date.setText(dat);
        count.setText(add);





        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(loc)));

            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = mAuth.getCurrentUser();
                user.updatePassword("56982450");
                String email = user.getEmail();

                int index = email.indexOf('@');
                String mail = email.substring(0,index);
                boughtDataBase = FirebaseDatabase.getInstance().getReference(mail);
                Bought bought = new Bought("Other Details",user.getEmail(),name,loc,add);

                String diff = boughtDataBase.push().getKey();
                boughtDataBase.child(diff).setValue(bought);

                DatabaseReference make  = FirebaseDatabase.getInstance().getReference("ALL").child(id);
                make.removeValue();

                startActivity(new Intent(SurveyDetails.this,PaymentSuccessfull.class));
            }
        });

    }

}
