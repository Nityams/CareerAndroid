package com.nityam.career.View;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.io.File;

public class Profile extends AppCompatActivity {

    TextView username;
    TextView email;
    TextView number;
    ImageView img;
    String url;
    Button camera;

    static final int CAM_REQUEST = 1;
    private static final int SELECT_PICTURE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_profile);

        username = (TextView) findViewById(R.id.userName);
        email = (TextView) findViewById(R.id.userEmail);
        number = (TextView) findViewById(R.id.userNumberCompaniesApplied);
        img = (ImageView) findViewById(R.id.image);
        camera = (Button) findViewById(R.id.clickPicture);



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

    public void changePic(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

    }

    public void clickPicture(View view) {

        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getFile();
        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(camera_intent, CAM_REQUEST);

    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private File getFile(){
        File folder = new File("sdcard/camera_app");
        if(!folder.exists()){
            folder.mkdir();
        }
        File image_file = new File(folder, "cam_image.jpg");
        return image_file;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i("<NITYAMImg>", "Image Path : " + path);
                    // Set the image in ImageView
                    img.setImageURI(selectedImageUri);
                }
            }
        }
        else {
            String path = "sdcard/camera_app/cam_image.jpg";
            img.setImageDrawable(Drawable.createFromPath(path));
        }
//        super.onActivityResult(requestCode, resultCode, data);

        rotateImage();
    }

    private void rotateImage(){

        Matrix matrix = new Matrix();

        img.setScaleType(ImageView.ScaleType.MATRIX);   //required
        matrix.postRotate((float) -90f, 180, 300);
        img.setImageMatrix(matrix);
    }
}
