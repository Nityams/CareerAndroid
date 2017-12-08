package com.nityam.career.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.LoginManager;
import com.nityam.career.Controller.JobController;
import com.nityam.career.Model.PrefUtil;
import com.nityam.career.R;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    TextView username;
    TextView email;
    TextView number;
    ImageView img;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_profile);

        username = (TextView) findViewById(R.id.userName);
        email = (TextView) findViewById(R.id.userEmail);
        number = (TextView) findViewById(R.id.userNumberCompaniesApplied);
        img = (ImageView) findViewById(R.id.image);




        Toast.makeText(this, Boolean.toString(PrefUtil.isLoggedIn()), Toast.LENGTH_SHORT).show();

        String[] userInfo = PrefUtil.getFBUserInfoAll();

        username.setText(userInfo[0]+" "+userInfo[1]);
        email.setText(userInfo[2]);
        number.setText(Integer.toString(JobController.getJobSize()));
        url = userInfo[3];

        if(url != null)
            loadImage(url);

    }

    private void loadImage(String url) {
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher)
                .into(img, new com.squareup.picasso.Callback(

                ) {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Log.d("error","picture error");
                    }
                });
    }

    public void logout(View view) {
        LoginManager.getInstance().logOut();
        PrefUtil.clearToken();

        deleteAccessToken();

        Intent intent = new Intent(Profile.this, Login.class);
        startActivity(intent);

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

}
