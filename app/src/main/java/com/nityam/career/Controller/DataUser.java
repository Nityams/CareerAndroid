package com.nityam.career.Controller;

import java.util.List;

import com.nityam.career.Model.Data;

/**
 * Created by nityamshrestha on 12/5/17.
 */

public class DataUser {

    public void activateData(){
        Data.initializeData();
    }

    public List<Data> getDatas(){
//        System.out.println(Data.data.size());
        return Data.data;
    }
}
