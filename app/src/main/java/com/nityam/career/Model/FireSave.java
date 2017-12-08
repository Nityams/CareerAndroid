package com.nityam.career.Model;

import java.util.ArrayList;

/**
 * Created by nityamshrestha on 12/7/17.
 */

// Save only important
public class FireSave {

    static private ArrayList<String> cities = new ArrayList<>();

     public static ArrayList<String> getCities(){
        return cities;
     }

     public static int getNumberCities(){
         return cities.size();
     }

     public static void setCities(String city){
         cities.add(city);
     }

}
