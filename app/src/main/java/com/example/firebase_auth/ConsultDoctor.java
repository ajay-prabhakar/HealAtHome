package com.example.firebase_auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConsultDoctor extends AppCompatActivity {

    EditText edit_symptoms,edit_time,edit_email;
    private String sym,time,address,email,type="";
    Spinner doctorType;
    Button publish;
    CheckBox paticularDoc;
    FirebaseAuth mAuth;
    TextView selected;
    Button edit_Address;


    int flag=1;

    DatabaseReference patDoc;
    DatabaseReference allDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_survey);

        edit_symptoms = findViewById(R.id.pat_sys);
        edit_time = findViewById(R.id.pat_time);
        edit_Address = findViewById(R.id.pat_address);
        edit_email = findViewById(R.id.doc_email);
        doctorType =findViewById(R.id.typeDoctors);
        publish = findViewById(R.id.publish);
        paticularDoc = findViewById(R.id.particularDoc);
        selected = findViewById(R.id.selected);

        mAuth = FirebaseAuth.getInstance();

        doctorType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = type+"  "+ doctorType.getSelectedItem().toString();

                selected.setText("Selected Diseses"+type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        paticularDoc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edit_email.setVisibility(View.VISIBLE);
                    flag=0;
                }
                else {
                    edit_email.setVisibility(View.GONE);
                    flag=1;
                }
            }
        });


        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadSytomps();
            }
        });

        edit_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLocation();

            }
        });
    }

    public void getLocation(){
        Double latitude = 0.0, longitude;
        String message = "";
        LocationManager mlocManager = null;
        LocationListener mlocListener;
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new MyLocationListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
        if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            latitude = MyLocationListener.latitude;
            longitude = MyLocationListener.longitude;
            message = message +"https://www.google.com/maps/dir/@"+ latitude +","+  longitude;
            address=message;
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            if (latitude == 0.0) {
                Toast.makeText(getApplicationContext(), "Currently gps has not found your location....", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "GPS is currently off...", Toast.LENGTH_LONG).show();
        }
    }

    private void uploadSytomps() {
        sym = edit_symptoms.getText().toString().trim();
        time = edit_time.getText().toString().trim();
        email = edit_email.getText().toString().trim();


        if (flag==1){
            allDoc = FirebaseDatabase.getInstance().getReference("ALL");

            if(!(TextUtils.isEmpty(sym) && TextUtils.isEmpty(time) && TextUtils.isEmpty(type) && TextUtils.isEmpty(type))) {
                String id = allDoc.push().getKey();
                FirebaseUser user = mAuth.getCurrentUser();


                Consult consult = new Consult(sym, time, id, address,type,user.getEmail().trim(),email);

                    allDoc.child(id).setValue(consult);

                Toast.makeText(getApplicationContext(),"Published your survey",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"Some thing error occoured",Toast.LENGTH_SHORT).show();
            }

        }

        else {

            final DatabaseReference pat;
            pat = FirebaseDatabase.getInstance().getReference("DOC");
            pat.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){



                        int index = email.indexOf('@');
                        email = email.substring(0,index);

                        patDoc = FirebaseDatabase.getInstance().getReference(email);
                        String id = patDoc.push().getKey();

                        Consult dom = new Consult(sym, time,"null",address,type,"null","null");

                        patDoc.child(id).setValue(dom);

                        Toast.makeText(getApplicationContext(),"succesfull particular doctor",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Toast.makeText(getApplicationContext(),"Some thing wrong occured here",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }


}
