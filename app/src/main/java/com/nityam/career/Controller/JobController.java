package com.nityam.career.Controller;

import com.nityam.career.Model.JobPost;

import java.util.ArrayList;

/**
 * Created by nityamshrestha on 12/7/17.
 */

//Singleton class

public class JobController {

    ArrayList<JobPost> jobs;

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

    public void clear(){
        jobs.clear();
    }

    public void addJobs(JobPost jp){
        jobs.add(jp);
    }

    public int getJobSize(){
        return jobs.size();
    }

}
