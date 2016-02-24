package controllers;

import data.entities.Job;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import services.jobs.JobServices;

@ManagedBean
@ViewScoped
public class ShowJobsController {
    private List<Job> jobs;

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public ShowJobsController(){
        
        JobServices jobServices = new JobServices();
        jobs = jobServices.readAllJobs();
        System.out.println("kD " + jobs.size());
        Job xd = new Job();
        xd.setName("SDLKJSKJADL");
        xd.setDescription("KSDJSLDKJSKLDJS");
        xd.setSalary(23);
        jobs.add(xd);
    }
}
