package com.nityam.career.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nityam.career.Controller.Volley;
import com.nityam.career.Model.JobPost;
import com.nityam.career.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

public class JobPage extends AppCompatActivity {

    String id;
    EditText company ;
    EditText position;
    EditText city;
//    EditText dateTxt;
    EditText refName;
    EditText refEmail;
    EditText recName;
    EditText recEmail;
    String spinnerString = "will apply";
    Spinner statusSpinner;
//    Calendar myCalendar;

    Boolean update = false;


    ProgressDialog progressDialog;
    Volley volley ;
    ArrayList<String> data;

    JobPost jobPost;

    private static final String[] STATUS = {"Applied", "Interview","Offered","Rejected","Will Apply"};

    @Override
    protected void onResume() {
        super.onResume();
        if(data != null && !data.isEmpty())
        {
            data.clear();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_page);

        volley = new Volley();

        company = (EditText) findViewById(R.id.newCompany);
        position = (EditText) findViewById(R.id.newPosition);
        city = (EditText) findViewById(R.id.newCity);
        refName = (EditText) findViewById(R.id.newReferal);
        refEmail = (EditText) findViewById(R.id.newRefEmail);
        recName = (EditText) findViewById(R.id.newRecruiter);
        recEmail = (EditText) findViewById(R.id.newRecEmail);
        statusSpinner = (Spinner) findViewById(R.id.statusSpinner);
//        dateTxt = (EditText) findViewById(R.id.newDate);

        populateSpinner();

        JobPost jp = (JobPost) getIntent().getSerializableExtra("Update_Job");
        if(jp != null) {
            id = jp.getId();
            company.setText(jp.getCompany());
            position.setText(jp.getPosition());
            city.setText(jp.getCity());
            refName.setText(jp.getRecName());
            refEmail.setText(jp.getRefEmail());
            recEmail.setText(jp.getRecEmail());
            recName.setText(jp.getRecName());
            presetSpinner(jp.getStatus());
        }

        company.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                    if(!company.getText().toString().isEmpty()){
                        getData();
                    }
                }
            }
        });
    }

    private void presetSpinner(String spin) {

        if(spin.equals("applied")) statusSpinner.setSelection(0);
        else if(spin.equals("interview"))statusSpinner.setSelection(1);
        else if(spin.equals("offered"))statusSpinner.setSelection(2);
        else if(spin.equals("rejected"))statusSpinner.setSelection(3);
        else statusSpinner.setSelection(4);

    }

    private void populateSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,STATUS);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        //date applied
//                        Toast.makeText(JobPage.this, "Applied", Toast.LENGTH_SHORT).show();
                        spinnerString = "applied";
                        break;
                    case 1:
                        // Status
//                        Toast.makeText(JobPage.this, "Interview", Toast.LENGTH_SHORT).show();
                        spinnerString = "interview";
                        break;
                    case 2:
//                        Toast.makeText(JobPage.this, "Offered", Toast.LENGTH_SHORT).show();
                        spinnerString = "offered";
                        break;
                    case 3:
//                        Toast.makeText(JobPage.this, "Rejected", Toast.LENGTH_SHORT).show();
                        spinnerString = "rejected";
                        break;
                    default:
//                        Toast.makeText(JobPage.this, "Will apply", Toast.LENGTH_SHORT).show();
                        spinnerString= "will apply";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void submit(View view) {

        if(!company.getText().toString().isEmpty() ||
                !position.getText().toString().isEmpty() ||
                !city.getText().toString().isEmpty()
//                || !date.toString().isEmpty()
                )
        {
            if(id == null){
                id = Long.toString(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
            }
            jobPost = new JobPost(  id,
                                    company.getText().toString(),
                                    position.getText().toString(),
                                    city.getText().toString(),
                                    "",
                                    refName.getText().toString(),
                                    refEmail.getText().toString(),
                                    recName.getText().toString(),
                                    recEmail.getText().toString(),
                                    spinnerString
            );
            Intent intent = new Intent(this, DBLoadinScreen.class);
            intent.putExtra("job",jobPost);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Incomplete form", Toast.LENGTH_SHORT).show();
        }


//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
//        Log.d("<COMPANY>",company.getText().toString());
//
//
//        Log.d("<JOBPage: >", Arrays.toString(data.toArray()));
        // add to the database
//            Intent intent = new Intent(this, Home.class);
//            startActivity(intent);

//        progressDialog.hide();

    }

    private void getData(){
        data = volley.volleyJsonObjectRequest(company.getText().toString());
    }

    public void companyInfo(View view) {
        getData();
        Log.d("<Compny_info>", Arrays.toString(data.toArray()));

        if(data.size()>0) {
            Intent intent = new Intent(this, CompanyInfo.class);
            intent.putStringArrayListExtra("company_info", data);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Company Not Found", Toast.LENGTH_SHORT).show();
        }

    }
}
