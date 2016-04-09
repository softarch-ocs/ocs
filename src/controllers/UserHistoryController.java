package controllers;

import data.entities.User;
import data.entities.UsersJobs;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import presentation.beans.UserHistoryBean;
import services.UserService;
import services.jobs.UserJobRelationServices;

@ManagedBean
@RequestScoped
public class UserHistoryController extends BaseController {
    private UserJobRelationServices userJobService;
    
    @ManagedProperty("#{userHistoryBean}")
    private UserHistoryBean bean;

    public UserHistoryController(UserService userService, 
            UserJobRelationServices userJobService) {
        super(userService);
        
        if (userJobService == null) {
            throw new IllegalArgumentException("userJobService");
        }
        
        this.userJobService = userJobService;
    }
    
    public UserHistoryController() {
        this(new UserService(), new UserJobRelationServices());
    }
    
    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
        
        Integer userId = Integer.parseInt(FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("userId"));
        
        bean.setUser(userService.getFullUserById(userId));
        
        if (bean.getJobEntries() == null) {
            updateJobEntries();
        }
    }
    
    public UserHistoryBean getBean() {
        return bean;
    }

    public void setBean(UserHistoryBean bean) {
        this.bean = bean;
    }
    
    public List<UsersJobs> getJobEntries() {
        return bean.getJobEntries();
    }
    
    public User getUser() {
        return bean.getUser();
    }
    
    public String deleteJobEntry(int id) {
        userJobService.deleteUserJobRelation(userJobService.getUsersJobsById(id));
        updateJobEntries();
        
        return "";
    }
    
    private void updateJobEntries() {
        bean.setJobEntries(Collections.unmodifiableList(
                userJobService.getUsersJobsByUserId(getUser().getId())));
    }
}
