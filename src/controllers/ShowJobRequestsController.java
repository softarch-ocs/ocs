package controllers;

import data.entities.JobRequest;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import services.jobs.JobRequestService;

@ManagedBean
@ViewScoped
public class ShowJobRequestsController {

    private JobRequestService jobRequestService;
    private List<JobRequest> jobRequests;

    public ShowJobRequestsController(JobRequestService jobRequestService) {

        if (jobRequestService == null) {
            throw new IllegalArgumentException("jobRequest");
        }

        this.jobRequestService = jobRequestService;
        this.jobRequests = jobRequestService.readAllJobRequest();
    }

    public ShowJobRequestsController() {
        this(new JobRequestService());
    }

    public List getJobRequests() {
        return jobRequests;
    }

    public void setJobRequests(List jobRequests) {
        this.jobRequests = jobRequests;
    }

}
