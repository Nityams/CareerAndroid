package com.nityam.career.Controller;

import com.nityam.career.Model.WelcomeData;

import java.util.List;

/**
 * Created by nityamshrestha on 12/7/17.
 */

public class WelcomeController {

    public List<WelcomeData> getDatas(){
//        System.out.println(Data.data.size());
        return WelcomeData.welcomeData;
    }
}
