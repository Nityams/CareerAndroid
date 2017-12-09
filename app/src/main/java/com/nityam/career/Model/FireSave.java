package com.nityam.career.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nityamshrestha on 12/7/17.
 */

// Save only important
public class FireSave {

    static private HashMap<String, String> cityMap = new HashMap<>();
    static private ArrayList<String> companies = new ArrayList<>();

    public static String[] getMapAt(int index){
        String comp = companies.get(index);
        String city = cityMap.get(comp);
        String[] answer = {comp, city};
        return answer;
    }
    public static int getNumberCityMaps(){
        return companies.size();
    }

    public static void setCityMap(String company, String city){

        cityMap.put(company,city);
        companies.add(company);
    }
    public static void clear(){
        companies.clear();
        cityMap.clear();
    }



//
//    static private ArrayList<String> cities = new ArrayList<>();
//
//     public static ArrayList<String> getCities(){
//        return cities;
//     }
//
//     public static String getCity(int index){ return cities.get(index);}
//
//     public static int getNumberCities(){
//         return cities.size();
//     }
//
//     public static void setCities(String city){
//         cities.add(city);
//     }

}
