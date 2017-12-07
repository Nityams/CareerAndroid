package com.nityam.career.Controller;

import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nityam.career.Model.AppSingleton;
import com.nityam.career.Model.Glassdoor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by nityamshrestha on 12/6/17.
 */

public class Volley {

    final private String Link = Glassdoor.getAPI();
    ArrayList<String> data;

    public Volley(){
        data = new ArrayList<>();
    }

    public  ArrayList<String> volleyJsonObjectRequest(String company){

        Log.d("<V_COMPANY>",company);
        String url = Link + company;

        String  REQUEST_TAG = "com.androidtutorialpoint.volleyJsonObjectRequest";


        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            data = parseJson(response);
                            Log.d("<VSize>",Integer.toString(data.size()));

                            Log.d("<VOLLEY>","DATA FOUND");

                        }
                        catch (JSONException e) {
                            Log.d("<NITYAM>","ERROR on "+e.toString());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("<NITYAM>", "Error: " + error.getMessage());
            }
        });

        Log.d("<VOLLEYSIZE>",Integer.toString(data.size()));

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,REQUEST_TAG);

        return data;
        // Adding JsonObject request to request queue

    }



    private ArrayList<String> parseJson(JSONObject response) throws JSONException {
        ArrayList<String> data = new ArrayList<>();

        String  REQUEST_TAG = "com.Nityam.career";


        JSONObject jobj = response.getJSONObject("response");
        JSONArray array = jobj.getJSONArray("employers");


        //<DEBUG> just send the 1st one for now

        data.add(array.getJSONObject(0).getString("name"));
        data.add(array.getJSONObject(0).getString("website"));
        data.add(array.getJSONObject(0).getJSONObject("featuredReview").getString("location"));
        data.add(array.getJSONObject(0).getString("industry"));
        data.add(array.getJSONObject(0).getString("overallRating"));
        data.add(array.getJSONObject(0).getString("ratingDescription"));
        data.add(array.getJSONObject(0).getString("cultureAndValuesRating"));
        data.add(array.getJSONObject(0).getString("workLifeBalanceRating"));
        data.add(array.getJSONObject(0).getJSONObject("featuredReview").getString("attributionURL"));

//        Log.d("<NITYAM>", Arrays.toString(data.toArray()));
        return data;
//        return list;


    }

    public void volleyCacheRequest(String url){
        Cache cache = AppSingleton.getInstance(getApplicationContext()).getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if(entry != null){
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else{

        }
    }

    public void volleyInvalidateCache(String url){
        AppSingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().invalidate(url, true);
    }

    public void volleyDeleteCache(String url){
        AppSingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().remove(url);
    }

    public void volleyClearCache(){
        AppSingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
    }



}
