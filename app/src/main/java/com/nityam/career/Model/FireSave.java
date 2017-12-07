package com.nityam.career.Model;

import java.util.ArrayList;

/**
 * Created by nityamshrestha on 12/7/17.
 */

// Save only important
public class FireSave {

    static private int numberOfJobsApplied = 0;
    static private ArrayList<String> cities = new ArrayList<>();

    public static int getNumberOfJobsApplied(){
        return numberOfJobsApplied;
    }

    public  void setNumberOfJobsApplied(int numberOfJobsApplied){
        this.numberOfJobsApplied = numberOfJobsApplied;
     }

     public static ArrayList<String> getCities(){
        return cities;
     }

     public static int getNumberCities(){
         return cities.size();
     }

     public void setCities(ArrayList<String> cities){
         this.cities = cities;
     }

}
