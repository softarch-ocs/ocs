package controllers;

import data.entities.Job;
import data.entities.JobRequest;
import data.entities.User;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import presentation.beans.PostulateJobBean;
import services.UserService;
import services.exceptions.OcsServiceException;
import services.jobs.FeatureServices;
import services.jobs.JobRequestService;
import services.jobs.JobServices;

@ManagedBean
@ViewScoped
public class postulateJobController {

    private JobRequestService jobRequestService;
    private JobServices jobServices;
    private JobRequest jobRequest;
    private User user;
    private List jobs;

    public postulateJobController(JobRequestService jobRequestService, JobServices jobServices) {
        
        if (jobRequestService == null) {
            throw new IllegalArgumentException("jobRequest");
        }else if(jobServices == null){
            throw new IllegalArgumentException("jobService");
        }

        
        UserService userService = new UserService();
        user = userService.getLoggedInUser();
        
        this.jobRequest.setUser(user);
        this.jobs = jobServices.readAllJobs();
        this.jobRequestService = jobRequestService;
    }

    public postulateJobController() {
        this(new JobRequestService(), new JobServices());
    }

    public String requestJob(PostulateJobBean postulateJobBean) {
        try {
            jobRequest.setStatus(JobRequest.Status.ACTIVE);
            jobRequest.setJob(jobServices.readJob(postulateJobBean.getSelectedJob()));
            jobRequestService.createJobRequest(jobRequest);
            return "/index.xhtml";
        } catch (OcsServiceException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Oops, we had an error processing your request"));
            return "";
        }
    }
    
    public List<Job> getJobs() {
        return jobs;
    }
}
