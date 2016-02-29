package controllers;

import data.entities.Job;
import data.entities.User;
import data.entities.UsersJobs;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import services.jobs.JobServices;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import services.UserService;
import services.exceptions.OcsServiceException;
import services.jobs.UserJobRelationServices;

@ManagedBean
@ViewScoped
public class HandleUsersJobsController extends BaseController {
    private UsersJobs entity;
    private Integer id;
    private List<Job> jobs;
    private JobServices jobServices;
    private UserJobRelationServices usersJobsService;
    private User user;

    public User getUser() {
        return user;
    }

    public HandleUsersJobsController(JobServices jobServices, UserJobRelationServices usersJobsService,
            UserService userService) {
        super(userService);
        if (jobServices == null) {
            throw new IllegalArgumentException("jobServices");
        }
        if (usersJobsService == null) {
            throw new IllegalArgumentException("usersJobsService");
        }

        this.jobServices = jobServices;
        this.usersJobsService = usersJobsService;
        this.entity = new UsersJobs();
    }

    public HandleUsersJobsController() {
        this(new JobServices(), new UserJobRelationServices(), new UserService());
    }

    public Integer getId() {
        return id;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public Integer getJobId() {
        return entity.getJob() == null ? null : entity.getJob().getId();
    }

    public void setJobId(Integer jobId) {
        entity.setJob(jobServices.readJob(jobId));
    }

    public void initEdit(Integer id) {
        this.id = id;
        if (id != null) {
            entity = usersJobsService.getUsersJobsById(id);
            
            if (entity == null) {
                throw new IllegalArgumentException("UsersJobs with id " + id + " does not exist");
            }
        }
    }
    
    @PostConstruct
    public void initialize() {       
        Integer userId = Integer.parseInt(FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("userId"));
        
        jobs = Collections.unmodifiableList(jobServices.readAllJobs());
        user = userService.getFullUserById(userId);
        
        if (user == null) {
            throw new IllegalStateException("User not found");
        }
    }

    public boolean isEditing() {
        return id != null;
    }

    public UsersJobs getEntity() {
        return entity;
    }

    public void setEntity(UsersJobs entity) {
        this.entity = entity;
    }

    public String save() {
        if (entity.getJob() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Oops, we had an error processing your request"));

            return "";
        }
        
        entity.setUser(user);
        
        try {
            if (isEditing()) {
                usersJobsService.updateUserJobRelation(entity);
            } else {
                usersJobsService.createUserJobRelation(entity);
            }
            
            return "/usersJobs/userJobHistory.xhtml?faces-redirect=true&userId=" + user.getId();
        } catch (OcsServiceException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Oops, we had an error processing your request"));
        }
        
        return "";
    }
}
