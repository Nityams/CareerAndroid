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
import com.nityam.career.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class JobPage extends AppCompatActivity {

    EditText company ;
    EditText position;
    EditText city;
    Date date;
    EditText refName;
    EditText refEmail;
    EditText recName;
    EditText recEmail;
    Spinner statusSpinner;

    ProgressDialog progressDialog;
    Volley volley ;
    ArrayList<String> data;

    private static final String[] STATUS = {"Applied", "Interview","Rejected","Offered","Will Apply"};

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

//        data = (Date) findViewById(R.id.newDate);
    // get date
        //populate the spinner
        populateSpinner();


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
                        Toast.makeText(JobPage.this, "Applied", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        // Status
                        Toast.makeText(JobPage.this, "Interview", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(JobPage.this, "Rejected", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(JobPage.this, "Offered", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(JobPage.this, "Will apply", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void submit(View view) {

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
