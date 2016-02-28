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

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
    
    public ShowJobsController(UserService userService) {
        super(userService);
        
        JobServices jobServices = new JobServices();
        jobs = jobServices.readAllJobs();
    }

    public ShowJobsController() {
        this(new UserService());
    }
    
    @PostConstruct
    public void initialize() {
        requireRole(User.Role.USER);
    }
}
