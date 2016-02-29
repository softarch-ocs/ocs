package controllers;

import data.entities.JobRequest;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import services.exceptions.OcsServiceException;
import services.jobs.JobRequestService;
import services.jobs.UserJobRelationServices;

@ManagedBean
@ViewScoped
public class ReviewJobRequestController {

    private UserJobRelationServices userJobRelationServices;
    private JobRequestService jobRequestService;
    private JobRequest jobRequest;
    private Long id;

    public ReviewJobRequestController(JobRequestService jobRequestService,
            UserJobRelationServices userJobRelationServices) {

        if (jobRequestService == null) {
            throw new IllegalArgumentException("jobRequest");
        } else if (userJobRelationServices == null) {
            throw new IllegalArgumentException("userJobs");
        }

        this.jobRequestService = jobRequestService;
        this.userJobRelationServices = userJobRelationServices;

        this.jobRequest = new JobRequest();
    }

    public ReviewJobRequestController() {
        this(new JobRequestService(), new UserJobRelationServices());
    }

    public JobRequest getJobRequest() {
        return jobRequest;
    }

    public void setJobRequest(JobRequest jobRequest) {
        this.jobRequest = jobRequest;
    }

    public void setJobRequest(int jobRequestID) {
        this.jobRequest = jobRequestService.readJobRequest(jobRequestID);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String changeJobRequestStatus() {
        try {
            jobRequestService.changeStatusJobRequest(jobRequest);
            return "/requests/showRequests.xhtml";
        } catch (OcsServiceException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Oops, we had an error processing your request"));
            return "";
        }
    }

}
