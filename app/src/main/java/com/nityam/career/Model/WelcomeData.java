package com.nityam.career.Model;

import java.util.ArrayList;

/**
 * Created by nityamshrestha on 12/7/17.
 */

public class WelcomeData {

     String company = "Welcome, ";
     String position = "you must be new here ";
     String date = "Start by adding job applications ";
     String location = "Good luck ";

    public static ArrayList<WelcomeData> welcomeData = new ArrayList<>();

    private WelcomeData(){
         welcomeData.add(this);
     }

}
