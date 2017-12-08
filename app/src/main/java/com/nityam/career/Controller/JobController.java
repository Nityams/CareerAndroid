package com.nityam.career.Controller;

import com.nityam.career.Model.JobPost;

import java.util.ArrayList;

/**
 * Created by nityamshrestha on 12/7/17.
 */

//Singleton class
    // used after getting info from database

public class JobController {

    static ArrayList<JobPost> jobs = new ArrayList<>();

    static JobController jobController;

    private JobController(){
        jobs = new ArrayList<>();
    }

    public static JobController getInstance(){
        if(jobController == null){
            return new JobController();
        }else
            return jobController;
    }

    public static ArrayList<JobPost> getJobs(){
        return jobs;
    }

    public static void clear(){
        jobs.clear();
    }

    public static void addJobs(JobPost jp){
        jobs.add(jp);
    }

    public static int getJobSize(){
        return jobs.size();
    }

}
