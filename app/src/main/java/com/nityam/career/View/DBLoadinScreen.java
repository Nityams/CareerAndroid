package com.nityam.career.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nityam.career.Controller.JobController;
import com.nityam.career.Model.FireSave;
import com.nityam.career.Model.JobPost;
import com.nityam.career.Model.PrefUtil;
import com.nityam.career.R;

public class DBLoadinScreen extends Activity {

    String user;
    static DatabaseReference rootRef ;
    static DatabaseReference userRef;
    JobController jobController;
    TextView status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbloadin_screen);

        status = (TextView) findViewById(R.id.loadingStatus);

        status.setText("fetching user fb token");
        user = PrefUtil.getUserToken();

        status.setText("checking job posting");
        // posting to firebase database
        JobPost jp = (JobPost) getIntent().getSerializableExtra("job");
        if(jp != null){
            Log.d("<Nityam>","job found");

            status.setText("posting new job on firebase");
            rootRef = FirebaseDatabase.getInstance().getReference();
            rootRef.child(user).child(jp.getId()).setValue(jp);


            getData();

        }else{
            Log.d("<NITYAM>", "job not found");
            status.setText("new job not found ");
            //push with user id
        }
        status.setText("Getting DATA");
        getData();
    }

    public void getData() {
        Log.d("<Nityam>","testing db");

        if(user.isEmpty()) {
            Log.d("<Nityam>","user token error");
            status.setText("Token error");
        }else{
            status.setText("Connecting to firebase.. for "+ user);
            rootRef = FirebaseDatabase.getInstance().getReference();
            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.hasChild(user)) {
                        // run some code
                        Log.d("<Nityam_Fbase>","User Found");
                        status.setText("User found");
                        // add to the phone
                        populateFromDatabase();

                        status.setText("starting home page");
                        Intent intent = new Intent(DBLoadinScreen.this, Home.class);
                        intent.putExtra("first_start", false);
                        startActivity(intent);
                    }
                    else{
                        Log.d("<Nityam_Fbase>","User NOT Found");
                        status.setText("New User found");
                        // create welcome on homePage
                        status.setText("starting home page");
                        Intent intent = new Intent(DBLoadinScreen.this, Home.class);
                        intent.putExtra("first_start", true);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("<Nityam>", "Failed to read value.", databaseError.toException());
                    status.setText("Failed to read value");
                }
            });
        }
    }

    private void populateFromDatabase() {
//        jobController = JobController.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference(user);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                JobController.clear();
                status.setText("Getting database info");
                for(DataSnapshot heroSnapshot: dataSnapshot.getChildren()){

                    JobPost value = heroSnapshot.getValue(JobPost.class);

                   JobController.addJobs(new JobPost(value.getId(),value.getCompany(),value.getPosition(), value.getCity(),value.getDate(), value.getRefName(), value.getRefEmail(), value.getRecName(), value.getRecEmail(), value.getStatus()));

                    FireSave.setCities(value.getCity());

                   Log.d("<from-db>", Integer.toString(JobController.getJobSize()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("<NITYAM>", "Failed to read value.", databaseError.toException());

            }
        });



    }
}
