package com.nityam.career.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nityam.career.R;

import java.util.ArrayList;

public class CompanyInfo extends AppCompatActivity {

    ArrayList<String> data ;
    TextView company;
    TextView website;
    TextView industry;
    TextView ratingDesc;
    TextView culture;
    TextView workLife;
    TextView location;
    RatingBar rating;
//    TextView link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);

        Intent i = getIntent();
        data = i.getStringArrayListExtra("company_info");

         company = (TextView) findViewById(R.id.displayCompany);
         website = (TextView) findViewById(R.id.displayWebsite);
         industry = (TextView) findViewById(R.id.displayIndustry);
         ratingDesc = (TextView) findViewById(R.id.displayRatingDesc);
         culture = (TextView) findViewById(R.id.displayCulture);
         workLife = (TextView) findViewById(R.id.displayWorkLife);
         location = (TextView) findViewById(R.id.displayLocation);
         rating = (RatingBar) findViewById(R.id.rating);

//         link = (TextView) findViewById(R.id.displayLink);


        company.setText(data.get(0));
        website.setText(data.get(1));
        location.setText(data.get(2));
        industry.setText(data.get(3));
        ratingDesc.setText(data.get(5));
        culture.setText(data.get(6));
        workLife.setText(data.get(7));

        Float rate = Float.parseFloat(data.get(4));
        rating.setRating(rate);

//        link.setText(data.get(8));

    }
}
