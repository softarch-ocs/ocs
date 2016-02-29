package controllers;

import data.entities.JobRequest;
import data.entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import services.UserService;
import services.jobs.JobRequestService;

@ManagedBean
@ViewScoped
public class ShowJobRequestsController extends BaseController{

    private JobRequestService jobRequestService;
    private List<JobRequest> jobRequests;

    public ShowJobRequestsController(JobRequestService jobRequestService,
            UserService userService) {
        
        super(userService);

        if (jobRequestService == null) {
            throw new IllegalArgumentException("jobRequest");
        }

        this.jobRequestService = jobRequestService;
        this.jobRequests = jobRequestService.readAllJobRequest();
    }

    public ShowJobRequestsController() {
        this(new JobRequestService(), new UserService());
    }
    
    @PostConstruct
    public void initialize(){
        requireRole(User.Role.ADMIN);
    }

    public List getJobRequests() {
        return jobRequests;
    }

    public void setJobRequests(List jobRequests) {
        this.jobRequests = jobRequests;
    }
    
    public List<JobRequest> showActiveJobRequest(){
        List<JobRequest> active = new ArrayList<>();
        
        for(JobRequest jb : jobRequests){
            if(jb.getStatus() == JobRequest.Status.ACTIVE) active.add(jb);
        }
        
        return active;
    }
    
    public List<JobRequest> showResolvedJobRequest(){
        List<JobRequest> resolved = new ArrayList<>();
        
        for(JobRequest jb : jobRequests){
            if(jb.getStatus() != JobRequest.Status.ACTIVE) resolved.add(jb);
        }
        
        return resolved;
    }

}
