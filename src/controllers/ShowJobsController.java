package controllers;

import data.entities.Job;
import data.entities.User;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import services.UserService;
import services.jobs.JobServices;

@ManagedBean
@ViewScoped
public class ShowJobsController extends BaseController {
    private List<Job> jobs;
    private JobServices jobServices;
    
    public ShowJobsController(UserService userService, JobServices jobServices) {
        super(userService);
        this.jobServices = jobServices;
        
        jobs = this.jobServices.readAllJobsWithArea();
        Collections.sort(jobs, new Comparator<Job>(){

            @Override
            public int compare(Job o1, Job o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        
    }
    

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public ShowJobsController() {
        this(new UserService(), new JobServices());
    }
    
    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
    }
}
