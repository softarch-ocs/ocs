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
import services.jobs.JobRequestService;
import services.jobs.JobServices;

@ManagedBean
@ViewScoped
public class PostulateJobController {

    private JobRequestService jobRequestService;
    private JobServices jobServices;
    private JobRequest jobRequest;
    private UserService userService;
    private User user;
    private List jobs;

    public PostulateJobController(JobRequestService jobRequestService,
            JobServices jobServices, UserService userService) {

        if (jobRequestService == null) {
            throw new IllegalArgumentException("jobRequest");
        } else if (jobServices == null) {
            throw new IllegalArgumentException("jobService");
        } else if (userService == null) {
            throw new IllegalArgumentException("userService");
        }

        this.jobs = jobServices.readAllJobs();
        this.jobRequestService = jobRequestService;
        this.jobServices = jobServices;
    }

    public PostulateJobController() {
        this(new JobRequestService(), new JobServices(), new UserService());
    }

    public String requestJob(PostulateJobBean postulateJobBean) {
        try {
            this.user = userService.getLoggedInUser();
            this.jobRequest.setUser(user);
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

    public void selectJob(PostulateJobBean postulateJobBean) {

        Job selectedJob = jobServices.readJobWithFeatures(postulateJobBean.getSelectedJob());
        postulateJobBean.setDescription(selectedJob.getDescription());
        postulateJobBean.setFeatures(selectedJob.getJobFeatures());

        System.out.println("Selected job is: " + selectedJob.getName());
    }

}
