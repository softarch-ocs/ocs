package controllers;

import data.entities.JobRequest;
import data.entities.User;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import services.UserService;
import services.exceptions.OcsServiceException;
import services.jobs.JobRequestService;
import services.jobs.UserJobRelationServices;

@ManagedBean
@ViewScoped
public class ReviewJobRequestController extends BaseController{

    private UserJobRelationServices userJobRelationServices;
    private JobRequestService jobRequestService;
    private JobRequest jobRequest;

    public ReviewJobRequestController(JobRequestService jobRequestService,
            UserJobRelationServices userJobRelationServices, UserService userService) {

        super(userService);
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
        this(new JobRequestService(), new UserJobRelationServices(), new UserService());
    }
    
    @PostConstruct
    public void initialize(){
        requireRole(User.Role.ADMIN);
    }

    public JobRequest getJobRequest() {
        return jobRequest;
    }

    public void setJobRequest(int jobRequestID) {
        this.jobRequest = jobRequestService.readJobRequest(jobRequestID);
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
