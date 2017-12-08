package com.nityam.career.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbloadin_screen);

        user = PrefUtil.getUserToken();

        // posting to firebase database
        JobPost jp = (JobPost) getIntent().getSerializableExtra("job");
        if(jp != null){
            Log.d("<Nityam>","job found");

            rootRef = FirebaseDatabase.getInstance().getReference();
            rootRef.child(user).child(jp.getId()).setValue(jp);


            getData();

        }else{
            Log.d("<NITYAM>", "job not found");
            //push with user id
        }

        getData();
    }

    public void getData() {
        Log.d("<Nityam>","testing db");

        if(user.isEmpty()) {
            Log.d("<Nityam>","user token error");
        }else{
            rootRef = FirebaseDatabase.getInstance().getReference();
            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.hasChild(user)) {
                        // run some code
                        Log.d("<Nityam_Fbase>","User Found");
                        // add to the phone
                        populateFromDatabase();

                        SystemClock.sleep(1000);

                        Intent intent = new Intent(DBLoadinScreen.this, Home.class);
                        intent.putExtra("first_start", false);
                        startActivity(intent);
                    }
                    else{
                        Log.d("<Nityam_Fbase>","User NOT Found");

                        // create welcome on homePage
                        Intent intent = new Intent(DBLoadinScreen.this, Home.class);
                        intent.putExtra("first_start", true);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("<Nityam>", "Failed to read value.", databaseError.toException());
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
