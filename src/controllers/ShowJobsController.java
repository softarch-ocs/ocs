package controllers;

import data.entities.Job;

import java.util.ArrayList;
import java.util.List;


public class ShowJobsController {
    private List<Job> jobs;

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public ShowJobsController(){
        jobs = new ArrayList<>();

        Job job = new Job();
        job.setName("Monitor de Fabio");
        job.setDescription(":VVVVVVVV");
        job.setSalary(0x7ffffff);
        job.setId(0);
        jobs.add(job);

        job = new Job();
        job.setName("Monitor de Jonatan");
        job.setDescription(":))))))))))))");
        job.setSalary(-0x7ffffff);
        job.setId(1);
        jobs.add(job);

        job = new Job();
        job.setName("Desarrollador de JEE");
        job.setDescription("kha");
        job.setSalary(0);
        job.setId(2);
        jobs.add(job);
    }
}
