package com.nityam.career.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nityamshrestha on 12/4/17.
 */

public class Data {
    public String company;
    public String position;
    public String date;
    public String status;
    public String location;

    Data(String company, String position, String date, String status, String location) {
        this.company = company;
        this.position = position;
        this.date = date;
        this.status = status;
        this.location = location;
    }

    public static List<Data> data;

    public static void initializeData() {
        data = new ArrayList<>();
        data.add(new Data("Google", "Software 1", "12/12/12", "interview", "san jose"));
        data.add(new Data("Facebook", "Software 2", "12/12/13", "accepted", "melno park"));
        data.add(new Data("LinkedIn", "Software 3", "03/12/19", "interview", "San Francisco"));
        data.add(new Data("Noogle", "Software New grad", "12/12/12", "interview", "san jose"));
        data.add(new Data("Basefook", "Software Old grad", "12/12/13", "rejected", "melno park"));
        data.add(new Data("Reddit", "Software Intern", "03/12/19", "interview", "San Francisco"));
    }
}
