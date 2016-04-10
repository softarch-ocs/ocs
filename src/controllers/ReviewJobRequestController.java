package controllers;

import data.entities.JobRequest;
import data.entities.User;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import presentation.beans.ReviewJobRequestBean;
import services.UserService;
import services.exceptions.OcsServiceException;
import services.jobs.JobRequestService;

@ManagedBean
@RequestScoped
public class ReviewJobRequestController extends BaseController {

    private JobRequestService jobRequestService;

    @ManagedProperty("#{reviewJobRequestBean}")
    private ReviewJobRequestBean bean;

    public ReviewJobRequestController(JobRequestService jobRequestService,
            UserService userService) {

        super(userService);
        if (jobRequestService == null) {
            throw new IllegalArgumentException("jobRequest");
        }

        this.jobRequestService = jobRequestService;
    }

    public ReviewJobRequestController() {
        this(new JobRequestService(), new UserService());
    }

    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);

        if (bean.getJobRequest() == null) {
            bean.setJobRequest(new JobRequest());
        }
    }

    public ReviewJobRequestBean getBean() {
        return bean;
    }

    public void setBean(ReviewJobRequestBean bean) {
        this.bean = bean;
    }

    public JobRequest getJobRequest() {
        return bean.getJobRequest();
    }

    public void setJobRequest(int jobRequestID) {
        bean.setJobRequest(jobRequestService.readJobRequest(jobRequestID));
    }

    public String changeJobRequestStatus() {
        try {
            jobRequestService.changeStatusJobRequest(bean.getJobRequest());
            return "/requests/showRequests.xhtml";
        } catch (OcsServiceException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Oops, we had an error processing your request"));
            return "";
        }
    }

}
