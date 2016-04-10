package controllers;

import data.entities.JobRequest;
import data.entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import presentation.beans.ShowJobRequestsBean;
import services.UserService;
import services.jobs.JobRequestService;

@ManagedBean
@RequestScoped
public class ShowJobRequestsController extends BaseController{

    private JobRequestService jobRequestService;
    
    @ManagedProperty("#{showJobRequestsBean}")
    private ShowJobRequestsBean bean;
    
    
    public ShowJobRequestsController(JobRequestService jobRequestService,
            UserService userService) {
        
        super(userService);

        if (jobRequestService == null) {
            throw new IllegalArgumentException("jobRequest");
        }

        this.jobRequestService = jobRequestService;
    }

    public ShowJobRequestsController() {
        this(new JobRequestService(), new UserService());
    }
    
    @PostConstruct
    public void initialize(){
        requireRole(User.Role.ADMIN);
        bean.setJobRequests(jobRequestService.readAllJobRequest());
    }

    public ShowJobRequestsBean getBean() {
        return bean;
    }

    public void setBean(ShowJobRequestsBean bean) {
        this.bean = bean;
    }

    
    public List getJobRequests() {
        return bean.getJobRequests();
    }

    public void setJobRequests(List jobRequests) {
        bean.setJobRequests(jobRequests);
    }
    
    public List<JobRequest> showActiveJobRequest(){
        List<JobRequest> active = new ArrayList<>();
        
        for(JobRequest jb : bean.getJobRequests()){
            if(jb.getStatus() == JobRequest.Status.ACTIVE) active.add(jb);
        }
        
        return active;
    }
    
    public List<JobRequest> showResolvedJobRequest(){
        List<JobRequest> resolved = new ArrayList<>();
        
        for(JobRequest jb : bean.getJobRequests()){
            if(jb.getStatus() != JobRequest.Status.ACTIVE) resolved.add(jb);
        }
        
        return resolved;
    }

}
