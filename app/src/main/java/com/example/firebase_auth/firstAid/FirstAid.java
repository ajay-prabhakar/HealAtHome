package com.example.firebase_auth.firstAid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.firebase_auth.R;

public class FirstAid extends AppCompatActivity implements View.OnClickListener {
    private CardView drown, elec, open, burns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aid);
        drown = (CardView) findViewById(R.id.Drowning);
        elec = (CardView) findViewById(R.id.elec);
        open = (CardView) findViewById(R.id.open_wounds);
        burns = (CardView) findViewById(R.id.Burns);
        drown.setOnClickListener(this);
        elec.setOnClickListener(this);
        open.setOnClickListener(this);
        burns.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.Drowning:
                i = (new Intent(this, drown.class));
                startActivity(i);
                break;
            case R.id.elec:
                i = (new Intent(this, elec.class));
                startActivity(i);
                break;
            case R.id.Burns:
                i = (new Intent(this, burn.class));
                startActivity(i);
                break;
            default:
                break;


        }
    }
}
