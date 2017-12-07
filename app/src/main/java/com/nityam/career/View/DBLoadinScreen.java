package com.nityam.career.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nityam.career.Model.PrefUtil;
import com.nityam.career.R;

public class DBLoadinScreen extends Activity {

    String user;
    static DatabaseReference rootRef ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbloadin_screen);

        user = PrefUtil.getUserToken();

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

                        Intent intent = new Intent(DBLoadinScreen.this, Home.class);
                        intent.putExtra("first_start", false);
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
}
