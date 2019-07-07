package com.example.firebase_auth;

import android.content.Intent;
import android.os.Bundle;

import com.example.firebase_auth.Medical.MedicalProblems;
import com.example.firebase_auth.firstAid.FirstAid;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference databaseReq;
    FirebaseAuth mAuth;


    public static final String DISEASES = "diseases";
    public static final String SURVEY_ID = "surveyId";
    public static final String DATE = "date";
    public static final String LOCATION = "location";

    public static final String ADDITIONAL = "additional";



    ListView consultList;

    List<Consult> consulList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        consultList = findViewById(R.id.listViewConsolts);


        mAuth = FirebaseAuth.getInstance();
        consulList = new ArrayList<>();
        final FirebaseUser user = mAuth.getCurrentUser();
        String email = user.getEmail();

        final int index = email.indexOf('@');
        String mail = email.substring(0, index);
        databaseReq = FirebaseDatabase.getInstance().getReference("ALL");


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        consultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FirebaseUser user = mAuth.getCurrentUser();

                String name = user.getDisplayName();
                String lastWord = name.substring(name.lastIndexOf(" ") + 1);

                if (lastWord.equals("DOC")) {
                    Consult consult = consulList.get(position);
                    Intent intent = new Intent(MainActivity.this, SurveyDetails.class);
                    intent.putExtra(SURVEY_ID, consult.getDoctor());
                    intent.putExtra(DISEASES, consult.getDiseses());
                    intent.putExtra(DATE,consult.getTime());
                    intent.putExtra(LOCATION,consult.getAddress());
                    intent.putExtra(ADDITIONAL,consult.getCount());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "You are surveyer Can not buy", Toast.LENGTH_SHORT).show();
                }
            }

        });

        hideItem();


    }

    private void hideItem() {

        FirebaseUser user = mAuth.getCurrentUser();

        String name = user.getDisplayName();
        String lastWord = name.substring(name.lastIndexOf(" ") + 1);

        if (lastWord.equals("DOC")) {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_gallery).setVisible(false);
            nav_Menu.findItem(R.id.nav_share).setVisible(true);

        } else {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_gallery).setVisible(true);
            nav_Menu.findItem(R.id.nav_share).setVisible(false);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseReq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                consulList.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                    Consult consult = artistSnapshot.getValue(Consult.class);
                    consulList.add(consult);
                }

                ConsultAdapter adapter = new ConsultAdapter(MainActivity.this, consulList);
                consultList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(MainActivity.this,ConsultDoctor.class));


        } else if (id == R.id.nav_slideshow) {

            startActivity(new Intent(MainActivity.this, MapsActivity.class));

        } else if (id == R.id.nav_tools) {
            startActivity(new Intent(MainActivity.this, FirstAid.class));

        } else if (id == R.id.nav_share) {
            startActivity(new Intent(MainActivity.this,BoughtSurveys.class));


        } else if (id == R.id.nav_send) {
            startActivity(new Intent(MainActivity.this, MedicalProblems.class));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
