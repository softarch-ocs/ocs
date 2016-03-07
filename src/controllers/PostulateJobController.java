package controllers;

import data.entities.Job;
import data.entities.JobRequest;
import data.entities.User;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import presentation.beans.PostulateJobBean;
import services.UserService;
import services.exceptions.OcsPersistenceException;
import services.exceptions.OcsValidationException.ValidationItem;
import services.exceptions.OcsValidationException;
import services.jobs.JobRequestService;
import services.jobs.JobServices;

@ManagedBean
@ViewScoped
public class PostulateJobController extends BaseController{

    private JobRequestService jobRequestService;
    private JobServices jobServices;
    private JobRequest jobRequest;
    private User user;
    private List jobs;

    public PostulateJobController(JobRequestService jobRequestService,
            JobServices jobServices, UserService userService) {
        
        super(userService);
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
        this.jobRequest = new JobRequest();
        this.user = userService.getLoggedInUser();
    }

    public PostulateJobController() {
        this(new JobRequestService(), new JobServices(), new UserService());
    }
    
    @PostConstruct
    public void initialize(){
        requireRole(User.Role.USER);
    }

    public String requestJob(PostulateJobBean postulateJobBean) {
        try {

            this.jobRequest.setUser(user);
            jobRequest.setStatus(JobRequest.Status.ACTIVE);
            jobRequest.setJob(jobServices.readJob(postulateJobBean.getSelectedJob()));

            jobRequestService.checkAvailability(jobRequest, postulateJobBean.getFeatures());
                        
            jobRequestService.createJobRequest(jobRequest);
            return "/index.xhtml?faces-redirect=true";
        } catch (OcsPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Oops, we had an error processing your request"));
            return "";
        } catch (OcsValidationException ex) {
            for (ValidationItem val : ex.getValidationItems()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(val.getMessage()));
            }
            return "";
        }
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void selectJob(PostulateJobBean postulateJobBean) {

        Job selectedJob = jobServices.readJobWithFeatures(postulateJobBean.getSelectedJob());
        postulateJobBean.setDescription(selectedJob.getDescription());
        postulateJobBean.setFeatures(jobRequestService.checkJobRequirements(user, selectedJob));
        
        System.out.println("Selected job is: " + selectedJob.getName());
    }

}
