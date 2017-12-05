package nityam.com.career.Controller;

import java.util.List;

import nityam.com.career.Model.Data;

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
