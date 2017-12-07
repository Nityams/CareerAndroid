package com.nityam.career.Model;

/**
 * Created by nityamshrestha on 12/6/17.
 */

public class Glassdoor {

    //"http://api.glassdoor.com/api/api.htm?t.p=235020&t.k=bffbX6BXhNK&userip=0.0.0.0&useragent=&format=json&v=1&action=employers&q=cisco";

    final static String HOST = "http://api.glassdoor.com/api/api.htm?";
    final static String PARTNERTAG = "t.p=";
    final static String PARTNERID = "235020";
    final static String KEYTAG = "t.k=";
    final static String TAG ="bffbX6BXhNK";
    final static String FOOTER = "userip=0.0.0.0&useragent=&format=json&v=1&action=employers";


    public static String  getAPI( ){
        return HOST+PARTNERTAG+PARTNERID+"&"+KEYTAG+TAG+"&"+FOOTER+"&q=";
    }
}
