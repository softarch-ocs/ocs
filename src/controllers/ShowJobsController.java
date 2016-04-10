package controllers;

import data.entities.Job;
import data.entities.User;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import presentation.beans.ShowJobsBean;
import services.UserService;
import services.jobs.JobServices;

@ManagedBean
@RequestScoped
public class ShowJobsController extends BaseController {
    
    private JobServices jobServices;
    
    @ManagedProperty("#{showJobsBean}")
    private ShowJobsBean bean;
    
    public ShowJobsController(UserService userService, JobServices jobServices) {
        super(userService);
        this.jobServices = jobServices;       
    }

    public JobServices getJobServices() {
        return jobServices;
    }

    public void setJobServices(JobServices jobServices) {
        this.jobServices = jobServices;
    }

    public ShowJobsBean getBean() {
        return bean;
    }

    public void setBean(ShowJobsBean bean) {
        this.bean = bean;
    }
    
    public ShowJobsController() {
        this(new UserService(), new JobServices());
    }
    
    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
        bean.setJobs( this.jobServices.readAllJobsWithArea() );
        Collections.sort(bean.getJobs(), new Comparator<Job>(){

            @Override
            public int compare(Job o1, Job o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }
}
