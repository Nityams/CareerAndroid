package com.nityam.career.View;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.LoginManager;
import com.nityam.career.Controller.DummyAdapter;
import com.nityam.career.Controller.JobController;
import com.nityam.career.Controller.RVAdapter;
import com.nityam.career.Model.PrefUtil;
import com.nityam.career.R;

public class Home extends AppCompatActivity {

    private Spinner spinner;
    private static final String[]sortingMethods = {"Applied Date", "Status"};
    private RecyclerView rv;
    private static Context context;
    RVAdapter adapter;

    DummyAdapter dummyAdapter;

    Boolean firstStart = false;
    String user;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Toast.makeText(this, "ABOUT", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                LoginManager.getInstance().logOut();
                PrefUtil.clearToken();

                deleteAccessToken();

                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                break;
            case R.id.profile:
                Intent intente = new Intent(this,Profile.class);
                startActivity(intente);
                break;
        }
        return true;
    }

    private void deleteAccessToken() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null) {
                    //User logged out
                    PrefUtil.clearToken();
                    LoginManager.getInstance().logOut();
                }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.app_name);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                goToJobPage();
            }
        });


        Configuration config = getResources().getConfiguration();
        if (config.orientation == config.ORIENTATION_LANDSCAPE) {
            goToMap(this);
        }

        context = getApplicationContext();
        user = PrefUtil.getUserToken();

        firstStart = getIntent().getBooleanExtra("first_start",false);

        Toast.makeText(this, Boolean.toString(firstStart), Toast.LENGTH_SHORT).show();

//        if(!firstStart) {

            spinner = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, sortingMethods);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            // Whatever you want to happen when the first item gets selected
                            //date applied
                            Toast.makeText(Home.this, "Sort by dates", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            // Status
                            Toast.makeText(Home.this, "Sort by status", Toast.LENGTH_SHORT).show();

                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            setCards();

//        }else{
//            setWelcomeCards();

//            createNewUser();
//        }


    }

    /*

    private void createNewUser() {
        Log.d("<NitDatabase>", "creating new use in firebase");
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child(user).setValue("apple");
//        DatabaseReference job = FirebaseDatabase.getInstance().getReference(user);

//        job.keepSynced(true);

    }
*/
    private void setWelcomeCards() {
        // make it fragment
        TextView welcome1 = (TextView) findViewById(R.id.welcome1);
        TextView welcome2 = (TextView) findViewById(R.id.welcome2);
        TextView welcome3 = (TextView) findViewById(R.id.welcome3);
        TextView welcome4 = (TextView) findViewById(R.id.welcome4);

        welcome1.setText("Welcome, \n");
        welcome2.setText("you must be new here");
        welcome3.setText("Start by adding job applications \n \n");
        welcome4.setText("Good Luck");

    }

    private void goToMap(Home home) {
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }

    private void goToJobPage(){
        Intent intent = new Intent(this, JobPage.class);
        startActivity(intent);
    }

    private void setCards() {
        rv = (RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);
//
//        DataUser du = new DataUser();
//        du.activateData();
//        dummyAdapter = new DummyAdapter(du.getDatas());
//        rv.setAdapter(dummyAdapter);

//        JobController jc = JobController.getInstance();
        Log.d("<CARDS>", Integer.toString(JobController.getJobSize()));

        adapter= new RVAdapter(JobController.getJobs());
         rv.setAdapter(adapter);


    }


}
