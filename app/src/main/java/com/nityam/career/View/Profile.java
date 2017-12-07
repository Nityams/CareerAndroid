package com.nityam.career.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nityam.career.Model.PrefUtil;
import com.nityam.career.R;

public class Profile extends AppCompatActivity {

    TextView username;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        username = (TextView) findViewById(R.id.userName);
        email = (TextView) findViewById(R.id.userEmail);

        Toast.makeText(this, Boolean.toString(PrefUtil.isLoggedIn()), Toast.LENGTH_SHORT).show();

        String[] userInfo = PrefUtil.getFBUserInfoAll();

        username.setText(userInfo[0]+" "+userInfo[1]);
        email.setText(userInfo[2]);
    }

    public void logout(View view) {

    }
}
