package com.nityam.career.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nityam.career.R;

public class JobPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_page);


    }

    public void submit(View view) {
            // add to the database
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
    }
}
