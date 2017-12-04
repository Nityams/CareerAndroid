package nityam.com.career;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

    }

    public void signupOnClick(View view) {
        Toast.makeText(this, " SIgn Up ", Toast.LENGTH_SHORT).show();
    }

    public void signinOnClick(View view){
        // disabled
        Toast.makeText(this, " Sign In ", Toast.LENGTH_SHORT).show();

    }
}
