package controllers;

import data.entities.Job;
import data.entities.User;

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
        
        jobs = jobServices.readAllJobsWithArea();
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
