package controllers;

import data.entities.JobRequest;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import services.jobs.JobRequestService;
import services.jobs.UserJobRelationServices;

@ManagedBean
@ViewScoped
public class ShowJobRequestsController {

    private UserJobRelationServices userJobRelationServices;
    private JobRequestService jobRequestService;
    private List<JobRequest> jobRequests;

    public ShowJobRequestsController(JobRequestService jobRequestService,
            UserJobRelationServices userJobRelationServices) {

        if (jobRequestService == null) {
            throw new IllegalArgumentException("jobRequest");
        } else if (userJobRelationServices == null) {
            throw new IllegalArgumentException("userJobs");
        }

        this.jobRequestService = jobRequestService;
        this.userJobRelationServices = userJobRelationServices;

        this.jobRequests = jobRequestService.readAllJobRequest();
    }

    public ShowJobRequestsController() {
        this(new JobRequestService(), new UserJobRelationServices());
    }

    public List getJobRequests() {
        return jobRequests;
    }

    public void setJobRequests(List jobRequests) {
        this.jobRequests = jobRequests;
    }

}
