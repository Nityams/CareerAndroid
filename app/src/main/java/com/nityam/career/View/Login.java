package com.nityam.career.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.widget.ShareDialog;
import com.nityam.career.Model.PrefUtil;
import com.nityam.career.R;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;


public class Login extends AppCompatActivity {

    com.facebook.login.widget.LoginButton loginButton;
    private CallbackManager callbackManager;
    ShareDialog shareDialog;
    private PrefUtil prefUtil;

    public Activity getActivity(){
        return this.getActivity();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prefUtil = new PrefUtil(this);


        getSupportActionBar().hide();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);

        if(PrefUtil.isLoggedIn()){
            toHomePage();
        }

    //---------------------------------FACEBOOK----------------------------------------------------------

        loginButton = (LoginButton) findViewById(R.id.login_button);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // put them on log
//        Log.d("<onActResult-ReqCode> ",Integer.toString(requestCode));
//        Log.d("<onActResult-ResCode> ",Integer.toString(resultCode));
//        Log.d("<onActResult-IntData> ",data.toString());
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void fbOnClick(View view){

        loginButton.setReadPermissions("email");

        // Callback registration

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d("<regCallback-Succ> ",loginResult.toString());
                String accessToken = loginResult.getAccessToken().getToken();

                // save accessToken to SharedPreference
                Log.d("<TOKEN>",accessToken.toString());
                prefUtil.saveAccessToken(accessToken);

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject,
                                                    GraphResponse response) {

                                // Getting FB User Data
                                Bundle facebookData = getFacebookData(jsonObject);

                                Log.d("<FB-DATA>",facebookData.toString());

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();
                toHomePage();

            }

            @Override
            public void onCancel () {
                Log.d("<NITYAM>", "Login attempt cancelled.");
            }

            @Override
            public void onError (FacebookException e){
                e.printStackTrace();
                Log.d("<NITYAM>", "Login attempt failed.");
                deleteAccessToken();
            }
        });


    }

    private void toHomePage(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();

        try {
            String id = object.getString("id");
            URL profile_pic;
            try {
                profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));


            prefUtil.saveFacebookUserInfo(object.getString("first_name"),
                    object.getString("last_name"),object.getString("email"),
                    object.getString("gender"), profile_pic.toString());

        } catch (Exception e) {
            Log.d("<NITYAM-BundleError>", "BUNDLE Exception : "+e.toString());
        }

        return bundle;
    }



    //debug
    public void debugOnClick(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //

    }


    private void deleteAccessToken() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    //User logged out
                    prefUtil.clearToken();
                    LoginManager.getInstance().logOut();
                }
            }
        };
    }

}
