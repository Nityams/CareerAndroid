package nityam.com.career;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.widget.ShareDialog;

import java.util.Arrays;


public class Login extends AppCompatActivity {

    com.facebook.login.widget.LoginButton loginButton;
    private CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        getSupportActionBar().hide();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);

    //---------------------------------FACEBOOK----------------------------------------------------------

        loginButton = (LoginButton) findViewById(R.id.login_button);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // put them on log
        System.out.println("<NITYAM DEBUG>,,,OnActivityResult requestCode..." + requestCode);
        System.out.println("<NITYAM DEBUG>,,,OnActivityResult resultCode..." + resultCode);
        System.out.println("<NITYAM DEBUG>,,,OnActivityResult intentData..." + data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void fbOnClick(View view){

        loginButton.setReadPermissions("email");

        // Callback registration

        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                System.out.println("*** NITYAM's DEBUGGING onSUCCESS...." + loginResult);
            }

            @Override
            public void onCancel() {
                System.out.println("*** NITYAM's DEBUGGING onCANCEL....");
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                System.out.println("*** NITYAM's DEBUGGING onERROR...." );
            }
        });

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));


    }


    public void signupOnClick(View view) {
        Toast.makeText(this, " SIgn Up ", Toast.LENGTH_SHORT).show();
    }

    public void signinOnClick(View view){
        // disabled
        Toast.makeText(this, " Sign In ", Toast.LENGTH_SHORT).show();

    }
}
