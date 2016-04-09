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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import presentation.beans.HandleUsersJobsBean;
import services.UserService;
import services.exceptions.OcsServiceException;
import services.jobs.UserJobRelationServices;

@ManagedBean
@RequestScoped
public class HandleUsersJobsController extends BaseController {
    private JobServices jobServices;
    private UserJobRelationServices usersJobsService;
    
    @ManagedProperty("#{handleUsersJobsBean}")
    private HandleUsersJobsBean bean;

    public HandleUsersJobsBean getBean() {
        return bean;
    }

    public void setBean(HandleUsersJobsBean bean) {
        this.bean = bean;
    }

    public User getUser() {
        return bean.getUser();
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
    }

    public HandleUsersJobsController() {
        this(new JobServices(), new UserJobRelationServices(), new UserService());
    }

    public Integer getId() {
        return bean.getId();
    }

    public List<Job> getJobs() {
        return bean.getJobs();
    }

    public Integer getJobId() {
        return bean.getEntity().getJob() == null ? null : bean.getEntity().getJob().getId();
    }

    public void setJobId(Integer jobId) {
        bean.getEntity().setJob(jobServices.readJob(jobId));
    }

    public void initEdit(Integer id) {
        bean.setId(id);
        if (id != null) {
            bean.setEntity(usersJobsService.getUsersJobsById(id));
            
            if (bean.getEntity() == null) {
                throw new IllegalArgumentException("UsersJobs with id " + id + " does not exist");
            }
        }
    }
    
    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
        
        if (bean.getJobs() == null) {
            bean.setJobs(Collections.unmodifiableList(jobServices.readAllJobs()));
        }
        
        if (bean.getUser() == null) {
            Integer userId = Integer.parseInt(FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("userId"));
            bean.setUser(userService.getFullUserById(userId));
            
            if (bean.getUser() == null) {
                throw new IllegalStateException("User not found");
            }
        }
    }

    public boolean isEditing() {
        return bean.getId() != null;
    }

    public UsersJobs getEntity() {
        return bean.getEntity();
    }

    public String save() {
        UsersJobs entity = getEntity();
        
        if (entity.getJob() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Oops, we had an error processing your request"));

            return "";
        }
        
        entity.setUser(getUser());
        
        try {
            if (isEditing()) {
                usersJobsService.updateUserJobRelation(entity);
            } else {
                usersJobsService.createUserJobRelation(entity);
            }
            
            return "/usersJobs/userJobHistory.xhtml?faces-redirect=true&userId=" + getUser().getId();
        } catch (OcsServiceException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Oops, we had an error processing your request"));
        }
        
        return "";
    }
}
